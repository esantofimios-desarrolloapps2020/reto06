package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * Game State
     */
    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;
    private TicTacToeGame mGame;
    private Button mBoardButtons[];
    private TextView mInfoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.btn_one);
        mBoardButtons[1] = (Button) findViewById(R.id.btn_two);
        mBoardButtons[2] = (Button) findViewById(R.id.btn_three);
        mBoardButtons[3] = (Button) findViewById(R.id.btn_four);
        mBoardButtons[4] = (Button) findViewById(R.id.btn_five);
        mBoardButtons[5] = (Button) findViewById(R.id.btn_six);
        mBoardButtons[6] = (Button) findViewById(R.id.btn_seven);
        mBoardButtons[7] = (Button) findViewById(R.id.btn_eight);
        mBoardButtons[8] = (Button) findViewById(R.id.btn_nine);

        mInfoTextView = (TextView) findViewById(R.id.tv_information);

        mGame = new TicTacToeGame();
        startNewGame();
        // newGame();
    }

    private void startNewGame(){
        mGame.clearBoard();
        for(int i = 0; i < mBoardButtons.length; i++){
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
        mInfoTextView.setText("You go first");
    }
    private class ButtonClickListener implements View.OnClickListener{
        int location;

        public ButtonClickListener(int location){
            this.location = location;
        }
        @Override
        public void onClick(View view) {
            if (mBoardButtons[location].isEnabled()){
                setMove(TicTacToeGame.HUMAN_PLAYER, location);

                int winner = mGame.checkForWinner(mBoardButtons);
                Log.i("state", String.valueOf(winner));
                if(winner == 0){
                    mInfoTextView.setText("Machine turn");
                    int move = mGame.getComputerMove(mBoardButtons);
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner(mBoardButtons);
                }
                if(winner==0)
                    mInfoTextView.setText("is your turn");
                else if(winner == 1)
                    mInfoTextView.setText("It's a tie!");
                else if(winner == 2)
                    mInfoTextView.setText("You won!");
                else
                    mInfoTextView.setText("Android won!");

            }
        }
    }
    private void setMove(char player, int location){
        //mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if(player == TicTacToeGame.HUMAN_PLAYER){
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        }
        else{
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
        }
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
            case R.id.ai_difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
                return true;
            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
        }
        startNewGame();
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch (id){
            case DIALOG_DIFFICULTY_ID:
                builder.setTitle(R.string.difficulty_choose);

                final CharSequence[] levels = {
                    getResources().getString(R.string.difficulty_easy),
                    getResources().getString(R.string.difficulty_harder),
                    getResources().getString(R.string.difficulty_expert)
                };
                final int selected = 0;

            builder.setSingleChoiceItems(levels, selected, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    TicTacToeGame.DifficultyLevel mDifficultyLevel;
                    if (levels[i].equals("Easy")){
                        mDifficultyLevel = TicTacToeGame.DifficultyLevel.Easy;
                        mGame.setmDifficultyLevel(mDifficultyLevel);
                    }
                    if (levels[i].equals("Harder")){
                        mDifficultyLevel = TicTacToeGame.DifficultyLevel.Harder;
                        mGame.setmDifficultyLevel(mDifficultyLevel);
                    }
                    if (levels[i].equals("Expert")){
                        mDifficultyLevel = TicTacToeGame.DifficultyLevel.Expert;
                        mGame.setmDifficultyLevel(mDifficultyLevel);
                    }
                    Toast.makeText(getApplicationContext(), mGame.getmDifficultyLevel().toString(), Toast.LENGTH_SHORT).show();
                }
            });
                dialog = builder.create();
                break;

            case DIALOG_QUIT_ID:
                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();
                break;
        }
        return dialog;
    }

    /*public void newGame(){
        Button newgame = (Button) findViewById(R.id.btn_start);
        newgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
            }
        });
    }*/


}

