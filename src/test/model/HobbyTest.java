package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class HobbyTest {
    Hobby hobby;

    @BeforeEach
    public void setUp() {
        hobby = new Hobby("Test");
    }
    @Test
    public void testHobbyConstructor() {
        assertEquals("Test", hobby.getName());
        assertEquals(0, hobby.totalProgress);
        assertEquals(0, hobby.progressList.size());
        assertEquals(0, hobby.milestoneList.size());
    }

    @Test
    public void testAddTime() {
        int progressInRun = 0;
        for (int i = 0; i < 6; i++) {
            hobby.addTime(i);
            progressInRun += i;
            assertEquals(progressInRun, hobby.totalProgress);
            assertEquals(i + 1, hobby.progressList.size());
        }
    }

    @Test
    public void testAddMilestone() {
        Milestone milestone = new Milestone("testMilestone");
        assertEquals(0, hobby.milestoneList.size());

        hobby.addMilestone(milestone);
        assertEquals(1, hobby.milestoneList.size());

        Milestone nextMilestone = new Milestone("nextTestMilestone");
        hobby.addMilestone(nextMilestone);
        assertEquals(2, hobby.milestoneList.size());
    }

    @Test
    public void testGetLog() {
        LinkedList<String> log;
        log = hobby.getLog();
        assertEquals(0,log.size());
        for (int i = 0; i < 6; i++) {
            hobby.addTime(i);
        }
        log = hobby.getLog();
        assertEquals(6, log.size());

        // test example submission and output
        DatedHour datedHour = hobby.progressList.get(2);
        String logEntry = log.get(2);
        String expected = datedHour.getHour() + " hours as of " + datedHour.getDate();
        assertEquals(expected, logEntry);
    }

    @Test
    public void testMilestoneLog() {
        LinkedList<String> log;
        log = hobby.getMilestoneLog();
        assertEquals(0,log.size());
        for (int i = 0; i < 6; i++) {
            Milestone milestone = new Milestone(Integer.toString(i));
            hobby.addMilestone(milestone);
            milestone.setTime(i);
            String description = i + "sample description";
            milestone.setDescription(description);
        }
        log = hobby.getMilestoneLog();
        assertEquals(6, log.size());

        // test example submission and output
        Milestone milestone = hobby.milestoneList.get(3);
        String logEntry = log.get(3);
        DatedHour datedHour = milestone.savedTime;
        String expected = milestone.title + " \n submitted " + datedHour.getDate()
                + "\n Description: \n" + milestone.description + "\n";
        assertEquals(expected, logEntry);
    }




}