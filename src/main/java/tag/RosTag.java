package tag;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RosTag implements Tag {
    private final String name;

    public RosTag(final String name) {

        this.name = name;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Tag){
            final Tag tag = (Tag) o;
            return tag.name().equals(this.name());
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return this.name();
    }
}
