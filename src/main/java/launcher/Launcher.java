package launcher;

import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import dom.RosUserPagedDom;
import github.*;
import iterator.Colaborators;
import iterator.IterateByUserLinks;
import iterator.IterateDomPages;
import iterator.IteratePagedContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import tools.*;
import user.RosDomUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class Launcher {

    public static void main(String[] args) throws ClassNotFoundException,
            SQLException, InterruptedException, IOException {
        final String token = "381a360d9fe7a2ed6d45e2a593375132d91e6717";
        switch (args[0]){
            case "gh":
                fetchGithubData(args[1], token, args[2]);
                break;
            case "rs":
                fetchRosAnswersUsers(args[1]);
                break;
            default:
                System.out.println("Usage: ...");
        }
    }

    private static void fetchRosAnswersUsers(String dbPath)
            throws SQLException, ClassNotFoundException, IOException {
        final int initialPage = 1;
        final String root = "https://answers.ros.org";
        final Document usersPage = Jsoup.connect(root + "/users/").get();
        final Iterator<String> usersLinks = new IteratePagedContent<>(
                new IterateDomPages(
                        new RosUserPagedDom(),
                        initialPage,
                        new LastRosUserPage(usersPage).value(),
                        new IterateByUserLinks()
                )
        );
        try (
                final Connection connection = new SqliteConnection(
                        dbPath
                ).connection()
        ) {
            int count = 1;
            while (usersLinks.hasNext()) {
                final String next = usersLinks.next();
                System.out.println(
                        "Processing user " + count++ + " ->" + next
                );
                new SaveIntoSqliteDb(
                        new RosDomUser(Jsoup.connect(root + next).get()),
                        connection
                ).execute();
            }
        }
    }


    private static void fetchGithubData(String path, String token, String dbPath)
            throws IOException, SQLException, ClassNotFoundException,
            InterruptedException {
        final Github github = new RtGithub(token);
        final CanRequest canRequest = new CanRequest(60);
        try (final Connection connection = new SqliteConnection(
                dbPath
        ).connection()
        ) {
            for (RosPackage rosPackage : new FromJsonFile(path).repoList()) {
                try {
                    if (rosPackage.source().isEmpty()) {
                        printSourceNotFound(rosPackage);
                    } else {
                        printProcessState(rosPackage);
                        GhRepo ghRepo = rosPackage.asRepo(github);
                        canRequest.waitForRate();
                        // save owner if not exists
                        final int ownerId =
                                new InsertGhUserIfNotExists(
                                        ghRepo.owner(), github, canRequest
                                ).execute(connection, -1);
                        // save repo
                        final int repoId = new InsertRepoIfNotExists(
                                ghRepo,
                                rosPackage.source(), ownerId, canRequest
                        ).execute(connection, -1);
                        // save all contributors
                        for (
                                GhColaborator ghColaborator : new Colaborators(
                                ghRepo.fullName(), canRequest, github
                        ).colaboratorList()
                                ) {
                            // save ghUser and url to repo
                            final int contributorId =
                                    new InsertGhUserIfNotExists(
                                            ghColaborator.login(), github,
                                            canRequest).execute(connection, -1);
                            new GhUserCommitRepo(
                                    contributorId, ghColaborator.commits(), repoId
                            ).execute(connection, -1);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    private static void printProcessState(RosPackage rosPackage) {
        System.out.println(
                "Processing " + rosPackage.name() + " from " +
                        rosPackage.source()
        );
    }

    private static void printSourceNotFound(RosPackage rosPackage) {
        System.out.println("SOURCE NOT FOUND: " + rosPackage.name());
    }
}
