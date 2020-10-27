package persistence;

import org.json.JSONObject;

// framework for persistence package classes taken from JsonSerializationDemo
// https://github.com/stleary/JSON-java.git


// interface with function that will be implemented in other classes to turn given hobbyList
// into JSON object

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
