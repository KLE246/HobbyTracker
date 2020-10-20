package model;

import java.util.LinkedList;

// The hobbyList is what the program looks through at the first level
// Hobbies are added to it and can be searched for by index or Hobby name
public class HobbyList {
    public LinkedList<Hobby> hobbyList;

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: creates an empty hobbyList
    public HobbyList() {
        hobbyList = new LinkedList<>();
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds a hobby into the list
    public void add(Hobby hobby) {
        hobbyList.add(hobby);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: gives size of the hobby list
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
    public LinkedList<String> giveAllHobbies() {
        LinkedList<String> names = new LinkedList<>();
        for (Hobby hobby : hobbyList) {
            names.add(hobby.getName());
        }
        return names;
    }
}
