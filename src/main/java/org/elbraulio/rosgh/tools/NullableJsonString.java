package org.elbraulio.rosgh.tools;

import javax.json.JsonObject;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class NullableJsonString {
    private final JsonObject jsonObject;
    private final String field;

    public NullableJsonString(JsonObject jsonObject, String field) {
        this.jsonObject = jsonObject;
        this.field = field;
    }

    public String string() {
        if (this.jsonObject.isNull(this.field))
            return "";
        else
            return this.jsonObject.getString(this.field);
    }
}
