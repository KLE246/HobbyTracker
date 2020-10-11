package model;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatedHour {
    DateFormat date;
    int hour;

    public DatedHour(DateFormat date, int hour) {
        this.date = date;
        this.hour = hour;
    }

    public String getDatedHourString() {
        Date calender = Calendar.getInstance().getTime();
        return hour + " hours at " + date.format(calender);
    }
}
