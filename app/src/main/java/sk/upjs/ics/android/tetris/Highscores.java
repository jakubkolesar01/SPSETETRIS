package sk.upjs.ics.android.tetris;

import java.util.HashMap;
import java.util.Map;

public class Highscores {

    private static Highscores instance;

    private String currentName;
    private Map<String, Integer> scores = new HashMap<>();

    private Highscores() {
    }

    public static Highscores getInstance() {
        if (instance == null)
            instance = new Highscores();
        return instance;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void addScore(Integer score){
        scores.put(currentName, score);
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public int getLastScore(){return scores.get(currentName);}
}
