package org.elbraulio.rosgh.tag;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class WithCount implements TagWithCount {
    private final Tag originTag;
    private final int count;

    public WithCount(final String name, final int count) {
        this(new RosTag(name), count);
    }

    public WithCount(final Tag originTag, final int count) {

        this.originTag = originTag;
        this.count = count;
    }

    @Override
    public int count() {
        return this.count;
    }

    @Override
    public String name() {
        return this.originTag.name();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TagWithCount) {
            final TagWithCount tag = (TagWithCount) o;
            return tag.name().equals(this.name()) &&
                    tag.count() == this.count();
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return this.name() + " x " + this.count();
    }
}
