package Models;

import Forms.GameField;
import Forms.MainWindow;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
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

    public void processGame(Board board, Team teamFirst, Team teamSecond) {
        int boardSize = board.getSize();

        GameWindow.InitializeGameTeam(teamFirst);
        GameWindow.InitializeGameTeam(teamSecond);
        GameWindow.EnableTeamFields(teamFirst);
        board.placeTeam(teamFirst);
        board.placeTeam(teamSecond);


 /*
        while (true) {

            //process teamFirst move (real player move)
            //modify board and teamFirst's pawns
            //while player don't end his turn
            while (true) {
                GameWindow.EnableTeamFields(teamFirst);



            }


            if (teamFirst.countDistance(boardSize) == teamFirst.getWinDistance()) {
                //endgame message
                return;
            }

            List<Point> AIMove = countAIMove(board, teamFirst, teamSecond);
            Point oldPoint = AIMove.get(0);
            Point newPoint = AIMove.get(AIMove.size() - 1);
            teamSecond.movePawnInTeam(oldPoint, newPoint);
            board.movePawnOnBoard(oldPoint, newPoint);
            System.out.println(teamSecond.countDistance(boardSize));
            if (teamSecond.countDistance(boardSize) == teamSecond.getWinDistance()) {
                //endgame message
                return;
            }
        }
*/
    }

    private List<Point> countAIMove(Board board, Team teamFirst, Team teamSecond) {
        TreeNode root = new TreeNode(null, board, teamSecond, teamFirst, 0);
        GameTree tree = new GameTree(root);
        tree.bildTree(7, TimeUnit.SECONDS); //time form game settings
        return tree.getBestMove();
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
