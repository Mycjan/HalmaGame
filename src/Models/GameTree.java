package Models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class GameTree {

    private TreeNode root;
    private List<TreeNode> lastBuildLevel;
    private List<TreeNode> levelInBuild;

    public GameTree(TreeNode root) {
        this.root = root;
        this.lastBuildLevel = new ArrayList<>();
        this.lastBuildLevel.add(root);
    }

    public void buildTree(int timeSpan, TimeUnit timeUnit) {
        CancellableRunnable runnable;
        runnable = new CancellableRunnable() {
            private boolean bool;

            @Override
            public void run() {
                bool = true;
                boolean foundWonPos = false;
                while (!foundWonPos) {
                    levelInBuild = new ArrayList<>();
                    for (TreeNode t : lastBuildLevel) {
                        if (!bool)
                            return;
                        if (findChildren(t)) {
                            foundWonPos = true;
                        }
                    }
                    lastBuildLevel = levelInBuild;
                }
            }

            @Override
            public void cancel() {
                bool = false;
            }
        };

        ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r) {
                    @Override
                    public void interrupt() {
                        super.interrupt();
                        runnable.cancel();
                    }
                };
            }
        });

        executor.submit(runnable);
        try {
            executor.awaitTermination(timeSpan, timeUnit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdownNow();
    }

    public List<Point> getBestMove() {
        findBestMove(root);
        return root.getChildren().get(root.getBestChild()).getMoves();
    }

    private void findBestMove(TreeNode t) {
        if (t.getChildren() == null) {
            t.setCount(t.getMyTeam().countDistance(t.getBoard().getSize()));
            return;
        }

        for (int i = 0; i < t.getChildren().size(); i++) {
            TreeNode children = t.getChildren().get(i);

            findBestMove(children);
            switch (t.getLevelNum() % 2) {
                case 0:
                    if (children.getCount() <= t.getCount() || t.getBestChild() == null) {
                        t.setCount(children.getCount());
                        t.setBestChild(i);
                    }
                    break;
                case 1:
                    if (children.getCount() >= t.getCount() || t.getBestChild() == null) {
                        t.setCount(children.getCount());
                        t.setBestChild(i);
                    }
                    break;
            }
        }

    }

    private boolean findChildren(TreeNode node) {
        List<TreeNode> nodes = MoveFinder.GetAllMovesOnLevel(node.getMyTeam(), node.getConcurrentTeam(), node.getBoard(), node.getLevelNum() + 1);
        boolean foundWonPos = false;
        for (TreeNode n : nodes) {
            if (n.getMyTeam().countDistance(n.getBoard().getSize()) == n.getMyTeam().getWinDistance()) {
                foundWonPos = true;
            }
        }
        node.setChildren(nodes);
        levelInBuild.addAll(nodes);
        return foundWonPos;
    }
}
