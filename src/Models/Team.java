package Models;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Team {
    private List<Pawn> pawns;
    private TeamDirection direction;
    private double winDistance;

    public Team() {
    }

    public Team(TeamDirection direction, int boardSize) {
        this.direction = direction;
        List<Pawn> NETeam = Arrays.asList(new Pawn(0, boardSize - 1), new Pawn(0, boardSize - 2), new Pawn(0, boardSize - 3),
                new Pawn(1, boardSize - 1), new Pawn(1, boardSize - 2), new Pawn(1, boardSize - 3),
                new Pawn(2, boardSize - 1), new Pawn(2, boardSize - 2));
        List<Pawn> SWTeam = Arrays.asList(new Pawn(boardSize - 1, 0), new Pawn(boardSize - 1, 1), new Pawn(boardSize - 1, 2),
                new Pawn(boardSize - 2, 0), new Pawn(boardSize - 2, 1), new Pawn(boardSize - 2, 2),
                new Pawn(boardSize - 3, 0), new Pawn(boardSize - 3, 1));

        switch (direction) {
            case NE:
                this.pawns = NETeam;
                this.winDistance = teamDistance(boardSize, SWTeam, TeamDirection.NE);
                break;
            case SW:
                this.pawns = SWTeam;
                this.winDistance = teamDistance(boardSize, NETeam, TeamDirection.SW);
                break;
        }
    }

    public double getWinDistance() {
        return winDistance;
    }

    public void movePawnInTeam(Point oldPoint, Point newPoint) {
        for (Pawn p : pawns) {
            if (p.getX() == (int) oldPoint.getX() && p.getY() == (int) oldPoint.getY()) {
                p.setX((int) newPoint.getX());
                p.setY((int) newPoint.getY());
                return;
            }
        }

        throw new IllegalArgumentException("Point not found in team");
    }

    private static double teamDistance(int boardSize, List<Pawn> endTeam, TeamDirection direction) {
        double distSum = 0;
        int x = Integer.MIN_VALUE;
        int y = Integer.MIN_VALUE;
        switch (direction) {
            case SW:
                x = 0;
                y = boardSize - 1;
                break;
            case NE:
                x = boardSize - 1;
                y = 0;
                break;
        }

        for (Pawn p : endTeam) {
            double dist = Math.sqrt(((x - p.getX()) * (x - p.getX())) + ((y - p.getY()) * (y - p.getY())));
            distSum += dist;
        }

        return distSum;
    }


    public double countDistance(int boardSize) {
        return teamDistance(boardSize, pawns, direction);
    }

    public TeamDirection getDirection() {
        return direction;
    }

    public List<Pawn> getPawns() {
        return pawns;
    }

    public Team clone() {
        Team nTeam = new Team();
        List<Pawn> nPawns = new ArrayList<>();
        for (Pawn p : pawns)
            nPawns.add(p.clone());
        nTeam.pawns = nPawns;
        nTeam.direction = direction;
        nTeam.winDistance = winDistance;
        return nTeam;
    }

    public void setPawns(List<Pawn> pawns) {
        this.pawns = pawns;
    }
}
