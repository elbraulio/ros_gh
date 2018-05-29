package github;

import com.jcabi.github.Github;
import tools.CanRequest;
import tools.SqlCommand;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertGhUserIfNotExists implements SqlCommand {
    private final Github github;
    private final CanRequest canRequest;
    private final String owner;

    public InsertGhUserIfNotExists(
            String owner, Github github, CanRequest canRequest
    ) {
        this.owner = owner;
        this.github = github;
        this.canRequest = canRequest;
    }

    @Override
    public int execute(Connection connection, int defaultValue)
            throws SQLException, IOException, InterruptedException {
        if (new GhUserExists(this.owner).query(connection)) {
            return new GhUserByLogin(this.owner).query(connection)
                    .dbId();
        } else {
            canRequest.waitForRate();
            return new InsertGhUser(
                    new FetchGhUser(
                            this.github, this.owner
                    ).ghUser()
            ).execute(connection, -1);
        }
    }
}
