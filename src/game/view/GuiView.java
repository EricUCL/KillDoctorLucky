package game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import game.controller.GuiGameControllerImpl;
import game.model.KillDoctorLucky;
import game.view.panels.GameMapPanel;

public class GuiView extends JFrame {
  KillDoctorLucky model;
  private JButton setMapButton;
  private JButton addPlayerButton;
  private JButton startGameButton;
  private JTextArea turnsCountArea;
  private JTextArea targetInfoArea;
  private JTextArea turnInformationArea;
  private JTextArea commandsInformationArea;
  private JSplitPane splitPane;
  private JPanel gameMapPanel;
  private JMenuItem newGameMenuItem;

  public GuiView(KillDoctorLucky model) {
    super("Kill Doctor Lucky");

    this.model = model;
    this.getContentPane().removeAll();
    this.setLayout(new FlowLayout());

    this.setMapButton = new JButton("Set Map");
    setMapButton.setActionCommand("Open Set Map Dialog");

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
    createMenuBar();
    pack();
    setSize(1200, 1000);
    this.setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  private void createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    JMenu optionsMenu = new JMenu("Options");

    newGameMenuItem = new JMenuItem("New Game");
    newGameMenuItem.setActionCommand("New Game");
    JMenuItem quitMenuItem = new JMenuItem("Quit");

    newGameMenuItem.addActionListener(e -> {
      splitPane.setVisible(false);
      startGameButton.setVisible(true);
      startGameButton.setEnabled(false);
      addPlayerButton.setVisible(true);
      addPlayerButton.setEnabled(false);
      setMapButton.setVisible(true);
    });

    quitMenuItem.addActionListener(e -> {
      System.out.println("Quit clicked");
      System.exit(0);
    });

    //    optionsMenu.add(newGameMenuItem);
    optionsMenu.add(quitMenuItem);

    menuBar.add(optionsMenu);

    this.setJMenuBar(menuBar);
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
    newGameMenuItem.addActionListener(actionListener);
  }

  public void initialComponents() {
    this.getContentPane().removeAll();
    addPlayerButton.setVisible(false);
    startGameButton.setVisible(false);
    setMapButton.setVisible(false);
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

    turnsCountArea.setEditable(false);
    turnsCountArea.setFocusable(false);
    targetInfoArea.setEditable(false);
    targetInfoArea.setFocusable(false);
    turnInformationArea.setEditable(false);
    turnInformationArea.setFocusable(false);
    commandsInformationArea.setEditable(false);
    commandsInformationArea.setFocusable(false);

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
    this.setSize(1200, 1000);
    this.setVisible(true);
  }

  public void updateView(GuiGameControllerImpl listener) {

    GameMapPanel newGameMapPanel = new GameMapPanel(model, listener);

    if (this.gameMapPanel != null) {
      this.remove(this.gameMapPanel);
    }

    this.gameMapPanel = newGameMapPanel;
    splitPane.setRightComponent(gameMapPanel);

    String turnInfo = model.getMaxTurns() - model.getTurnCount() + " turns left";
    turnInformationArea.setText(turnInfo);

    String targetInfo = model.displayTargetInfo();
    targetInfoArea.setText(targetInfo);

    String commandsInfo =
        "To Move Player - Click on the space you want to\n" + "To Pick Item - Press P\n"
            + "To Perform Look Around - Press L\n" + "To Attack Target - Press A\n"
            + "To Move Pet - Press M\n" + "To Get Player Information - Click on the Player";
    commandsInformationArea.setText(commandsInfo);
    String turnsCount = model.displayPrepareMessage();
    turnsCountArea.setText(turnsCount);

    this.validate();
    this.repaint();
    this.requestFocus();
  }
}
