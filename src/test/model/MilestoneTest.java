package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MilestoneTest {
    Hobby hobby;
    Milestone milestone;

    @BeforeEach
    public void setUp() {
        hobby = new Hobby("Test");
        milestone = new Milestone("TestMilestone");
        hobby.addMilestone(milestone);
    }

    @Test
    public void testSetTimeOnce() {
        milestone.setTime(34);
        DatedHour time = milestone.savedTime;
        String date = time.getDate();
        int hour = time.getHour();
        assertEquals( 34, hour);
    }

    @Test
    public void testSetTimeMultiple() {
       int hour;
        for (int i = 0; i < 7; i += 2) {
            milestone.setTime(i);
            DatedHour time = milestone.savedTime;
            String date = time.getDate();
            hour = time.getHour();
            assertEquals(i, hour);
        }
    }

    @Test
    public void testSetDescription() {
        String description = "Sample description";
        milestone.setDescription(description);
        assertEquals(description, milestone.description);
    }
}