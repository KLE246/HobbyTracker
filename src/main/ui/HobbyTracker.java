package ui;

import model.DatedHour;
import model.Hobby;
import model.HobbyList;

import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class HobbyTracker {
    public HobbyList hobbyList;
    private Scanner input;

    public HobbyTracker() {
        runTracker();
    }

    private void runTracker() {
        boolean keepGoing = true;
        String command = null;

        initializeHobbies();

        while (keepGoing) {
            prompts();
            command = input.next();

            if (command.equals("done")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    private void processCommand(String command) {
        if (command.equals("new")) {
            addHobby();
        } else if (command.equals("all")) {
            seeAllHobbiesAndHours();
        } else if (command.equals("add")) {
            addProgress();
        } else {
            System.out.println("Try again");
        }
    }


    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: creates initial empty list of hobbies
    private void initializeHobbies() {
        hobbyList = new HobbyList();
        input = new Scanner(System.in);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds a hobby into the list
    public void addHobby() {
        System.out.println("Name the hobby or skill");
        Scanner answer = new Scanner(System.in);
        String name = answer.nextLine();
        Hobby hobby = new Hobby(name);
        hobbyList.add(hobby);

    }

    public void prompts() {
        System.out.println("To add a hobby - \"new\"");
        System.out.println("To see all current hobbies and their hours - \"all\"");
        System.out.println("To add hours to a hobby - \"add\"");
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: display a log of all milestones from selected hobby
    public void getLog(Hobby hobby) {
        System.out.println();
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: display a plot with the hours increases throughout a time period
    public void visualizeProgress(Hobby hobby) {
        // stub
    }

    // REQUIRES: positive hours added
    // MODIFIES: this
    // EFFECTS: adds hours to hobby and remembers when it was added
    public void addProgress() {
        System.out.println("Which hobby have you progressed in?");
        Scanner answer = new Scanner(System.in);
        String name = answer.nextLine();
        int index = hobbyList.getByName(name);

        while (index == -1) {
            System.out.println("That is not a hobby you are working on, try again");
            answer = new Scanner(System.in);
            name = answer.nextLine();
            index = hobbyList.getByName(name);
        }

        System.out.println("How many hours have you progressed?");
        Scanner timeGiven = new Scanner(System.in);
        String timeStr = answer.nextLine();
        int time = Integer.parseInt(timeStr);

        Hobby hobby = hobbyList.getByIndex(index);
        hobby.addTime(time);

        LinkedList<DatedHour> progress = hobby.progressList;
        DatedHour lastEntry = progress.get(0);
        System.out.println(lastEntry.getDatedHourString() + " has been added to " + hobby.getName() + " progress");
    }


    // REQUIRES:
    // MODIFIES:
    // EFFECTS: shows all hobbies and progress in each
    public void seeAllHobbiesAndHours() {
        for (int i = 0; i < hobbyList.length(); i++) {
            Hobby hobby = hobbyList.getByIndex(i);
            System.out.println(hobby.getName() + " - Total Hours:" + hobby.totalProgress);
        }
    }
}
