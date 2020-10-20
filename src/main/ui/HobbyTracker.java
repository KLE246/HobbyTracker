package ui;

import model.DatedHour;
import model.Hobby;
import model.HobbyList;
import model.Milestone;

import java.util.LinkedList;
import java.util.Scanner;
// The HobbyTracker keeps track of user inputs and prompts them depending
// one what information needs to be submitted.

public class HobbyTracker {
    private HobbyList hobbyList;
    private Scanner input;

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: starts runTracker
    public HobbyTracker() {
        runTracker();
    }

    // original frame work from TellerApp, runTeller, processCommand, init, and
    // displayMenu in the AccountNotRobust program

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: sees if the tracker should still be running and runs the app if not
    //          exited from yet
    private void runTracker() {
        boolean keepGoing = true;
        String command;

        initializeHobbies();

        while (keepGoing) {
            prompts();
            command = input.next();

            if (command.equals("exit")) {
                keepGoing = false;
                System.out.println("Looking forward to more progress!");
            } else {
                processCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("new")) {
            addHobby();
        } else if (command.equals("all")) {
            seeAllHobbiesAndHours();
        } else if (command.equals("log")) {
            displayLog();
        } else if (command.equals("add")) {
            addProgress();
        } else if (command.equals("event")) {
            addMilestone();
        } else if (command.equals("ms")) {
            getMilestoneLog();
        } else {
            System.out.println("Try again");
        }
    }

    // EFFECTS: displays all possible options for user
    public void prompts() {
        if (hobbyList.length() == 0) {
            System.out.println("To add a hobby - \"new\"");
            System.out.println("To exit Tracker - \"exit\"");
        } else {
            System.out.println("To add a hobby - \"new\"");
            System.out.println("To see all current hobbies and their hours - \"all\"");
            System.out.println("To see progression in a hobby - \"log\"");
            System.out.println("To add hours to a hobby - \"add\"");
            System.out.println("To see a log of milestones in a hobby - \"ms\"");
            System.out.println("To add a milestone to a hobby - \"event\"");
            System.out.println("To exit Tracker - \"exit\"");
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

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds a milestone to remember and display recent milestones
    private void addMilestone() {
        Hobby hobby = selectHobby("addMs");
        System.out.println("What big milestone just happened?");
        Scanner title = new Scanner(System.in);
        String milestoneTitle = title.nextLine();

        System.out.println("Add some details to this milestone");
        Scanner description = new Scanner(System.in);
        String milestoneDescription = description.nextLine();

        Milestone milestone = new Milestone(milestoneTitle);
        milestone.setTime(hobby.getTotalProgress());
        milestone.setDescription(milestoneDescription);

        LinkedList<Milestone> milestones = hobby.getMilestoneList();
        hobby.addMilestone(milestone);

        DatedHour lastEntry = milestones.get(milestones.size() - 1).savedTime;
        System.out.println("The milestone \"" + milestoneTitle + "\"" + " has been added to " + hobby.getName()
                + " log at " + lastEntry.getProgressHour() + " hours on " + lastEntry.getDate());
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: display a log of all milestones from selected hobby
    public void getMilestoneLog() {
        Hobby hobby = selectHobby("msLog");
        for (String logEntry : hobby.getMilestoneLog()) {
            System.out.println(logEntry);
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: display a log of times when hours were added to a hobby
    public void displayLog() {
        Hobby hobby = selectHobby("progressLog");
        for (String logEntry : hobby.getLog()) {
            System.out.println(logEntry);
        }
    }

    // REQUIRES: positive hours added
    // MODIFIES: this
    // EFFECTS: adds hours to hobby and remembers when it was added
    public void addProgress() {
        Hobby hobby = selectHobby("addProgress");
        System.out.println("How many hours have you progressed?");
        Scanner timeGiven = new Scanner(System.in);
        int time = Integer.parseInt(timeGiven.nextLine());

        hobby.addTime(time);

        LinkedList<DatedHour> progress = hobby.getProgressList();
        DatedHour lastEntry = progress.get(progress.size() - 1);
        System.out.println(time + " hours added; Current progress of " + lastEntry.getProgressHour()
                + " hours" + " updated to " + hobby.getName() + " progress at " + lastEntry.getDate());
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: shows all hobbies and progress in each
    public void seeAllHobbiesAndHours() {
        if (hobbyList.length() == 0) {
            System.out.println("You have no current hobbies");
        } else {
            for (int i = 0; i < hobbyList.length(); i++) {
                Hobby hobby = hobbyList.getByIndex(i);
                System.out.println(hobby.getName() + " - Total Hours:" + hobby.getTotalProgress());
            }
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: gives the index of the hobby from the user input
    public Hobby selectHobby(String type) {
        String phrase = null;
        if (type.equals("addMs")) {
            phrase = "Which hobby have you reached a milestone in?";
        } else if (type.equals("msLog")) {
            phrase = "Which hobby do you want to show milestones of?";
        } else if (type.equals("progressLog")) {
            phrase = "Which hobby do you want to show progress of?";
        } else if (type.equals("addProgress")) {
            phrase = "Which hobby have you progressed in?";
        }
        for (String name : hobbyList.giveAllHobbies()) {
            System.out.println(name);
        }
        System.out.println(phrase);
        int index = -1;
        while (index == -1) {
            index = indexFromInput();
            if (index == -1) {
                System.out.println("That is not a hobby you are working on, try again");
            }
        }
        return hobbyList.getByIndex(index);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns the index of the location where the given Hobby name is, -1 if
    //          can't be found
    public int indexFromInput() {
        Scanner answer = new Scanner(System.in);
        String name = answer.nextLine();
        return hobbyList.getByName(name);
    }
}
