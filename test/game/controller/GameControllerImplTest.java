package game.controller;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import game.constants.PlayerType;
import game.constants.ProgramState;
import game.model.MockKillDoctorLuckyImpl;
import game.model.Player;
import game.model.PlayerImpl;
import game.utils.RandomGenerator;
import game.view.CommandLineView;
import game.view.View;
import org.junit.Before;
import org.junit.Test;

public class GameControllerImplTest {
  private GameControllerImpl gameController;
  private MockKillDoctorLuckyImpl mockModel;
  Appendable output = new StringBuilder();
  private Readable fileReader;
  private Readable inputReader;
  private View view;
  private RandomGenerator randomGenerator;

  @Before
  public void setUp() throws Exception {
    fileReader = new StringReader("...");
    inputReader = new StringReader("");
    String[] args = new String[2];
    args[0] = "res/mansion.txt";
    args[1] = "10";
    int maxTurns = Integer.parseInt(args[1]);
    Readable fileReader = new InputStreamReader(new FileInputStream(args[0]));
    final Readable in = new InputStreamReader(System.in);
    view = new CommandLineView(output);
    randomGenerator = new RandomGenerator(1, 1, 2, 3, 2, 1);
    mockModel = new MockKillDoctorLuckyImpl(maxTurns, randomGenerator);
    gameController = new GameControllerImpl(mockModel, in, view, fileReader);
  }

  @Test
  public void testStartGameFinalizing() throws IOException {
    mockModel.setProgramState(ProgramState.FINALIZING);
    gameController.startGame();
    System.out.println(output.toString());
    assertTrue(output.toString().contains("Game Over"));
  }

  @Test
  public void testStartGameWithComputerPlayer() throws IOException {
    mockModel.setProgramState(ProgramState.RUNNING);
    Player computerPlayer = new PlayerImpl("Computer1", 1, 10, PlayerType.COMPUTER);
    mockModel.setCurrentPlayer(computerPlayer);
    gameController.startGame();
    assertTrue(output.toString().contains("----------------- Start ----------------\n"
        + "Computer player Computer1 is playing\n" + "Current player turn is computer player\n"
        + "------------------ End -----------------\n" + "Game Over"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithNullModel() throws IOException {
    GameControllerImpl controllerWithNullModel = new GameControllerImpl(null, inputReader, view,
        fileReader);
    controllerWithNullModel.startGame();
  }
}