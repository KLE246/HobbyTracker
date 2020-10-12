package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatedHour {
    Date date;
    int hour;

    public DatedHour(int hour) {
        this.date = Calendar.getInstance().getTime();;
        this.hour = hour;
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public int getHour() {
        return hour;
    }
}
