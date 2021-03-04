package model;


import model.courts.Court;
import model.users.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player testPlayer;
    private int initialID;
    private int testId;
    private String testPlayerName;
    private String testLevel;
    private Court testCourt1;
    private Court testCourt2;

    @BeforeEach
    public void runBefore() {
        initialID = 135;
        testId = 444;
        testPlayerName = "testPlayer";


        testPlayer = new Player(initialID, testPlayerName);
        testCourt1 = new Court("testCourt1");
        testCourt2 = new Court("testCourt2");

    }

    @Test
    public void testConstructor() {
        assertEquals(initialID, testPlayer.getId());
        assertEquals("testPlayer", testPlayer.getUserName());
        assertEquals(0, testPlayer.getTimeSlot().size());
        assertEquals(0, testPlayer.getPreferredCourt().size());
        assertFalse(testPlayer.getStatus());
        assertEquals("novice", testPlayer.getLevel());
    }

    @Test
    public void testSetID() {
        assertEquals(initialID, testPlayer.getId());
        testPlayer.setID(testId);
        assertEquals(testId, testPlayer.getId());
    }

    @Test
    public void testSetUserName() {
        assertEquals(testPlayerName, testPlayer.getUserName());
        testPlayer.setUserName("testUser");
        assertEquals("testUser", testPlayer.getUserName());
    }

//    @Test
//    public void testGetCourt() {
//        testPlayer.addPreferredCourt(testCourt1);
//        Iterator<Court> iterator = testPlayer.getPreferredCourt().iterator();
//        while(iterator.hasNext())
//        assertEquals((Court) testCourt1, testPlayer.getPreferredCourt());
//    }

    @Test
    public void testSetLevel() {
        assertEquals("novice", testPlayer.getLevel());
        testPlayer.setLevel("intermediate");
        assertEquals("intermediate", testPlayer.getLevel());
        testPlayer.setLevel("advance");
        assertEquals("advance", testPlayer.getLevel());
    }


    @Test
    public void testAddOncePreferredCourts() {
        testPlayer.addPreferredCourt(testCourt1);
        assertTrue(testPlayer.getPreferredCourt().contains(testCourt1));
    }

    @Test
    public void testAddManyPreferredCourts() {
        testPlayer.addPreferredCourt(testCourt1);
        testPlayer.addPreferredCourt(testCourt2);
        assertTrue(testPlayer.getPreferredCourt().contains(testCourt1));
        assertTrue(testPlayer.getPreferredCourt().contains(testCourt2));

    }

    @Test
    public void testRemovePreferredCourt() {
        testPlayer.removePreferredCourt(testCourt1);
        assertFalse(testPlayer.getPreferredCourt().contains(testCourt1));
    }

    @Test
    public void testAddTimeSlot() {
        testPlayer.addTimeSlot(13);
        assertTrue(testPlayer.getTimeSlot().contains(13));
    }

    @Test
    public void testAddTimeSlotMany() {
        testPlayer.addTimeSlot(4);
        testPlayer.addTimeSlot(7);
        testPlayer.addTimeSlot(15);
        System.out.println(testPlayer.getTimeSlot());
        assertTrue(testPlayer.getTimeSlot().contains(15));
        assertTrue(testPlayer.getTimeSlot().contains(4));
        assertTrue(testPlayer.getTimeSlot().contains(7));
    }

    @Test
    public void testRemoveTimeSlot() {
        testPlayer.addTimeSlot(13);
        assertTrue(testPlayer.getTimeSlot().contains(13));
        testPlayer.removeTimeSlot((Integer) 13);
        assertFalse(testPlayer.getTimeSlot().contains(13));
    }

    @Test
    public void testSetStatus() {
        assertFalse(testPlayer.getStatus());
        testPlayer.setStatus(true);
        assertTrue(testPlayer.getStatus());
    }
}