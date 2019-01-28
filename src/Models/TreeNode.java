package Models;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class TreeNode {
    private List<Point> moves;
    private Board newBoard;
    private Team myTeam;
    private Team concurrentTeam;
    private List<TreeNode> children;
    private int levelNum;
    private double count;
    private Integer bestChild;

    public TreeNode(List<Point> moves, Board newBoard, Team myTeam, Team concurrentTeam, int levelNum) {
        this.moves = moves;
        this.newBoard = newBoard;
        this.children = null;
        this.myTeam = myTeam;
        this.concurrentTeam = concurrentTeam;
        this.levelNum = levelNum;
        this.count = Double.MIN_VALUE;
        this.bestChild = null;
    }

    public TreeNode(Point newPoint, Point oldPoint, Board oldBoard, Team myOldTeam, Team concurrentOldTeam, int levelNum) {
        this.moves = Arrays.asList(oldPoint, newPoint);
        this.concurrentTeam = myOldTeam.clone();
        this.myTeam = concurrentOldTeam.clone();
        this.concurrentTeam.movePawnInTeam(oldPoint, newPoint);
        this.newBoard = oldBoard.clone();
        this.newBoard.movePawnOnBoard(oldPoint, newPoint);
        this.children = null;
        this.levelNum = levelNum;
        this.count = Double.MIN_VALUE;
        this.bestChild = null;
    }

    public TreeNode(Point newPoint, Point oldPoint, Board oldBoard, List<Point> moves, Team myOldTeam, Team concurrentOldTeam, int levelNum) {
        moves.add(newPoint);
        this.moves = moves;
        this.concurrentTeam = myOldTeam.clone();
        this.myTeam = concurrentOldTeam.clone();
        this.concurrentTeam.movePawnInTeam(oldPoint, newPoint);
        this.newBoard = oldBoard.clone();
        this.newBoard.movePawnOnBoard(oldPoint, newPoint);
        this.children = null;
        this.levelNum = levelNum;
        this.count = Double.MIN_VALUE;
        this.bestChild = null;
    }

    public List<Point> getMoves() {
        return moves;
    }

    public Board getBoard() {
        return newBoard;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public Team getMyTeam() {
        return myTeam;
    }

    public Team getConcurrentTeam() {
        return concurrentTeam;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public Integer getBestChild() {
        return bestChild;
    }

    public void setBestChild(Integer bestChild) {
        this.bestChild = bestChild;
    }
}
