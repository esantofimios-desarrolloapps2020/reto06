package com.example.tictactoe;
import android.util.Log;
import android.widget.Button;

import java.util.Random;

public class TicTacToeGame {
    public static final int BOARD_SIZE = 9;

    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT  = ' ';
    private char mBoard[];
    // The computer´s difficult levels
    public enum DifficultyLevel {Easy, Harder, Expert}
    // Current Difficulty level
    private DifficultyLevel mDifficultyLevel = DifficultyLevel.Expert;

    private Random mRand;

    public TicTacToeGame(){
        mRand = new Random();
        mBoard = new char[9];
    }
        // Seed the random number generator

    public DifficultyLevel getDifficultyLevel() {
        return mDifficultyLevel;
    }

    public boolean setDifficultyLevel(DifficultyLevel difficultyLevel) {
        boolean mchange=true;
        if( mDifficultyLevel==difficultyLevel) mchange=false;
        mDifficultyLevel = difficultyLevel;
        return mchange;
    }

    public void clearBoard(){
        //TODO
        /**
         * It is to set the board on this original state
         */
        for (int i=0; i < mBoard.length; i++){
            mBoard[i] = OPEN_SPOT;
        }
        Log.i("Details","Click");
    }

    public char getBoardOccupant(int i){
        return mBoard[i];
    }


    public int getComputerMove() {
        int move = -1;

        if (mDifficultyLevel == DifficultyLevel.Easy)
            move = getRandomMove();
        else if (mDifficultyLevel == DifficultyLevel.Harder) {
            move = getWinningMove();
            if (move == -1)
                move = getRandomMove();
        } else if (mDifficultyLevel == DifficultyLevel.Expert) {

            // Try to win, but if that's not possible, block.
            // If that's not possible, move anywhere.
            move = getWinningMove();
            if (move == -1)
                move = getBlockingMove();
            if (move == -1)
                move = getRandomMove();
        }

        // mBoard[move] = COMPUTER_PLAYER;

        return move;
    }


    public boolean setMove(char player, int location){
        if(mBoard[location] == OPEN_SPOT){
            mBoard[location] = player;
            return true;
        }
        Log.i("Details","Click");
        return false;

    }

    public int getRandomMove() {
        int move=-1;
        // Generate random move
        do {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] == HUMAN_PLAYER
                || mBoard[move] == COMPUTER_PLAYER);

        return move;
    }

    public int getWinningMove( ){
        // First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if ((mBoard[i] != HUMAN_PLAYER) && (mBoard[i] != COMPUTER_PLAYER)) {
                mBoard[i]= COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                }
                else
                    mBoard[i]=OPEN_SPOT;
            }
        }
        return -1;
    }


    private int getBlockingMove( ){
        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                mBoard[i]= HUMAN_PLAYER;
                if (checkForWinner( ) == 2) {
                    mBoard[i]=COMPUTER_PLAYER;
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                }
                else
                    mBoard[i]=OPEN_SPOT;
            }
        }
        return -1;
    }


    public int checkForWinner(  ){

        /**
         * Check for a winner and return the status
         * 1 if was a tie, 2 for X winner and 3 for O winner
         */
        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3)	{
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+1] == HUMAN_PLAYER &&
                    mBoard[i+2] == HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+1]== COMPUTER_PLAYER &&
                    mBoard[i+2] == COMPUTER_PLAYER)
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+3] == HUMAN_PLAYER &&
                    mBoard[i+6] == HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i+3] == COMPUTER_PLAYER &&
                    mBoard[i+6] == COMPUTER_PLAYER)
                return 3;
        }

        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER) ||
                (mBoard[2] == HUMAN_PLAYER &&
                        mBoard[4] == HUMAN_PLAYER &&
                        mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0]== COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER) ||
                (mBoard[2] == COMPUTER_PLAYER &&
                        mBoard[4] == COMPUTER_PLAYER &&
                        mBoard[6] == COMPUTER_PLAYER))
            return 3;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;
    }



    public char[] getBoardState() {
        return mBoard;
    }

    public void setBoardState(char[] mBoard) {
        this.mBoard = mBoard;
    }



}
