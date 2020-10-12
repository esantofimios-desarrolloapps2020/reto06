package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * Game State
     */
    static final int DIALOG_QUIT_ID = 1;
    private TicTacToeGame mGame;
    private boolean mGameOver;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    private BoardView mBoardView;
    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;
    private boolean mSoundOn = true;
    private SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new TicTacToeGame();
        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setGame(mGame);
        newGame();
        mBoardView.setOnTouchListener(mTouchListener);

        mInfoTextView = (TextView) findViewById(R.id.tv_information);
        //Restore the scores from the persisten preference
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSoundOn = mPrefs.getBoolean("sound", true);
        String difficultyLevel = mPrefs.getString("difficulty_level",
                getResources().getString(R.string.difficulty_harder));
        if(difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
        else if(difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
        else
            mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);

    }

    private void startNewGame(){
        mGameOver = false;
        mGame.clearBoard();
        mBoardView.invalidate();
        mInfoTextView.setText("You go first");
    }

    private boolean setMove(char player, int location){
        // mHumanMediaPlayer.start();
        if (mGame.setMove(player, location)){
            if(TicTacToeGame.HUMAN_PLAYER == player)
                mHumanMediaPlayer.start();
            mBoardView.invalidate();
            return true;
        }
        return false;
        //mGame.setMove(player, location);
        /*mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if(player == TicTacToeGame.HUMAN_PLAYER){
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        }
        else{
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
        // menu.add("Menu Game");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.settings:
                startActivityForResult(new Intent(this, Settings.class), 0);
                return true;
            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {

        super.onResume();
        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.blown_vmax);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bubble_short);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
    }

    // Listen for touches the board
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @SuppressLint("WrongConstant")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            CharSequence text = "Hello toast!";
            int duration = 3;
            Toast.makeText(MainActivity.this, text,duration);
            int col = (int) motionEvent.getX() / mBoardView.getBoardCellWidth();
            int row = (int) motionEvent.getY() / mBoardView.getBoardCellHeight();
            int pos = row * 3 + col;
            // pending to add !mGameOver &&
            Log.i("button", String.valueOf(mGameOver));
            Log.i("button", String.valueOf(TicTacToeGame.HUMAN_PLAYER));
            Log.i("button", String.valueOf(pos));
            if (!mGameOver && setMove(TicTacToeGame.HUMAN_PLAYER, pos)){
                Log.i("data", String.valueOf(pos));

                int winner = mGame.checkForWinner();
                Log.i("state", String.valueOf(winner));
                if(winner == 0){
                    mInfoTextView.setText("Machine turn");
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }
                if(winner==0)
                    mInfoTextView.setText("is your turn");
                else if(winner == 1)
                    mInfoTextView.setText("It's a tie!");
                else if(winner == 2){
                    mGameOver=true;
                    // mHumansWins++;
                    mInfoTextView.setText(R.string.human_wins);
                }
                else{
                    mGameOver=true;
                    mInfoTextView.setText(R.string.computer_wins);
                }

            }
            return false;
        }
    };

    public void newGame(){
        Button newgame = (Button) findViewById(R.id.btn_start);
        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CANCELED) {
            mSoundOn = mPrefs.getBoolean("sound", true);
            String difficultyLevel = mPrefs.getString("difficulty_level",
                    getResources().getString(R.string.difficulty_harder));
            if (difficultyLevel.equals(getResources().getString(R.string.difficulty_easy)))
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
            else if (difficultyLevel.equals(getResources().getString(R.string.difficulty_harder)))
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
            else
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
        }
    }

}

