import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import github.*;
import iterator.Colaborators;
import org.junit.Ignore;
import org.junit.Test;
import resources.SqliteConnection;
import tools.CanRequest;
import tools.FromJsonFile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class GithubInfoTest {

    @Ignore
    @Test
    public void fetchGithubData()
            throws IOException, SQLException, ClassNotFoundException,
            InterruptedException {
        final String token = "62d4bef9321253abf1a4525f97eb6744bd3b1b24";
        final String path = "src/test/java/resources/github/distribution.json";
        final Github github = new RtGithub(token);
        final CanRequest canRequest = new CanRequest(60);
        try (final Connection connection = new SqliteConnection(
                "src/test/java/resources/sqlite/test.db"
        ).connection()
        ) {
            for (RosPackage rosPackage : new FromJsonFile(path).repoList()) {
                if (rosPackage.source().isEmpty()) {
                    printSourceNotFound(rosPackage);
                } else {
                    printProcessState(rosPackage);
                    JsonRepo jsonRepo = rosPackage.asRepo(github);
                    canRequest.waitForRate();
                    // save owner if not exists
                    final int ownerId =
                            new InsertGhUserIfNotExists(
                                    jsonRepo.owner(), github, canRequest
                            ).execute(connection, -1);
                    // save repo
                    final int repoId = new InsertRepo(
                            jsonRepo,
                            rosPackage.source(), ownerId
                    ).execute(connection, -1);
                    // save all contributors
                    for (
                            GhColaborator ghColaborator : new Colaborators(
                            jsonRepo.fullName(), canRequest, github
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

    private void printProcessState(RosPackage rosPackage) {
        System.out.println(
                "Processing " + rosPackage.name() + " from " +
                        rosPackage.source()
        );
    }

    private void printSourceNotFound(RosPackage rosPackage) {
        System.out.println("SOURCE NOT FOUND: " + rosPackage.name());
    }
}
