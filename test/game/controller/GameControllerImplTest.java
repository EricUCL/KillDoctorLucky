package game.controller;

import static org.junit.Assert.assertTrue;

import game.constants.ProgramState;
import game.model.MockKillDoctorLuckyImpl;
import game.utils.RandomGenerator;
import game.view.CommandLineView;
import game.view.View;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for GameControllerImpl.
 */
public class GameControllerImplTest {
  private GameControllerImpl gameController;
  private MockKillDoctorLuckyImpl mockModel;
  private final Appendable output = new StringBuilder();
  private Readable fileReader;
  private Readable inputReader;
  private View view;
  private RandomGenerator randomGenerator;

  /**
   * Set up the test.
   *
   * @throws IOException if file is not found
   */
  @Before
  public void setUp() throws IOException {
    //    inputReader = new StringReader("");
    String[] args = new String[2];
    args[0] = "res/mansion.txt";
    args[1] = "10";
    fileReader = new InputStreamReader(new FileInputStream(args[0]));
    //    final Readable in = new InputStreamReader(System.in);
    view = new CommandLineView(output);
    randomGenerator = new RandomGenerator(1, 1, 2, 3, 2, 1);
    int maxTurns = Integer.parseInt(args[1]);
    mockModel = new MockKillDoctorLuckyImpl(maxTurns, randomGenerator);
    //    gameController = new GameControllerImpl(mockModel, in, view, fileReader);
  }

  @Test
  public void testStartGameFinalizing() throws IOException {
    mockModel.setProgramState(ProgramState.FINALIZING);
    gameController.startGame();
    assertTrue(output.toString().contains("Game Over"));
  }

  @Test
  public void testStartGameWithComputerPlayer() throws IOException {
    inputReader = new StringReader("q");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    System.out.println(output.toString());
    assertTrue(output.toString()
        .contains("Turn Counter: 1\n" + "Max Turn: 10\n" + "Current turn: Player1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithNullModel() throws IOException {
    inputReader = new StringReader("");
    GameControllerImpl controllerWithNullModel = new GameControllerImpl(null, inputReader, view,
        fileReader);
    controllerWithNullModel.startGame();
  }

  @Test
  public void testAddHumanPlayer() throws IOException {
    mockModel.setProgramState(ProgramState.INIT);
    inputReader = new StringReader("7\neric\n1\n1\nq");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    assertTrue(output.toString().contains("Player added successfully!"));
  }

  @Test
  public void testInvalidCommand() throws IOException {
    inputReader = new StringReader("invalid\nq");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    assertTrue(output.toString().contains("Invalid command"));
  }

  @Test
  public void testAddHumanPlayerWithNullName() throws IOException {
    mockModel.setProgramState(ProgramState.INIT);
    inputReader = new StringReader("7\n\n1\n1\nq");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    System.out.println(output.toString());
    assertTrue(output.toString().contains("Invalid input"));
  }

  @Test
  public void testPickItemCommand() throws IOException {
    inputReader = new StringReader("3\n1\nq");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    System.out.println(output.toString());
    assertTrue(output.toString().contains("Pick Item Activated!"));
  }

  @Test
  public void testMovePlayerCommand() throws IOException {
    inputReader = new StringReader("1\n1\nq");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    System.out.println(output.toString());
    assertTrue(output.toString().contains("Move Player Activated!"));
  }

  @Test
  public void testLookAroundCommand() throws IOException {
    inputReader = new StringReader("2\nq");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    System.out.println(output.toString());
    assertTrue(output.toString().contains("Look Around Activated!"));
  }

  @Test
  public void testDisplayWorldDescriptionCommand() throws IOException {
    mockModel.setProgramState(ProgramState.INIT);
    inputReader = new StringReader("1\nq");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    System.out.println(output.toString());
    assertTrue(output.toString().contains("Display World Description Activated!"));
  }

  @Test
  public void testDisplayPlayerDescriptionCommand() throws IOException {
    inputReader = new StringReader("4\neric\nq");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    System.out.println(output.toString());
    assertTrue(output.toString().contains("Display Player Description Activated!"));
  }

  @Test
  public void testDisplayTargetInfoCommand() throws IOException {
    inputReader = new StringReader("5\nq");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    System.out.println(output.toString());
    assertTrue(output.toString().contains("Display Target Info Activated!"));
  }

  @Test
  public void testCreateWorldImageCommand() throws IOException {
    mockModel.setProgramState(ProgramState.INIT);
    inputReader = new StringReader("4\nq");
    GameController gameController = new GameControllerImpl(mockModel, inputReader, view,
        fileReader);
    gameController.startGame();
    System.out.println(output.toString());
    assertTrue(output.toString().contains("Create World Image Activated!"));
  }
}