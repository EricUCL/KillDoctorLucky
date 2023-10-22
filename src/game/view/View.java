package game.view;

import game.controller.command.Command;

import java.io.IOException;
import java.util.List;

/**
 * Represents the user interface for the game, providing methods to display information and prompts
 * to the user. The interface is designed to abstract the presentation layer, allowing different
 * implementations (e.g., console-based, GUI-based) to be plugged in as needed.
 */
public interface View {

  /**
   * Displays a list of available commands to the user.
   *
   * @param commands A list of commands that can be shown to the user.
   * @throws IOException If there's an error during displaying the options.
   */
  void displayOptions(List<Command> commands) throws IOException;

  /**
   * Displays a general message to the user.
   *
   * @param message The message to be displayed.
   * @throws IOException If there's an error during displaying the message.
   */
  void displayMessage(String message) throws IOException;

  /**
   * Displays an error message to the user.
   *
   * @param error The error message to be displayed.
   * @throws IOException If there's an error during displaying the error message.
   */
  void displayError(String error) throws IOException;

  /**
   * Displays a prompt to the user, indicating that some input is expected.
   *
   * @param promptMessage The message to be shown as a prompt.
   * @throws IOException If there's an error during displaying the prompt.
   */
  void prompt(String promptMessage) throws IOException;
}
