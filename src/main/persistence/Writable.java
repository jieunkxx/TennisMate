package persistence;

import org.json.JSONObject;

/*
This code is referred to the JSonSerializationDemo example
*/

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}


