package Models;

import Forms.MainWindow;

public class ConfigureInformation {

    private int GameSize;
    private DifficultyLevel DifficultyLevel;
    public MainWindow GameWindow;



    public ConfigureInformation(int gameSize, Models.DifficultyLevel difficultyLevel, MainWindow gameWindow) {
        GameSize = gameSize;
        DifficultyLevel = difficultyLevel;
        GameWindow = gameWindow;
    }

    public int getGameSize() {
        return GameSize;
    }

    public void setGameSize(int gameSize) {
        GameSize = gameSize;
    }

    public Models.DifficultyLevel getDifficultyLevel() {
        return DifficultyLevel;
    }

    public void setDifficultyLevel(Models.DifficultyLevel difficultyLevel) {
        DifficultyLevel = difficultyLevel;
    }

    public MainWindow getGameWindow() {
        return GameWindow;
    }

    public void setGameWindow(MainWindow gameWindow) {
        GameWindow = gameWindow;
    }

}
