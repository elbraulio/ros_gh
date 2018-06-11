package github;

import com.jcabi.github.Github;
import tools.CanRequest;
import tools.SqlCommand;

import java.io.IOException;
import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhUserCommitRepo implements SqlCommand {

    private final int contributorId;
    private final int commits;
    private final int repoId;

    public GhUserCommitRepo(int contributorId, int commits, int repoId) {

        this.contributorId = contributorId;
        this.commits = commits;
        this.repoId = repoId;
    }

    @Override
    public int execute(Connection connection, int defaultValue)
            throws SQLException, IOException {
        String user = "INSERT INTO gh_commits(" +
                "gh_user_id," +
                "gh_repo_id," +
                "commits) VALUES(?,?,?)";
        try (PreparedStatement pstmt =
                     connection.prepareStatement(
                             user, Statement.RETURN_GENERATED_KEYS
                     )
        ) {
            pstmt.setInt(1, this.contributorId);
            pstmt.setInt(2, this.repoId);
            pstmt.setInt(3, this.commits);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return defaultValue;
    }
}
