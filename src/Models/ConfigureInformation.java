package Models;

import Forms.MainWindow;

public class ConfigureInformation {

    private int GameSize;
    private DifficultyLevel DifficultyLevel;
    public MainWindow GameWindow;
    private int TurnDisplayTime;

    public int getTurnDisplayTime() {
        return TurnDisplayTime;
    }

    public ConfigureInformation(int gameSize, Models.DifficultyLevel difficultyLevel, MainWindow gameWindow, int turnDisplayTime) {
        GameSize = gameSize;
        DifficultyLevel = difficultyLevel;
        GameWindow = gameWindow;
        TurnDisplayTime = turnDisplayTime;
    }

    public int getGameSize() {
        return GameSize;
    }

    public Models.DifficultyLevel getDifficultyLevel() {
        return DifficultyLevel;
    }

}
