import com.marist.mscs721.Meeting;
import org.junit.Test;
import java.sql.Timestamp;
import static org.junit.Assert.assertEquals;
/**
 * Created by Tom on 2/19/2017.
 */
public class MeetingTest {


  Meeting testMeeting = new Meeting(Timestamp.valueOf("2017-02-19 3:00:00.0"), Timestamp.valueOf("2017-02-19 4:00:00.0"),
                                    "MSCS721");

  /**
   * This method tests if getSubject() returns the same subject as the on given to testMeeting
   */
  @Test
  public void testGetSubject(){
    assertEquals(testMeeting.getSubject(), "MSCS721");
  }

  /**
   * This method tests if getStartTime() returns the same start time as the one given to testMeeting
   */
  @Test
  public void testGetStartTime(){
    assertEquals(testMeeting.getStartTime(), Timestamp.valueOf("2017-02-19 3:00:00.0"));
  }

  /**
   * This method tests if getStopTime() returns the same stop time as the one given to testMeeting
   */
  @Test
  public void testGetStopTime(){
    assertEquals(testMeeting.getStopTime(), Timestamp.valueOf("2017-02-19 4:00:00.0"));
  }

  /**
   * This method tests that if an empty string is given as the subject for a meeting, that it returns "N/A"
   */
  @Test
  public void testEmptySubject(){
    Meeting testMeeting = new Meeting(Timestamp.valueOf("2017-02-19 3:00:00.0"), Timestamp.valueOf("2017-02-19 4:00:00.0"),
        "");
    assertEquals(testMeeting.getSubject(), "N/A");
  }
}