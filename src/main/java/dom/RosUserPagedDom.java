package dom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Represent the paged ROS users list.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 * @see <a href="https://answers.ros.org/users/">ROS users</a>
 */
public final class RosUserPagedDom implements PagedDom {

    /**
     * ROS users list page sorted by user's creation date.
     */
    private final String webUrl =
            "https://answers.ros.org/users/?sort=last&page=";

    @Override
    public Document page(final int index) throws IOException {
        return Jsoup.connect(this.webUrl + index).get();
    }
}
