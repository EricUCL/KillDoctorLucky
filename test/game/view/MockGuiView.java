package game.view;

import java.io.IOException;
import java.util.List;
import game.controller.GuiGameController;
import game.controller.command.Command;

public class MockGuiView extends GuiView {
  public MockGuiView() {
    super(null);

  }

  @Override
  public void displayOptions(List<Command> commands) throws IOException {

  }

  @Override
  public void displayMessage(String message) throws IOException {

  }

  @Override
  public void updateView(GuiGameController listener) {
    return;
  }

  @Override
  public void displayError(String error) throws IOException {

  }

  @Override
  public void prompt(String promptMessage) throws IOException {

  }
}
