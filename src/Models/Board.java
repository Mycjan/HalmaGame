package Models;

public class Board {
    private boolean[] tiles;
    private int size;

    public Board(int size) {
        this.size = size;
        tiles = new boolean[size];
    }

    public int getSize() {
        return size;
    }

    public boolean[] getTiles() {
        return tiles;
    }

    public void setTiles(boolean[] tiles) {
        this.tiles = tiles;
    }
}
