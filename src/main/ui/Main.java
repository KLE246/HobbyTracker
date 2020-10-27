package ui;

import java.io.FileNotFoundException;
    // try and catch taken from Main of JsonSerializationDemo

    // EFFECTS: runs HobbyTracker
public class Main {
    public static void main(String[] args) {
        try {
            new HobbyTracker();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}

