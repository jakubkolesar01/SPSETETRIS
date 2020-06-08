package sk.upjs.ics.android.tetris;

import android.content.Context;
import android.content.SharedPreferences;

public class Points {

private int currentPoints =0;
private MainActivity mainActivity;
public int level = 0;

    public Points(Context context) {
        mainActivity = (MainActivity) context;
    }

    public void writeHighscore() {  // Als Highscore wird er höchste erreichte Level gespeichert
        SharedPreferences pref = mainActivity.getSharedPreferences("GAME",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("HIGHSCORE", level);
        editor.commit();
    }

    public int loadHighscore() {
        SharedPreferences pref = mainActivity.getSharedPreferences("GAME",0);
        return pref.getInt("HIGHSCORE",0);
    }


    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public int getCurrentPoints() {
        return this.currentPoints;
    }

    public void setLevel() {
    /*
        if(currentPoints>=20) {
           this.level = 1;
        }

        if(currentPoints>=60) {
        this.level = 2;
        }

        if(currentPoints>=100) {
        this.level = 3;
        }

        if(currentPoints>=140) {
        this.level = 4;
        }

        if(currentPoints>=180) {
        this.level = 5;
        }

        if(currentPoints>=220) {
        this.level = 6;
        }

        if(currentPoints>=260) {
        this.level = 7;
        }*/
    }

    public int getLevel() {
        return this.level;
    }
}
