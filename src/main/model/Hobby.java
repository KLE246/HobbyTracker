package model;

import exceptions.NegativeTimeException;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.json.JSONObject;
import persistence.Writable;

import java.util.LinkedList;

// Hobbies have names, an total hourly progress, and two lists for milestones and progress
// totalProgress is initially 0 and both progress and milestone lists are empty
public class Hobby implements Writable {
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

    // MODIFIES: this
    // EFFECTS: adds the time towards the total hours in the hobby
    //          and adds an entry into progressList
    public DatedHour addTime(int time) throws NegativeTimeException {
        if (time < 0) {
            throw new NegativeTimeException();
        }
        totalProgress = totalProgress + time;
        DatedHour datedHour = new DatedHour(totalProgress);
        progressList.add(datedHour);
        return datedHour;
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
            DatedHour datedHour = milestone.getSavedTime();
            log.add(milestone.getTitle() + " \nsubmitted " + datedHour.getDate() + " after "
                    + datedHour.getProgressHour() + " hours of progress \nDescription:\n"
                    + milestone.getDescription() + "\n");
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
    // MODIFIES: this
    // EFFECTS: set current totalProgress
    public void setTotalProgress(int totalProgress) {
        this.totalProgress = totalProgress;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: set milestoneList
    public void setMilestoneList(LinkedList<Milestone> milestoneList) {
        this.milestoneList = milestoneList;
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

    // EFFECTS: returns the hobby as a JSONObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("total progress", totalProgress);
        json.put("progress list", progressList);
        json.put("milestone list", milestoneList);
        return json;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: set progressList
    public void setProgressList(LinkedList<DatedHour> progressList) {
        this.progressList = progressList;
    }


    // EFFECTS: create a dataset of progress hours and dates
    public XYDataset makeDataset() {
        TimeSeries series = new TimeSeries("progress");
        for (DatedHour entry : progressList) {
            series.addOrUpdate(new Day(entry.returnDate()), entry.getProgressHour());
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }
}
