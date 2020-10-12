package model;

import java.util.LinkedList;

public class Hobby {
    public String name;
    public int totalProgress;
    public LinkedList<DatedHour> progressList;
    public LinkedList<Milestone> milestoneList;

    public Hobby(String name) {
        this.name = name;
        this.totalProgress = 0;
        this.progressList = new LinkedList<>();
        this.milestoneList = new LinkedList<>();
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
    // MODIFIES:
    // EFFECTS: returns the name of the hobby
    public String getName() {
        return name;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns the log in an easily read format
    public LinkedList<String> getLog() {
        LinkedList<String> log = new LinkedList();
        for (DatedHour datedHour : progressList) {
            log.add(datedHour.getHour() + " hours as of " + datedHour.getDate());
        }
        return log;
    }
}
