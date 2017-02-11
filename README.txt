Synopsis

This program allows a user to create a room and schedule a meeting for a specified room. It features a main menu with
several options. The user has several options to choose from such as add a room, remove a room, schedule a room, list
the schedule, list the rooms, export the rooms and meetings to JSON, and import the rooms and meetings from JSON. The
user can also quit the program from the main menu.

Code Example

Here is a snippet of the mainMenu() function. You press a number from 1-8 and you get a corresponding option.

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
    } catch (InputMismatchException exception) {
      LOGGER.setUseParentHandlers(false);
      FileHandler fileHandler = new FileHandler(LOG_FILE);
      LOGGER.addHandler(fileHandler);
      LOGGER.log(Level.INFO, ERR_MSG1, exception);
      System.out.println(ERR_MSG1);
      keyboard.nextLine();
    }

    if (selection < 1 || selection > 8) {
      System.out.println("Please enter a valid number from 1 - 8.");
    }

    return selection;
  }

Some basic functionality such as listing all of the rooms and their associated meetings is shown here:

  protected static String listRooms(ArrayList<Room> roomList) {
    System.out.println("Room Name - Capacity");
    System.out.println(SEPARATOR);

    for (Room room : roomList) {
      System.out.println(room.getName() + " - " + room.getCapacity());
    }

    System.out.println(SEPARATOR);

    return roomList.size() + " Room(s)";
  }

This is the method that allows the information that the user enters to be exported and saved to a json file:

  protected static String exportToJson(ArrayList<Room> roomList) throws IOException {
    Gson gson = new Gson();

    String json = gson.toJson(roomList);
    System.out.println(json);

    try (FileWriter writer = new FileWriter("file.json")) {

      gson.toJson(roomList, writer);

    } catch (IOException e) {
      LOGGER.setUseParentHandlers(false);
      FileHandler fileHandler = new FileHandler(LOG_FILE);
      LOGGER.addHandler(fileHandler);
      LOGGER.log(Level.INFO, "IOException", e);
    }

    return "Successfully exported objects to JSON!";
  }

It uses gson to convert list of java objects into JSON, and it use FileWriter to create and write to the file "file.json"

Motivation

This project exists because it is an ongoing project for MSCS 721 at Marist College. As the semester goes on, it will
continue to expand and become more robust through the use of testing and verification.

Installation

To download and install this project, simply clone it from git.

  usage:
   git clone https://github.com/gin-nader/MSCS721-roomScheduler.git

API Reference

The API documents are located at: RoomScheduler\target\site\apidocs

Tests

There are currently no tests that you can run for this program.

Contributors

Tom Ginader - Thomas.Ginader1@marist.edu
Michael Gildein - Michael.Gildein1@marist.edu

License

This project is licensed under the MIT License - see the LICENSE.txt file for details
