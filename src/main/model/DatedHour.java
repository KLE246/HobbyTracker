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
        return Integer.toString(hour) + " hours of progress as of " + date.format(calender);
    }
}
