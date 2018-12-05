package examples.devrec;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class DefaultTaggedItem implements TaggedItem {
    private final int projectId;
    private final List<Integer> tagIds;

    public DefaultTaggedItem(int projectId, List<Integer> tagIds) {

        this.projectId = projectId;
        this.tagIds = tagIds;
    }

    @Override
    public int projectId() {
        return this.projectId;
    }

    @Override
    public List<Integer> tags() {
        return this.tagIds;
    }
}
