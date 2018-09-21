package github;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultGhRepo implements GhRepo {
    private final int id;
    private final String name;
    private final String fullName;
    private final int ownerId;
    private final String description;
    private final String source;

    public DefaultGhRepo(int id, String name, String fullName, int ownerId,
                         String description, String source) {

        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.ownerId = ownerId;
        this.description = description;
        this.source = source;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String fullName() {
        return this.fullName;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public String owner() {
        return this.ownerId + "";
    }

    @Override
    public int dbId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof DefaultGhRepo){
            return ((DefaultGhRepo) o).id == this.id;
        } else {
            return false;
        }
    }
}
