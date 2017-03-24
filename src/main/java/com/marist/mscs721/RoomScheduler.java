package com.marist.mscs721;

import com.google.gson.Gson;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * This class is the main portion for the RoomScheduler program. It is a console based interface the prompts a user to
 * make a selection from the main menu. The user has several options to choose from such as add a room, remove a room,
 * schedule a room, list the schedule, list the rooms, export the rooms and meetings to JSON, and import the rooms
 * and meetings from JSON.
 *
 * @author Tom Ginader
 * @since 2017-01-26
 */
public class RoomScheduler {
  protected static Scanner keyboard = new Scanner(System.in);
  static final String SEPARATOR = "---------------------";
  static final String ERR_MSG1 = "Integers only, please. Try again.";
  static Logger logger = Logger.getLogger(RoomScheduler.class);
  static boolean debugMode = true;

  /**
   * Main method for the class. This is where the main menu is called, and the user is prompted to enter a number
   * value that corresponds to one of the methods described above. After a number is entered, it then calls that
   * specific function that takes a list of rooms as a parameter. This is also where the list of rooms is created.
   */
  public static void main(String[] args) throws IOException {
    Boolean end = false;
    ArrayList<Room> rooms = new ArrayList<Room>();

    while (!end) {
      switch (mainMenu()) {

        case 1:
          String return1 = addRoom(rooms);
          System.out.println(return1);
          logger.info(return1);
          break;
        case 2:
          String return2 = removeRoom(rooms);
          System.out.println(return2);
          logger.info(return2);
          break;
        case 3:
          String return3 = scheduleRoom(rooms);
          System.out.println(return3);
          logger.info(return3);
          break;
        case 4:
          String return4 = listSchedule(rooms);
          System.out.println(return4);
          logger.info(return4);
          break;
        case 5:
          String return5 = listRooms(rooms);
          System.out.println(return5);
          logger.info(return5);
          break;
        case 6:
          String return6 = exportToJson(rooms);
          System.out.println(return6);
          logger.info(return6);
          break;
        case 7:
          String return7 = importFromJson(rooms);
          System.out.println(return7);
          logger.info(return7);
          break;
        case 8:
          end = true;
          logger.info("User quit program");
          break;
        default:
          break;
      }

    }

  }

  /**
   * This method lists the schedules for a specified room. It calls getRoomName and the user is prompted to enter the
   * room number for an existing room in the room list. The schedule for that room is then displayed.
   *
   * @param roomList the list of rooms that have been created
   * @return String   an empty string
   */
  protected static String listSchedule(ArrayList<Room> roomList) {
    String roomName = getRoomName();
    System.out.println(roomName + " Schedule");
    System.out.println(SEPARATOR);

    if (getRoomFromName(roomList, roomName).getCapacity() == -1) {
      logger.error("Room does not exist.");
      return "Room does not exist.";
    } else {
      for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) {
        System.out.println(m.toString());
      }
      System.out.println(SEPARATOR);

      return "";
    }
  }

  /**
   * This is the main menu for the program. It lists all of the options that the user can choose from, and then accepts
   * the user's input for a number.
   *
   * @return int  the user's number that they entered
   */
  protected static int mainMenu() throws IOException {
    int selection = 0;

    System.out.println("Main Menu:");
    System.out.println("  1 - Add a room");
    System.out.println("  2 - Remove a room");
    System.out.println("  3 - Schedule a room");
    System.out.println("  4 - List Schedule");
    System.out.println("  5 - List Rooms");
    System.out.println("  6 - Export to JSON");
    System.out.println("  7 - Import from JSON");
    System.out.println("  8 - Quit program");
    System.out.println("Enter your selection: ");

    // Forces integer input
    try {
      selection = keyboard.nextInt();
      logger.info(selection);
    } catch (InputMismatchException exception) {
      logger.error(exception);
      System.out.println(ERR_MSG1);
      keyboard.nextLine();
    }

    if (selection < 1 || selection > 8) {
      logger.error("Please enter a valid number from 1 - 8.");
      System.out.println("Please enter a valid number from 1 - 8.");
    }
    return selection;
  }

  /**
   * This method adds a room to the room list. It prompts the user for a room name and a room capacity. The information
   * is then saved and added to a newly created room object. The room object is then added to the room list.
   *
   * @param roomList the list of rooms that have been created
   * @return String   a string that displays the room name and that it was created successfully.
   */
  protected static String addRoom(ArrayList<Room> roomList) throws IOException {
    int capacity;
    System.out.println("Add a room:");
    String name = getRoomName();
    System.out.println("Room capacity?");

    // Forces input to be an integer
    try {
      capacity = keyboard.nextInt();
      logger.info(capacity);
    } catch (InputMismatchException exception) {
      logger.error(exception);
      System.out.println(ERR_MSG1);
      keyboard.nextLine();
      return "";
    }

    if(capacity <= 5){
      logger.error("Room capacity must be at least 5.");
      System.out.println("Room capacity must be at least 5.");
      return "";
    }
    Room newRoom = new Room(name, capacity);
    roomList.add(newRoom);

    return "Room '" + newRoom.getName() + "' added successfully!";
  }

  /**
   * This method removes a room from the room list. It prompts the user for the name of a room. It then compares that
   * room name with the rooms in the room list to see if that room exists. If that room exists, it is then removed from
   * the list.
   *
   * @param roomList the list of rooms that have been created
   * @return String    a string that states the room was removed successfully.
   */
  protected static String removeRoom(ArrayList<Room> roomList) {
    System.out.println("Remove a room:");
    String roomName = getRoomName();

    // Checks if room exists
    if (findRoomIndex(roomList, roomName) == -1) {
      return "";
    }
    else {
      roomList.remove(findRoomIndex(roomList, roomName));
    }

    return "Room removed successfully!";
  }

  /**
   * This method lists the rooms in the room list. It iterates through each room in the room list and displays the room
   * name and capacity to the user.
   *
   * @param roomList the list of rooms that have been created
   * @return String    a string that displays how many rooms are in the room list
   */
  protected static String listRooms(ArrayList<Room> roomList) {
    System.out.println("Room Name - Capacity");
    System.out.println(SEPARATOR);

    for (Room room : roomList) {
      System.out.println(room.getName() + " - " + room.getCapacity());
    }

    System.out.println(SEPARATOR);

    return roomList.size() + " Room(s)";
  }

  /**
   * This method schedules a meeting for a specified room. It prompts the user for an existing room name. It then asks
   * for a start date and time, and an end date and time. It then saves the dates and times as a Timestamp for the start
   * and end dates/times.
   * <p>
   * It then checks to see if there is any schedule conflict with an existing meeting. If there is, then the method
   * returns an empty string.
   * <p>
   * If there is no schedule conflict, then it prompts the user for a subject for the meeting. It then creates the
   * meeting, and adds the meeting to the room.
   *
   * @param roomList the list of rooms that have been created
   * @return String    an empty string
   */
  protected static String scheduleRoom(ArrayList<Room> roomList) {
    System.out.println("Schedule a room:");
    String name = getRoomName();
    Room curRoom = getRoomFromName(roomList, name);

    if (curRoom.getCapacity() == -1) {
      return "";
    } else {
      System.out.println("Start Date? (yyyy-mm-dd):");
      String startDate = keyboard.nextLine();
      logger.info(startDate);
      System.out.println("Start Time? (h:mm):");
      String startTime = keyboard.nextLine();
      logger.info(startTime);
      startTime = startTime + ":00.0";

      System.out.println("End Date? (yyyy-mm-dd):");
      String endDate = keyboard.nextLine();
      logger.info(endDate);
      System.out.println("End Time? (h:mm):");
      String endTime = keyboard.nextLine();
      logger.info(endTime);
      endTime = endTime + ":00.0";

      // Checks if user input matches proper Timestamp format
      SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
      try {
        format.parse(startDate + " " + startTime);
      } catch (ParseException e) {
        System.out.println("Invalid format for start date or start time.");
        logger.error(e);
        return "";
      }

      Timestamp startTimestamp = Timestamp.valueOf(startDate + " " + startTime);

      // Same as above but for endTimestamp
      try {
        format.parse(endDate + " " + endTime);
      } catch (ParseException e) {
        System.out.println("Invalid format for end date or end time.");
        logger.error(e);
        return "";
      }

      Timestamp endTimestamp = Timestamp.valueOf(endDate + " " + endTime);

    /* Checks if a start time starts in the middle of an existing meeting, checks if an end time ends in the middle of
     * an existing meeting, checks if a meeting starts before and ends after an existing meeting, checks if a start time
     * is the same start time as an existing meeting, and checks if the end time is the same as the end time of
     * an existing meeting.
     */
      if (!roomList.isEmpty() && getRoomFromName(roomList, name).getMeetings().size() > 0) {
        for (int i = 0; i < roomList.size(); i++) {
          if (startTimestamp.after(getRoomFromName(roomList, name).getMeetings().get(0).getStartTime())
              && startTimestamp.before(getRoomFromName(roomList, name).getMeetings().get(0).getStopTime())) {
            System.out.println("Sorry, a meeting cannot start in the middle of another meeting");
            logger.error("Sorry, a meeting cannot start in the middle of another meeting");
            return "";
          } else if (startTimestamp.before(getRoomFromName(roomList, name).getMeetings().get(0).getStartTime())
              && endTimestamp.after(getRoomFromName(roomList, name).getMeetings().get(0).getStopTime())) {
            System.out.println("Sorry, a meeting cannot start before and end after another meeting.");
            logger.error("Sorry, a meeting cannot start before and end after another meeting.");
            return "";
          } else if (endTimestamp.after(getRoomFromName(roomList, name).getMeetings().get(0).getStartTime())
              && endTimestamp.before(getRoomFromName(roomList, name).getMeetings().get(0).getStopTime())) {
            System.out.println("Sorry, a meeting cannot end in the middle of another meeting");
            logger.error("Sorry, a meeting cannot end in the middle of another meeting");
            return "";
          } else if (startTimestamp.equals(getRoomFromName(roomList, name).getMeetings().get(0).getStartTime())) {
            System.out.println("Sorry, a meeting start time cannot be the same as another meeting start time");
            logger.error("Sorry, a meeting start time cannot be the same as another meeting start time");
            return "";
          } else if (endTimestamp.equals(getRoomFromName(roomList, name).getMeetings().get(0).getStopTime())) {
            System.out.println("Sorry, a meeting end time cannot be the same as another meeting end time");
            logger.error("Sorry, a meeting end time cannot be the same as another meeting end time");
            return "";
          }
        }
      }
      System.out.println("Subject?");
      String subject = keyboard.nextLine();
      logger.info(subject);

      Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);

      curRoom.addMeeting(meeting);

      return "Successfully scheduled meeting!\n";
    }
  }

  /**
   * This method allows the user to export the room list to JSON so that they can save the room list for a later
   * time. It uses gson to convert the roomlist into JSON, and then saves it to a file called "file.json" and stores it
   * in the RoomScheduler folder.
   *
   * @param roomList the list of rooms that have been created
   * @return String  a string that displays that the objects were successfully exported to JSON
   */
  public static String exportToJson(ArrayList<Room> roomList) throws IOException {
    Gson gson = new Gson();

    String json = gson.toJson(roomList);
    if(debugMode) {
      logger.debug(json);
      System.out.println(json);
    }

    try (FileWriter writer = new FileWriter("file.json")) {
      gson.toJson(roomList, writer);
    } catch (IOException e) {
      logger.error(e);
      return "Error related to JSON file has occurred.";
    }

    return "Successfully exported objects to JSON!";
  }

  /**
   * This method allows a user to import a JSON file into the program. It looks for a file name "file.json" in the
   * RoomScheduler folder and converts it into an array. It then adds that array to the room list in the program.
   *
   * @param roomList the list of rooms that have been created
   * @return String  a string that displays that the import was successful
   */
  public static String importFromJson(ArrayList<Room> roomList) throws IOException {
    Gson gson = new Gson();

    try (Reader reader = new FileReader("file.json")) {
      Room[] roomArr = gson.fromJson(reader, Room[].class);

      for (int i = 0; i < roomArr.length; i++) {
        roomList.add(roomArr[i]);
      }
    } catch (IOException e) {
      logger.error(e);
      return "Error related to JSON file has occurred.";
    }

    return "Successfully imported JSON to objects!";
  }

  /**
   * This method gets the room object based on the user inputted room name. It takes the room list and name and checks
   * if the room is in the room list. If it is, then it returns the room.
   *
   * @param roomList the list of rooms that have been created
   * @param name     the name of the room that the user entered
   * @return Room    the room that was found in the room list
   */
  protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
    // Checks if room exists in roomList
    if (findRoomIndex(roomList, name) == -1) {
      return new Room("error", -1);
    }
    return roomList.get(findRoomIndex(roomList, name));
  }

  /**
   * This method is used to get the index of the room. The index can then be used to find a user specified room. The
   * index starts at 0 and compares each room in the list to the user submitted room name. If the room is found, then
   * it exits the for loop. If it is not found then it increments the room index.
   *
   * @param roomList the list of rooms that have been created
   * @param roomName the name of the room that the user entered
   * @return int     the roomIndex where the room is located
   */
  protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
    int roomIndex = 0;

    if (roomList.isEmpty()) {
      logger.error("There are no rooms with that name.");
      System.out.println("There are no rooms with that name.");
      return -1;
    }

    for (Room room : roomList) {
      if (room.getName().compareTo(roomName) == 0) {
        break;
      }
      roomIndex++;
    }

    // If no room name matches the rooms in the room list, it returns a -1
    if (roomIndex >= roomList.size()) {
      logger.error("There are no rooms with that name.");
      System.out.println("There are no rooms with that name.");
      return -1;
    }

    return roomIndex;
  }

  /**
   * This method prompts the user for a room name and returns the user's input.
   *
   * @return String  the name of the room that the user entered.
   */
  protected static String getRoomName() {
    String selection;

    System.out.println("Room Name?");
    // Eats new line character input
    keyboard.nextLine();
    selection = keyboard.nextLine();
    logger.info(selection);

    return selection;
  }

}
