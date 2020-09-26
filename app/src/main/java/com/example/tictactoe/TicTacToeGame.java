package com.example.tictactoe;
import android.util.Log;
import android.widget.Button;

import java.util.Random;

public class TicTacToeGame {
    //private char mBoard[] = {'1','2','3','4','5','6','7','8','9'};
    public static final int BOARD_SIZE = 9;

    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT  = ' ';
    // The computer´s difficult levels
    public enum DifficultyLevel {Easy, Harder, Expert}
    // Current Difficulty level
    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Expert;

    private Random mRand;

    public TicTacToeGame(){
        mRand = new Random();
    }
        // Seed the random number generator

    public DifficultyLevel getmDifficultyLevel(){
        return mDifficultyLevel;
    }

    public void setmDifficultyLevel(DifficultyLevel difficultyLevel){
        mDifficultyLevel = difficultyLevel;
    }

    public void clearBoard(){
        //TODO
        /**
         * It is to set the board on this original state
         */
        Log.i("Details","Click");
    }

    public void setMove(char player, int location){

        Log.i("Details","Click");

    }

    public int getBlockingMove(Button mBoardButtons[]){
        // See if there's a move O can make to block X from winning

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (!mBoardButtons[i].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                    !mBoardButtons[i].getText().equals(String.valueOf(COMPUTER_PLAYER))) {
                String curr = (String) mBoardButtons[i].getText();
                mBoardButtons[i].setText(String.valueOf(HUMAN_PLAYER));
                if (checkForWinner(mBoardButtons) == 2) {
                    // mBoardButtons[i].setText(String.valueOf(COMPUTER_PLAYER));
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                }
                else
                    mBoardButtons[i].setText(curr);
            }
        }
        return -1;
    }

    public int getRandomMove(Button mBoardButtons[]){
        int move = mRand.nextInt(9 - 1 + 1) + 1;
        return move -1;
    }

    public int getWinningMove(Button mBoardButtons[]){
        //TODO
        /**
         * Return the best move for the computer to make
         */
        int move;

        // First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (!mBoardButtons[i].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                    !mBoardButtons[i].getText().equals(String.valueOf(COMPUTER_PLAYER))) {
                String curr = (String) mBoardButtons[i].getText();
                mBoardButtons[i].setText(String.valueOf(COMPUTER_PLAYER));
                if (checkForWinner(mBoardButtons) == 3) {
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                }
                else
                    mBoardButtons[i].setText(curr);
            }
        }

        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (!mBoardButtons[i].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                    !mBoardButtons[i].getText().equals(String.valueOf(COMPUTER_PLAYER))) {
                String curr = (String) mBoardButtons[i].getText();
                mBoardButtons[i].setText(String.valueOf(HUMAN_PLAYER));
                if (checkForWinner(mBoardButtons) == 2) {
                    mBoardButtons[i].setText(String.valueOf(COMPUTER_PLAYER));
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                }
                else
                    mBoardButtons[i].setText(curr);
            }
        }

        // Generate random move
        do
        {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoardButtons[move].getText().equals(String.valueOf(HUMAN_PLAYER)) ||
                mBoardButtons[move].getText().equals(String.valueOf(COMPUTER_PLAYER)));

        System.out.println("Computer is moving to " + (move + 1));
        mBoardButtons[move].setText(String.valueOf(COMPUTER_PLAYER));
        return 0;
    }

    public int getComputerMove(Button mBoardButtons[])
    {
        //TODO
        /**
         * Return the best move for the computer to make
         */
        int move = -1;
        Log.i("level", String.valueOf(getmDifficultyLevel()));
        if(getmDifficultyLevel() == DifficultyLevel.Easy)
            move = getRandomMove(mBoardButtons);
        else if(getmDifficultyLevel() == DifficultyLevel.Harder){
            move = getWinningMove(mBoardButtons);
            if (move == -1)
                return getRandomMove(mBoardButtons);
            }
            else if(getmDifficultyLevel() == DifficultyLevel.Expert){
                move = getWinningMove(mBoardButtons);
                /*if (move == -1)
                    Log.i("level", "getBlockingMove");
                    move = getBlockingMove(mBoardButtons);
                if (move == -1)
                    Log.i("level", "getRandomMove");
                    return getRandomMove(mBoardButtons);*/

        }
            return move;
    }

    public int checkForWinner(Button mBoardButtons[]) {
        /**
         * Check for a winner and return the status
         * 1 if was a tie, 2 for X winner and 3 for O winner
         */

        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3)	{
            if (mBoardButtons[i].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                    mBoardButtons[i+1].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                    mBoardButtons[i+2].getText().equals(String.valueOf(HUMAN_PLAYER)))
                return 2;
            if (mBoardButtons[i].getText().equals(String.valueOf(COMPUTER_PLAYER)) &&
                    mBoardButtons[i+1].getText().equals(String.valueOf(COMPUTER_PLAYER)) &&
                    mBoardButtons[i+2].getText().equals(String.valueOf(COMPUTER_PLAYER)))
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoardButtons[i].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                    mBoardButtons[i+3].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                    mBoardButtons[i+6].getText().equals(String.valueOf(HUMAN_PLAYER)))
                return 2;
            if (mBoardButtons[i].getText().equals(String.valueOf(COMPUTER_PLAYER)) &&
                    mBoardButtons[i+3].getText().equals(String.valueOf(COMPUTER_PLAYER)) &&
                    mBoardButtons[i+6].getText().equals(String.valueOf(COMPUTER_PLAYER)))
                return 3;
        }

        // Check for diagonal wins
        if ((mBoardButtons[0].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                mBoardButtons[4].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                mBoardButtons[8].getText().equals(String.valueOf(HUMAN_PLAYER))) ||
                (mBoardButtons[2].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                        mBoardButtons[4].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                        mBoardButtons[6].getText().equals(String.valueOf(HUMAN_PLAYER))))
            return 2;
        if ((mBoardButtons[0].getText().equals(String.valueOf(COMPUTER_PLAYER)) &&
                mBoardButtons[4].getText().equals(String.valueOf(COMPUTER_PLAYER)) &&
                mBoardButtons[8].getText().equals(String.valueOf(COMPUTER_PLAYER))) ||
                (mBoardButtons[2].getText().equals(String.valueOf(COMPUTER_PLAYER)) &&
                        mBoardButtons[4].getText().equals(String.valueOf(COMPUTER_PLAYER)) &&
                        mBoardButtons[6].getText().equals(String.valueOf(COMPUTER_PLAYER))))
            return 3;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (!mBoardButtons[i].getText().equals(String.valueOf(HUMAN_PLAYER)) &&
                    !mBoardButtons[i].getText().equals(String.valueOf(COMPUTER_PLAYER)))
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;

    }


}
