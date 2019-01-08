package Models;

import java.awt.*;
import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private boolean[][] tiles;
    private int size;

    public Board(int size) {
        this.size = size;
        tiles = new boolean[size][size];
    }

    public void placeTeam(Team t) {
        for (Pawn p : t.getPawns()) {
            tiles[p.getX()][p.getY()] = true;
        }
    }

    public void movePawnOnBoard(Point oldPoint, Point newPoint) {
        tiles[(int) oldPoint.getX()][(int) oldPoint.getY()] = false;
        tiles[(int) newPoint.getX()][(int) newPoint.getY()] = true;
    }

    public int getSize() {
        return size;
    }

    public boolean[][] getTiles() {
        return tiles;
    }

    public void setTiles(boolean[][] tiles) {
        this.tiles = tiles;
    }

    public Board clone() {
        Board newBoard = new Board(size);
        newBoard.setTiles(this.tilesDeepCopy());

        return newBoard;
    }

    public boolean[][] tilesDeepCopy() {
        if (tiles == null) {
            return null;
        }

        final boolean[][] result = new boolean[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            result[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }
        return result;
    }

    public List<Point> PossibleMovesFromPoint(Point From, Boolean FirstMoveInTurn) {
        List<Point> PossibleMoves = new ArrayList<Point>();

        Point Right = new Point(From.x + 1, From.y);
        Point Left = new Point(From.x - 1, From.y);
        Point Up = new Point(From.x, From.y - 1);
        Point Down = new Point(From.x, From.y + 1);

        if (FirstMoveInTurn) {
            if (isOnBoard(Right) && !isTileOccupied(Right))
                PossibleMoves.add(Right);
            if (isOnBoard(Left) && !isTileOccupied(Left))
                PossibleMoves.add(Left);
            if (isOnBoard(Up) && !isTileOccupied(Up))
                PossibleMoves.add(Up);
            if (isOnBoard(Down) && !isTileOccupied(Down))
                PossibleMoves.add(Down);
        }

        Point DoubleRight = new Point(From.x + 2, From.y);
        Point DoubleLeft = new Point(From.x - 2, From.y);
        Point DoubleDown = new Point(From.x, From.y + 2);
        Point DoubleUp = new Point(From.x, From.y - 2);

        if (isPossibleJump(From, DoubleRight))
            PossibleMoves.add(DoubleRight);
        if (isPossibleJump(From, DoubleLeft))
            PossibleMoves.add(DoubleLeft);
        if (isPossibleJump(From, DoubleDown))
            PossibleMoves.add(DoubleDown);
        if (isPossibleJump(From, DoubleUp))
            PossibleMoves.add(DoubleUp);

        return PossibleMoves;
    }

    private boolean isPossibleJump(Point From, Point To) {

        if (!isOnBoard(To))
            return false;
        if (isTileOccupied(To))
            return false;

        //up or down
        if (From.x == To.x) {
            //up
            if (To.y == From.y - 2) {
                if (isTileOccupied(new Point(From.x, From.y - 1)))
                    return true;
            }
            //down
            if (To.y == From.y + 2) {
                if (isTileOccupied(new Point(From.x, From.y + 1)))
                    return true;
            }
        }
        //left or right
        else if (From.y == To.y) {
            //right
            if (To.x == From.x + 2) {
                if (isTileOccupied(new Point(From.x + 1, From.y)))
                    return true;
            }
            //left
            if (To.x == From.x - 2) {
                if (isTileOccupied(new Point(From.x - 1, From.y)))
                    return true;
            }
        }
            return false;
    }

    public Boolean isOnBoard(Point point) {
        return point.x >= 0 && point.x < size && point.y >= 0 && point.y < size;
    }

    public Boolean isTileOccupied(Point point) {
        return tiles[point.x][point.y] == true;
    }
}
