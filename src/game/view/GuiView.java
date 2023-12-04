package game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import game.model.KillDoctorLucky;

public class GuiView extends JFrame {
  KillDoctorLucky model;
  private JButton setMapButton;
  //  private JButton showWorldDescriptionButton;
  private JButton addPlayerButton;
  private JButton startGameButton;
  private JTextArea infoArea;
  private JSplitPane splitPaneH;

  private JPanel informationPanel;
  private Box targetBox;
  private GridBagConstraints gbc;
  private Box turnInformationBox;
  private Box turnsCountBox;
  private Box commandsBox;
  private JPanel imagePanel;
  private JPanel topPanel;

  private JLabel turnsCount;
  private JLabel turnInformation;
  private JLabel targetInfo;
  private JLabel commandsInformation;

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

    add(buttonPanel);
    pack();
    setSize(800, 600);
    this.setLocationRelativeTo(null);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void addActionListener(ActionListener actionListener) {
    System.out.println("Adding action listener to buttons");
    setMapButton.addActionListener(actionListener);
    //    showWorldDescriptionButton.addActionListener(actionListener);
    addPlayerButton.addActionListener(actionListener);
    startGameButton.addActionListener(actionListener);
  }

  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  public void showGameBoard1() {
    this.getContentPane().removeAll();
    informationPanel = new JPanel();
    informationPanel.setPreferredSize(new Dimension(400, 300));
    informationPanel.setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.insets = new Insets(5, 0, 5, 0);

    imagePanel = new JPanel();
//    preGameImage = null;
//    scrollPane = new JScrollPane(preGameImage);
//    imagePanel.add(scrollPane);
    splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    topPanel = new JPanel();
    getContentPane().add(topPanel);
    topPanel.add(splitPaneH, BorderLayout.CENTER);

    turnsCountBox = Box.createHorizontalBox();
    turnsCountBox.setBorder(BorderFactory.createLineBorder(Color.white));
    turnsCount = new JLabel("");

    targetBox = Box.createVerticalBox();
    targetBox.setBorder(BorderFactory.createLineBorder(Color.white));
    targetInfo = new JLabel("");

    turnInformationBox = Box.createVerticalBox();
    turnInformationBox.setBorder(BorderFactory.createLineBorder(Color.white));
    turnInformation = new JLabel("");

    commandsBox = Box.createVerticalBox();
    commandsBox.setBorder(BorderFactory.createLineBorder(Color.white));
    commandsInformation = new JLabel("");

    informationPanel.setBackground(Color.black);
    informationPanel.add(turnsCountBox, gbc);
    informationPanel.add(targetBox, gbc);
    informationPanel.add(turnInformationBox, gbc);
    informationPanel.add(commandsBox, gbc);

    splitPaneH.setLeftComponent(informationPanel);
    splitPaneH.setRightComponent(imagePanel);

    setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  public void showGameBoard() {
    this.getContentPane().removeAll();

    // 左侧信息面板
    JPanel informationPanel = new JPanel();
    informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));

    // 添加文本框或标签
    JLabel turnsCountLabel = new JLabel("Turns Count");
    JLabel targetInfoLabel = new JLabel("Target Info");
    JLabel turnInformationLabel = new JLabel("Turn Information");
    JLabel commandsInformationLabel = new JLabel("Commands Information");

    // 将标签添加到信息面板
    informationPanel.add(turnsCountLabel);
    informationPanel.add(targetInfoLabel);
    informationPanel.add(turnInformationLabel);
    informationPanel.add(commandsInformationLabel);

    // 右侧游戏地图面板
    JPanel gameMapPanel = new JPanel();
    // TODO: 在这里添加游戏地图的实现

    // 创建分割面板
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, informationPanel, gameMapPanel);
    splitPane.setDividerLocation(0.33); // 设置分割线位置为窗口的1/3

    // 将分割面板添加到框架中
    this.add(splitPane);

    // 设置框架属性
    setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}
