package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// The hobbyList is what the program looks through at the first level
// Hobbies are added to it and can be searched for by index or Hobby name
public class HobbyList implements Writable {
    private final LinkedList<Hobby> hobbyList;
    private String name;

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: creates an empty hobbyList
    public HobbyList() {
        hobbyList = new LinkedList<>();
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: gives name to the hobby list
    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return name of hobbyList
    public String getName() {
        return name;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds a hobby into the list
    public void addHobby(Hobby hobby) {
        hobbyList.add(hobby);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds a hobby into the list given a string
    public void addHobby(String name) {
        Hobby hobby = new Hobby(name);
        hobbyList.add(hobby);
    }

    // EFFECTS: returns an unmodifiable list of hobbies in this hobby list
    public List<Hobby> getHobbyList() {
        return Collections.unmodifiableList(hobbyList);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns size of the hobby list
    public int length() {
        return hobbyList.size();
    }

    // REQUIRES: i is not an index greater than what the list contains,
    //           can't be called on an empty hobbyList
    // MODIFIES:
    // EFFECTS: returns the hobby at given index
    public Hobby getByIndex(int i) {
        return hobbyList.get(i);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: gets index of given hobby, returns -1 if can't be found
    public int getByName(String toFind) {
        toFind = toFind.substring(0, 1).toUpperCase() + toFind.substring(1);
        int index = -1;
        for (int i = 0; i < hobbyList.size(); i++) {
            Hobby hobby = hobbyList.get(i);
            String name = hobby.getName();
            if (name.equals(toFind)) {
                index = i;
            }
        }
        return index;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns list of all hobby names
    public LinkedList<String> giveAllHobbyNames() {
        LinkedList<String> names = new LinkedList<>();
        for (Hobby hobby : hobbyList) {
            names.add(hobby.getName());
        }
        return names;
    }

    //JSON methods taken from WorkRoom class of JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("Hobbies", hobbiesToJson());
        return json;
    }

    // EFFECTS: returns hobbies in this hobbyList as a JSON array
    private JSONArray hobbiesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Hobby hobby : hobbyList) {
            jsonArray.put(hobby.toJson());
        }
        return jsonArray;
    }
}
