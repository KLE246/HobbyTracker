package model;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class MilestoneTest {
    Hobby hobby;

    @BeforeEach
    public void setUp() {
        new Hobby("Test");
    }

}