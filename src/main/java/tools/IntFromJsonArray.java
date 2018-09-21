package tools;

import javax.json.JsonArray;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class IntFromJsonArray {
    private final JsonArray array;

    public IntFromJsonArray(JsonArray array) {
        this.array = array;
    }

    public List<Integer> list() {
        final List<Integer> result = new LinkedList<>();
        this.array.forEach(
                value -> result.add(
                        Integer.parseInt(
                                value.toString()
                                        .replace("\"", "")
                        )
                )
        );
        return result;
    }
}
