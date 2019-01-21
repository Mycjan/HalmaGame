package Models;

import Forms.GameField;
import Forms.HighlightMode;
import Forms.MainWindow;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
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

        /*try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<Point> FirstExampleComputerMove = new ArrayList<Point>();
        FirstExampleComputerMove.add(new Point(5, 0));
        FirstExampleComputerMove.add(new Point(5, 2));
        FirstExampleComputerMove.add(new Point(5, 3));
        FirstExampleComputerMove.add(new Point(5, 4));
        List<Point> SecondExampleComputerMove = new ArrayList<Point>();
        SecondExampleComputerMove.add(new Point(6, 2));
        SecondExampleComputerMove.add(new Point(4, 2));*/

        /*Thread first = new Thread(new Runnable() {
            @Override
            public void run() {
                GameWindow.ComputerTurnDisplay(FirstExampleComputerMove);
                GameWindow.ComputerTurnDisplay(SecondExampleComputerMove);
            }
        });
        first.start();*/





 /*


           */

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
        //System.out.println(teamSecond.countDistance(boardSize));
    }


    public void processGame() {
        endUserTurn();
        if (CheckWinCondition(teamFirst))
            return;
        MakeAIMove();
        if (CheckWinCondition(teamSecond))
            return;
        nextUserTurn();
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
