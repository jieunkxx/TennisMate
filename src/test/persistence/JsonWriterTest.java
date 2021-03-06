package persistence;

import model.Locations.Location;
import model.courts.Court;
import model.users.Admin;
import model.users.Coach;
import model.users.Player;
import model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JsonWriterTest {

    Location vancouver;
    Admin admin;

    private Player testUser1;
    private Coach testUser2;
    private Court UBC;

    @BeforeEach
    public void runBefore() {
        vancouver = new Location("Vancouver");
        admin = new Admin(vancouver);

        UBC = new Court("UBC");
        testUser1 = new Player(53, "test1");
        testUser1.setLevel("novice");
        testUser1.addTimeSlot(1);
        testUser1.addTimeSlot(4);
        testUser1.addPreferredCourt(UBC);
        testUser1.setStatus(true);

        testUser2 = new Coach(504, "test2");
        testUser2.setLevel("novice");
        testUser2.addTimeSlot(11);
        testUser2.addTimeSlot(13);
    }


    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyVancouver() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyVancouver.json");
            writer.open();
            writer.write(admin);
            writer.close();

            JsonReaderAdmin reader = new JsonReaderAdmin("./data/testWriterEmptyVancouver.json");
            admin = reader.readAdmin();
            assertEquals("Vancouver", admin.getLocationName());
            assertTrue(admin.getUserList().isEmpty());
            assertTrue(admin.getUserIdList().isEmpty());
            assertTrue(admin.getUserNameList().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralVancouver() {
        try {
            admin = new Admin(vancouver);
            admin.addUser(testUser1);
            admin.addUser(testUser2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom1.json");
            writer.open();
            writer.write(admin);
            writer.close();

            JsonReaderAdmin reader = new JsonReaderAdmin("./data/testWriterGeneralWorkroom1.json");
            admin = reader.readAdmin();
            assertEquals("Vancouver", admin.getLocationName());
            List<User> userList = admin.getUserList();
            Collection<String> userNameList = admin.getUserNameList();
            Collection<Integer> userIdList = admin.getUserIdList();
            assertEquals(2, userList.size());
            assertEquals(2, userNameList.size());
            assertEquals(2, userIdList.size());
            checkUserInfo(testUser1, userList.get(0));
            checkUserInfo(testUser2, userList.get(1));
            assertTrue(userList.get(0).lookingupCourtByName("UBC"));
            assertFalse(userList.get(1).lookingupCourtByName("UBC"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    public void checkUserInfo(User user1, User user2) {
        assertEquals(user1.getUserName(), user2.getUserName());
        assertEquals(user1.getId(), user2.getId());
        assertEquals(user1.getTimeSlot().size(), user2.getTimeSlot().size());
        assertTrue(user2.getTimeSlot().containsAll(user1.getTimeSlot()));

        assertEquals(user1.getType(), user2.getType());
        assertEquals(user1.getLevel(), user2.getLevel());
        assertEquals(user1.getStatus(), user2.getStatus());
    }

}
