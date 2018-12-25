package Models;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MoveFinder {

    private static class PawnMove implements Runnable {
        private Pawn pawn;
        private List<TreeNode> nodes;
        private Board board;
        private Team myTeam;
        private Team concurrentTeam;

        public PawnMove(Pawn pawn, List<TreeNode> nodes, Board board, Team myTeam, Team concurrentTeam) {
            this.pawn = pawn;
            this.nodes = nodes;
            this.board = board;
            this.myTeam = myTeam;
            this.concurrentTeam = concurrentTeam;
        }

        @Override
        public void run() {
            nodes.addAll(MoveFinder.GetPawnsAllMoves(pawn, board, myTeam, concurrentTeam));
        }
    }

    public static List<TreeNode> GetAllMoves(Team myTeam, Team concurrentTeam, Board currentBoard) {
        List<TreeNode> nodes = Collections.synchronizedList(new ArrayList<>());
        List<Thread> threads = new ArrayList<>();
        for (Pawn p : myTeam.getPawns()) {
            Thread t = new Thread(new PawnMove(p, nodes, currentBoard, myTeam, concurrentTeam));
            threads.add(t);
            t.run();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return nodes;
    }

    private static List<TreeNode> GetPawnsAllMoves(Pawn pawn, Board currentBoard, Team myTeam, Team concurrentTeam) {

        if (!currentBoard.getTiles()[pawn.getX()][pawn.getY()])
            throw new IllegalArgumentException("Incorrect pawn position");

        List<TreeNode> nodes = new ArrayList<>(MoveFinder.GetOneFieldsMove(pawn, currentBoard, myTeam, concurrentTeam));

        ArrayList<Point> pointToCheck = new ArrayList<Point>() {
            {
                add(new Point(pawn.getX(), pawn.getY()));
            }
        };
        ArrayList<List<Point>> pointsToCheck = new ArrayList<List<Point>>() {
            {
                add(pointToCheck);
            }
        };

        boolean[][] checked = currentBoard.tilesDeepCopy();
        while (!pointsToCheck.isEmpty()) {
            List<Point> pawnPosition = pointsToCheck.remove(0);
            nodes.addAll(MoveFinder.GetJumpMove(pawnPosition, currentBoard, pointsToCheck, checked, myTeam, concurrentTeam));
        }

        return nodes;
    }

    private static List<TreeNode> GetOneFieldsMove(Pawn pawn, Board currentBoard, Team myTeam, Team concurrentTeam) {
        List<TreeNode> nodes = new ArrayList<>();

        int x = pawn.getX();
        int y = pawn.getY();
        int size = currentBoard.getSize();

        //check field right
        if (x + 1 < size && !currentBoard.getTiles()[x + 1][y]) {
            TreeNode node = new TreeNode(new Point(x + 1, y), new Point(x, y), currentBoard, myTeam, concurrentTeam);
            nodes.add(node);
        }
        //check field up
        if (y + 1 < size && !currentBoard.getTiles()[x][y + 1]) {
            TreeNode node = new TreeNode(new Point(x, y + 1), new Point(x, y), currentBoard, myTeam, concurrentTeam);
            nodes.add(node);
        }
        //check field left
        if (x - 1 >= 0 && !currentBoard.getTiles()[x - 1][y]) {
            TreeNode node = new TreeNode(new Point(x - 1, y), new Point(x, y), currentBoard, myTeam, concurrentTeam);
            nodes.add(node);
        }
        //check field down
        if (y - 1 >= 0 && !currentBoard.getTiles()[x][y - 1]) {
            TreeNode node = new TreeNode(new Point(x, y - 1), new Point(x, y), currentBoard, myTeam, concurrentTeam);
            nodes.add(node);
        }

        return nodes;
    }

    private static List<TreeNode> GetJumpMove(List<Point> pawnPosition, Board currentBoard, List<List<Point>> pointsToCheck, boolean[][] checked, Team myTeam, Team concurrentTeam) {
        List<TreeNode> nodes = new ArrayList<>();

        int x = (int) pawnPosition.get(pawnPosition.size() - 1).getX();
        int y = (int) pawnPosition.get(pawnPosition.size() - 1).getY();
        int size = currentBoard.getSize();

        //check jump right
        if (x + 2 < size && !checked[x + 2][y] && currentBoard.getTiles()[x + 1][y] && !currentBoard.getTiles()[x + 2][y]) {
            checked[x + 2][y] = true;
            List<Point> pointToCheck = new ArrayList<>(pawnPosition);
            pointToCheck.add(new Point(x + 2, y));
            pointsToCheck.add(pointToCheck);
            TreeNode node = new TreeNode(new Point(x + 2, y), new Point(x, y), currentBoard, new ArrayList<>(pawnPosition), myTeam, concurrentTeam);
            nodes.add(node);
        }

        //check jump left
        if (x - 2 >= 0 && !checked[x - 2][y] && currentBoard.getTiles()[x - 1][y] && !currentBoard.getTiles()[x - 2][y]) {
            checked[x - 2][y] = true;
            List<Point> pointToCheck = new ArrayList<>(pawnPosition);
            pointToCheck.add(new Point(x - 2, y));
            pointsToCheck.add(pointToCheck);
            TreeNode node = new TreeNode(new Point(x - 2, y), new Point(x, y), currentBoard, new ArrayList<>(pawnPosition), myTeam, concurrentTeam);
            nodes.add(node);
        }

        //check jump down
        if (y - 2 >= 0 && !checked[x][y - 2] && currentBoard.getTiles()[x][y - 1] && !currentBoard.getTiles()[x][y - 2]) {
            checked[x][y - 2] = true;
            List<Point> pointToCheck = new ArrayList<>(pawnPosition);
            pointToCheck.add(new Point(x, y - 2));
            pointsToCheck.add(pointToCheck);
            TreeNode node = new TreeNode(new Point(x, y - 2), new Point(x, y), currentBoard, new ArrayList<>(pawnPosition), myTeam, concurrentTeam);
            nodes.add(node);
        }

        //check jump up
        if (y + 2 < size && !checked[x][y + 2] && currentBoard.getTiles()[x][y + 1] && !currentBoard.getTiles()[x][y + 2]) {
            checked[x][y + 2] = true;
            List<Point> pointToCheck = new ArrayList<>(pawnPosition);
            pointToCheck.add(new Point(x, y + 2));
            pointsToCheck.add(pointToCheck);
            TreeNode node = new TreeNode(new Point(x, y + 2), new Point(x, y), currentBoard, new ArrayList<>(pawnPosition), myTeam, concurrentTeam);
            nodes.add(node);
        }

        return nodes;
    }

}

