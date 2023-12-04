package game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.SwingUtilities;
import game.controller.GameController;
import game.controller.GuiGameControllerImpl;
import game.model.KillDoctorLucky;
import game.model.KillDoctorLuckyImpl;
import game.utils.RandomGenerator;
import game.view.GuiView;

public class GuiDriver {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          createAndShowGUI();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }

  private static void createAndShowGUI() throws IOException {
    Readable fileReader = new InputStreamReader(new FileInputStream("res/mansion.txt"));
    int maxTurns = Integer.parseInt("50");
    RandomGenerator randomGenerator = new RandomGenerator();
    KillDoctorLucky model = new KillDoctorLuckyImpl(maxTurns, randomGenerator);
    GuiView view = new GuiView(model);
    GameController controller = new GuiGameControllerImpl(model, view, fileReader);
    controller.startGame();
  }
}
