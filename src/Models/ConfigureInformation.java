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

    public void setTurnDisplayTime(int turnDisplayTime) {
        TurnDisplayTime = turnDisplayTime;
    }

    public ConfigureInformation(int gameSize, Models.DifficultyLevel difficultyLevel, MainWindow gameWindow, int turnDisplayTime) {
        GameSize = gameSize;
        DifficultyLevel = difficultyLevel;
        GameWindow = gameWindow;
        TurnDisplayTime=turnDisplayTime;
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
