package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Used https://www.javatpoint.com/java-date-to-string for information for this class
public class DatedHour {
    Date date;
    int hour;
    String datePattern = "yyyy-MM-dd hh:mm";

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: creates a DateHour with the date and time of when constructor was called
    public DatedHour(int hour) {
        this.date = Calendar.getInstance().getTime();
        this.hour = hour;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns the date in a specified format
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        return dateFormat.format(date);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns hour
    public int getHour() {
        return hour;
    }
}
