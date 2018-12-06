package org.elbraulio.rosgh.tag;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RosTag implements Tag {
    private final String name;
    private final int id;

    public RosTag(final String name, int id) {

        this.name = name;
        this.id = id;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Integer id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tag) {
            final Tag tag = (Tag) o;
            return tag.name().equals(this.name());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.name();
    }
}
