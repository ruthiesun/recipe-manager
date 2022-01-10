package persistence;

import org.json.JSONObject;

/* Interface for classes that can be written to a JSON object
 * This class references code from JsonSerializationDemo
 * Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

