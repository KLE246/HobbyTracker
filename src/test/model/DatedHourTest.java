package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DatedHourTest {
    DatedHour datedHour;
    Date dateAtInstance;

    @BeforeEach
    public void setUp() {
        datedHour = new DatedHour(5);
        dateAtInstance = Calendar.getInstance().getTime();
    }

    @Test
    public void testConstructor() {
        datedHour = new DatedHour(4, dateAtInstance);
        assertEquals(4, datedHour.getProgressHour());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String expected = dateFormat.format(dateAtInstance);
        assertEquals(expected, datedHour.getDate());
    }

    @Test
    public void testGetReturnDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String expected = dateFormat.format(dateAtInstance);
        assertEquals(expected, datedHour.getDate());
        Date date = datedHour.returnDate();
        String expectedFromReturn = dateFormat.format(date);
        assertEquals(expectedFromReturn, datedHour.getDate());
    }

    @Test
    public void testGetHour() {
        assertEquals(5, datedHour.getProgressHour());
    }

}