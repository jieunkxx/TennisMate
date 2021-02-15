package model;

import model.courts.Court;
import model.users.Coach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoachTest {

    private Coach testCoach;
    private int initialId;
    private int testId;
    private int testFees;
    private Court testCourt1;
    private Court testCourt2;

    @BeforeEach
    public void runBefore() {
        initialId = 515;
        testId = 999;
        testCoach = new Coach(initialId, "testCoach");
        testCourt1 = new Court("testCourt1");
        testCourt2 = new Court("testCourt2");
        testFees = 50;
    }

    @Test
    public void testConstructor() {
        assertEquals(515, testCoach.getId());
        assertEquals("testCoach", testCoach.getUserName());
        assertEquals(0, testCoach.getTimeSlot().size());
        assertEquals(0, testCoach.getPreferredCourt().size());
        assertFalse(testCoach.getStatus());
        assertEquals("novice", testCoach.getLevel());
        assertEquals(0, testCoach.getFees());
    }

    @Test
    public void testSetId() {
        assertEquals(initialId, testCoach.getId());
        testCoach.setID(testId);
        assertEquals(testId, testCoach.getId());
    }

    @Test
    public void testSetLevel() {
        assertEquals("novice", testCoach.getLevel());
        testCoach.setLevel("intermediate");
        assertEquals("intermediate", testCoach.getLevel());
        testCoach.setLevel("advance");
        assertEquals("advance", testCoach.getLevel());
    }

    @Test
    public void testAddOncePreferredCourts() {
        testCoach.addPreferredCourt(testCourt1);
        assertTrue(testCoach.getPreferredCourt().contains(testCourt1));
    }

//    @Test
//    public void testAddManyPreferredCourts() {
//        testPlayer.addPreferredCourt(testCourt1);
//        testPlayer.addPreferredCourt(testCourt2);
//    }

    @Test
    public void testRemovePreferredCourt() {
        testCoach.removePreferredCourt(testCourt1);
        assertFalse(testCoach.getPreferredCourt().contains(testCourt1));
    }

    @Test
    public void testAddTimeSlot() {
        testCoach.addTimeSlot(13);
        assertTrue(testCoach.getTimeSlot().contains(13));
    }

    @Test
    public void testRemoveTimeSlot() {
        testCoach.addTimeSlot(13);
        assertTrue(testCoach.getTimeSlot().contains(13));
        testCoach.removeTimeSlot(13);
        assertFalse(testCoach.getTimeSlot().contains(13));
    }

    @Test
    public void testSetStatus() {
        assertFalse(testCoach.getStatus());
        testCoach.setStatus(true);
        assertTrue(testCoach.getStatus());
    }

    @Test
    public void testSetFees() {
        testCoach.setFees(100);
        assertEquals(100, testCoach.getFees());
    }


}
