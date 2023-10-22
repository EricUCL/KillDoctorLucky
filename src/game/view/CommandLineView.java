package game.view;

import game.controller.command.Command;

import java.io.IOException;
import java.util.List;

public class CommandLineView implements View {
  private final Appendable out;

  public CommandLineView(Appendable out) {
    this.out = out;
  }

  public void displayOptions(List<Command> commands) throws IOException {
    out.append("Available commands: \n");
    for (Command cmd : commands) {
      out.append(cmd.getIdentifier()).append(" - ").append(cmd.getDescription()).append("\n");
    }
  }

  public void displayMessage(String message) throws IOException {
    out.append(message).append("\n");
  }

  public void displayError(String error) throws IOException {
    out.append(error).append("\n");
  }

  public void prompt(String promptMessage) throws IOException {
    out.append(promptMessage).append("\n");
  }
}
