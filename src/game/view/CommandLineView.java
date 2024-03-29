package game.view;

import game.controller.GuiGameController;
import game.controller.command.Command;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * Represents a view for the game.
 */
public class CommandLineView implements View {
  private final Appendable out;

  /**
   * Constructs a command line view.
   *
   * @param out the output stream
   */
  public CommandLineView(Appendable out) {
    this.out = out;
  }

  @Override
  public void enableButtons() {

  }

  @Override
  public void addActionListener(ActionListener actionListener) {

  }

  @Override
  public void initialComponents() {

  }

  @Override
  public void updateView(GuiGameController listener) {

  }

  @Override
  public void displayOptions(List<Command> commands) throws IOException {
    out.append("Available commands: \n");
    for (Command cmd : commands) {
      out.append(cmd.getIdentifier()).append(" - ").append(cmd.getDescription()).append("\n");
    }
  }

  @Override
  public void displayMessage(String message) throws IOException {
    out.append(message).append("\n");
  }

  @Override
  public void displayError(String error) throws IOException {
    out.append(error).append("\n");
  }

  @Override
  public void prompt(String promptMessage) throws IOException {
    out.append(promptMessage).append("\n");
  }
}
