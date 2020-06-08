package sk.upjs.ics.android.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class Welcome extends AppCompatActivity {

    public ImageView start, scores, vypni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StorageManager storageManager = new StorageManager(this);
        Highscores highscores = Highscores.getInstance();
        if (highscores.getScores().size() == 0){
            try {
                highscores.setScores(storageManager.loadScores());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        start = (ImageView)findViewById(R.id.play);
        scores = (ImageView) findViewById(R.id.highscores);
        vypni = (ImageView) findViewById(R.id.exit);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                // start.setBackground(highlight);
                Intent startovanie = new Intent(Welcome.this, SetDiff.class);
                startActivity(startovanie);

            }
        });
        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                // scores.setBackground(highlight);
                Intent scoreboard = new Intent(Welcome.this, Scoreboard.class);
                startActivity(scoreboard);

            }
        });
        vypni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Drawable highlight = getResources().getDrawable( R.drawable.highlight);
                // vypni.setBackground(highlight);
                System.exit(0);
            }
        });
    }


}
