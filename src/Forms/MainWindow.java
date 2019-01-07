package Forms;

import Models.ConfigureInformation;
import Models.DifficultyLevel;
import Models.TeamDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainWindow extends JFrame {

    private JLabel InstructionLabel;
    private JPanel RootPanel;
    private JPanel PropertiesPanel;
    private JLabel SizeLabel;
    private JButton StartGameButton;
    private JRadioButton Size8RadioButton;
    private JRadioButton Size9RadioButton;
    private JLabel DifficultyLevelLabel;
    private JRadioButton EasyDifficultyRadioButton;
    private JRadioButton HardDifficultyRadioButton;
    private ButtonGroup SizeButtonGroup;
    private ButtonGroup DifficultyLevelButtonGroup;
    private JPanel GamePanel;
    private JPanel GameViewPanel;
    private JPanel GameLabelHorizontalPanel;
    private JPanel GameLabelVerticalPanel;


    private final static int PROPERTIES_FRAME_WIDTH = 300;
    private final static int PROPERTIES_FRAME_HEIGHT = 150;
    private final static int GAME_FRAME_WIDTH = 700;
    private final static int GAME_FRAME_HEIGHT = 700;
    private final static int GAME_FRAME_SIZE = 750;
    public final static double PROPERTIES_LAYOUT_WEIGHT = 0.05;
    public final static double GAME_LAYOUT_WEIGHT = 1;


    private ConfigureInformation Configuration;


    private void createUIComponents() {

    }

    public MainWindow() {

        //Main window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setMinimumSize(new Dimension(PROPERTIES_FRAME_WIDTH, PROPERTIES_FRAME_HEIGHT));
        setPreferredSize(new Dimension(PROPERTIES_FRAME_WIDTH, PROPERTIES_FRAME_HEIGHT));
        setMaximumSize(new Dimension(PROPERTIES_FRAME_WIDTH, PROPERTIES_FRAME_HEIGHT));
        setResizable(false);

        //RootPanel properties
        RootPanel = new JPanel();
        RootPanel.setLayout(new GridBagLayout());
        GridBagConstraints RootGBC = new GridBagConstraints();

        //PropertiesPanel properties
        InitializePropertiesPanel(RootGBC);

        add(RootPanel);
        setVisible(true);
    }

    private void InitializeGameViewPanel(GridBagConstraints rootGBC, int length) {
        setResizable(true);
        setMinimumSize(new Dimension(GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT));
        setPreferredSize(new Dimension(GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT));
        setMaximumSize(new Dimension(GAME_FRAME_WIDTH,GAME_FRAME_HEIGHT));
        setLocationRelativeTo(null);
        setResizable(false);

        GameViewPanel = new JPanel();
        GameViewPanel.setLayout(new GridBagLayout());
        GridBagConstraints GameViewGBC = new GridBagConstraints();
        rootGBC.gridx = 0;
        rootGBC.gridy = 0;
        rootGBC.fill = GridBagConstraints.BOTH;
        rootGBC.gridwidth = 2;
        rootGBC.gridheight = 5;
        rootGBC.weighty = GAME_LAYOUT_WEIGHT;
        rootGBC.weightx = GAME_LAYOUT_WEIGHT;
        RootPanel.add(GameViewPanel, rootGBC);
        InitializeGameLabelHorizontalPanel(length, GameViewGBC);
        InitializeGameLabelVerticalPanel(length, GameViewGBC);
        InitializeGamePanel(length, GameViewGBC);
        RootPanel.revalidate();
    }

    private void InitializeGameLabelHorizontalPanel(int length, GridBagConstraints gameViewGBC) {
        GameLabelHorizontalPanel = new JPanel(new GridLayout(1, length));
        gameViewGBC.gridx = 1;
        gameViewGBC.gridy = 0;
        gameViewGBC.fill = GridBagConstraints.HORIZONTAL;
        gameViewGBC.weightx = 0;
        gameViewGBC.weighty = 0;
        for (int i = 0; i < length; i++) {
            JLabel TempLabel = new JLabel();
            TempLabel.setText("" + i);
            setLabelProperties(TempLabel);
            GameLabelHorizontalPanel.add(TempLabel);
        }
        GameViewPanel.add(GameLabelHorizontalPanel, gameViewGBC);
    }

    private void InitializeGameLabelVerticalPanel(int length, GridBagConstraints gameViewGBC) {
        GameLabelVerticalPanel = new JPanel(new GridLayout(length, 1));
        gameViewGBC.gridx = 0;
        gameViewGBC.gridy = 1;
        gameViewGBC.fill = GridBagConstraints.VERTICAL;
        gameViewGBC.weightx = 0;
        gameViewGBC.weighty = 0;
        char firstLetter = 'A';
        for (int i = 0; i < length; i++) {
            JLabel TempLabel = new JLabel();
            TempLabel.setText("" + firstLetter);
            firstLetter++;
            setLabelProperties(TempLabel);
            GameLabelVerticalPanel.add(TempLabel);
        }
        GameViewPanel.add(GameLabelVerticalPanel, gameViewGBC);
    }

    private void InitializeGamePanel(int length, GridBagConstraints gameViewGBC) {
        GamePanel = new JPanel();
        GamePanel.setLayout(new GridLayout(length, length));
        GamePanel.setMinimumSize(new Dimension(600, 600));
        GamePanel.setPreferredSize(new Dimension(600, 600));
        GamePanel.setMaximumSize(new Dimension(600, 600));
        gameViewGBC.gridx = 1;
        gameViewGBC.gridy = 1;
        gameViewGBC.fill = GridBagConstraints.BOTH;
        gameViewGBC.weightx = 0;
        gameViewGBC.weighty = 0;
        GameViewPanel.add(GamePanel, gameViewGBC);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                GameField TemporaryButton = new GameField(i,j);
                GamePanel.add(TemporaryButton);
            }
        }
    }

    private void InitializePropertiesPanel(GridBagConstraints rootGBC) {
        PropertiesPanel = new JPanel();
        PropertiesPanel.setLayout(new GridBagLayout());
        rootGBC.gridx = 0;
        rootGBC.gridy = 0;
        rootGBC.fill = GridBagConstraints.BOTH;
        rootGBC.weightx = 1;
        rootGBC.weighty = 1;
        RootPanel.add(PropertiesPanel, rootGBC);


        InitializeSizeLabel(rootGBC);
        InitializeSize8RadioButton(rootGBC);
        InitializeSize9RadioButton(rootGBC);

        InitializeDifficultyLevelLabel(rootGBC);
        InitializeEasyDifficultyRadioButton(rootGBC);
        InitializeHardDifficultyRadioButton(rootGBC);

        StartGameButton = new JButton();
        StartGameButton.setText("Start game");
        StartGameButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        StartGameButton.addActionListener(e -> {
            int GameSize = getLength();
            DifficultyLevel difficultyLevel = getDifficultyLevel();
            Configuration = new ConfigureInformation(GameSize, difficultyLevel);
            RootPanel.removeAll();
            InitializeGameViewPanel(rootGBC, getLength());

        });
        rootGBC.gridx = 1;
        rootGBC.gridy = 4;
        rootGBC.gridwidth = 2;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.fill = GridBagConstraints.BOTH;
        PropertiesPanel.add(StartGameButton, rootGBC);


        InitializeInstructionLabel(rootGBC);

        InitializePropertiesButtonGroups();
    }

    private void InitializeInstructionLabel(GridBagConstraints rootGBC) {
        InstructionLabel = new JLabel();
        InstructionLabel.setText("This is place for instructions");
        rootGBC.gridx = 0;
        rootGBC.gridy = 0;
        rootGBC.gridwidth = 4;
        rootGBC.weightx = GAME_LAYOUT_WEIGHT;
        rootGBC.weighty = GAME_LAYOUT_WEIGHT;
        PropertiesPanel.add(InstructionLabel, rootGBC);
    }

    private void InitializePropertiesButtonGroups() {
        SizeButtonGroup = new ButtonGroup();
        SizeButtonGroup.add(Size8RadioButton);
        SizeButtonGroup.add(Size9RadioButton);
        SizeButtonGroup.clearSelection();
        Size8RadioButton.setSelected(true);

        DifficultyLevelButtonGroup = new ButtonGroup();
        DifficultyLevelButtonGroup.add(EasyDifficultyRadioButton);
        DifficultyLevelButtonGroup.add(HardDifficultyRadioButton);
        DifficultyLevelButtonGroup.clearSelection();
        EasyDifficultyRadioButton.setSelected(true);
    }

    private void InitializeHardDifficultyRadioButton(GridBagConstraints rootGBC) {
        HardDifficultyRadioButton = new JRadioButton();
        HardDifficultyRadioButton.setText("Hard");
        rootGBC.gridx = 2;
        rootGBC.gridy = 3;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        PropertiesPanel.add(HardDifficultyRadioButton, rootGBC);
    }

    private void InitializeEasyDifficultyRadioButton(GridBagConstraints rootGBC) {
        EasyDifficultyRadioButton = new JRadioButton();
        EasyDifficultyRadioButton.setText("Easy");
        rootGBC.gridx = 2;
        rootGBC.gridy = 2;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        PropertiesPanel.add(EasyDifficultyRadioButton, rootGBC);
    }

    private void InitializeDifficultyLevelLabel(GridBagConstraints rootGBC) {
        DifficultyLevelLabel = new JLabel();
        DifficultyLevelLabel.setText("Difficulty level");

        rootGBC.gridx = 2;
        rootGBC.gridy = 1;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        PropertiesPanel.add(DifficultyLevelLabel, rootGBC);
    }

    private void InitializeSize9RadioButton(GridBagConstraints rootGBC) {
        Size9RadioButton = new JRadioButton();
        Size9RadioButton.setText("9  x  9");
        rootGBC.gridx = 1;
        rootGBC.gridy = 3;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        PropertiesPanel.add(Size9RadioButton, rootGBC);
        Size9RadioButton.addActionListener(e -> {
            int length = 9;
            ResizeGame(length);

        });
    }

    private void InitializeSizeLabel(GridBagConstraints rootGBC) {
        SizeLabel = new JLabel();
        SizeLabel.setText("Size");

        rootGBC.gridx = 1;
        rootGBC.gridy = 1;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        PropertiesPanel.add(SizeLabel, rootGBC);
    }

    private void InitializeSize8RadioButton(GridBagConstraints rootGBC) {
        Size8RadioButton = new JRadioButton();
        Size8RadioButton.setText("8  x  8");
        rootGBC.gridx = 1;
        rootGBC.gridy = 2;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        PropertiesPanel.add(Size8RadioButton, rootGBC);
        Size8RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int length = 8;
                ResizeGame(length);
            }
        });

    }

    private void ResizeGame(int length) {
        ResizeGamePanel(length);
        ResizeGameLabelHorizontalPanel(length);
        ResizeGameLabelVerticalPanel(length);
    }

    private void ResizeGameLabelVerticalPanel(int length) {
        JPanel NewGameLabelVerticalPanel = new JPanel(new GridLayout(length, 1));
        char FirstLetter = 'A';
        for (int i = 0; i < length; i++) {
            JLabel TempLabel = new JLabel();
            TempLabel.setText("" + FirstLetter);
            FirstLetter++;
            setLabelProperties(TempLabel);
            NewGameLabelVerticalPanel.add(TempLabel);
        }
        GridBagLayout Layout = (GridBagLayout) GameViewPanel.getLayout();
        GridBagConstraints GameViewGBC = Layout.getConstraints(GameLabelVerticalPanel);
        GameViewPanel.remove(GameLabelVerticalPanel);
        GameLabelVerticalPanel = NewGameLabelVerticalPanel;
        GameViewPanel.add(GameLabelVerticalPanel, GameViewGBC);
        GameViewPanel.revalidate();
    }

    private void ResizeGameLabelHorizontalPanel(int length) {
        JPanel NewGameLabelHorizontalPanel = new JPanel(new GridLayout(1, length));
        for (int i = 0; i < length; i++) {
            JLabel TempLabel = new JLabel();
            TempLabel.setText("" + i);
            setLabelProperties(TempLabel);
            NewGameLabelHorizontalPanel.add(TempLabel);
        }
        GridBagLayout Layout = (GridBagLayout) GameViewPanel.getLayout();
        GridBagConstraints GameViewGBC = Layout.getConstraints(GameLabelHorizontalPanel);
        GameViewPanel.remove(GameLabelHorizontalPanel);
        GameLabelHorizontalPanel = NewGameLabelHorizontalPanel;
        GameViewPanel.add(GameLabelHorizontalPanel, GameViewGBC);
        GameViewPanel.revalidate();
    }

    private void setLabelProperties(JLabel tempLabel) {
        tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tempLabel.setVerticalAlignment(SwingConstants.CENTER);
        tempLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        int LabelSize = 30;
        tempLabel.setMinimumSize(new Dimension(LabelSize, LabelSize));
        tempLabel.setPreferredSize(new Dimension(LabelSize, LabelSize));
    }

    private void ResizeGamePanel(int length) {
        JPanel newGamePanel = new JPanel();
        newGamePanel.setLayout(new GridLayout(length, length));
        int GamePanelSize = 600;
        newGamePanel.setMinimumSize(new Dimension(GamePanelSize, GamePanelSize));
        newGamePanel.setPreferredSize(new Dimension(GamePanelSize, GamePanelSize));
        newGamePanel.setMaximumSize(new Dimension(GamePanelSize, GamePanelSize));
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                GameField TemporaryButton = new GameField(i,j);
                newGamePanel.add(TemporaryButton);
            }
        }
        GridBagLayout Layout = (GridBagLayout) GameViewPanel.getLayout();
        GridBagConstraints GameViewGBC = Layout.getConstraints(GamePanel);
        GameViewPanel.remove(GamePanel);
        GamePanel = newGamePanel;
        GameViewPanel.add(GamePanel, GameViewGBC);
        GameViewPanel.revalidate();
    }

    private int getLength() {
        int length = 0;
        if (Size8RadioButton.isSelected()) {
            length = 8;
        } else if (Size9RadioButton.isSelected()) {
            length = 9;
        }
        return length;
    }

    private DifficultyLevel getDifficultyLevel() {
        DifficultyLevel level = DifficultyLevel.Easy;
        if (EasyDifficultyRadioButton.isSelected()) {
            level = DifficultyLevel.Easy;
        } else if (HardDifficultyRadioButton.isSelected()) {
            level = DifficultyLevel.Hard;
        }
        return level;
    }

}