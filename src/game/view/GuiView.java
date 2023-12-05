package game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import game.controller.GameController;
import game.controller.GuiGameControllerImpl;
import game.model.KillDoctorLucky;
import game.view.listeners.MouseClickListener;
import game.view.panels.GameMapPanel;

public class GuiView extends JFrame {
  KillDoctorLucky model;
  private JButton setMapButton;
  //  private JButton showWorldDescriptionButton;
  private JButton addPlayerButton;
  private JButton startGameButton;
  private JTextArea turnsCountArea;
  private JTextArea targetInfoArea;
  private JTextArea turnInformationArea;
  private JTextArea commandsInformationArea;
  private JSplitPane splitPane;

  private JPanel gameMapPanel;

  public GuiView(KillDoctorLucky model) {
    super("Kill Doctor Lucky");

    this.model = model;

    this.setLayout(new FlowLayout());

    setMapButton = new JButton("Set Map");
    setMapButton.setActionCommand("Open Set Map Dialog");

    //    showWorldDescriptionButton = new JButton("Show World Description");
    //    showWorldDescriptionButton.setActionCommand("Show World Description Button");

    startGameButton = new JButton("Start Game");
    startGameButton.setActionCommand("Start Game Button");

    addPlayerButton = new JButton("Add Player");
    addPlayerButton.setActionCommand("Add Player Button");

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.add(setMapButton);
    buttonPanel.add(addPlayerButton);
    buttonPanel.add(startGameButton);
    addPlayerButton.setEnabled(false);
    startGameButton.setEnabled(false);

    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    add(buttonPanel);
    pack();
    setSize(800, 600);
    this.setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void enableButtons() {
    addPlayerButton.setEnabled(true);
    if (!model.getPlayers().isEmpty()) {
      startGameButton.setEnabled(true);
    }
  }

  public void addActionListener(ActionListener actionListener) {
    setMapButton.addActionListener(actionListener);
    addPlayerButton.addActionListener(actionListener);
    startGameButton.addActionListener(actionListener);
  }

//  public void addClickListener(GuiGameControllerImpl controller) {
//    gameMapPanel.addClickListener(controller);
//  }

  public void initialComponents() {

    this.getContentPane().removeAll();
    this.setLayout(new BorderLayout());

    JPanel informationPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER; // 单列
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH; // 水平垂直扩展

    turnsCountArea = new JTextArea("Turns Count");
    targetInfoArea = new JTextArea("Target Info");
    turnInformationArea = new JTextArea("Turn Information");
    commandsInformationArea = new JTextArea("Commands Information");

    turnsCountArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    informationPanel.add(turnsCountArea, gbc);

    targetInfoArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    informationPanel.add(targetInfoArea, gbc);

    turnInformationArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    informationPanel.add(turnInformationArea, gbc);

    commandsInformationArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    informationPanel.add(commandsInformationArea, gbc);

    splitPane.setLeftComponent(informationPanel);
    splitPane.setRightComponent(gameMapPanel);

    this.add(splitPane, BorderLayout.CENTER);

    splitPane.setDividerLocation(200);

    this.validate();
    this.repaint();

    this.pack();
    this.setSize(800, 600);
    //    this.setVisible(true);
  }

  public void updateView(GuiGameControllerImpl listener) {

    GameMapPanel newGameMapPanel = new GameMapPanel(model, listener);

    if (this.gameMapPanel != null) {
      this.remove(this.gameMapPanel);
    }

    this.gameMapPanel = newGameMapPanel;
    splitPane.setRightComponent(gameMapPanel);

    String turnInfo = model.getTurnInfo();
    turnInformationArea.setText(turnInfo);
    String targetInfo = model.displayTargetInfo();
    targetInfoArea.setText(targetInfo);
    String commandsInfo = "SELECT THE COMMAND YOU WANT TO PERFORM\n"
        + "To Move Player - Click on the space you want to\n" + "To Pick Item - Press P\n"
        + "To Perform Look Around - Press L\n" + "To Kill Target - Press K\n"
        + "To Move Pet - Press M\n" + "To Get Space Information - Press S\n"
        + "To Get Player Information - Click on the Player";
    commandsInformationArea.setText(commandsInfo);
    String turnsCount = model.displayPrepareMessage();
    turnsCountArea.setText(turnsCount);

    this.validate();
    this.repaint();
  }

  public void addClickListener(MouseClickListener clickListener) {
    addMouseListener(clickListener);
  }

}
