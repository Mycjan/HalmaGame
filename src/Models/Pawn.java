package Models;

public class Pawn {

    private int x;
    private int y;


    Pawn(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    public Pawn clone() {
        return new Pawn(x, y);
    }

}
