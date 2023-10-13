package game.controller;

import game.controller.command.DisplayWorldInfo;
import game.model.KillDoctorLucky;
import game.model.RoomImpl;
import game.model.TargetImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Controller class to run the game.
 */
public class GameControllerImpl implements GameController {
  private final List<String> commands;
  private final Map<String, Runnable> gameCommands;
  private KillDoctorLucky killDoctorLucky;
  private Scanner in;
  private Appendable out;
  private CommandRegistry commandRegistry;
  //  private int maxTurns;

  /**
   * Constructor to initialize the controller.
   *
   * @param in              Readable object.
   * @param out             Appendable object.
   * @param killDoctorLucky Model object.
   */
  public GameControllerImpl(KillDoctorLucky killDoctorLucky, Readable in, Appendable out,
      Readable fileReader) throws IOException {

    this.in = new Scanner(in);
    this.out = out;
    this.killDoctorLucky = killDoctorLucky;
    gameCommands = new HashMap<>();
    commands = new ArrayList<>();
    this.readFile(fileReader);

    commandRegistry = new CommandRegistry();
  }

  /**
   * Method to start the game.
   */
  @Override
  public void startGame() throws IOException {
    if (killDoctorLucky == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    commandRegistry();
    //    loadCmdLabels();
    //    loadCommand();
    //    readExecuteCommands();

    while (true) {
      displayOptions();
      String input = in.nextLine();

      if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
        out.append("Exiting...");
        return;
      }

      Optional<Command> matchedCommand = commandRegistry.getCommands(ProgramState.INIT).stream()
          .filter(cmd -> cmd.getIdentifier().equals(input)).findFirst();

      if (matchedCommand.isEmpty()) {
        System.out.println("Invalid command. Try again.");
      } else {
        displayWorldInfoCallBack(matchedCommand.get());
      }
    }
  }

  private void displayOptions() throws IOException {
    out.append("Choose a command:").append("\n");
    for (Command command : commandRegistry.getCommands(ProgramState.INIT)) {
      out.append(command.getIdentifier()).append(": ").append(command.getDescription()).append("\n");
    }
    out.append("q: Quit").append("\n");
  }

  private void commandRegistry() {
    commandRegistry.registerCommand(ProgramState.INIT, new DisplayWorldInfo("1", killDoctorLucky));

  }

  /**
   * Function for reading the file and populating the data structures.
   *
   * @param fileReader path of the file
   * @throws IllegalArgumentException if file not found
   */
  private void readFile(Readable fileReader) throws IOException {
    try {
      String line;
      BufferedReader br = new BufferedReader((Reader) fileReader);
      line = br.readLine();

      String[] worldInfo = line.trim().split("\\s+");
      killDoctorLucky.setNumCols(Integer.parseInt(worldInfo[0]));
      killDoctorLucky.setNumRows(Integer.parseInt(worldInfo[1]));
      killDoctorLucky.setWorldName(
          line.substring(worldInfo[0].length() + worldInfo[1].length() + 2));
      line = br.readLine();
      String[] targetInfo = line.trim().split("\\s+");
      int targetHealth = Integer.parseInt(targetInfo[0]);
      String targetName = line.substring(targetInfo[0].length() + 1);
      if (targetHealth <= 0) {
        throw new IllegalArgumentException("Target health can't be negative");
      }
      killDoctorLucky.setTarget(new TargetImpl(targetName, targetHealth, 0));

      // check numRooms not null or empty
      int numRooms = Integer.parseInt(br.readLine());
      if (numRooms <= 0) {
        throw new IllegalArgumentException("No rooms in the world");
      }
      for (int i = 0; i < numRooms; i++) {
        line = br.readLine();
        String[] roomInfo = line.trim().split("\\s+");
        int upperLeftRow = Integer.parseInt(roomInfo[0]);
        int upperLeftCol = Integer.parseInt(roomInfo[1]);
        int lowerRightRow = Integer.parseInt(roomInfo[2]);
        int lowerRightCol = Integer.parseInt(roomInfo[3]);
        StringBuilder roomNameBuilder = new StringBuilder();
        for (int j = 4; j < roomInfo.length; j++) {
          roomNameBuilder.append(roomInfo[j]);
          if (j < roomInfo.length - 1) {
            roomNameBuilder.append(" ");
          }
        }
        String roomName = roomNameBuilder.toString();
        //        System.out.println("Room name: " + roomName);
        killDoctorLucky.addRooms(
            new RoomImpl(i, roomName, upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol));
      }

      int numItems = Integer.parseInt(br.readLine());
      for (int i = 0; i < numItems; i++) {
        line = br.readLine();
        String[] itemInfo = line.trim().split("\\s+");
        int itemRoomIndex = Integer.parseInt(itemInfo[0]);
        int itemDamage = Integer.parseInt(itemInfo[1]);
        StringBuilder itemNameBuilder = new StringBuilder();
        for (int j = 2; j < itemInfo.length; j++) {
          itemNameBuilder.append(itemInfo[j]);
          if (j < itemInfo.length - 1) {
            itemNameBuilder.append(" ");
          }
        }
        String itemName = itemNameBuilder.toString();
        killDoctorLucky.addItems(itemDamage, itemName, itemRoomIndex);
      }
      killDoctorLucky.initialMap();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      out.append(e.getMessage());
    }
  }

  /**
   * Method to load the commands.
   */
  private void loadCmdLabels() {
    commands.addAll(Arrays.asList("Display World Info", "Display Room Info By Index",
        "Display Item Info By Index", "Create World Image", "Move Target", "Display Target Info"));
  }

  //  /**
  //   * Method to load the commands.
  //   */
  //  private void loadCommand() {
  //    gameCommands.putAll(new HashMap<String, Runnable>() {
  //      {
  //        put("1", () -> displayWorldInfoCallBack());
  //        put("2", () -> displayRoomInfoCallBack());
  //        put("3", () -> displayItemInfoCallBack());
  //        put("4", () -> createImageCallBack());
  //        put("5", () -> moveTargetCallBack());
  //        put("6", () -> displayTargetInfoCallBack());
  //      }
  //    });
  //  }

  /**
   * Method to display the target information.
   */
  private void displayTargetInfoCallBack() {
    try {
      this.out.append("----------------- Start ----------------\n");
      this.out.append(killDoctorLucky.displayTargetInfo()).append("\n");
      this.out.append("------------------ End -----------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing.");
    }
  }

  /**
   * Method to move the target.
   */
  private void moveTargetCallBack() {
    try {
      this.out.append("----------------- Start ----------------\n");
      this.out.append(killDoctorLucky.moveTarget()).append("\n");
      this.out.append("------------------ End -----------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing.");
    }
  }

  /**
   * Method to create the image.
   */
  private void createImageCallBack() {
    try {
      this.out.append("----------------- Start ----------------\n");
      this.out.append(killDoctorLucky.createBufferedImage()).append("\n");
      this.out.append("------------------ End -----------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing.");
    }
  }

  /**
   * Method to display the item information.
   */
  private void displayItemInfoCallBack() {
    try {
      this.out.append("----------------- Start ----------------\n");
      this.out.append("Enter item index: ");
      int itemIdx = Integer.parseInt(in.next());
      this.out.append(killDoctorLucky.displayItemInfo(itemIdx)).append("\n");
      this.out.append("------------------ End -----------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing.");
    }
  }

  /**
   * Method to display the room information.
   */
  private void displayRoomInfoCallBack() {
    try {
      this.out.append("----------------- Start ----------------\n");
      this.out.append("Enter room index: ");
      int roomIdx = Integer.parseInt(in.next());
      this.out.append(killDoctorLucky.displayRoomDescription(roomIdx)).append("\n");
      this.out.append("------------------ End -----------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing.");
    }
  }

  /**
   * Method to display the world information.
   */
  private void displayWorldInfoCallBack(Command command) {
    try {
      this.out.append("----------------- Start ----------------\n");
      this.out.append(command.execute()).append("\n");
      this.out.append("------------------ End -----------------\n");
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing.");
    }
  }

  /**
   * Method to read and execute the commands.
   */
  private void readExecuteCommands() {
    String input = "";
    try {
      do {
        // pre game state
        this.out.append("Available commands are: \n");
        displayList(commands);
        input = in.next();

        if ("q".equalsIgnoreCase(input)) {
          this.out.append("Ending Game\n");
          break;
        }
        executor(gameCommands, input);
      } while (!"7".equalsIgnoreCase(input));
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing.");
    }
  }

  /**
   * Method to display the list of commands.
   *
   * @param cmd List of commands.
   */
  private void displayList(List<String> cmd) {
    try {
      for (int i = 0; i < cmd.size(); i++) {
        this.out.append(String.format("%d. %s\n", i + 1, cmd.get(i)));
      }
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }

  /**
   * Method to execute the commands.
   *
   * @param cmd Map of commands.
   * @param ip  Input command.
   */
  private void executor(Map<String, Runnable> cmd, String ip) {
    try {
      Runnable runnable = cmd.get(ip);
      if (runnable == null) {
        this.out.append(String.format("Invalid command: %s. Please check once again!!!", ip));
        return;
      }
      // execute the command
      runnable.run();
    } catch (IOException e) {
      throw new IllegalStateException("Appendable write is failing!!!");
    }
  }
}
