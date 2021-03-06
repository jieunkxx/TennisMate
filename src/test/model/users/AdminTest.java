package model.users;

import model.locations.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {


    private Admin testAdmin;

    private Player testPlayer1;
    private Player testPlayer2;
    private Player testPlayer3;

    private Coach testCoach1;
    private Coach testCoach2;
    private Coach testCoach3;

    private String testPlayer1Name;
    private String testPlayer2Name;
    private String testPlayer3Name;

    private String testCoach1Name;
    private String testCoach2Name;
    private String testCoach3Name;

    private int testId1;
    private int testId2;
    private int testId3;

    private int testId5;
    private int testId6;
    private int testId7;

    @BeforeEach
    public void runBefore() {
        Location testLocation1 = new Location("testLocation1");
        testAdmin = new Admin(testLocation1);

        testPlayer1Name = "testPlayer1";
        testPlayer2Name = "testPlayer2";
        testPlayer3Name = "testPlayer3";

        testCoach1Name = "testCoach1";

        testId1 = 111;
        testId2 = 222;
        testId3 = 333;

        testId5 = 555;

        testPlayer1 = new Player(testId1, testPlayer1Name);
        testPlayer2 = new Player(testId2, testPlayer2Name);
        testPlayer3 = new Player(testId3, testPlayer3Name);
        testCoach1 = new Coach(testId5, testCoach1Name);

    }

    @Test
    void testConstructor() {
        assertTrue(testAdmin.getUserList().isEmpty());
        assertTrue(testAdmin.getUserNameList().isEmpty());
        assertTrue(testAdmin.getUserIdList().isEmpty());
    }

    @Test
    void testAddUserOnce() {
        testAdmin.addUser(testPlayer1);
        assertEquals(1, testAdmin.getUserList().size());
        assertTrue(testAdmin.getUserList().contains(testPlayer1));
    }

    @Test
    void testAddUserMany() {
        testAdmin.addUser(testPlayer1);
        testAdmin.addUser(testPlayer2);
        testAdmin.addUser(testCoach1);
        assertEquals(3, testAdmin.getUserList().size());
        assertTrue(testAdmin.getUserList().contains(testPlayer1));
        assertTrue(testAdmin.getUserList().contains(testPlayer2));
        assertTrue(testAdmin.getUserList().contains(testCoach1));
        assertFalse(testAdmin.getUserList().contains(testPlayer3));
    }

    @Test
    void testAddUserNameOnce() {
        testAdmin.addUserName(testPlayer1Name);
        assertEquals(1, testAdmin.getUserNameList().size());
        assertTrue(testAdmin.getUserNameList().contains(testPlayer1Name));
    }

    @Test
    void testAddUserNameMany() {
        testAdmin.addUserName(testPlayer1Name);
        testAdmin.addUserName(testPlayer2Name);
        testAdmin.addUserName(testCoach1Name);
        assertEquals(3, testAdmin.getUserNameList().size());
        assertTrue(testAdmin.getUserNameList().contains(testPlayer1Name));
        assertTrue(testAdmin.getUserNameList().contains(testPlayer2Name));
        assertTrue(testAdmin.getUserNameList().contains(testCoach1Name));
    }


    @Test
    void testAddUserIdOnce() {
        testAdmin.addUserId(testId1);
        assertEquals(1, testAdmin.getUserIdList().size());
        assertTrue(testAdmin.getUserIdList().contains(testId1));
    }

    @Test
    void testAddUserIdMany() {
        List<Integer> testIds = new ArrayList<>();
        for (int i=0; i < 20; i++) {
            int id = testAdmin.generateUserId();
            testIds.add(id);
            testAdmin.addUserId(id);
        }
        assertEquals(20, testAdmin.getUserIdList().size());
        assertTrue(testAdmin.getUserIdList().contains(testIds.get(7)));
        assertTrue(testAdmin.getUserIdList().contains(testIds.get(10)));
    }

    @Test
    public void testSetLocation() {
        Location testLocation2 = new Location("testLocation2");
        testAdmin.setLocation(testLocation2);
        assertTrue(testAdmin.getLocation().equals(testLocation2));
        assertEquals("testLocation2", testAdmin.getLocationName());
    }
}