package Models;

public class GameMaster {
    private Board board;
    private Team teamFirst;
    private Team teamSecond;

    public GameMaster() {
    }

    public GameMaster(Board board, Team teamFirst, Team teamSecond) {
        this.board = board;
        this.teamFirst = teamFirst;
        this.teamSecond = teamSecond;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Team getTeamFirst() {
        return teamFirst;
    }

    public void setTeamFirst(Team teamFirst) {
        this.teamFirst = teamFirst;
    }

    public Team getTeamSecond() {
        return teamSecond;
    }

    public void setTeamSecond(Team teamSecond) {
        this.teamSecond = teamSecond;
    }
}
