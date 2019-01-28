package Models;

import Forms.HighlightMode;
import Forms.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameMaster {

    private Board board;
    private Team teamFirst;
    private Team teamSecond;
    private ConfigureInformation Configuration;
    private MainWindow GameWindow;
    private JPanel GamePanel;

    public GameMaster(ConfigureInformation configuration) {
        Configuration = configuration;
        board = new Board(Configuration.getGameSize());
        teamFirst = new Team(TeamDirection.NE, Configuration.getGameSize());
        teamSecond = new Team(TeamDirection.SW, Configuration.getGameSize());
        GamePanel = Configuration.GameWindow.getGamePanel();
        GameWindow = Configuration.GameWindow;
    }

    public void nextUserTurn() {
        GameWindow.EnableTeamFields(teamFirst);
        GameWindow.setItPossibleToEndTurn(false);
    }

    public void endUserTurn() {
        GameWindow.ResetAllListeners();
        GameWindow.HighlightAllFields(HighlightMode.HighlightNone);
    }

    public void prepareGame(Board board, Team teamFirst, Team teamSecond) {
        GameWindow.InitializeGameTeam(teamFirst);
        GameWindow.InitializeGameTeam(teamSecond);
        GameWindow.EnableTeamFields(teamFirst);
        board.placeTeam(teamFirst);
        board.placeTeam(teamSecond);
    }

    public List<Point> countAIMove() {
        TreeNode root = new TreeNode(null, board, teamSecond, teamFirst, 0);
        GameTree tree = new GameTree(root);
        int time = -1;
        switch (Configuration.getDifficultyLevel()) {
            case Easy:
                time = 1;
                break;
            case Medium:
                time = 5;
                break;
            case Hard:
                time = 5;
                break;
        }
        tree.buildTree(time, TimeUnit.SECONDS); //time form game settings
        return tree.getBestMove();
    }

    public void makeAIMove() {
        List<Point> AIMove = countAIMove();
        Point oldPoint = AIMove.get(0);
        Point newPoint = AIMove.get(AIMove.size() - 1);
        teamSecond.movePawnInTeam(oldPoint, newPoint);
        board.movePawnOnBoard(oldPoint, newPoint);
        GameWindow.ComputerTurnDisplay(AIMove);
    }

    public void processGame() {
        try {
            SwingUtilities.invokeAndWait(() -> endUserTurn());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (checkWinCondition(teamFirst)) {
            GameWindow.ShowWinMessage(teamFirst);
            restartGame();

            return;
        }
        makeAIMove();
        if (checkWinCondition(teamSecond)) {
            GameWindow.ShowWinMessage(teamSecond);
            restartGame();
            return;
        }
        nextUserTurn();
    }

    private void restartGame() {
        GameWindow.RestartGameView();
        board = new Board(Configuration.getGameSize());
        teamFirst = new Team(TeamDirection.NE, Configuration.getGameSize());
        teamSecond = new Team(TeamDirection.SW, Configuration.getGameSize());
        prepareGame(board, teamFirst, teamSecond);
    }

    private boolean checkWinCondition(Team team) {
        return team.countDistance(board.getSize()) == team.getWinDistance();
    }

    public Board getBoard() {
        return board;
    }

    public Team getTeamFirst() {
        return teamFirst;
    }

    public Team getTeamSecond() {
        return teamSecond;
    }
}
