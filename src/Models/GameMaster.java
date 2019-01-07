package Models;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameMaster {
    private Board board;
    private Team teamFirst;
    private Team teamSecond;

    public GameMaster() {
    }

//    public GameMaster(/*GameSettings*/) {
//    }

    public void processGame(Board board, Team teamFirst, Team teamSecond) {
        int boardSize = board.getSize();
        while (true) {

            //process teamFirst move (real player move)
            //modify board and teamFirst's pawns

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
}
