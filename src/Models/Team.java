package Models;

import java.util.List;

public class Team {
    private List<Pawn> pawns;
    private TeamDirection direction;
    private double teamDistance;

    public Team(TeamDirection direction) {
        this.direction = direction;
    }

    public TeamDirection getDirection() {
        return direction;
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public void setPawns(List<Pawn> pawns) {
        this.pawns = pawns;
    }

    public double getTeamDistance() {
        return teamDistance;
    }

    public void setTeamDistance(double teamDistance) {
        this.teamDistance = teamDistance;
    }
}
