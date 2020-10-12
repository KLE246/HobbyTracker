package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Used https://www.javatpoint.com/java-date-to-string for information for this class
public class DatedHour {
    Date date;
    int hour;

    public DatedHour(int hour) {
        this.date = Calendar.getInstance().getTime();
        this.hour = hour;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        return dateFormat.format(date);
    }

    public int getHour() {
        return hour;
    }
}
