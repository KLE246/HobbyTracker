package model;

import java.text.DateFormat;

public class DatedHour {
    DateFormat date;
    int hour;

    public DatedHour(DateFormat date, int hour) {
        this.date = date;
        this.hour = hour;
    }
}
