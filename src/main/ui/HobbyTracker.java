package ui;

import model.DatedHour;
import model.Hobby;
import model.HobbyList;
import model.Milestone;

import java.text.DateFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class HobbyTracker {
    public HobbyList hobbyList;
    private Scanner input;

    public HobbyTracker() {
        runTracker();
    }

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

    private void processCommand(String command) {
        if (command.equals("new")) {
            addHobby();
        } else if (command.equals("all")) {
            seeAllHobbiesAndHours();
        } else if (command.equals("log")) {
            getLog();
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

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds a milestone to remember and display recent five milestones
    private void addMilestone() {
        System.out.println("Which hobby have you reached a milestone in?");
        Scanner answer = new Scanner(System.in);
        String name = answer.nextLine();
        int index = hobbyList.getByName(name);

        while (index == -1) {
            System.out.println("That is not a hobby you are working on, try again");
            answer = new Scanner(System.in);
            index = hobbyList.getByName(answer.nextLine());
        }
        System.out.println("What big milestone just happened?");
        Scanner title = new Scanner(System.in);
        String milestoneTitle = title.nextLine();
        Hobby hobby = hobbyList.getByIndex(index);
        Milestone milestone = new Milestone(milestoneTitle, hobby.totalProgress);

        System.out.println("Add some details to this milestone");
        Scanner description = new Scanner(System.in);
        String milestoneDescription = description.nextLine();
        milestone.describeMilestone(milestoneDescription);

        LinkedList<Milestone> milestones = hobby.milestoneList;
        milestones.add(milestone);
        DatedHour lastEntry = milestones.get(milestones.size() - 1).savedTime;
        System.out.println("The milestone \"" + milestoneTitle + "\"" + " has been added to " + hobby.getName()
                + " log at " + lastEntry.getHour() + " hours on " + lastEntry.getDate());
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
        System.out.println("To see progression in a hobby - \"log\"");
        System.out.println("To add hours to a hobby - \"add\"");
        System.out.println("To see a log of milestones in a hobby - \"ms\"");
        System.out.println("To add a milestone to a hobby - \"event\"");
        System.out.println("To exit Tracker - \"exit\"");
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: display a log of all milestones from selected hobby
    public void getMilestoneLog() {
        System.out.println("Which hobby do you want to show milestones of?");
        Scanner answer = new Scanner(System.in);
        String name = answer.nextLine();
        int index = hobbyList.getByName(name);

        while (index == -1) {
            System.out.println("That is not a hobby you are working on, try again");
            answer = new Scanner(System.in);
            name = answer.nextLine();
            index = hobbyList.getByName(name);
        }
        Hobby hobby = hobbyList.getByIndex(index);
        for (Milestone m: hobby.milestoneList) {
            DatedHour datedHour = m.savedTime;
            System.out.println(m.title + " \n submitted " + datedHour.getDate()
                    + "\n Description: \n" + m.description + "\n");
        }
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: display a log of times when hours were added to a hobby
    public void getLog() {
        System.out.println("Which hobby do you want to show progress of?");
        Scanner answer = new Scanner(System.in);
        String name = answer.nextLine();
        int index = hobbyList.getByName(name);

        while (index == -1) {
            System.out.println("That is not a hobby you are working on, try again");
            answer = new Scanner(System.in);
            name = answer.nextLine();
            index = hobbyList.getByName(name);
        }
        Hobby hobby = hobbyList.getByIndex(index);
        for (DatedHour d: hobby.progressList) {
            System.out.println(d.getHour() + " as of " + d.getDate());
        }
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
        String timeStr = timeGiven.nextLine();
        int time = Integer.parseInt(timeStr);

        Hobby hobby = hobbyList.getByIndex(index);
        hobby.addTime(time);

        LinkedList<DatedHour> progress = hobby.progressList;
        int progressSize = progress.size();
        DatedHour lastEntry = progress.get(progressSize - 1);
        System.out.println(timeStr + " hours added; Current progress of " + lastEntry.getHour()
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
                System.out.println(hobby.getName() + " - Total Hours:" + hobby.totalProgress);
            }
        }
    }
}
