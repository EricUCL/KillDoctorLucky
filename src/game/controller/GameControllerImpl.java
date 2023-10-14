package game.controller;

import game.controller.command.*;
import game.model.KillDoctorLucky;
import game.model.RoomImpl;
import game.model.TargetImpl;
import game.view.CommandLineView;
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
  private KillDoctorLucky killDoctorLucky;
  private Scanner in;
  private Appendable out;
  private CommandRegistry commandRegistry;
  //  private int maxTurns;
  private View view;

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
    this.readFile(fileReader);
    this.view = new CommandLineView(this.in, this.out);
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

    while (true) {
      view.displayOptions(commandRegistry.getCommands(ProgramState.INIT));
      String input = in.nextLine();

      if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
        out.append("Exiting...\n");
        return;
      }

      Optional<Command> matchedCommandOpt = commandRegistry.getCommands(ProgramState.INIT).stream()
          .filter(cmd -> cmd.getIdentifier().equals(input)).findFirst();

      if (matchedCommandOpt.isEmpty()) {
        view.displayError("Invalid command. Try again.");
        continue;
      }
      view.prompt("----------------- Start ----------------\n");
      Command matchedCommand = matchedCommandOpt.get();
      Map<String, String> params = new HashMap<>();
      for (ParameterRequest paramRequest : matchedCommand.requiredParameters()) {
        view.prompt(paramRequest.getPromptMessage());
        params.put(paramRequest.getParamName(), in.nextLine());
      }

      CommandResult result = matchedCommand.execute(params);
      if (result.isError()) {
        view.displayError(result.getMessage());
      } else {
        view.displayMessage(result.getMessage());
      }
      view.prompt("------------------ End -----------------\n");
    }
  }

  private void commandRegistry() {
    commandRegistry.registerCommand(ProgramState.INIT, new DisplayWorldInfo("1", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT,
        new DisplayRoomInfoByIndex("2", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT,
        new DisplayItemInfoByIndex("3", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new CreateWorldImage("4", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new MoveTarget("5", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new DisplayTargetInfo("6", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new AddPlayer("7", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new AddComputerPlayer("8", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.INIT, new StartGame("9", killDoctorLucky));
    commandRegistry.registerCommand(ProgramState.RUNNING, new MovePlayer("1", killDoctorLucky));
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
}