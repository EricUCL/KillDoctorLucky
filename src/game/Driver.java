package game;

import game.controller.GameController;
import game.controller.GameControllerImpl;
import game.model.KillDoctorLuckyImpl;
import game.utils.RandomGenerator;
import game.view.CommandLineView;
import game.view.View;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Driver class to run the game.
 */
public class Driver {
  /**
   * Main method to run the game.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    try {
      // read a file path from args
      if (args.length < 1) {
        throw new IllegalArgumentException("Please provide a file path as an argument.");
      }
      //      args = new String[2];
      //      args[0] = "res/mansion.txt";
      //      args[1] = "10";
      int maxTurns = Integer.parseInt(args[1]);
      Readable fileReader = new InputStreamReader(new FileInputStream(args[0]));
      final Readable in = new InputStreamReader(System.in);
      final Appendable out = System.out;
      View view = new CommandLineView(out);
      RandomGenerator randomGenerator = new RandomGenerator();
      GameController controller = new GameControllerImpl(
          new KillDoctorLuckyImpl(maxTurns, randomGenerator), in, view, fileReader);
      controller.startGame();
    } catch (IllegalArgumentException e) {
      System.out.printf("%s%n", e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
