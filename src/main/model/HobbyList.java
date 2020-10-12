package model;

import java.util.LinkedList;

public class HobbyList {
    public LinkedList<Hobby> hobbyList;

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

    // REQUIRES: hobbyList is not empty
    // MODIFIES: this
    // EFFECTS: gets hobby at given index
    public Hobby getByIndex(int i) {
        return hobbyList.get(i);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: gets index of given hobby
    public int getByName(String toFind) {
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
}
