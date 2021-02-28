package model;

import model.courts.Court;
import model.users.Coach;
import model.users.Player;
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

        testPlayerList = new HashSet<Player>();
        testPlayerList.add(testPlayer1);
        testPlayerList.add(testPlayer2);
        testPlayerList.add(testPlayer3);

        testCoachList = new HashSet<Coach>();
        testCoachList.add(testCoach1);
        testCoachList.add(testCoach2);
        testCoachList.add(testCoach3);

    }

    @Test
    public void testConstructor() {
        assertTrue(testCourt.getPlayers().isEmpty());
        assertTrue(testCourt.getCoaches().isEmpty());
    }
    @Test
    public void testAddOnePlayer() {
        testCourt.addPlayer(testPlayer1);
        assertTrue(testCourt.getPlayers().contains(testPlayer1));
    }

    @Test
    public void testAddManyPlayer() {
        testCourt.addPlayer(testPlayer1);
        testCourt.addPlayer(testPlayer2);
        testCourt.addPlayer(testPlayer3);
        System.out.println(testCourt.getPlayers());
        assertTrue(testCourt.getPlayers().containsAll(testPlayerList));
    }


    @Test
    public void testRemoveOnePlayer() {
        testCourt.addPlayer(testPlayer1);
        assertTrue(testCourt.getPlayers().contains(testPlayer1));
        testCourt.removePlayer(testPlayer1);
        assertFalse(testCourt.getPlayers().contains(testPlayer1));
    }

    @Test
    public void testRemovePlayer() {
        testCourt.addPlayer(testPlayer1);
        testCourt.addPlayer(testPlayer2);
        testCourt.addPlayer(testPlayer3);
        assertTrue(testCourt.getPlayers().containsAll(testPlayerList));
        testCourt.removePlayer(testPlayer1);
        testCourt.removePlayer(testPlayer2);
        testCourt.removePlayer(testPlayer3);
        assertFalse(testCourt.getPlayers().containsAll(testPlayerList));
    }

    @Test
    public void testLookingUpPlayerByName() {
        testCourt.addPlayer(testPlayer1);
        testCourt.addPlayer(testPlayer2);
        testCourt.addPlayer(testPlayer3);
        assertTrue(testCourt.getPlayers().containsAll(testPlayerList));

        assertEquals(testPlayer1, testCourt.lookingUpPlayerByName(testPlayer1Name));
        assertEquals(testPlayer2, testCourt.lookingUpPlayerByName(testPlayer2Name));
        assertEquals(testPlayer3, testCourt.lookingUpPlayerByName(testPlayer3Name));
        assertFalse(testCourt.lookingUpPlayerByName(testPlayer1Name).equals(testPlayer2));
        assertFalse(testCourt.lookingUpPlayerByName(testPlayer2Name).equals(testPlayer1));
        assertTrue(testCourt.lookingUpPlayerByName(testPlayer1Name).equals(testPlayer1));

    }

    @Test
    public void testLookupPlayersByStatusTrue() {
        testPlayer1.setStatus(true);
        testPlayer2.setStatus(false);
        assertTrue(testPlayer1.getStatus());
        assertFalse(testPlayer2.getStatus());
        testCourt.addPlayer(testPlayer1);
        testCourt.addPlayer(testPlayer2);
        assertTrue(testCourt.lookupPlayersByStatusTrue().contains(testPlayer1));
        assertFalse(testCourt.lookupPlayersByStatusTrue().contains(testPlayer2));
    }

    @Test
    public void testAddCoach() {
        testCourt.addCoach(testCoach1);
        assertTrue(testCourt.getCoaches().contains(testCoach1));
    }

    @Test
    public void testRemoveCoach() {
        testCourt.addCoach(testCoach1);
        assertTrue(testCourt.getCoaches().contains(testCoach1));
        testCourt.removeCoach(testCoach1);
        assertFalse(testCourt.getCoaches().contains(testCoach1));
    }

    @Test
    public void testLookupCoachByStatusTrue() {
        testCoach1.setStatus(true);
        testCoach2.setStatus(false);
        assertTrue(testCoach1.getStatus());
        assertFalse(testCoach2.getStatus());
        testCourt.addCoach(testCoach1);
        testCourt.addCoach(testCoach2);
        assertTrue(testCourt.lookupCoachByStatusTrue().contains(testCoach1));
        assertFalse(testCourt.lookupCoachByStatusTrue().contains(testCoach2));

    }
}
