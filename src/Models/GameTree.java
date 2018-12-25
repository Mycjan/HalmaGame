package Models;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class GameTree {
    private TreeNode root;
    private List<TreeNode> lastBuildLevel;
    private List<TreeNode> levelInBulid;

    public GameTree(TreeNode root) {
        this.root = root;
        this.lastBuildLevel = new ArrayList<>();
        this.lastBuildLevel.add(root);
    }

    public void bildTree(int levelCount) {
        for (int i = 0; i < levelCount; i++) {
            long start = System.currentTimeMillis();
            bildNextLevel(lastBuildLevel);
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed);
        }
    }

    private void bildNextLevel(List<TreeNode> thisLevel) {
        levelInBulid = new ArrayList<>();
        for (TreeNode t : thisLevel) {
            findChildrens(t);
        }
        lastBuildLevel = levelInBulid;
    }

    private void findChildrens(TreeNode node) {
        List<TreeNode> nodes = MoveFinder.GetAllMoves(node.getMyTeam(), node.getConcurrentTeam(), node.getBoard());
        node.setChildres(nodes);
        levelInBulid.addAll(nodes);
    }
}
