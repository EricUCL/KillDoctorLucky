package game;

import game.controller.GameController;
import game.controller.GuiGameControllerImpl;
import game.model.KillDoctorLucky;
import game.model.KillDoctorLuckyImpl;
import game.utils.RandomGenerator;
import game.view.GuiView;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.SwingUtilities;

/**
 * Driver class for the GUI version of the game.
 */
public class GuiDriver {
  /**
   * Main method.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      try {
        createAndShowGui();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   *
   * @throws IOException if file is not found
   */
  private static void createAndShowGui() throws IOException {
    Readable fileReader = new InputStreamReader(new FileInputStream("res/mansion.txt"));
    int maxTurns = Integer.parseInt("50");
    RandomGenerator randomGenerator = new RandomGenerator();
    KillDoctorLucky model = new KillDoctorLuckyImpl(maxTurns, randomGenerator);
    GuiView view = new GuiView(model);
    GameController controller = new GuiGameControllerImpl(model, view, fileReader);
    controller.startGame();
  }
}
