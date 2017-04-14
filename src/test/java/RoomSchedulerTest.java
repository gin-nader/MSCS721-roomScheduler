/*
 *
 * (C) Copyright 2017 Tom Ginader
 *
 * Anyone can use the Room Scheduler program because it's open source and on github.com
 *
 * Contributers: Michael Gildein, Tom Ginader
 */
import com.marist.mscs721.Meeting;
import com.marist.mscs721.Room;
import com.marist.mscs721.RoomScheduler;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
/**
 * Created by Tom on 3/24/2017.
 */
public class RoomSchedulerTest{

    RoomScheduler rs = new RoomScheduler();
    Room room = new Room("Test", 20, "TestBuilding", "TestLocation");
    Room room2 = new Room("Test2", 20, "TestBuilding2", "TestLocation2");
    Room room3 = new Room("Test3", 20, "TestBuilding3", "TestLocation3");
    Meeting testMeeting = new Meeting(Timestamp.valueOf("2017-02-19 3:00:00.0"), Timestamp.valueOf("2017-02-19 4:00:00.0"),
            "MSCS721");
    ArrayList<Room> rooms = new ArrayList<Room>();

  /**
   * This method tests the exportToJson method by checking to see that the method works properly and returns the correct
   * string value.
   */
    /*@Test
    public void testExportToJson() throws IOException {
        rooms.add(room);
        assertEquals(rs.exportToJson(rooms), "Successfully exported objects to JSON!");
    }*/

  /**
   * This method tests the importFromJson method by checking to see that the method works properly and returns the correct
   * string value.
   */
    /*@Test
    public void testImportFromJson() throws IOException {
      assertEquals(rs.importFromJson(rooms), "Successfully imported JSON to objects!");
    }*/

    /**
     * This method tests the roomsAvailable method by adding three rooms and scheduling a meeting to one of the rooms.
     * It then tries to schedule the same meeting, so it returns that only 2 rooms are available.
     */
    @Test
    public void testRoomsAvailable(){
        room.addMeeting(testMeeting);
        rooms.add(room);
        rooms.add(room2);
        rooms.add(room3);

        String answer = rs.roomsAvailable(Timestamp.valueOf("2017-02-19 3:00:00.0"),
                                                Timestamp.valueOf("2017-02-19 4:00:00.0"), rooms);
        assertEquals("2 Room(s) available\n", answer);
    }
}
