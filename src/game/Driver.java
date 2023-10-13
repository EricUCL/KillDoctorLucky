package game;

import game.controller.GameController;
import game.controller.GameControllerImpl;
import game.model.KillDoctorLuckyImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
      // read file path from args
//      if (args.length < 1) {
//        throw new IllegalArgumentException("Please provide a file path as an argument.");
//      }
      args = new String[2];
      args[0] = "res/mansion.txt";
      args[1] = "100";
      int maxTurns = Integer.parseInt(args[1]);
      Readable fileReader = new InputStreamReader(new FileInputStream(args[0]));
      final Readable in = new InputStreamReader(System.in);
      final Appendable out = System.out;
      GameController controller = new GameControllerImpl(new KillDoctorLuckyImpl(maxTurns), in, out,
          fileReader);
      controller.startGame();
    } catch (IllegalArgumentException e) {
      System.out.printf("%s%n", e.getMessage());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
