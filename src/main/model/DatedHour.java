package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Class contains an hour value that will be used to mark the current hour total in a hobby
// any time a DatedHour is made, the instance it is called and the current hobby progress is saved
public class DatedHour {
    private Date date;
    private int progressHour;

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: creates a DateHour with the date and time of when constructor was called
    public DatedHour(int progressHour) {
        this.date = Calendar.getInstance().getTime();
        this.progressHour = progressHour;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: creates a DateHour with the date and a specified time
    public DatedHour(int progressHour, Date date) {
        this.date = date;
        this.progressHour = progressHour;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns the date in a specified format
    public String getDate() {
        String datePattern = "yyyy-MM-dd hh:mm";
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        return dateFormat.format(date);
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: returns hour
    public int getProgressHour() {
        return progressHour;
    }

}
