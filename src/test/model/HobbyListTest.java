package model;

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
        hobbyList.add(hobby);
        assertEquals(1,hobbyList.length());
    }
    @Test
    public void testAdd() {
        assertEquals(0, hobbyList.length());
        Hobby hobby = new Hobby("test");
        hobbyList.add(hobby);
        assertEquals(1,hobbyList.length());
    }

    @Test
    public void testGetByIndex() {
        Hobby hobby = new Hobby("test");
        hobbyList.add(hobby);
        assertEquals(hobby, hobbyList.getByIndex(0));
        Hobby hobbyTwo = new Hobby("test2");
        Hobby hobbyThree = new Hobby("test3");
        hobbyList.add(hobbyTwo);
        hobbyList.add(hobbyThree);
        assertEquals(hobbyThree, hobbyList.getByIndex(2));
        assertEquals(hobbyTwo, hobbyList.getByIndex(1));
    }

    @Test
    public void testGetByName() {
        Hobby hobby = new Hobby("a1");
        Hobby nextHobby = new Hobby("b2");
        hobbyList.add(hobby);
        hobbyList.add(nextHobby);
        assertEquals(2,hobbyList.length());
        assertEquals(1, hobbyList.getByName("b2"));
        assertEquals(0, hobbyList.getByName("a1"));

    }

    @Test
    public void testGiveAllHobbiesNone() {
        assertEquals(0, hobbyList.length());
        LinkedList<String> names = hobbyList.giveAllHobbies();
        assertEquals(0, names.size());
    }

    @Test
    public void testGiveAllHobbies() {
        hobbyList.add(new Hobby ("a1"));
        hobbyList.add(new Hobby ("B2"));
        hobbyList.add(new Hobby ("c3"));
        assertEquals(3, hobbyList.length());
        LinkedList<String> names = hobbyList.giveAllHobbies();
        assertEquals(3, names.size());
        assertEquals("A1",names.get(0));
        assertEquals("B2",names.get(1));
        assertEquals("C3",names.get(2));
    }
}