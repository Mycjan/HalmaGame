import Models.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello World!");
        int boardSize = 8;
        Board board = new Board(boardSize);
        Team tNE = new Team(TeamDirection.NE, boardSize);
        Team tSW = new Team(TeamDirection.SW, boardSize);
        board.placeTeam(tNE);
        board.placeTeam(tSW);
        List<TreeNode> nodes = MoveFinder.GetAllMoves(tNE, board);
    }


}
