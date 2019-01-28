package Forms;

import Models.TeamDirection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class GameField extends JButton {

    private Point Position;
    private int xPosition;
    private int yPosition;
    private MainWindow GameWindow;

    public GameField(int xPosition, int yPosition, MainWindow GameWindow) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.GameWindow = GameWindow;
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        Position = new Point(xPosition, yPosition);
    }

    public Point getPosition() {
        return Position;
    }

    public Boolean isMoveShort(Point from, Point to) {
        if (Math.abs(from.x - to.x) == 1 || Math.abs(from.y - to.y) == 1)
            return true;
        return false;
    }

    public void highlightField(HighlightMode mode) {
        switch (mode) {
            case HighlightPawn:
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                break;
            case HighlightPossibleMove:
                setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
                break;
            case HighlightNone:
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
    }

    public void clearField() {
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        highlightField(HighlightMode.HighlightNone);
        setIcon(null);
    }

    public void setTeamPawn(TeamDirection team) {
        String CurrentWorkingDirectory = System.getProperty("user.dir") + "\\src\\Resources";
        Image img = null;
        try {
            if (team == TeamDirection.SW) {
                String path = CurrentWorkingDirectory + "\\pionek2.jpg";
                img = ImageIO.read(new File(path));
            } else if (team == TeamDirection.NE) {
                String path = CurrentWorkingDirectory + "\\pionek1.jpg";
                img = ImageIO.read(new File(path));
            }
        } catch (Exception ex) {
            System.out.println("Image not found");
            System.out.println(ex.getMessage());
        }
        if (img != null) {
            Image newImage = img.getScaledInstance(getWidth() - 5, getHeight() - 5, Image.SCALE_FAST);
            this.setIcon(new ImageIcon(newImage));
        } else {
            this.setIcon(null);
        }
    }

    public void checkFieldAtTurnStart() {
        GameWindow.HighlightAllFields(HighlightMode.HighlightNone);
        highlightField(HighlightMode.HighlightPawn);
        List<Point> PossibleMoves = GameWindow.getGM().getBoard().PossibleMovesFromPoint(new Point(xPosition, yPosition), true);
        GameWindow.HighlightFields(PossibleMoves, HighlightMode.HighlightPossibleMove);
        GameWindow.ResetListenersOnFields(GameWindow.getHighlightedPossibleMoves());
        GameWindow.AddPossibleMoveListeners(PossibleMoves);
        GameWindow.setCheckedField(this);
    }

    public void checkFieldTurnContinue() {
        GameWindow.HighlightAllFields(HighlightMode.HighlightNone);
        highlightField(HighlightMode.HighlightPawn);
        List<Point> PossibleMoves = GameWindow.getGM().getBoard().PossibleMovesFromPoint(new Point(xPosition, yPosition), false);
        GameWindow.HighlightFields(PossibleMoves, HighlightMode.HighlightPossibleMove);
        GameWindow.AddPossibleMoveListeners(PossibleMoves);
        GameWindow.setCheckedField(this);
        addEndTurnListener();
        GameWindow.setItPossibleToEndTurn(true);
    }

    public void moveTo() {
        GameWindow.getGM().getBoard().movePawnOnBoard(GameWindow.getCheckedField().getPosition(), this.Position);
        GameWindow.getGM().getTeamFirst().movePawnInTeam(GameWindow.getCheckedField().getPosition(), this.Position);
        GameWindow.HighlightAllFields(HighlightMode.HighlightNone);
        GameWindow.getHighlightedPossibleMoves().remove(new Point(xPosition, yPosition));
        Point From = GameWindow.getCheckedField().getPosition();
        Point To = this.Position;
        GameWindow.getCheckedField().clearField();
        GameWindow.setCheckedField(this);
        this.setTeamPawn(GameWindow.getGM().getTeamFirst().getDirection());


        if (isMoveShort(From, To)) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    GameWindow.getGM().processGame();
                }
            });
            thread.start();
        } else {
            GameWindow.ResetAllListeners();
            this.addCheckFieldContinueListener();
            this.doClick();
        }
    }

    public void endTurn() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GameWindow.getGM().processGame();
            }
        });
        thread.start();
    }

    public void addCheckFieldListener() {
        addActionListener(e -> checkFieldAtTurnStart());
    }

    public void addCheckFieldContinueListener() {
        addActionListener(e -> checkFieldTurnContinue());
    }

    public void addEndTurnListener() {
        addActionListener(e -> endTurn());
    }

    public void addMoveListeners() {
        addActionListener(e -> moveTo());
    }

    public void resetListeners() {
        ActionListener[] ActionList = getActionListeners();
        for (ActionListener Action : ActionList) {
            this.removeActionListener(Action);
        }
    }
}
