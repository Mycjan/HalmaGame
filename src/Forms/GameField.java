package Forms;

import Models.GameMaster;
import Models.TeamDirection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public void HighlightField(HighlightMode mode) {
        switch (mode) {
            case HighlightPawn:
                setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
                break;
            case HighlightPossibleMove:
                setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
                break;
            case HighlightNone:
                setBorder(BorderFactory.createLineBorder(Color.black, 1));
        }

    }

    public void ClearField() {
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        HighlightField(HighlightMode.HighlightNone);
        setIcon(null);
    }

    public Point getPosition() {
        return Position;
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

    public void CheckFieldAtTurnStart() {
        GameWindow.HighlightAllFields(HighlightMode.HighlightNone);
        HighlightField(HighlightMode.HighlightPawn);
        List<Point> PossibleMoves = GameWindow.getGM().getBoard().PossibleMovesFromPoint(new Point(xPosition, yPosition), true);
        GameWindow.HighlightFields(PossibleMoves, HighlightMode.HighlightPossibleMove);
        GameWindow.ResetListenersOnFields(GameWindow.getHighlightedPossibleMoves());
        GameWindow.AddPossibleMoveListeners(PossibleMoves);
        GameWindow.setCheckedField(this);
    }

    public void CheckFieldTurnContinue() {
        GameWindow.HighlightAllFields(HighlightMode.HighlightNone);
        HighlightField(HighlightMode.HighlightPawn);
        List<Point> PossibleMoves = GameWindow.getGM().getBoard().PossibleMovesFromPoint(new Point(xPosition, yPosition), false);
        GameWindow.HighlightFields(PossibleMoves, HighlightMode.HighlightPossibleMove);
        GameWindow.AddPossibleMoveListeners(PossibleMoves);
        GameWindow.setCheckedField(this);
        AddEndTurnListener();
        GameWindow.setItPossibleToEndTurn(true);
    }

    public void MoveTo() {
        GameWindow.getGM().getBoard().movePawnOnBoard(GameWindow.getCheckedField().getPosition(), this.Position);
        GameWindow.getGM().getTeamFirst().movePawnInTeam(GameWindow.getCheckedField().getPosition(), this.Position);
        GameWindow.HighlightAllFields(HighlightMode.HighlightNone);
        GameWindow.getHighlightedPossibleMoves().remove(new Point(xPosition, yPosition));
        Point From = GameWindow.getCheckedField().getPosition();
        Point To = this.Position;
        GameWindow.getCheckedField().ClearField();
        GameWindow.setCheckedField(this);
        this.setTeamPawn(GameWindow.getGM().getTeamFirst().getDirection());

        if (IsMoveShort(From, To)) {
            Thread thread= new Thread(new Runnable() {
                @Override
                public void run() {
                    GameWindow.getGM().processGame();
                }
            });
            thread.start();
        } else {
            GameWindow.ResetAllListeners();
            this.AddCheckFieldContinueListener();
            this.doClick();
        }
    }

    public void EndTurn() {
        Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                GameWindow.getGM().processGame();
            }
        });
        thread.start();
    }

    public Boolean IsMoveShort(Point from, Point to) {
        if (Math.abs(from.x - to.x) == 1 || Math.abs(from.y - to.y) == 1)
            return true;
        return false;
    }

    public void AddCheckFieldListener() {
        addActionListener(e -> CheckFieldAtTurnStart());
    }

    public void AddCheckFieldContinueListener() {
        addActionListener(e -> CheckFieldTurnContinue());
    }

    public void AddEndTurnListener()
    {
        addActionListener(e->EndTurn());
    }

    public void AddMoveListeners() {
        addActionListener(e -> MoveTo());
    }

    public void ResetListeners() {
        ActionListener[] ActionList = getActionListeners();
        for (ActionListener Action : ActionList) {
            this.removeActionListener(Action);
        }
    }


}
