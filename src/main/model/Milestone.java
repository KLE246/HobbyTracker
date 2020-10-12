package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Milestone {

    public String title;
    //protected String notes;
    public DatedHour savedTime;
    public String description;

    public Milestone(String title, int totalProgress) {
        this.title = title;
        savedTime = new DatedHour(totalProgress);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: adds given description to the Milestone
    public void describeMilestone(String description) {
        this.description = description;
    }

//    public String getDatedHourString() {
//        Date calender = Calendar.getInstance().getTime();
//        return savedTime.date.format(calender);
//    }

}


