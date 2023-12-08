package game.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import game.constants.PlayerType;
import game.model.MockKillDoctorLuckyImpl;
import game.model.PlayerImpl;
import game.model.RoomImpl;
import game.utils.RandomGenerator;
import game.view.MockGuiView;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Test class for GameControllerImpl.
 */
public class GuiGameControllerImplTest {
  private GuiGameController gameController;
  private MockKillDoctorLuckyImpl mockModel;
  private Readable fileReader;
  private Readable inputReader;
  private MockGuiView view;

  /**
   * Set up the test.
   *
   * @throws IOException if file is not found
   */
  @Before
  public void setUp() throws IOException {
    inputReader = new StringReader("");
    String[] args = new String[2];
    args[0] = "res/mansion.txt";
    args[1] = "10";
    fileReader = new InputStreamReader(new FileInputStream(args[0]));
    RandomGenerator randomGenerator = new RandomGenerator(1, 1, 2, 3, 2, 1);
    int maxTurns = Integer.parseInt(args[1]);
    mockModel = new MockKillDoctorLuckyImpl(maxTurns, randomGenerator);
    view = new MockGuiView();
    gameController = new GuiGameControllerImpl(mockModel, view, fileReader);
  }

  @Test(expected = NullPointerException.class)
  public void testStartGameWithNullModel() throws IOException {
    inputReader = new StringReader("");
    GameControllerImpl controllerWithNullModel = new GameControllerImpl(null, inputReader, view,
        fileReader);
    controllerWithNullModel.startGame();
  }

  @Test
  public void testHandlePlayerClick() {
    gameController.handlePlayerClick(new PlayerImpl("Eric", 1, 1, PlayerType.HUMAN));
    assertEquals(mockModel.getMessage(), "Display Player Description Activated!");
  }

  @Test
  public void testHandleRoomClick() {
    gameController.handleRoomClick(new RoomImpl(1, "Room1", 1, 1, 1, 2));
    assertEquals(mockModel.getMessage(), "Move Player Activated!");
  }
}
