package game.controller;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import game.constants.ProgramState;
import game.controller.command.AddComputerPlayer;
import game.controller.command.AddPlayer;
import game.controller.command.AttackTarget;
import game.controller.command.DisplayPlayerDescription;
import game.controller.command.DisplayWorldInfo;
import game.controller.command.LookAround;
import game.controller.command.MovePlayer;
import game.controller.command.PickItem;
import game.controller.command.StartGame;
import game.model.Item;
import game.model.KillDoctorLucky;
import game.model.PetImpl;
import game.model.Player;
import game.model.Room;
import game.model.RoomImpl;
import game.model.TargetImpl;
import game.view.GuiView;
import game.view.listeners.ButtonListener;
import game.view.listeners.KeyboardListener;

public class GuiGameControllerImpl implements GameController {
  private KillDoctorLucky model;
  private GuiView view;
  private Readable fileReader;
  private final CommandRegistry commandRegistry;

  public GuiGameControllerImpl(KillDoctorLucky model, GuiView view, Readable fileReader) {
    this.model = model;
    this.view = view;
    this.fileReader = fileReader;
    commandRegistry = new CommandRegistry();
  }

  @Override
  public void startGame() throws IOException {
    Objects.requireNonNull(model, "Model cannot be null");
    Objects.requireNonNull(view, "View cannot be null");
    Objects.requireNonNull(fileReader, "FileReader cannot be null");

    //    readFile(fileReader);
    //    commandRegistry();
    configureButtonListener();
    configureKeyBoardListener();
    //    configureMouseClickListener();
    //    new CreateWorldImage("createWorldImage", model).execute(null);
  }

  /**
   * Function for reading the file and populating the data structures.
   *
   * @param fileReader path of the file
   * @throws IllegalArgumentException if file isn't found
   */
  public void readFile(Readable fileReader) throws IOException {
    try (BufferedReader br = new BufferedReader((Reader) fileReader)) {
      String line = br.readLine();
      String[] worldInfo = line.trim().split("\\s+");
      int numCols = Integer.parseInt(worldInfo[0]);
      int numRows = Integer.parseInt(worldInfo[1]);
      String worldName = line.substring(worldInfo[0].length() + worldInfo[1].length() + 2);
      line = br.readLine();
      String[] targetInfo = line.trim().split("\\s+");
      int targetHealth = Integer.parseInt(targetInfo[0]);
      String targetName = line.substring(targetInfo[0].length() + 1);

      if (targetHealth <= 0) {
        throw new IllegalArgumentException("Target health can not be negative!");
      }

      int defaultTargetRoomIndex = 0;
      model.setNumCols(numCols);
      model.setNumRows(numRows);
      model.setWorldName(worldName);
      model.setTarget(new TargetImpl(targetName, targetHealth, defaultTargetRoomIndex));

      // Pet Name
      line = br.readLine();
      String petName = line.trim();
      model.setPet(new PetImpl(petName, defaultTargetRoomIndex));

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
        model.addRooms(
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
        model.addItems(i, itemDamage, itemName, itemRoomIndex);
      }

      model.initialMap();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private void configureButtonListener() {
    System.out.println("configureButtonListener");
    Map<String, Runnable> buttonClickedMap = new HashMap<>();

    buttonClickedMap.put("Open Set Map Dialog", () -> {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Choose Map File");
      fileChooser.setCurrentDirectory(new File("res"));
      int result = fileChooser.showOpenDialog(view);
      if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        String filepath = selectedFile.getAbsolutePath();
        System.out.println("Selected file: " + filepath);
        try {
          this.fileReader = new InputStreamReader(new FileInputStream(filepath));
          readFile(fileReader);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      String maxTurnsString = JOptionPane.showInputDialog(view, "Enter Max Turns:");
      if (maxTurnsString != null && !maxTurnsString.isEmpty()) {
        try {
          int maxTurns = Integer.parseInt(maxTurnsString);
          model.setMaxTurns(maxTurns);
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(view, "Invalid max turns number", "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
      String message = new DisplayWorldInfo("displayWorldInfo", model).execute(null).getMessage();
      JOptionPane.showMessageDialog(null, message);
      view.enableButtons();
    });

    buttonClickedMap.put("Start Game Button", () -> {
      new StartGame("startGame", model).execute(null);
      view.initialComponents();
      view.updateView(this);
    });

    buttonClickedMap.put("Add Player Button", () -> {
      String[] options = { "Computer Player", "Human Player" };
      int playerType = JOptionPane.showOptionDialog(null, "Please select player type", "Add Player",
          JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
      if (playerType == 0) {
        System.out.println("Computer Player");
        String message = new AddComputerPlayer("addComputerPlayer", model).execute(null)
            .getMessage();
        JOptionPane.showMessageDialog(null, message);
      } else {
        String playerName = JOptionPane.showInputDialog("Please input player name");
        String maxItemsLimit = JOptionPane.showInputDialog("Please input max item limit");
        Map<String, String> params = new HashMap<>();
        params.put("playerName", playerName);
        params.put("maxItemsLimit", maxItemsLimit);
        String message = new AddPlayer("addPlayer", model).execute(params).getMessage();
        JOptionPane.showMessageDialog(null, message);
      }
      view.enableButtons();
    });

    buttonClickedMap.put("Exit Button", () -> System.exit(0));

    ButtonListener buttonListener = new ButtonListener();
    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  public void handlePlayerClick(Player player) {
    System.out.println("Player clicked: " + player.getPlayerName());
    Map<String, String> params = new HashMap<>();
    params.put("playerName", player.getPlayerName());
    String message = new DisplayPlayerDescription("displayPlayerDescription", model).execute(params)
        .getMessage();
    JOptionPane.showMessageDialog(null, message);
  }

  public void handleRoomClick(Room room) {
    System.out.println("Room clicked: " + room.getName());
    Map<String, String> params = new HashMap<>();
    params.put("roomIndex", String.valueOf(room.getIndex()));
    if (!new MovePlayer("movePlayer", model).execute(params).isError()) {
      updateView();
    }
  }

  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyPresses.put(KeyEvent.VK_P, this::pickItem);
    keyPresses.put(KeyEvent.VK_L, this::lookAround);
    keyPresses.put(KeyEvent.VK_A, this::attackTarget);
    keyPresses.put(KeyEvent.VK_M, this::movePet);

    KeyboardListener kbd = new KeyboardListener();

    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbd);
  }

  private void pickItem() {
    List<Item> itemList = model.getCurrentRoom().getItems();
    if (itemList.isEmpty()) {
      JOptionPane.showMessageDialog(view, "No items in current room", "Error",
          JOptionPane.ERROR_MESSAGE);
    } else {
      String[] itemNames = itemList.stream()
          .map(item -> item.getName() + " (Damage: " + item.getDamage() + ")")
          .toArray(String[]::new);
      String chosenItem = (String) JOptionPane.showInputDialog(view, "Choose an item:", "Pick Item",
          JOptionPane.QUESTION_MESSAGE, null, itemNames, itemNames[0]);
      if (chosenItem != null) {
        int itemIndex = Arrays.asList(itemNames).indexOf(chosenItem);
        itemIndex = itemList.get(itemIndex).getId();
        Map<String, String> params = new HashMap<>();
        params.put("itemIndex", String.valueOf(itemIndex));
        String message = new PickItem("pickItem", model).execute(params).getMessage();
        JOptionPane.showMessageDialog(null, message);
      }

      updateView();

    }
  }

  private void lookAround() {
    String message = new LookAround("lookAround", model).execute(null).getMessage();
    JOptionPane.showMessageDialog(null, message);
    updateView();
  }

  private void attackTarget() {
    if (model.getCurrentRoom().getIndex() == model.getTarget().getRoomIndex()) {
      List<Item> itemList = model.getCurrentPlayer().getItemsList();
      String[] itemNames = itemList.stream()
          .map(item -> item.getName() + " (Damage: " + item.getDamage() + ")")
          .toArray(String[]::new);
      String chosenItem = (String) JOptionPane.showInputDialog(view, "Choose an item:", "Pick Item",
          JOptionPane.QUESTION_MESSAGE, null, itemNames, itemNames[0]);
      if (chosenItem != null) {
        int itemIndex = Arrays.asList(itemNames).indexOf(chosenItem);
        itemIndex = itemList.get(itemIndex).getId();
        Map<String, String> params = new HashMap<>();
        params.put("itemIndex", String.valueOf(itemIndex));
        String message = new AttackTarget("attackTarget", model).execute(params).getMessage();
        JOptionPane.showMessageDialog(null, message);
        updateView();
      }
    } else {
      JOptionPane.showMessageDialog(view, "Target is not in current room", "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void movePet() {
    String roomNumberString = JOptionPane.showInputDialog(view, "Enter pet's new room number:");
    if (roomNumberString != null && !roomNumberString.isEmpty()) {
      try {
        int roomNumber = Integer.parseInt(roomNumberString);
        String message = model.movePet(roomNumber);
        JOptionPane.showMessageDialog(null, message);
        updateView();
      } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(view, "Invalid room number", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void updateView() {
    view.updateView(this);
    ProgramState programState = model.getProgramState();
    if (programState == ProgramState.FINALIZING) {
      JOptionPane.showMessageDialog(view, model.displayFinalMessage(), "Game Over",
          JOptionPane.INFORMATION_MESSAGE);

    }
  }

}
