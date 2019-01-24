package Forms;

import Models.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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
    private JRadioButton MediumDifficultyRadioButton;
    private JRadioButton HardDifficultyRadioButton;
    private ButtonGroup SizeButtonGroup;
    private ButtonGroup DifficultyLevelButtonGroup;
    private JPanel GamePanel;
    private JPanel GameViewPanel;
    private JPanel GameLabelHorizontalPanel;
    private JPanel GameLabelVerticalPanel;
    private GameMaster GM;

    private final static int PROPERTIES_FRAME_WIDTH = 800;
    private final static int PROPERTIES_FRAME_HEIGHT = 500;
    private final static int GAME_FRAME_WIDTH = 700;
    private final static int GAME_FRAME_HEIGHT = 710;
    public final static double PROPERTIES_LAYOUT_WEIGHT = 0.05;
    public final static double GAME_LAYOUT_WEIGHT = 1;

    private List<Point> HighlightedPossibleMoves = new ArrayList<>();
    private GameField CheckedField;
    private ConfigureInformation Configuration;
    private Boolean IsItPossibleToEndTurn = false;
    private GameField FromField;


    public void setItPossibleToEndTurn(Boolean itPossibleToEndTurn) {
        IsItPossibleToEndTurn = itPossibleToEndTurn;
    }

    public List<Point> getHighlightedPossibleMoves() {
        return HighlightedPossibleMoves;
    }

    public GameField getCheckedField() {
        return CheckedField;
    }

    public void setCheckedField(GameField checkedField) {
        CheckedField = checkedField;
    }

    private void createUIComponents() {

    }

    public MainWindow() {

        //Main window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(PROPERTIES_FRAME_WIDTH, PROPERTIES_FRAME_HEIGHT));
        setPreferredSize(new Dimension(PROPERTIES_FRAME_WIDTH, PROPERTIES_FRAME_HEIGHT));
        setMaximumSize(new Dimension(PROPERTIES_FRAME_WIDTH, PROPERTIES_FRAME_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);


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
        setMaximumSize(new Dimension(GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT));
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
                GameField TemporaryButton = new GameField(j, i, this);
                GamePanel.add(TemporaryButton);
            }
        }
        AddEnterListener();
    }

    private void AddEnterListener() {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        ActionListener SpaceListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (IsItPossibleToEndTurn)
                            GM.processGame();
                    }
                });
                thread.start();
            }
        };
        RootPanel.registerKeyboardAction(SpaceListener, keyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
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
        InitializeMediumDifficultyRadioButton(rootGBC);
        InitializeHardDifficultyRadioButton(rootGBC);

        InitializeStartGameButton(rootGBC);
        InitializeInstructionLabel(rootGBC);

        InitializePropertiesButtonGroups();
    }

    private void InitializeStartGameButton(GridBagConstraints rootGBC) {
        StartGameButton = new JButton();
        StartGameButton.setText("Start game");
        StartGameButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        StartGameButton.addActionListener(e -> {
            int GameSize = getLength();
            DifficultyLevel difficultyLevel = getDifficultyLevel();

            Configuration = new ConfigureInformation(GameSize, difficultyLevel, this, 1000);
            RootPanel.removeAll();
            GM = new GameMaster(Configuration);
            InitializeGameViewPanel(rootGBC, getLength());


            Thread GameThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    GM.prepareGame(GM.getBoard(), GM.getTeamFirst(), GM.getTeamSecond());
                }
            });
            GameThread.start();


        });
        rootGBC.gridx = 1;
        rootGBC.gridy = 5;
        rootGBC.gridwidth = 2;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.fill = GridBagConstraints.BOTH;
        PropertiesPanel.add(StartGameButton, rootGBC);
    }

    private void InitializeInstructionLabel(GridBagConstraints rootGBC) {

        InstructionLabel = new JLabel();
        String CurrentWorkingDirectory = System.getProperty("user.dir") + "\\src\\Resources";
        String path = CurrentWorkingDirectory + "\\instrukcja.txt";
        String instruction = "<html>";
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                instruction += line;
            }


        } catch (Exception e) {
            System.err.println("Wystapil blad przy wczytywaniu danych");
            e.printStackTrace();
        }

        InstructionLabel.setText(instruction);
        Border border = InstructionLabel.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        InstructionLabel.setBorder(new CompoundBorder(border, margin));
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
        DifficultyLevelButtonGroup.add(MediumDifficultyRadioButton);
        DifficultyLevelButtonGroup.add(HardDifficultyRadioButton);
        DifficultyLevelButtonGroup.clearSelection();
        EasyDifficultyRadioButton.setSelected(true);
    }

    private void InitializeHardDifficultyRadioButton(GridBagConstraints rootGBC) {
        HardDifficultyRadioButton = new JRadioButton();
        HardDifficultyRadioButton.setText("Hard");
        rootGBC.gridx = 2;
        rootGBC.gridy = 4;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        PropertiesPanel.add(HardDifficultyRadioButton, rootGBC);
    }

    private void InitializeMediumDifficultyRadioButton(GridBagConstraints rootGBC) {
        MediumDifficultyRadioButton = new JRadioButton();
        MediumDifficultyRadioButton.setText("Medium");
        rootGBC.gridx = 2;
        rootGBC.gridy = 3;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        PropertiesPanel.add(MediumDifficultyRadioButton, rootGBC);
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
    }

    private void setLabelProperties(JLabel tempLabel) {
        tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tempLabel.setVerticalAlignment(SwingConstants.CENTER);
        tempLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        int LabelSize = 30;
        tempLabel.setMinimumSize(new Dimension(LabelSize, LabelSize));
        tempLabel.setPreferredSize(new Dimension(LabelSize, LabelSize));
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

    private int index(int x, int y) {
        if (x < 0)
            return -1;
        if (y < 0)
            return -1;
        if (x > getLength())
            return -1;
        if (y > getLength())
            return -1;

        return y * getLength() + x;
    }

    //SECTION API

    public JPanel getGamePanel() {
        return GamePanel;
    }

    public void InitializeGameTeam(Team team1) {

        for (Pawn Pawn : team1.getPawns()) {
            int index = index(Pawn.getX(), Pawn.getY());
            GameField Field = (GameField) GamePanel.getComponent(index);
            Field.setTeamPawn(team1.getDirection());
        }

    }

    public GameMaster getGM() {
        return GM;
    }

    public void EnableTeamFields(Team team) {
        for (Pawn Pawn : team.getPawns()) {
            GameField Field = (GameField) GamePanel.getComponent(index(Pawn.getX(), Pawn.getY()));
            Field.AddCheckFieldListener();
        }
    }

    public void AddPossibleMoveListeners(List<Point> PossibleMoves) {
        HighlightedPossibleMoves = PossibleMoves;
        for (Point point : PossibleMoves) {
            GameField Field = (GameField) GamePanel.getComponent(index(point.x, point.y));
            Field.AddMoveListeners();
        }
    }

    public void HighlightAllFields(HighlightMode Mode) {
        for (Component Component : GamePanel.getComponents()) {
            GameField Field = (GameField) Component;
            Field.HighlightField(Mode);
        }
    }

    public void HighlightFields(List<Point> Fields, HighlightMode Mode) {
        for (Point point : Fields) {
            GameField Field = (GameField) GamePanel.getComponent(index(point.x, point.y));
            Field.HighlightField(Mode);
        }
    }

    public void ResetAllListeners() {
        for (Component Component : GamePanel.getComponents()) {
            GameField Field = (GameField) Component;
            Field.ResetListeners();
        }
    }

    public void ResetListenersOnFields(List<Point> Fields) {
        for (Point fieldPoint : Fields) {
            GameField Field = (GameField) GamePanel.getComponent(index(fieldPoint.x, fieldPoint.y));
            Field.ResetListeners();
        }
    }

    public void ShowWinMessage(Team Winner) {
        String message = "";
        switch (Winner.getDirection()) {
            case SW:
                message = "Unfortunately, Computer is the winner!";
                break;
            case NE:
                message = "Congratulations! You are the winner!";
                break;
        }
        JOptionPane.showMessageDialog(GameViewPanel, message);
    }

    public void ComputerTurnDisplay(List<Point> Moves) {
        if (Moves.size() < 2)
            return;
        Point From = Moves.get(0);
        FromField = (GameField) GamePanel.getComponent(index(From.x, From.y));
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    FromField.HighlightField(HighlightMode.HighlightPawn);
                    try {
                        Thread.sleep(Configuration.getTurnDisplayTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < Moves.size(); i++) {
            Point To = Moves.get(i);
            GameField ToField = (GameField) GamePanel.getComponent(index(To.x, To.y));
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        FromField.ClearField();
                        ToField.setTeamPawn(GM.getTeamSecond().getDirection());
                        ToField.HighlightField(HighlightMode.HighlightPawn);
                        revalidate();
                        try {
                            Thread.sleep(Configuration.getTurnDisplayTime());
                        } catch (Exception e) {
                            System.out.printf(e.getMessage());
                        }

                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            FromField = ToField;
        }
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(Configuration.getTurnDisplayTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    FromField.HighlightField(HighlightMode.HighlightNone);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void RestartGameView() {
        try {
            SwingUtilities.invokeAndWait(
                    new Runnable() {
                        public void run() {
                            for (Component component : GamePanel.getComponents()) {
                                GameField field = (GameField) component;
                                field.ClearField();
                            }
                            ResetAllListeners();
                            HighlightAllFields(HighlightMode.HighlightNone);
                        }
                    });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


}
