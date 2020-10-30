package model;

import org.json.JSONObject;
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
        assertEquals(0, hobby.getTotalProgress());
        assertEquals(0, hobby.getProgressList().size());
        assertEquals(0, hobby.getMilestoneList().size());
    }

    @Test
    public void testAddTime() {
        int progressInRun = 0;
        for (int i = 0; i < 6; i++) {
            DatedHour lastEntry = hobby.addTime(i);
            progressInRun += i;
            assertEquals(progressInRun, hobby.getTotalProgress());
            assertEquals(i + 1, hobby.getProgressList().size());
            assertEquals(hobby.getProgressList().get(i),lastEntry);
        }
    }

    @Test
    public void testAddMilestone() {
        Milestone milestone = new Milestone("testMilestone");
        assertEquals(0, hobby.getMilestoneList().size());

        hobby.addMilestone(milestone);
        assertEquals(1, hobby.getMilestoneList().size());

        Milestone nextMilestone = new Milestone("nextTestMilestone");
        hobby.addMilestone(nextMilestone);
        assertEquals(2, hobby.getMilestoneList().size());
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
        DatedHour datedHour = hobby.getProgressList().get(2);
        String logEntry = log.get(2);
        String expected = datedHour.getProgressHour() + " hours as of " + datedHour.getDate();
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
        Milestone milestone = hobby.getMilestoneList().get(3);
        String logEntry = log.get(3);
        DatedHour datedHour = milestone.getSavedTime();
        String expected = milestone.getTitle() + " \nsubmitted " + datedHour.getDate() + " after "
                + datedHour.getProgressHour()
                + " hours of progress \nDescription:\n" + milestone.getDescription() + "\n";
        assertEquals(expected, logEntry);
    }

    @Test
    public void testToJson() {
        hobby = new Hobby("test");
        DatedHour datedHour = new DatedHour(1);
        hobby.addTime(1);
        hobby.addMilestone(new Milestone("sample", "example", datedHour));
        JSONObject jsonHobby = hobby.toJson();
        assertEquals("Test", jsonHobby.getString("name"));
        assertEquals(1, jsonHobby.getInt("total progress"));
        assertEquals("example",
                jsonHobby.getJSONArray("milestone list").getJSONObject(0).getString("description"));
        assertEquals(1,
                jsonHobby.getJSONArray("progress list").getJSONObject(0).getInt("progressHour"));
    }
}