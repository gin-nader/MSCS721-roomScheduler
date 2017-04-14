/*
 *
 * (C) Copyright 2017 Tom Ginader
 *
 * Anyone can use the Room Scheduler program because it's open source and on github.com
 *
 * Contributers: Michael Gildein, Tom Ginader
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
    MeetingTest.class,
    RoomTest.class,
    RoomSchedulerTest.class
})
/**
 * Created by Tom on 2/19/2017.
 */
public class JunitTestSuite {
}
