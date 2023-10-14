package game.view;

import game.controller.command.Command;

import java.io.IOException;
import java.util.List;

public interface View {
  void displayOptions(List<Command> commands) throws IOException;

  // This method displays general messages to the user.
  void displayMessage(String message) throws IOException;

  // This method displays error messages to the user.
  void displayError(String error) throws IOException;

  // This method displays a prompt for the user to enter data.
  void prompt(String promptMessage) throws IOException;
}
