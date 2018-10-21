package org.elbraulio.rosgh.github;

import org.elbraulio.rosgh.tools.CanRequest;
import org.elbraulio.rosgh.tools.SqlCommand;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertRepoIfNotExists implements SqlCommand {
    private final GhRepo ghRepo;
    private final String source;
    private final int ownerId;
    private final CanRequest canRequest;

    public InsertRepoIfNotExists(GhRepo ghRepo, String source, int ownerId, CanRequest canRequest) {
        this.ghRepo = ghRepo;
        this.source = source;
        this.ownerId = ownerId;
        this.canRequest = canRequest;
    }

    public int execute(Connection connection, int defaultValue) throws
            InterruptedException, SQLException {

        if(new GhRepoExists(ghRepo.name()).query(connection)){
            return new GhRepoByName(ghRepo.name()).query(connection).dbId();
        } else {
            this.canRequest.waitForRate();
            return new InsertRepo(
                    ghRepo,
                    source, ownerId
            ).execute(connection, defaultValue);
        }
    }
}
