package persistence;

import model.DatedHour;
import model.Hobby;
import model.HobbyList;
import model.Milestone;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            HobbyList hobbyList = new HobbyList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            writer.write(hobbyList);
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyHobbyList() {
        try {
            HobbyList hobbyList = new HobbyList();
            hobbyList.setName("Test List");
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(hobbyList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            hobbyList = reader.read();
            assertEquals("Test List", hobbyList.getName());
            assertEquals(0, hobbyList.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralHobbyList() {
        LinkedList<String> codeProgressList = new LinkedList<>();
        LinkedList<String> guitarProgressList = new LinkedList<>();
        LinkedList<String> codeMilestoneList = new LinkedList<>();
        LinkedList<String> guitarMilestoneList = new LinkedList<>();
        try {
            // make hobby list with two hobbies and a name
            HobbyList hobbyList = new HobbyList();
            Hobby codeHobby = new Hobby("Code");
            Hobby guitarHobby = new Hobby("Guitar");
            hobbyList.setName("Test List");
            hobbyList.addHobby(codeHobby);
            hobbyList.addHobby(guitarHobby);

            codeHobby.addTime(5);
            codeProgressList.add("5 hours as of " + new DatedHour(5).getDate());
            Milestone codeMilestone = new Milestone("App","new app",5);
            codeHobby.addMilestone(codeMilestone);
            codeMilestoneList.add("App \n" +
                    "submitted " + new DatedHour(5).getDate() + " after 5 hours of progress \n" +
                    "Description:\n" +
                    "new app" + "\n");
            codeHobby.addTime(2);
            codeProgressList.add("7 hours as of " + new DatedHour(7).getDate());

            Milestone codeMilestoneTwo = new Milestone("Second","another app", 7);
            codeHobby.addMilestone(codeMilestoneTwo);
            codeMilestoneList.add("Second \n" +
                    "submitted " + new DatedHour(7).getDate() + " after 7 hours of progress \n" +
                    "Description:\n" +
                    "another app" + "\n");

            guitarHobby.addTime(8);
            guitarProgressList.add("8 hours as of " + new DatedHour(8).getDate());

            Milestone guitarMilestoneOne = new Milestone("Song", "new song", 8);
            guitarHobby.addMilestone(guitarMilestoneOne);
            guitarMilestoneList.add("Song \n" +
                    "submitted " + new DatedHour(8).getDate() + " after 8 hours of progress \n" +
                    "Description:\n" +
                    "new song" + "\n");


            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(hobbyList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            hobbyList = reader.read();
            assertEquals("Test List", hobbyList.getName());
            List<Hobby> hobbies = hobbyList.getHobbyList();

            assertEquals(2, hobbies.size());
            checkHobby("Code", 7, codeProgressList, codeMilestoneList, hobbies.get(0));
            checkHobby("Guitar", 8, guitarProgressList, guitarMilestoneList, hobbies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}