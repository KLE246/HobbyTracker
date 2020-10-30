package persistence;

import model.DatedHour;
import model.Hobby;
import model.HobbyList;
import model.Milestone;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
// Tests based off tests in JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNoFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            HobbyList hobbyList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyHobbyList() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            HobbyList hobbyList = reader.read();
            assertEquals("empty", hobbyList.getName());
            assertEquals(0, hobbyList.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralHobbyList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralHobbyList.json");
        try {
            HobbyList hobbyList = reader.read();
            assertEquals("GeneralTest", hobbyList.getName());
            List<Hobby> hobbies = hobbyList.getHobbyList();
            assertEquals(2, hobbies.size());

            LinkedList<String> codeProgressList = new LinkedList<>();
            codeProgressList.add("2 hours as of 2020-10-29 06:04");
            codeProgressList.add("9 hours as of 2020-10-29 06:04");
            codeProgressList.add("19 hours as of 2020-10-29 06:05");

            LinkedList<String> codeMilestoneList = new LinkedList<>();
            codeMilestoneList.add("App \n" +
                    "submitted 2020-10-29 06:04 after 9 hours of progress \n" +
                    "Description:\n" +
                    "made new app" + "\n");
            codeMilestoneList.add("Second app \n" +
                    "submitted 2020-10-29 06:05 after 19 hours of progress \n" +
                    "Description:\n" +
                    "made second app" + "\n");

            LinkedList<String> guitarProgressList = new LinkedList<>();
            guitarProgressList.add("1 hours as of 2020-10-29 06:04");
            guitarProgressList.add("8 hours as of 2020-10-29 06:05");

            LinkedList<String> guitarMilestoneList = new LinkedList<>();
            guitarMilestoneList.add("Song \n" +
                    "submitted 2020-10-29 06:05 after 1 hours of progress \n" +
                    "Description:\n" +
                    "learned new song" + "\n");

            checkHobby("Code", 19, codeProgressList, codeMilestoneList, hobbies.get(0));
            checkHobby("Guitar", 8, guitarProgressList, guitarMilestoneList, hobbies.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}