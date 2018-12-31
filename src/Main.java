import Models.*;
import Forms.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

       /* System.out.println("Hello World!");
        int boardSize = 8;
        Board board = new Board(boardSize);
        Team tNE = new Team(TeamDirection.NE, boardSize);
        Team tSW = new Team(TeamDirection.SW, boardSize);
        board.placeTeam(tNE);
        board.placeTeam(tSW);
        TreeNode root = new TreeNode(null, board, tNE, tSW);
        GameTree tree = new GameTree(root);
        tree.bildTree(10);
*/
        MainWindow menu = new MainWindow();
        menu.setVisible(true);


    }


}
