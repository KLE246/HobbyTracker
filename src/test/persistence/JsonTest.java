package persistence;

import model.DatedHour;
import model.Hobby;
import model.Milestone;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkHobby(String name, int totalProgress, LinkedList<String> progressList,
                              LinkedList<String> milestoneList, Hobby hobby) {
        assertEquals(name, hobby.getName());
        assertEquals(totalProgress, hobby.getTotalProgress());
        assertEquals(progressList, hobby.getLog());
        assertEquals(milestoneList, hobby.getMilestoneLog());
    }
}
