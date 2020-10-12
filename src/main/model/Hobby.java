package model;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class Hobby {
    public String name;
    public int totalProgress;
    public LinkedList<DatedHour> progressList;
    public LinkedList<Milestone> milestoneList;
    //public LinkedList<Event> goalList;

    public Hobby(String name) {
        this.name = name;
        this.totalProgress = 0;
        this.progressList = new LinkedList<DatedHour>();
        this.milestoneList = new LinkedList<Milestone>();
        //this.goalList = new LinkedList<Event>();
    }

    // REQUIRES: a positive time
    // MODIFIES: this
    // EFFECTS: adds the time towards the total hours in the hobby
    public void addTime(int time) {
        totalProgress = totalProgress + time;
        DatedHour datedHour = new DatedHour(totalProgress);
        progressList.add(datedHour);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds event to be saved within the hobby
    public void addEvent(String type, String title) {
        Milestone event;
//        if ("Goal" == type) {
//            event = new Goal(title);
//            goalList.add(event);
//        }
        if ("Milestone" == type) {
            event = new Milestone(title, totalProgress);
            milestoneList.add(event);
        }
    }

    public int getProgress() {
        return totalProgress;
    }

    public String getName() {
        return name;
    }
}
