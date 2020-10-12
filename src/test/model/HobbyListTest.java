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
    public void testAdd() {
        assertEquals(0, hobbyList.length());
        Hobby hobby = new Hobby("test");
        hobbyList.add(hobby);
        assertEquals(1,hobbyList.length());
    }

    @Test
    public void testGetByName() {
        Hobby hobby = new Hobby("a1");
        Hobby nextHobby = new Hobby("b2");
        hobbyList.add(hobby);
        hobbyList.add(nextHobby);
        assertEquals(2,hobbyList.length());

        assertEquals(0, hobbyList.getByName("a1"));
        assertEquals(1, hobbyList.getByName("b2"));
    }
}