package Models;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class TreeNode {
    private List<Point> moves;
    private Board newBoard;
    private Team myTeam;
    private Team concurrentTeam;
    private List<TreeNode> childrens;

    public TreeNode(List<Point> moves, Board newBoard) {
        this.moves = moves;
        this.newBoard = newBoard;
        this.childrens = null;
    }

    public TreeNode(Point newPoint, Point oldPoint, Board oldBoard, Team myOldTeam, Team concurrentOldTeam) {
        this.moves = Arrays.asList(oldPoint, newPoint);
        this.concurrentTeam = concurrentOldTeam.clone();
        this.myTeam = myOldTeam.clone();
        movePawnInTeam(this.myTeam, oldPoint, newPoint);
        this.newBoard = oldBoard.clone();
        movePawnOnBoard(this.newBoard, oldPoint, newPoint);
        this.childrens = null;
    }

    public TreeNode(Point newPoint, Point oldPoint, Board oldBoard, List<Point> moves, Team myOldTeam, Team concurrentOldTeam) {
        moves.add(newPoint);
        this.moves = moves;
        this.concurrentTeam = concurrentOldTeam.clone();
        this.myTeam = myOldTeam.clone();
        movePawnInTeam(this.myTeam, oldPoint, newPoint);
        this.newBoard = oldBoard.clone();
        movePawnOnBoard(this.newBoard, oldPoint, newPoint);
        this.childrens = null;
    }

    private static void movePawnInTeam(Team team, Point oldPoint, Point newPoint) {
        for (Pawn p : team.getPawns()) {
            if (p.getX() == oldPoint.getX() && p.getY() == oldPoint.getY()) {
                p.setX((int) newPoint.getX());
                p.setY((int) newPoint.getY());
                return;
            }
        }

        throw new IllegalArgumentException("Point not found in team");
    }

    private static void movePawnOnBoard(Board board, Point oldPoint, Point newPoint) {
        board.getTiles()[(int) oldPoint.getX()][(int) oldPoint.getY()] = false;
        board.getTiles()[(int) newPoint.getX()][(int) newPoint.getY()] = true;
    }

    public List<Point> getMoves() {
        return moves;
    }

    public Board getnewBoard() {
        return newBoard;
    }

    public List<TreeNode> getChildres() {
        return childrens;
    }

    public void setChildres(List<TreeNode> childrens) {
        this.childrens = childrens;
    }
}
