package sk.upjs.ics.android.tetris;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Tetris extends View implements View.OnClickListener{

    private GameBoard gameBoard;
    private MainActivity mainActivity;
    private ImageButton rotateButton;
    private ImageButton rightButton;
    private ImageButton downButton;
    private ImageButton leftButton;
    private  Timer timer = new Timer();
    private Random random = new Random();
    private  ArrayList<Piece> pieceList;
    private NextPieceView nextPieceView;
    private TextView currentLevelTextView;
    private TextView highscoreLevelTextView;
    private TextView currentPunkteTextView;
    private Points points;
    private final int score=10;
    private int timerPeriod =250;
    private int level=0;
    private boolean pause;
    private int diff;

    public Tetris(Context context, NextPieceView nextPieceView, GameBoard gameBoard, int diff) {
        super(context);
        this.diff = diff;
        this.mainActivity = (MainActivity) context;
        this.nextPieceView=nextPieceView;
        this.gameBoard =  gameBoard;
        pause = mainActivity.getPause();
        pieceList = gameBoard.getPieceList();
        points = new Points(context);


        currentLevelTextView = mainActivity.getCurrentLevelTextView();
        highscoreLevelTextView = mainActivity.getHighscoreLevelTextView();
        currentPunkteTextView = mainActivity.getPointTextView();

        currentLevelTextView.append("0");
        currentPunkteTextView.append("0");
        highscoreLevelTextView.append(""+points.loadHighscore());

        rotateButton = mainActivity.getRotateButton();
        rightButton = mainActivity.getRightButton();
        downButton = mainActivity.getDownButton();
        leftButton = mainActivity.getLeftButton();

        rotateButton .setOnClickListener(this);
        rightButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        gameLoop();
    }

    public void gameLoop() {

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                mainActivity.runOnUiThread(new TimerTask() {

                    @Override
                    public void run() {

                        if(gameOver()==false && mainActivity.getPause()==false ) {

                            for (int i = 0; i < diff; i++) {
                                if (gameBoard.can_Move_Down(gameBoard.getCurrentPiece()))
                                    gameBoard.moveDown(gameBoard.getCurrentPiece());
                            }

                            if (gameBoard.can_Move_Down(gameBoard.getCurrentPiece()) == false) {
                                int deletedRows = gameBoard.clearRows();
                                gameBoard.clearRows();
                                pieceList.remove(gameBoard.getCurrentPiece());
                                pieceList.add(new Piece(random.nextInt(7) + 1));
                                nextPieceView.invalidate();

                                if (deletedRows> 0) {
                                    points.setCurrentPoints(points.getCurrentPoints() + deletedRows * score);
                                    int p = points.getCurrentPoints();
                                    points.setLevel();

                                    currentPunkteTextView.setText("Points:" +" "+ p);
                                    currentLevelTextView.setText("Level" +" "+ points.getLevel());

                                    if (points.getLevel() > points.loadHighscore()) {
                                        points.writeHighscore();
                                        highscoreLevelTextView.setText("Highscore:" +" "+ points.getLevel());
                                    }
                                }

                                if(points.getLevel()>level) {
                                    level++;
                                    timerPeriod = timerPeriod - (points.getLevel()*20);
                                    timer.cancel();
                                    timer = new Timer();
                                    gameLoop();
                                }
                            }
                            invalidate();
                        }
                    }
                });
            }
        }, 0, timerPeriod);
    }

    public boolean gameOver() {

        if( gameBoard.checkGameOver(gameBoard.getCurrentPiece())==true ) {
            timer.cancel();
            pieceList.clear();
            gameBoard.clearGameBoard();
            mainActivity.setPause(true);
            Highscores highscores = Highscores.getInstance();
            highscores.addScore(points.getCurrentPoints());
            showGameOverScreen();
            return true;
        }
        return false;
    }

    public void resetGame() {
        timer.cancel();
        pieceList.clear();
        gameBoard.clearGameBoard();
        mainActivity.setPause(true);
        invalidate();
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        getContext().startActivity(intent);
    }

    public void showGameOverScreen() {
        Intent intent = new Intent(this.getContext(), GameOverScreen.class);
        getContext().startActivity(intent);
    }

    /*
    change colorCode to spezific Color and paint on GAmeboard
     */
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        Paint p = new Paint();

        for (int x = 0; x < gameBoard.getBoardHeight(); x++) {
            for (int y = 0; y < gameBoard.getBoardWidth(); y++) {

                int color  = gameBoard.codeToColor(x,y);
                p.setColor(color);
                canvas.drawRect(y*30, x*30, y*30+30, x*30+30,p);
            }
        }
    }

    /*
    control falling pieces with buttons
     */

    @Override
    public void onClick(View v) {
        if(mainActivity.getPause()==false) {

            switch(v.getId()) {
                case R.id.rightButton:
                    gameBoard.moveRight(gameBoard.getCurrentPiece());
                    invalidate();
                    break;
                case R.id.downButton:
                    gameBoard.fastDrop(gameBoard.getCurrentPiece());
                    invalidate();
                    break;
                case R.id.leftButton:
                    gameBoard.moveLeft(gameBoard.getCurrentPiece());
                    invalidate();
                    break;
                case R.id.rotateButton:
                    gameBoard.rotatePiece(gameBoard.getCurrentPiece());
                    invalidate();
                    break;
            }
        }
    }

    public Timer getTimer() {
        return this.timer;
    }

}
