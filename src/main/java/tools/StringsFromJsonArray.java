package tools;

import javax.json.JsonArray;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class StringsFromJsonArray {
    private final JsonArray array;

    public StringsFromJsonArray(JsonArray array) {
        this.array = array;
    }

    public List<String> list() {
        final List<String> result = new LinkedList<>();
        this.array.forEach(
                value -> result.add(
                        value.toString().replace("\"", "")
                )
        );
        return result;
    }
}
