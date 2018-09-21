package iterator;

import com.jcabi.github.Github;
import github.RosPackage;

import java.util.Iterator;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class IterateRosPackage implements Iterator<RosPackage> {
    private final List<RosPackage> rosPackages;
    private final Github github;
    private int next;

    // TODO: 21-09-18 why does this exist? is just a collection.
    public IterateRosPackage(
            final List<RosPackage> rosPackages, final Github github
    ) {
        this.rosPackages = rosPackages;
        this.github = github;
        this.next = 0;
    }

    @Override
    public boolean hasNext() {
        return this.next < this.rosPackages.size();
    }

    @Override
    public RosPackage next() {
        return this.rosPackages.get(this.next);
    }
}
