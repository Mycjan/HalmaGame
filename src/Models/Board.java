package Models;

import java.util.Arrays;

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
}
