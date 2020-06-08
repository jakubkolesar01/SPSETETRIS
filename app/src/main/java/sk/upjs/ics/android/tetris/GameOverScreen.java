package sk.upjs.ics.android.tetris;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class GameOverScreen extends AppCompatActivity implements View.OnTouchListener {

    private GameOverView gv;
    private RelativeLayout layout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        layout = (RelativeLayout) findViewById(R.id.layout);
        gv = new GameOverView(this);
        layout.setOnTouchListener(this);
        layout.addView(gv);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            return true;
        }
        return false;
    }

    class GameOverView extends View {

        public GameOverView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(70);
            canvas.drawText("Game Over", 50, 400, p);
            p.setTextSize(50);
            Highscores highscores = Highscores.getInstance();
            canvas.drawText("Score:" + highscores.getLastScore(), 450, 800, p);
            StorageManager storageManager = new StorageManager(getContext());
            try {
                storageManager.saveScores(highscores.getScores());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
