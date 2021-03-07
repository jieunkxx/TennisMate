package model.locations;

import model.courts.Court;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocationTest {

    private Location testLocation;
    private Collection<Court> courts;
    private Collection<Court> testCourtList;

    private Court testCourt;
    private Court testCourt1;
    private Court testCourt2;
    private Court testCourt3;
    private Court testCourt4;
    private Court testCourt5;

    @BeforeEach
    public void runBefore() {
        testLocation = new Location("testLocation");
        courts = new HashSet<>();
        testCourt = new Court("testCourt");
        testCourt1 = new Court("testCourt1");
        testCourt2 = new Court("testCourt2");
        testCourt3 = new Court("testCourt3");
        testCourt4 = new Court("testCourt4");
        testCourt5 = new Court("testCourt5");

        testCourtList = new HashSet<>();

        testCourtList.add(testCourt1);
        testCourtList.add(testCourt2);
        testCourtList.add(testCourt3);
        testCourtList.add(testCourt4);
        testCourtList.add(testCourt5);

    }

    @Test
    public void testConstructor() {
        //assertEquals("testLocation", testLocation.getName());
        assertTrue(testLocation.getCourts().isEmpty());
    }

    @Test
    public void testAddCourt() {
        testLocation.addCourt(testCourt);
        assertTrue(testLocation.getCourts().contains(testCourt));
    }

    @Test
    public void testAddManyCourt() {
        for(Court c : testCourtList) {
            testLocation.addCourt(c);
        }
        assertTrue(testLocation.getCourts().containsAll(testCourtList));
    }

    @Test
    public void testSetLocationName() {
        assertEquals("testLocation", testLocation.getLocationName());
        testLocation.setLocationName("test");
        assertEquals("test", testLocation.getLocationName());
    }

    @Test
    void testLookingupCourtByName() {
        testLocation.addCourt(testCourt1);
        testLocation.addCourt(testCourt2);
        testLocation.addCourt(testCourt3);
        assertEquals(testCourt1, testLocation.lookingUpCourtByName("testCourt1"));

    }






}
