import com.marist.mscs721.Meeting;
import com.marist.mscs721.Room;
import com.marist.mscs721.RoomScheduler;
import org.junit.Test;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
/**
 * Created by Tom on 3/24/2017.
 */
public class RoomSchedulerTest{

    RoomScheduler rs = new RoomScheduler();
    Room room = new Room("Test", 20);
    ArrayList<Room> rooms = new ArrayList<Room>();

    @Test
    public void testExportToJson() throws IOException {
        rooms.add(room);
        assertEquals(rs.exportToJson(rooms), "Successfully exported objects to JSON!");
    }
}
