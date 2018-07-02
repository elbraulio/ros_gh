package launcher;

import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import github.*;
import iterator.Colaborators;
import tools.CanRequest;
import tools.FromJsonFile;
import tools.SqliteConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class Launcher {

    public static void main(String[] args) throws ClassNotFoundException,
            SQLException, InterruptedException, IOException {
        final String token = "381a360d9fe7a2ed6d45e2a593375132d91e6717";
        final String file = args[0];
        final String dbPath = args[1];
        fetchGithubData(file, token, dbPath);
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
                        // save ghUser and link to repo
                        final int contributorId =
                                new InsertGhUserIfNotExists(
                                        ghColaborator.login(), github,
                                        canRequest).execute(connection, -1);
                        new GhUserCommitRepo(
                                contributorId, ghColaborator.commits(), repoId
                        ).execute(connection, -1);
                    }
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
