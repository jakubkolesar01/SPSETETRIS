package sk.upjs.ics.android.tetris;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart;
    private Button buttonReset;
    private Button back;
    private ImageButton rotateButton;
    private ImageButton rightButton;
    private ImageButton downButton;
    private ImageButton leftButton;

    private TextView pointTextView;
    private TextView highscoreLevelTextView;
    private TextView currentLevelTextView;
    private Tetris tetris;
    private NextPieceView nextPieceView;
    private boolean pause = true;
    private MediaPlayer mediaPlayer;
    private int stopMediaplayer;
    private GameBoard gameBoard = new GameBoard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        back = (Button)findViewById(R.id.back);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
       mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource( this, Uri.parse("android.resource://com.example.admin.tetris/raw/tetrismusik"));
            mediaPlayer.prepare();
        } catch (Exception e) {e.printStackTrace();}


        buttonStart = (Button) findViewById(R.id.buttonstart);
        buttonReset = (Button) findViewById(R.id.buttonreset);
        rotateButton = (ImageButton) findViewById(R.id.rotateButton);
        rightButton = (ImageButton) findViewById(R.id.rightButton);
        downButton = (ImageButton) findViewById(R.id.downButton);
        leftButton = (ImageButton) findViewById(R.id.leftButton);
        pointTextView = (TextView) findViewById(R.id.textViewPunkte);
        highscoreLevelTextView= (TextView) findViewById(R.id.textViewHighscore);
        currentLevelTextView = (TextView) findViewById(R.id.levelText);

        nextPieceView = new NextPieceView(this, gameBoard);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(300,300);
        nextPieceView.setLayoutParams(params1);
        RelativeLayout relativeSteinAnzeige = (RelativeLayout) findViewById(R.id.relativelayout1);
        nextPieceView.setBackgroundColor(Color.YELLOW);
        relativeSteinAnzeige.addView(nextPieceView);

        int diff = getIntent().getIntExtra("Difficulty", 1);
        tetris = new Tetris(this,nextPieceView, gameBoard, diff);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(480, 900);
        tetris.setLayoutParams(params);
        RelativeLayout relativeTetris = (RelativeLayout) findViewById(R.id.relativelayout);
        tetris.setBackgroundColor(Color.YELLOW);
        relativeTetris.addView(tetris);
        buttonStart.setOnClickListener(new View.OnClickListener() {

           int tmp=0;
            @Override
            public void onClick(View v) {
                stopMediaplayer = mediaPlayer.getCurrentPosition();
                 tmp++;

                if(buttonStart.getText().equals("Start")) {
                    buttonStart.setText("Pause");
                    pause = false;

                    if(tmp==1) {
                        mediaPlayer.start();
                        mediaPlayer.setLooping(true);
                    } else if(tmp>1) {
                        mediaPlayer.seekTo(stopMediaplayer);
                        mediaPlayer.start();
                    }
                }

                else if(buttonStart.getText().equals("Pause")) {
                    buttonStart.setText("Start");
                    pause = true;
                    mediaPlayer.pause();
                }

            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               tetris.resetGame();
            }
        });

    }

    @Override
    public void onRestart() {
        super.onRestart();
        pause = false;
        mediaPlayer.seekTo(stopMediaplayer);
        mediaPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause = true;
        mediaPlayer.stop();
        mediaPlayer.pause();
        stopMediaplayer = mediaPlayer.getCurrentPosition();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

    public Button getResetButton() { return buttonReset;}
    public ImageButton getRightButton() { return rightButton;}
    public ImageButton getDownButton() { return downButton;}
    public ImageButton getLeftButton() { return leftButton;}
    public ImageButton getRotateButton() { return rotateButton; }

    public boolean getPause() {  return pause;}
    public void setPause(boolean pause) { this.pause=pause;}
    public TextView getHighscoreLevelTextView() { return highscoreLevelTextView; }
    public TextView getPointTextView() { return pointTextView; }
    public TextView getCurrentLevelTextView() { return currentLevelTextView;}
    public MediaPlayer getMediaPlayer() {  return mediaPlayer; }

    public void goback(View view) {
        Intent nazad = new Intent(MainActivity.this, SetDiff.class);
        startActivity(nazad);
    }
}
