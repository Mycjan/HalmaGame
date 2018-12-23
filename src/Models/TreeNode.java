package Models;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class TreeNode {
    private List<Point> moves;
    private Board newBoard;
    private Team myTeam;
    private Team concurentTeam;
    private List<TreeNode> childrens;

    public TreeNode(List<Point> moves, Board newBoard) {
        this.moves = moves;
        this.newBoard = newBoard;
        this.childrens = null;
    }

    public TreeNode(Point newPoint, Point oldPoint, Board oldBoard) {
        this.moves = Arrays.asList(oldPoint, newPoint);
        Board newBoard = oldBoard.clone();
        newBoard.getTiles()[(int) oldPoint.getX()][(int) oldPoint.getY()] = false;
        newBoard.getTiles()[(int) newPoint.getX()][(int) newPoint.getY()] = true;
        this.newBoard = newBoard;
        this.childrens = null;
    }

    public TreeNode(Point newPoint, Point oldPoint, Board oldBoard, List<Point> moves) {
        moves.add(newPoint);
        this.moves = moves;
        Board newBoard = oldBoard.clone();
        newBoard.getTiles()[(int) oldPoint.getX()][(int) oldPoint.getY()] = false;
        newBoard.getTiles()[(int) newPoint.getX()][(int) newPoint.getY()] = true;
        this.newBoard = newBoard;
        this.childrens = null;
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
