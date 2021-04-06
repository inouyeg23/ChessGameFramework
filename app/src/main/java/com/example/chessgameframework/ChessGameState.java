package com.example.chessgameframework;

import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.Bishop;
import com.example.chessgameframework.game.GameFramework.Pieces.King;
import com.example.chessgameframework.game.GameFramework.Pieces.Knight;
import com.example.chessgameframework.game.GameFramework.Pieces.Pawn;
import com.example.chessgameframework.game.GameFramework.Pieces.Queen;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameState;

import java.io.Serializable;
import java.util.Objects;

/**
 * @authors: Jonah Ingler, Garrett Inouye, Logan Machida, Connor Morgan
 *
 */

public class ChessGameState extends GameState implements Serializable {

    //8x8 array of pieces
    private Piece[][] board;

    //whose turn it is
    private int playerTurn;

    //variables to see if either player is "checked"
    private boolean isCheckedWhite;
    private boolean isCheckedBlack;

    //point tally for each player
    private int pointsWhite;
    private int pointsBlack;

    //time remaining for each player
    private int secondsWhite;
    private int secondsBlack;

    //is the game paused
    private boolean isPaused;

    //is there a checkmate
    private boolean isCheckedmateWhite;
    private boolean isCheckedmateBlack;

    //booleans to work with onClick method and check if valid

    public boolean gameStarted;
    public boolean drawInitiated;
    public boolean forfeitInitiated;
    public boolean playAgainInitiated;

    //different from playerTurn, it holds the current player rather than
    //the whole game switching between players to make sure it is
    //that players turn
    public int currPlayer;

    //variables that may hold a pool of valid moves, may change to methods later
    public boolean highlightedPawnMove;
    public boolean highlightedKnightMove;
    public boolean highlightedRookMove;
    public boolean highlightedBishopMove;
    public boolean highlightedKingMove;
    public boolean highlightedQueenMove;

    /**
     * Constructor for class ChessGameState
     */
    public ChessGameState(){
        //initialize an empty board
        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
        //starts at 0
        //  0 for black, 1 for white
        playerTurn = 0;

        //nobody starts checked
        isCheckedBlack = false;
        isCheckedWhite = false;

        //nobody starts with checkmate
        isCheckedmateBlack = false;
        isCheckedmateWhite = false;

        //point tally for each starts at 0
        pointsBlack = 0;
        pointsWhite = 0;

        //time starts at 10 minutes, or 600 seconds
        secondsBlack = 600;
        secondsWhite = 600;

        //game starts paused
        isPaused = true;

    }//constructor

    /**
     * Copy Constructor for class ChessGameState
     *
     *  @param original
     * 	    the ChessGameState object to be cloned
     */
      public ChessGameState(ChessGameState original){
          // copy the values from original array
          board = new Piece[8][8];
          for (int i = 0; i < 8; i++) {
              for (int j = 0; j < 8; j++) {
                  board[i][j] = original.board[i][j];
              }
          }
          // copy player information

          // update which player's turn it is
          playerTurn = original.playerTurn;

          // update whether a player is in check
          isCheckedBlack = original.isCheckedBlack;
          isCheckedWhite = original.isCheckedWhite;

          // update whether a player is in checkmate
          isCheckedmateBlack = original.isCheckedmateBlack;
          isCheckedmateWhite = original.isCheckedmateWhite;

          // update number of points for each player
          pointsBlack = original.pointsBlack;
          pointsWhite = original.pointsWhite;

          // update game clock for each player
          secondsBlack = original.secondsBlack;
          secondsWhite = original.secondsWhite;

          // update whether game is paused
          isPaused = original.isPaused;

          // new variables
          gameStarted = original.gameStarted;
          drawInitiated = original.drawInitiated;
          forfeitInitiated = original.forfeitInitiated;
          playAgainInitiated = original.playAgainInitiated;

          currPlayer = original.currPlayer;

          highlightedPawnMove = original.highlightedPawnMove;
          highlightedKnightMove = original.highlightedKnightMove;
          highlightedRookMove = original.highlightedRookMove;
          highlightedBishopMove = original.highlightedBishopMove;
          highlightedKingMove = original.highlightedKingMove;
          highlightedQueenMove = original.highlightedQueenMove;
      }// copy constructor


    public Piece getPiece(int row, int col){
        if(board == null|| row < 0 || col < 0) {
            return null;
        }
        if(row >= board.length || col >= board[row].length){
            return null;
        }
        return board[row][col];
    }

    public void setPiece(int row, int col, Piece piece){
        if(board == null|| row < 0 || col < 0) {
            return;
        }
        if(row >= board.length || col >= board[row].length) {
            return;
        }
        board[row][col] = piece;
    }

    //determines whether the kings are checked or not, and returns the boolean value of which king is checked.
    public void inCheck() {
        int selectRow;
        int selectCol;
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                if (getPiece(i, j) instanceof King) {
                    selectRow = i;
                    selectCol = j;
                    for (int row = 0; row <= 8; row++) {
                        for (int col = 0; col <= 8; col++) {
                            Piece targetPiece = getPiece(row, col);
                            if (targetPiece.canMove(row, col, selectRow, selectCol)) {
                                if (targetPiece.isBlack()) {
                                    setCheckedWhite(true);
                                } else {
                                    setCheckedBlack(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * toString method
     * prints the values for all the variables
     * defined in this class
     */

    @Override
    public String toString(){
        return "Player turn: " + playerTurn + "\n" +
                "Black points: " + pointsBlack + "\n" +
                "White points: " + pointsWhite + "\n" +
                "Black seconds: " + secondsBlack + "\n" +
                "White seconds: " + secondsWhite + "\n" +
                "Black checked: " + isCheckedBlack + "\n" +
                "White checked: " + isCheckedWhite + "\n" +
                "Black checkmated: " + isCheckedmateBlack + "\n" +
                "White checkmated: " + isCheckedmateWhite + "\n" +
                "Game paused: " + isPaused + "\n";
    }

    public void movePiece(int row, int col, int selectedRow, int selectedCol, Piece piece){
        if(piece.canMove(row,col,selectedRow,selectedCol)){
            setPiece(selectedRow,selectedCol,piece);
            setPiece(row,col,null);
        }
        if(currPlayer == 1){
            currPlayer = 0;
        }
        else{
            currPlayer = 1;
        }
    }

    /**
     * all methods for each of the actions defined in
     * the actions.txt text file. Each method returns a
     * boolean and verifies whether the move is legal and
     * modifies the gamestate to reflect the taken action
     */
    public boolean gameStart(){
        gameStarted = true;
        return true;
    }

    public boolean gamePaused(){
        //this will be implemented using game framework; not required for game
        //state assignment
        return false;
    }

    public boolean isLegal(int row, int col){
        //checking if the current player is in check
        //0 for black, 1 for white
        if((currPlayer == 0 && !isCheckedBlack) || (currPlayer == 1 && !isCheckedWhite)){
            //checking if space is empty
            if(board[row][col] == null){
                //space is empty
                return true;
            } else {
                //space occupied by another place
                return false;
            }
        } else {
            //a player was in check so couldn't move
            return false;
        }

    }


    public boolean drawOffered(){
        //drawInitiated would turn true or false based on button onClick
        if(drawInitiated){
            //this will be implemented using game framework; not required for game
            //state assignment
            return true;
        } else {
            return false;
        }

    }

    //forfeitInitiated would turn true or false based on button onClick
    public boolean forfeitGame(){
        if(forfeitInitiated){
            //this will be implemented using game framework; not required for game
            //state assignment
            return true;
        } else {
            return false;
        }
    }

    //playAgainInitiated would turn true or false based on button onClick
    public boolean playAgain(){
        if(playAgainInitiated){
            //this will be implemented using game framework; not required for game
            //state assignment
            return true;
        } else {
            return false;
        }
    }


    //getter and setter for player turn
    public int getPlayerTurn() {
        return playerTurn;
    }
    public void setPlayerTurn(int player){
        playerTurn = player;
    }

    //getter and setter for points
    public int getPointsBlack() {
        return pointsBlack;
    }
    public void setPointsBlack(int pointsBlack) {
        this.pointsBlack = pointsBlack;
    }
    public int getPointsWhite() {
        return pointsWhite;
    }
    public void setPointsWhite(int pointsWhite) {
        this.pointsWhite = pointsWhite;
    }

    //getter and setter for time
    public int getSecondsBlack() {
        return secondsBlack;
    }
    public void setSecondsBlack(int secondsBlack) {
        this.secondsBlack = secondsBlack;
    }
    public int getSecondsWhite() {
        return secondsWhite;
    }
    public void setSecondsWhite(int secondsWhite) {
        this.secondsWhite = secondsWhite;
    }

    //set boolean checked condition, and get checked condition
    public boolean isCheckedBlack() {
        return isCheckedBlack;
    }
    public void setCheckedBlack(boolean checkedBlack) {
        isCheckedBlack = checkedBlack;
    }
    public boolean isCheckedWhite() {
        return isCheckedWhite;
    }
    public void setCheckedWhite(boolean checkedWhite) {
        isCheckedBlack = checkedWhite;
    }

    //set boolean checkmated condition, and get checkmated condition
    public boolean isCheckedmateBlack() {
        return isCheckedmateBlack;
    }
    public void setCheckedmateBlack(boolean checkedmateBlack) {
        isCheckedmateBlack = checkedmateBlack;
    }
    public boolean isCheckedmateWhite() {
        return isCheckedmateWhite;
    }
    public void setCheckedmateWhite(boolean checkedmateWhite) {
        isCheckedmateWhite = checkedmateWhite;
    }

    //set paused boolean and check paused boolean
    public boolean isPaused() {
        return isPaused;
    }
    public void setPaused(boolean paused) {
        isPaused = paused;
    }
//GameState class
}
