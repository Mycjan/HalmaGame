package Forms;

import javafx.scene.transform.Rotate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainWindow extends JFrame {

    private Font LabelFont;


    private JPanel RootPanel;
    private JLabel SizeLabel;
    private JRadioButton Size8RadioButton;
    private JRadioButton Size9RadioButton;
    private JLabel DifficultyLevelLabel;
    private JRadioButton EasyDifficultyRadioButton;
    private JRadioButton HardDifficultyRadioButton;
    private ButtonGroup SizeButtonGroup;
    private ButtonGroup DifficultyLevelButtonGroup;
    private JPanel GamePanel;


    public final static double PROPERTIES_LAYOUT_WEIGHT = 0.05;
    public final static double GAME_LAYOUT_WEIGHT = 1.0;


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public MainWindow() {


        //Main window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(600, 700));
        setPreferredSize(new Dimension(600, 700));


        //RootPanel properties
        RootPanel = new JPanel();
        RootPanel.setLayout(new GridBagLayout());
        GridBagConstraints RootGBC = new GridBagConstraints();


        //PropertiesPanel properties
        InitializePropertiesPanel(RootGBC);


        int length = 0;
        if (Size8RadioButton.isSelected()) {
            length = 8;
        } else if (Size9RadioButton.isSelected()) {
            length = 9;
        }

        //GamePanel Properties
        GamePanel = new JPanel();
        GamePanel.setLayout(new GridLayout(length, length));
        RootGBC.gridx = 0;
        RootGBC.gridy = 3;
        RootGBC.fill = GridBagConstraints.BOTH;
        RootGBC.gridwidth = 2;
        RootGBC.gridheight = 5;
        RootGBC.weighty = GAME_LAYOUT_WEIGHT;
        RootGBC.weightx = GAME_LAYOUT_WEIGHT;
        RootPanel.add(GamePanel, RootGBC);


        JPanel zast = new JPanel();
        zast.setLayout(new GridLayout(length, length));
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                JButton TemporaryButton = new JButton();
                String text = j + "," + i;
                TemporaryButton.setText(text);
                zast.add(TemporaryButton);


            }
        }
        RootGBC = ((GridBagLayout) RootPanel.getLayout()).getConstraints(GamePanel);
        RootPanel.remove(GamePanel);
        GamePanel = zast;
        RootPanel.add(GamePanel, RootGBC);


        add(RootPanel);
        setVisible(true);
    }

    private void InitializePropertiesPanel(GridBagConstraints rootGBC) {
        InitializeSizeLabel(rootGBC);
        InitializeSize8RadioButton(rootGBC);
        InitializeSize9RadioButton(rootGBC);

        InitializeDifficultyLevelLabel(rootGBC);
        InitializeEasyDifficultyRadioButton(rootGBC);
        InitializeHardDifficultyRadioButton(rootGBC);

        InitializePropertiesButtonGroups();
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
        rootGBC.gridx = 1;
        rootGBC.gridy = 2;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        RootPanel.add(HardDifficultyRadioButton, rootGBC);
    }

    private void InitializeEasyDifficultyRadioButton(GridBagConstraints rootGBC) {
        EasyDifficultyRadioButton = new JRadioButton();
        EasyDifficultyRadioButton.setText("Easy");
        rootGBC.gridx = 1;
        rootGBC.gridy = 1;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        RootPanel.add(EasyDifficultyRadioButton, rootGBC);
    }

    private void InitializeDifficultyLevelLabel(GridBagConstraints rootGBC) {
        DifficultyLevelLabel = new JLabel();
        DifficultyLevelLabel.setText("Difficulty level");

        rootGBC.gridx = 1;
        rootGBC.gridy = 0;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        RootPanel.add(DifficultyLevelLabel, rootGBC);
    }

    private void InitializeSize9RadioButton(GridBagConstraints rootGBC) {
        Size9RadioButton = new JRadioButton();
        Size9RadioButton.setText("9  x  9");
        rootGBC.gridx = 0;
        rootGBC.gridy = 2;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        RootPanel.add(Size9RadioButton, rootGBC);
        Size9RadioButton.addActionListener(e -> {
            JPanel NewPanel = new JPanel();
            int length = 9;
            ResizeGame(NewPanel, length);
        });
    }


    private void InitializeSize8RadioButton(GridBagConstraints rootGBC) {
        Size8RadioButton = new JRadioButton();
        Size8RadioButton.setText("8  x  8");
        rootGBC.gridx = 0;
        rootGBC.gridy = 1;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        RootPanel.add(Size8RadioButton, rootGBC);
        Size8RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel NewPanel = new JPanel();
                int length = 8;
                ResizeGame(NewPanel, length);
            }
        });

    }

    private void ResizeGame(JPanel newPanel, int length) {
        newPanel.setLayout(new GridLayout(length, length));
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                JButton TemporaryButton = new JButton();
                String text = j + "," + i;
                TemporaryButton.setText(text);
                newPanel.add(TemporaryButton);
            }
        }
        Component OldGamePanel = RootPanel.getComponent(6);
        GridBagLayout Layout = (GridBagLayout) RootPanel.getLayout();
        GridBagConstraints RootGBC = Layout.getConstraints(OldGamePanel);
        RootPanel.remove(OldGamePanel);
        RootPanel.add(newPanel, RootGBC);
        RootPanel.revalidate();
    }

    private void InitializeSizeLabel(GridBagConstraints rootGBC) {
        SizeLabel = new JLabel();
        SizeLabel.setText("Size");

        rootGBC.gridx = 0;
        rootGBC.gridy = 0;
        rootGBC.weightx = PROPERTIES_LAYOUT_WEIGHT;
        rootGBC.weighty = PROPERTIES_LAYOUT_WEIGHT;
        RootPanel.add(SizeLabel, rootGBC);
    }


}
