package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MilestoneTest {
    Hobby hobby;
    Milestone milestone;
    Date dateAtInstance;

    @BeforeEach
    public void setUp() {
        hobby = new Hobby("Test");
        milestone = new Milestone("TestMilestone");
        milestone.setDescription("a description");

        hobby.addMilestone(milestone);
    }

    @Test
    public void testSetTimeOnce() {
        milestone.setTime(34);
        dateAtInstance = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String expected = dateFormat.format(dateAtInstance);

        DatedHour time = milestone.getSavedTime();
        String date = time.getDate();
        int hour = time.getProgressHour();

        assertEquals( 34, hour);
        assertEquals(expected, date);
    }

    @Test
    public void testSetTimeMultiple() {
       int hour;
        for (int i = 0; i < 7; i += 2) {
            milestone.setTime(i);
            dateAtInstance = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String expected = dateFormat.format(dateAtInstance);

            DatedHour time = milestone.getSavedTime();
            String date = time.getDate();
            hour = time.getProgressHour();

            assertEquals(i, hour);
            assertEquals(expected, date);
        }
    }

    @Test
    public void testSetTimeThrownException() {

    }

    @Test
    public void testSetDescription() {
        String description = "Sample description";
        milestone.setDescription(description);
        assertEquals(description, milestone.getDescription());
    }
}