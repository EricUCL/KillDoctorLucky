package game;

import game.controller.GameController;
import game.model.KillDoctorLucky;
import game.model.KillDoctorLuckyImpl;
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
      // read file path from args
      if (args.length != 1) {
        throw new IllegalArgumentException("Please provide a file path as an argument.");
      }
      String filePath = args[0];
      //      String filePath = "res/mansion.txt";
      KillDoctorLucky killDoctorLucky = new KillDoctorLuckyImpl(filePath, 2);
      final Readable in = new InputStreamReader(System.in);
      final Appendable out = System.out;
      GameController controller = new GameController(killDoctorLucky, in, out);
      controller.startGame();
    } catch (IllegalArgumentException e) {
      System.out.printf("%s%n", e.getMessage());
    }
  }
}
