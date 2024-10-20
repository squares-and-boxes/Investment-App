package persistence;

import org.json.JSONObject;

// Credits to example JsonSerialization demo
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
