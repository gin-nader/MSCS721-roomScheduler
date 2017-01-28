package com.marist.mscs721;

import com.google.gson.Gson;

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
 * This allows a user to create rooms and then create meetings for those rooms.
 *
 * @author Tom Ginader
 * @since 2017-01-26
 */
public class RoomScheduler {
  protected static Scanner keyboard = new Scanner(System.in);

  /**
   * Main method for the program. This is where the main menu is called, and the user is prompted to enter a number
   * value that corresponds to one of the methods described above. After a number is entered, it then calls that
   * specific function that takes a list of rooms as a parameter. This is also where the list of rooms is created.
   */
  public static void main(String[] args){
    Boolean end = false;
    ArrayList<Room> rooms = new ArrayList<Room>();

    while (!end) {
      switch (mainMenu()) {

        case 1:
          System.out.println(addRoom(rooms));
          break;
        case 2:
          System.out.println(removeRoom(rooms));
          break;
        case 3:
          System.out.print(scheduleRoom(rooms));
          break;
        case 4:
          System.out.println(listSchedule(rooms));
          break;
        case 5:
          System.out.println(listRooms(rooms));
          break;
        case 6:
          System.out.println(exportToJson(rooms));
          break;
        case 7:
          System.out.println(importFromJson(rooms));
          break;
      }

    }

  }

  /**
   * This method lists the schedules for a specified room. It called getRoomName and the user is prompted to enter the
   * room number for an existing room in the room list. The schedule for that room is then displayed.
   *
   * @param  roomList  the list of rooms that have been created
   * @return   String   an empty string
   */
  protected static String listSchedule(ArrayList<Room> roomList) {
    String roomName = getRoomName();
    System.out.println(roomName + " Schedule");
    System.out.println("---------------------");

    for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) {
      System.out.println(m.toString());
    }

    return "";
  }

  /**
   * This is the main menu for the program. It lists all of the options that the user can choose from, and then accepts
   * the user's input for a number.
   *
   * @return    int  the user's number that they entered
   */
  protected static int mainMenu() throws InputMismatchException{
    int selection = 0;

    System.out.println("Main Menu:");
    System.out.println("  1 - Add a room");
    System.out.println("  2 - Remove a room");
    System.out.println("  3 - Schedule a room");
    System.out.println("  4 - List Schedule");
    System.out.println("  5 - List Rooms");
    System.out.println("  6 - Export to JSON");
    System.out.println("  7 - Import from JSON");
    System.out.println("Enter your selection: ");

    try
    {
      selection = keyboard.nextInt();
    }
    catch (InputMismatchException exception)
    {
      System.out.println("Integers only, please.");
      keyboard.nextLine();
    }

    if (selection < 1 || selection > 7){
      System.out.println("Please enter a valid number from 1 - 7.");
    }

    return selection;
  }

  /**
   * This method adds a room to the room list. It prompts the user for a room name and a room capacity. The information
   * is then saved and added to a newly created room object. The room object is then added to the room list.
   *
   * @param  roomList  the list of rooms that have been created
   * @return   String   a string that displays the room name and that it was created successfully.
   */
  protected static String addRoom(ArrayList<Room> roomList) {
    int capacity = 0;

    System.out.println("Add a room:");
    String name = getRoomName();
    System.out.println("Room capacity?");

    // Forces input to be an integer
    try
    {
      capacity = keyboard.nextInt();
    }
    catch (InputMismatchException exception)
    {
      System.out.println("Integers only, please.");
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
   * @param  roomList  the list of rooms that have been created
   * @return  String    an string that states the room was removed successfully.
   */
  protected static String removeRoom(ArrayList<Room> roomList) {
    System.out.println("Remove a room:");

    if(findRoomIndex(roomList, getRoomName()) == -1){
      return "";
    }

    roomList.remove(findRoomIndex(roomList, getRoomName()));

    return "Room removed successfully!";
  }

  /**
   * This method lists the rooms in the room list. It iterates through each room in the room list and displays the room
   * name and capacity to the user.
   *
   * @param  roomList  the list of rooms that have been created
   * @return  String    a string that displays how many rooms are in the room list
   */
  protected static String listRooms(ArrayList<Room> roomList) {
    System.out.println("Room Name - Capacity");
    System.out.println("---------------------");

    for (Room room : roomList) {
      System.out.println(room.getName() + " - " + room.getCapacity());
    }

    System.out.println("---------------------");

    return roomList.size() + " Room(s)";
  }

  /**
   * This method schedules a meeting for a specified room. It prompts the user for an existing room name. It then asks
   * for a start date and time, and an end date and time. It then saves the dates and times as a Timestamp for the start
   * and end dates/times.
   *
   * It then checks to see if there is any schedule conflict with an existing meeting. If there is, then the method is
   * called again and the user is asked to enter different meeting times.
   *
   * If there is no schedule conflict, then it prompts the user for a subject for the meeting. It then retrieves the
   * room, creates the meeting, and adds the meeting to the room.
   *
   * @param  roomList  the list of rooms that have been created
   * @return  String    an empty string
   */
  protected static String scheduleRoom(ArrayList<Room> roomList) {
    System.out.println("Schedule a room:");
    String name = getRoomName();
    Room curRoom = getRoomFromName(roomList, name);

    // Makes sure room exists before continuing
    if(curRoom == null){
      return "";
    }

    System.out.println("Start Date? (yyyy-mm-dd):");
    String startDate = keyboard.nextLine();
    System.out.println("Start Time? (h:mm):");
    String startTime = keyboard.nextLine();
    startTime = startTime + ":00.0";

    System.out.println("End Date? (yyyy-mm-dd):");
    String endDate = keyboard.nextLine();
    System.out.println("End Time? (h:mm):");
    String endTime = keyboard.nextLine();
    endTime = endTime + ":00.0";



    SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    try{
      format.parse(startDate + " " + startTime);
    }
    catch(ParseException e)
    {
      System.out.println("Invalid format for start date or start time.");
      return "";
    }

    Timestamp startTimestamp = Timestamp.valueOf(startDate + " " + startTime);



    try{
      format.parse(endDate + " " + endTime);
    }
    catch(ParseException e)
    {
      System.out.println("Invalid format for end date or end time.");
      return "";
    }

    Timestamp endTimestamp = Timestamp.valueOf(endDate + " " + endTime);

    /* Checks if a start time starts in the middle of an existing meeting, checks if an end time ends in the middle of
     * an existing meeting, checks if a meeting starts before and ends after an existing meeting, checks if a start time
     * is the same start time as an existing meeting, and checks if the end time is the same as the end time of
     * an existing meeting.
     *
     * If this condition is met, it calls the function again and prompts the user to type a different meeting time.
     */
    if(!roomList.isEmpty()) {
      for (int i = 0; i < roomList.size(); i++) {
        if (startTimestamp.after(getRoomFromName(roomList, name).getMeetings().get(0).getStartTime())
            && startTimestamp.before(getRoomFromName(roomList, name).getMeetings().get(0).getStopTime()) ||
            startTimestamp.before(getRoomFromName(roomList, name).getMeetings().get(0).getStartTime())
                && endTimestamp.after(getRoomFromName(roomList, name).getMeetings().get(0).getStopTime()) ||
            endTimestamp.after(getRoomFromName(roomList, name).getMeetings().get(0).getStartTime())
                && endTimestamp.before(getRoomFromName(roomList, name).getMeetings().get(0).getStopTime())||
            startTimestamp.equals(getRoomFromName(roomList, name).getMeetings().get(0).getStartTime()) ||
            endTimestamp.equals(getRoomFromName(roomList, name).getMeetings().get(0).getStopTime())) {
          System.out.println("Sorry, this meeting time conflicts with another meeting. " +
              "Please select another meeting time.");
          return "";
        }
      }
    }

    System.out.println("Subject?");
    String subject = keyboard.nextLine();

    Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);

    curRoom.addMeeting(meeting);

    return "Successfully scheduled meeting!\n";
  }

  /**
   * This method lists allows the user to export the room list to JSON so that they can saved the room list for a later
   * time. It uses gson to convert the roomlist into JSON, and then saves it to a file called "file.json" and stores it
   * in the RoomScheduler folder.
   *
   * @param  roomList  the list of rooms that have been created
   * @return    String  a string that displays that the objects were successfully exported to JSON
   */
  protected static String exportToJson(ArrayList<Room> roomList){
    Gson gson = new Gson();

    String json = gson.toJson(roomList);
    System.out.println(json);

    try (FileWriter writer = new FileWriter("file.json")) {

      gson.toJson(roomList, writer);

    } catch (IOException e) {
      e.printStackTrace();
    }

    return "Successfully exported objects to JSON!";
  }

  /**
   * This method allows a user to import a JSON file into the program. It looks for a file name "file.json" in the
   * RoomScheduler folder and converts it into an array. It then adds that array to the room list in the program.
   *
   * @param  roomList  the list of rooms that have been created
   * @return    String  a string that displays that the import was successful
   */
  protected static String importFromJson(ArrayList<Room> roomList){
    Gson gson = new Gson();

    try (Reader reader = new FileReader("file.json")) {

        Room[] roomArr = gson.fromJson(reader, Room[].class);

        for (int i = 0; i < roomArr.length; i++) {
          roomList.add(roomArr[i]);
        }

    } catch (IOException e) {
      e.printStackTrace();
    }

    return "Successfully imported JSON to objects!";
  }

  /**
   * This method gets the room object based on the user inputted room name. It takes the room list and name and checks
   * if the room is in the room list. If it is, then it returns the room.
   *
   * @param  roomList  the list of rooms that have been created
   * @param  name      the name of the room that the user entered
   * @return  Room    the room that was found in the room list
   */
  protected static Room getRoomFromName(ArrayList<Room> roomList, String name) {
    if(findRoomIndex(roomList,name) == -1){
      return null;
    }

    return roomList.get(findRoomIndex(roomList, name));
  }

  /**
   * This method is used to get the index of the room. The index can then be used to find a user specified room. The
   * index starts at 0 and compares each room in the list to the user submitted room name. If the room is found, then
   * it exits the for loop. If it is not found then it increments the room index.
   *
   * @param  roomList  the list of rooms that have been created
   * @param  roomName  the name of the room that the user entered
   * @return    int     the roomIndex where the room is located
   */
  protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) {
    int roomIndex = 0;

    if(roomList.isEmpty()){
      System.out.println("There are no rooms with that name.");
      return -1;
    }

    for (Room room : roomList) {
      if (room.getName().compareTo(roomName) == 0) {
        break;
      }
      roomIndex++;
    }

    if(roomIndex >= roomList.size()){
      System.out.println("There are no rooms with that name.");
      return -1;
    }

    return roomIndex;
  }

  /**
   * This method prompts the user for a room name and returns the user's input.
   *
   * @return  String  the name of the room that the user entered.
   */
  protected static String getRoomName() {
    String selection = "";

    System.out.println("Room Name?");
    // Eats new line character input
    keyboard.nextLine();
    selection = keyboard.nextLine();

    return selection;
  }

}
