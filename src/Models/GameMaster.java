package Models;

import Forms.GameField;
import Forms.HighlightMode;
import Forms.MainWindow;
import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.text.Utilities;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
        tree.bildTree(time, TimeUnit.SECONDS); //time form game settings
        return tree.getBestMove();
    }


    public void MakeAIMove() {
        List<Point> AIMove = countAIMove();
        Point oldPoint = AIMove.get(0);
        Point newPoint = AIMove.get(AIMove.size() - 1);
        teamSecond.movePawnInTeam(oldPoint, newPoint);
        board.movePawnOnBoard(oldPoint, newPoint);
        GameWindow.ComputerTurnDisplay(AIMove);
    }

    public void processGame() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    endUserTurn();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (CheckWinCondition(teamFirst)) {
            GameWindow.ShowWinMessage(teamFirst);
            restartGame();

            return;
        }
        MakeAIMove();
        if (CheckWinCondition(teamSecond)) {
            GameWindow.ShowWinMessage(teamSecond);
            restartGame();
            return;
        }
        nextUserTurn();


    }

    private void restartGame()
    {
        GameWindow.RestartGameView();
        board = new Board(Configuration.getGameSize());
        teamFirst = new Team(TeamDirection.NE, Configuration.getGameSize());
        teamSecond = new Team(TeamDirection.SW, Configuration.getGameSize());
        prepareGame(board,teamFirst,teamSecond);
    }



    private boolean CheckWinCondition(Team team) {
        return team.countDistance(board.getSize()) == team.getWinDistance();
    }


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Team getTeamFirst() {
        return teamFirst;
    }

    public void setTeamFirst(Team teamFirst) {
        this.teamFirst = teamFirst;
    }

    public Team getTeamSecond() {
        return teamSecond;
    }

    public void setTeamSecond(Team teamSecond) {
        this.teamSecond = teamSecond;
    }

    private int index(int x, int y) {
        if (x < 0)
            return -1;
        if (y < 0)
            return -1;
        if (x > board.getSize())
            return -1;
        if (y > board.getSize())
            return -1;

        return x * board.getSize() + y;
    }


}
