package model;

import java.text.SimpleDateFormat;

public abstract class Event {
    protected String title;
    //protected String notes;
    protected DatedHour savedTime;

    protected Event(String title, int totalProgress) {
        this.title = title;
        savedTime = new DatedHour(new SimpleDateFormat("dd/MM/yy HH:mm:ss"), totalProgress);
    }

}
