package model.users;

import model.Location;
import model.courts.Court;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminTest {

    int userId;
    String userName;
    private Location testLocation;
    private Admin testAdmin;

    private Collection<Integer> userIdList;
    private Collection<String> userNameList;
    private Collection<User> userList;

    private Court testCourt;
    private Player testPlayer1;
    private Player testPlayer2;
    private Player testPlayer3;

    private Coach testCoach1;
    private Coach testCoach2;
    private Coach testCoach3;

    private String testPlayer1Name = "testPlayer1";
    private String testPlayer2Name = "testPlayer2";
    private String testPlayer3Name = "testPlayer3";

    private String testCoach1Name = "testCoach1";
    private String testCoach2Name = "testCoach2";
    private String testCoach3Name = "testCoach3";

    private int testId1 = 111;
    private int testId2 = 222;
    private int testId3 = 333;

    private int testId5 = 555;
    private int testId6 = 666;
    private int testId7 = 777;

    @BeforeEach
    public void runBefore() {
        testAdmin = new Admin();
        testLocation = new Location();
        testAdmin.setLocation(testLocation);

        testPlayer1 = new Player(testId1, testPlayer1Name);
        testPlayer2 = new Player(testId2, testPlayer2Name);
        testPlayer3 = new Player(testId3, testPlayer3Name);
        testCoach1 = new Coach(testId5, testCoach1Name);
        testCoach2 = new Coach(testId6, testCoach2Name);
        testCoach3 = new Coach(testId7, testCoach3Name);
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
        testAdmin.addUser(testCoach1);
        assertEquals(2, testAdmin.getUserList().size());
        assertTrue(testAdmin.getUserList().contains(testPlayer1));
        assertTrue(testAdmin.getUserList().contains(testCoach1));
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
        testAdmin.setLocation(testLocation);
        assertEquals(testLocation.toString(), testAdmin.getLocation().toString());
    }
}