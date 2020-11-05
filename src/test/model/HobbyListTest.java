package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class HobbyListTest {
    public HobbyList hobbyList;

    @BeforeEach
    public void setUp() {
        hobbyList = new HobbyList();
    }

    @Test
    public void testLength() {
        assertEquals(0, hobbyList.length());
        Hobby hobby = new Hobby("test");
        hobbyList.addHobby(hobby);
        assertEquals(1,hobbyList.length());
    }
    @Test
    public void testAdd() {
        assertEquals(0, hobbyList.length());
        Hobby hobby = new Hobby("test");
        hobbyList.addHobby(hobby);
        assertEquals(1,hobbyList.length());
        assertEquals("Test", hobbyList.getByIndex(0).getName());

    }

    @Test
    public void testAddByString() {
        assertEquals(0, hobbyList.length());
        hobbyList.addHobby("test");
        assertEquals(1,hobbyList.length());
        assertEquals("Test", hobbyList.getByIndex(0).getName());
    }


    @Test
    public void testGetByIndex() {
        Hobby hobby = new Hobby("test");
        hobbyList.addHobby(hobby);
        assertEquals(hobby, hobbyList.getByIndex(0));
        Hobby hobbyTwo = new Hobby("test2");
        Hobby hobbyThree = new Hobby("test3");
        hobbyList.addHobby(hobbyTwo);
        hobbyList.addHobby(hobbyThree);
        assertEquals(hobbyThree, hobbyList.getByIndex(2));
        assertEquals(hobbyTwo, hobbyList.getByIndex(1));
    }

    @Test
    public void testGetByName() {
        Hobby hobby = new Hobby("a1");
        Hobby nextHobby = new Hobby("b2");
        hobbyList.addHobby(hobby);
        hobbyList.addHobby(nextHobby);
        assertEquals(2,hobbyList.length());
        assertEquals(1, hobbyList.getByName("b2"));
        assertEquals(0, hobbyList.getByName("a1"));

    }

    @Test
    public void testGiveAllHobbiesNone() {
        assertEquals(0, hobbyList.length());
        LinkedList<String> names = hobbyList.giveAllHobbyNames();
        assertEquals(0, names.size());
    }

    @Test
    public void testGiveAllHobbies() {
        hobbyList.addHobby(new Hobby ("a1"));
        hobbyList.addHobby(new Hobby ("B2"));
        hobbyList.addHobby(new Hobby ("c3"));
        assertEquals(3, hobbyList.length());
        LinkedList<String> names = hobbyList.giveAllHobbyNames();
        assertEquals(3, names.size());
        assertEquals("A1",names.get(0));
        assertEquals("B2",names.get(1));
        assertEquals("C3",names.get(2));
    }

    @Test
    public void testToJson() {
        Hobby hobbyOne = new Hobby ("a1");
        Hobby hobbyTwo = new Hobby ("B2");
        hobbyList.addHobby(hobbyOne);
        hobbyList.addHobby(hobbyTwo);
        JSONObject jsonList = hobbyList.toJson();
        assertEquals("A1",jsonList.getJSONArray("Hobbies").getJSONObject(0).getString("name"));
        assertEquals("B2",jsonList.getJSONArray("Hobbies").getJSONObject(1).getString("name"));
    }

    @Test
    public void testNullName() {
        assertEquals(" ", hobbyList.getName());
    }
}