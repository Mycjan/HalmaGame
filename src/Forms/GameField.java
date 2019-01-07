package Forms;

import Models.TeamDirection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameField extends JButton {

    private int xPosition;
    private int yPosition;


    public GameField(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }


    public void setTeamPawn(TeamDirection team) {
        Image img = null;
        try {
            if (team == TeamDirection.SW) {
                // img = ImageIO.read(getClass().getResource("C:\\Users\\Damian\\Documents\\GitHub\\HalmaGame\\src\\Resources\\pionek1.jpg"));
                img = ImageIO.read(new File("C:\\Users\\Damian\\Documents\\GitHub\\HalmaGame\\src\\Resources\\pionek2.jpg"));
            } else if (team == TeamDirection.NE) {
                //img = ImageIO.read(getClass().getResource("C:\\Users\\Damian\\Documents\\GitHub\\HalmaGame\\src\\Resources\\pionek2.jpg"));
                img = ImageIO.read(new File("C:\\Users\\Damian\\Documents\\GitHub\\HalmaGame\\src\\Resources\\pionek1.jpg"));
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








}
