package game.view.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ButtonListener implements ActionListener {
  private Map<String, Runnable> buttonClickedActions;

  /**
   * Creates an object of type ButtonListener.
   */
  public ButtonListener() {
    buttonClickedActions = null;
  }

  /**
   * Set the map for key typed events. Key typed events in Java Swing are characters.
   *
   * @param map the actions for button clicks
   */
  public void setButtonClickedActionMap(Map<String, Runnable> map) {
    if (map == null || map.isEmpty()) {
      throw new IllegalArgumentException("Runnable map is null or empty");
    }

    buttonClickedActions = map;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("Action performed: " + e.getActionCommand()); // 打印动作命令

    if (buttonClickedActions.containsKey(e.getActionCommand())) {
      buttonClickedActions.get(e.getActionCommand()).run();
    }
  }
}