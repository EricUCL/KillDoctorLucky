package game.controller;

import game.model.KillDoctorLucky;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Controller class to run the game.
 */
public class GameControllerImpl implements GameController{
  private final List<String> commands;
  private final Map<String, Runnable> gameCommands;
  private final KillDoctorLucky killDoctorLucky;
  private Scanner in;
  private Appendable out;

  /**
   * Constructor to initialize the controller.
   *
   * @param killDoctorLucky Model object.
   * @param in              Readable object.
   * @param out             Appendable object.
   */
  public GameControllerImpl(KillDoctorLucky killDoctorLucky, Readable in, Appendable out) {
    this.killDoctorLucky = killDoctorLucky;
    this.in = new Scanner(in);
    this.out = out;

    gameCommands = new HashMap<>();
    commands = new ArrayList<>();
  }

  /**
   * Method to start the game.
   */
  @Override
  public void startGame() {
    if (killDoctorLucky == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    loadCmdLabels();
    loadCommand();
    readExecuteCommands();
  }

  /**
   * Method to load the commands.
   */
  private void loadCmdLabels() {
    commands.addAll(Arrays.asList("Display World Info", "Display Room Info By Index",
        "Display Item Info By Index", "Create World Image", "Move Target", "Display Target Info"));
  }

  /**
   * Method to load the commands.
   */
  private void loadCommand() {
    gameCommands.putAll(new HashMap<String, Runnable>() {
      {
        put("1", () -> displayWorldInfoCallBack());
        put("2", () -> displayRoomInfoCallBack());
        put("3", () -> displayItemInfoCallBack());
        put("4", () -> createImageCallBack());
        put("5", () -> moveTargetCallBack());
        put("6", () -> displayTargetInfoCallBack());
      }
    });
  }

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
  private void displayWorldInfoCallBack() {
    try {
      this.out.append("----------------- Start ----------------\n");
      this.out.append(killDoctorLucky.getWorldDesc()).append("\n");
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
   * @param ip   Input command.
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
