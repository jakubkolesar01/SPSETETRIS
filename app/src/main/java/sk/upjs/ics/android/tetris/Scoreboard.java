package sk.upjs.ics.android.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Scoreboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        LinearLayout linearLayout = findViewById(R.id.scoresParent);
        Highscores highscores = Highscores.getInstance();
        for (String s : highscores.getScores().keySet()) {
            TextView textView = new TextView(this);
            textView.setText(s + ": " + highscores.getScores().get(s));
            linearLayout.addView(textView);
        }
    }
}
