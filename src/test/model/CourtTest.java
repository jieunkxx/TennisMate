package model;

import model.courts.Court;
import model.users.Coach;
import model.users.Player;
import model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class CourtTest {

    private Court testCourt;
    private Player testPlayer1;
    private Player testPlayer2;
    private Player testPlayer3;

    private Coach testCoach1;
    private Coach testCoach2;
    private Coach testCoach3;

    private Collection<User> testUserList;
    private Collection<Player> testPlayerList;
    private Collection<Coach> testCoachList;

    private String testPlayer1Name = "testPlayer1";
    private String testPlayer2Name = "testPlayer2";
    private String testPlayer3Name = "testPlayer3";

    private String testCoach1Name = "testCoach1";
    private String testCoach2Name = "testCoach2";
    private String testCoach3Name = "testCoach3";

    @BeforeEach
    public void runBefore() {
        testCourt = new Court("testCourt");

        testPlayer1 = new Player(111, testPlayer1Name);
        testPlayer2 = new Player(222, testPlayer2Name);
        testPlayer3 = new Player(333, testPlayer3Name);
        testCoach1 = new Coach(555, testCoach1Name);
        testCoach2 = new Coach(666, testCoach2Name);
        testCoach3 = new Coach(777, testCoach3Name);

        testUserList = new HashSet<User>();
        testUserList.add(testPlayer1);
        testUserList.add(testPlayer2);
        testUserList.add(testPlayer3);

        testCoachList = new HashSet<Coach>();
        testCoachList.add(testCoach1);
        testCoachList.add(testCoach2);
        testCoachList.add(testCoach3);

        //testCoachList = new HashSet<Coach>();

    }

    @Test
    public void testConstructor() {
        assertTrue(testCourt.getPlayers().isEmpty());
        assertTrue(testCourt.getCoaches().isEmpty());
    }

    @Test
    public void testAddOneUser() {
        testCourt.addUser(testPlayer1);
        assertTrue(testCourt.getUsers().contains(testPlayer1));
    }

    @Test
    public void testAddManyUser() {
        testCourt.addUser(testPlayer1);
        testCourt.addUser(testPlayer2);
        testCourt.addUser(testPlayer3);
        assertTrue(testCourt.getUsers().containsAll(testUserList));
    }


//    @Test
//    public void testAddOnePlayer() {
//        testCourt.addUser(testUser1);
//        assertTrue(testCourt.getPlayers().contains(testUser1));
//    }
//
//    @Test
//    public void testAddManyPlayer() {
//        testCourt.addUser(testUser1);
//        testCourt.addUser(testUser2);
//        testCourt.addUser(testUser3);
//        System.out.println(testCourt.getPlayers());
//        assertTrue(testCourt.getPlayers().containsAll(testUserList));
//    }


    @Test
    public void testRemoveOneUser() {
        testCourt.addUser(testPlayer1);
        assertTrue(testCourt.getUsers().contains(testPlayer1));
        testCourt.removeUser(testPlayer1);
        assertFalse(testCourt.getUsers().contains(testPlayer1));
    }

    @Test
    public void testRemoveUser() {
        testCourt.addUser(testPlayer1);
        testCourt.addUser(testPlayer2);
        testCourt.addUser(testPlayer3);
        assertTrue(testCourt.getUsers().containsAll(testUserList));
        testCourt.removeUser(testPlayer1);
        testCourt.removeUser(testPlayer2);
        testCourt.removeUser(testPlayer3);
        assertFalse(testCourt.getUsers().containsAll(testUserList));
    }

    @Test
    public void testLookingUpUserByName() {
        testCourt.addUser(testPlayer1);
        testCourt.addUser(testPlayer2);
        testCourt.addUser(testPlayer3);
        assertTrue(testCourt.getUsers().containsAll(testUserList));

        assertEquals(testPlayer1, testCourt.lookingUpUserByName(testPlayer1Name));
        assertEquals(testPlayer2, testCourt.lookingUpUserByName(testPlayer2Name));
        assertEquals(testPlayer3, testCourt.lookingUpUserByName(testPlayer3Name));
        assertFalse(testCourt.lookingUpUserByName(testPlayer1Name).equals(testPlayer2));
        assertFalse(testCourt.lookingUpUserByName(testPlayer2Name).equals(testPlayer1));
        assertTrue(testCourt.lookingUpUserByName(testPlayer1Name).equals(testPlayer1));

    }

    @Test
    public void testLookupUserByType() {
        testCourt.addUser(testPlayer1);
        testCourt.addUser(testCoach1);
        assertEquals(1, testCourt.lookupUserByType("player").size());
        assertTrue(testCourt.lookupUserByType("player").contains(testPlayer1));
        assertEquals(1, testCourt.lookupUserByType("coach").size());
        assertTrue(testCourt.lookupUserByType("coach").contains(testCoach1));
    }

    @Test
    public void testLookupUserByStatusTrue() {
        testPlayer1.setStatus(true);
        testPlayer2.setStatus(false);
        assertTrue(testPlayer1.getStatus());
        assertFalse(testPlayer2.getStatus());
        testCourt.addUser(testPlayer1);
        testCourt.addUser(testPlayer2);
        assertTrue(testCourt.lookupUserByStatusTrue().contains(testPlayer1));
        assertFalse(testCourt.lookupUserByStatusTrue().contains(testPlayer2));
    }


//    @Test
//    public void testLookupCoachByStatusTrue() {
//        testCoach1.setStatus(true);
//        testCoach2.setStatus(false);
//        assertTrue(testCoach1.getStatus());
//        assertFalse(testCoach2.getStatus());
//        testCourt.addCoach(testCoach1);
//        testCourt.addCoach(testCoach2);
//        assertTrue(testCourt.lookupCoachByStatusTrue().contains(testCoach1));
//        assertFalse(testCourt.lookupCoachByStatusTrue().contains(testCoach2));
//
//    }
}
