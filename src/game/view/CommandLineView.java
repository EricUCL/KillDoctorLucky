package game.view;

import game.controller.command.Command;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CommandLineView implements View {
  private Appendable out;

  public CommandLineView(Scanner in, Appendable out) {
    this.out = out;
  }

  // This method displays the list of available commands to the user.
  public void displayOptions(List<Command> commands) throws IOException {
    out.append("Available commands: \n");
    for (Command cmd : commands) {
      out.append(cmd.getIdentifier()).append(" - ").append(cmd.getDescription()).append("\n");
    }
  }

  // This method displays general messages to the user.
  public void displayMessage(String message) throws IOException {
    out.append(message).append("\n");
  }

  // This method displays error messages to the user.
  public void displayError(String error) throws IOException {
    out.append(error).append("\n");
  }

  // This method displays a prompt for the user to enter data.
  public void prompt(String promptMessage) throws IOException {
    out.append(promptMessage).append("\n");
  }
}
