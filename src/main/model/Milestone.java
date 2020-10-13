package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Milestone {

    public String title;
    public DatedHour savedTime;
    public String description;

    public Milestone(String title) {
        this.title = title;
    }

    // REQUIRES: savedTime has not yet been set
    // MODIFIES: this
    // EFFECTS: adds the time to be saved for this milestone
    public void setTime(int totalProgress) {
        savedTime = new DatedHour(totalProgress);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds a description to the milestone
    public void setDescription(String description) {
        this.description = description;
    }
}


