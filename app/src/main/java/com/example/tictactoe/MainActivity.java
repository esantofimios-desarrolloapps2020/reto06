package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    /**
     * Game State
     */
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
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if(player == TicTacToeGame.HUMAN_PLAYER){
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        }
        else{
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
        }
    }
}

