package Models;

public class ConfigureInformation {

    private int GameSize;
    private DifficultyLevel DifficultyLevel;

    public ConfigureInformation(int gameSize, Models.DifficultyLevel difficultyLevel) {
        GameSize = gameSize;
        DifficultyLevel = difficultyLevel;
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
}
