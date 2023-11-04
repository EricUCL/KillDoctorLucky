package game.controller;

import game.constants.PlayerType;
import game.constants.ProgramState;
import game.controller.command.AddComputerPlayer;
import game.controller.command.AddPlayer;
import game.controller.command.Command;
import game.controller.command.CreateWorldImage;
import game.controller.command.DisplayItemInfoByIndex;
import game.controller.command.DisplayPlayerDescription;
import game.controller.command.DisplayRoomInfoByIndex;
import game.controller.command.DisplayTargetInfo;
import game.controller.command.DisplayWorldInfo;
import game.controller.command.LookAround;
import game.controller.command.MovePet;
import game.controller.command.MovePlayer;
import game.controller.command.PickItem;
import game.controller.command.StartGame;
import game.model.KillDoctorLucky;
import game.model.PetImpl;
import game.model.Player;
import game.model.RoomImpl;
import game.model.TargetImpl;
import game.view.View;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controller class to run the game.
 */
public class GameControllerImpl implements GameController {
  private final KillDoctorLucky killDoctorLucky;
  private final Scanner in;
  private final CommandRegistry commandRegistry;
  private final View view;
  private final Readable fileReader;

  /**
   * Constructor to initialize the controller.
   *
   * @param in              Readable object.
   * @param view            Appendable object.
   * @param killDoctorLucky Model object.
   * @param fileReader      Readable object.
   */
  public GameControllerImpl(KillDoctorLucky killDoctorLucky, Readable in, View view,
      Readable fileReader) throws IOException {

    if (in == null) {
      throw new IllegalArgumentException("Readable cannot be null");
    }
    this.in = new Scanner(in);
    this.killDoctorLucky = killDoctorLucky;
    this.fileReader = fileReader;
    this.view = view;
    commandRegistry = new CommandRegistry();
  }

  @Override
  public void startGame() throws IOException {
    if (killDoctorLucky == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }
    if (fileReader == null) {
      throw new IllegalArgumentException("FileReader cannot be null");
    }
    this.readFile(fileReader);
    commandRegistry();

    while (true) {
      if (killDoctorLucky.getProgramState() == ProgramState.FINALIZING) {
        view.displayMessage("Game Over");
        return;
      }

      if (killDoctorLucky.getProgramState() == ProgramState.RUNNING) {
        view.displayMessage(killDoctorLucky.displayPrepareMessage());
        Player currentPlayer = killDoctorLucky.getCurrentPlayer();
        if (currentPlayer.getPlayerType() == PlayerType.COMPUTER) {
          view.prompt("----------------- Start ----------------");
          view.displayMessage("Computer player " + currentPlayer.getPlayerName() + " is playing");
          view.displayMessage(killDoctorLucky.computerPlayerTurn());
          view.prompt("------------------ End -----------------");
          continue;
        }
      }

      view.displayOptions(commandRegistry.getCommands(killDoctorLucky.getProgramState()));
      String input = in.nextLine();

      if ("q".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
        view.prompt("Exiting...\n");
        return;
      }

      Optional<Command> matchedCommandOpt = commandRegistry.getCommands(
              killDoctorLucky.getProgramState()).stream()
          .filter(cmd -> cmd.getIdentifier().equals(input)).findFirst();

      if (matchedCommandOpt.isEmpty()) {
        view.displayError("Invalid command. Try again.");
        continue;
      }

      view.prompt("----------------- Start ----------------");
      Command matchedCommand = matchedCommandOpt.get();
      Map<String, String> params = new HashMap<>();
      for (ParameterRequest paramRequest : matchedCommand.requiredParameters()) {
        view.prompt(paramRequest.getPromptMessage());
        params.put(paramRequest.getParamName(), in.nextLine());
        // check if the input is not empty
        if (params.get(paramRequest.getParamName()).isEmpty()) {
          view.displayError("Invalid input");
          break;
        }
      }

      CommandResult result = matchedCommand.execute(params);
      if (result.isError()) {
        view.displayError(result.getMessage());
      } else {
        view.displayMessage(result.getMessage());
      }
      view.prompt("------------------ End -----------------");
    }
  }

  /**
   * Function to register all the commands.
   */
  private void commandRegistry() {
    commandRegistry.registerCommand(ProgramState.INIT, new DisplayWorldInfo("1", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT,
        new DisplayRoomInfoByIndex("2", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT,
        new DisplayItemInfoByIndex("3", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new CreateWorldImage("4", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new DisplayTargetInfo("5", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new AddPlayer("7", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new AddComputerPlayer("8", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new StartGame("9", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.RUNNING, new MovePlayer("1", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.RUNNING, new LookAround("2", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.RUNNING, new PickItem("3", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.RUNNING,
        new DisplayPlayerDescription("4", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.RUNNING,
        new DisplayTargetInfo("5", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.RUNNING, new MovePet("6", killDoctorLucky));
  }

  /**
   * Function for reading the file and populating the data structures.
   *
   * @param fileReader path of the file
   * @throws IllegalArgumentException if file isn't found
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
        throw new IllegalArgumentException("Target health can not be negative!");
      }
      int defaultTargetRoomIndex = 0;
      killDoctorLucky.setTarget(new TargetImpl(targetName, targetHealth, defaultTargetRoomIndex));

      // Pet Name
      line = br.readLine();
      String petName = line.trim();
      killDoctorLucky.setPet(new PetImpl(petName, defaultTargetRoomIndex));

      // check numRooms not null or empty
      int numRooms = Integer.parseInt(br.readLine());
      if (numRooms <= 0) {
        throw new IllegalArgumentException("No rooms in the world!");
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
        killDoctorLucky.addItems(i, itemDamage, itemName, itemRoomIndex);
      }
      killDoctorLucky.initialMap();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      view.prompt(e.getMessage());
    }
  }
}