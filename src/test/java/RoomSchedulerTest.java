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
    ArrayList<Room> rooms = new ArrayList<Room>();

  /**
   * This method tests the exportToJson method by checking to see that the method works properly and returns the correct
   * string value.
   */
    @Test
    public void testExportToJson() throws IOException {
        rooms.add(room);
        assertEquals(rs.exportToJson(rooms), "Successfully exported objects to JSON!");
    }

  /**
   * This method tests the importFromJson method by checking to see that the method works properly and returns the correct
   * string value.
   */
    @Test
    public void testImportFromJson() throws IOException {
      assertEquals(rs.importFromJson(rooms), "Successfully imported JSON to objects!");
    }

}
