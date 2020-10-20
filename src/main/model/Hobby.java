package model;

import java.util.LinkedList;

// Hobbies have names, an total hourly progress, and two lists for milestones and progress
// totalProgress is initially 0 and both progress and milestone lists are empty
public class Hobby {
    private String name;
    private int totalProgress;
    private LinkedList<DatedHour> progressList;
    private LinkedList<Milestone> milestoneList;

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: creates a Hobby with given name capitalized, initializes all fields
    public Hobby(String name) {
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
        this.totalProgress = 0;
        this.progressList = new LinkedList<>();
        this.milestoneList = new LinkedList<>();
    }

    // REQUIRES: a positive time
    // MODIFIES: this
    // EFFECTS: adds the time towards the total hours in the hobby
    //          and adds an entry into progressList
    public void addTime(int time) {
        totalProgress = totalProgress + time;
        DatedHour datedHour = new DatedHour(totalProgress);
        progressList.add(datedHour);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds the milestone to the milestone log
    public void addMilestone(Milestone milestone) {
        milestoneList.add(milestone);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns the name of the hobby
    public String getName() {
        return name;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns the progress log in an easily read format
    public LinkedList<String> getLog() {
        LinkedList<String> log = new LinkedList<>();
        for (DatedHour datedHour : progressList) {
            log.add(datedHour.getProgressHour() + " hours as of " + datedHour.getDate());
        }
        return log;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns the milestone log in an easily read format
    public LinkedList<String> getMilestoneLog() {
        LinkedList<String> log = new LinkedList<>();
        for (Milestone milestone : milestoneList) {
            DatedHour datedHour = milestone.savedTime;
            log.add(milestone.title + " \nsubmitted " + datedHour.getDate() + " after " + datedHour.getProgressHour()
                    + " hours of progress \nDescription:\n" + milestone.description + "\n");
        }
        return log;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return current totalProgress
    public int getTotalProgress() {
        return totalProgress;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return current milestoneList
    public LinkedList<Milestone> getMilestoneList() {
        return milestoneList;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: return current progressList
    public LinkedList<DatedHour> getProgressList() {
        return progressList;
    }
}
