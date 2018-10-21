package org.elbraulio.rosgh.github;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface GhRepo {
    String name();

    String fullName();

    String description();

    String owner();

    int dbId();
}
