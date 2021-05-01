package com.example.chessgameframework;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.Bishop;
import com.example.chessgameframework.game.GameFramework.Pieces.King;
import com.example.chessgameframework.game.GameFramework.Pieces.MoveBoard;
import com.example.chessgameframework.game.GameFramework.Pieces.Pawn;
import com.example.chessgameframework.game.GameFramework.Pieces.Knight;
import com.example.chessgameframework.game.GameFramework.Pieces.Queen;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameState;
import com.example.chessgameframework.game.GameFramework.utilities.Logger;

import java.io.Serializable;
import java.util.Locale;

/**
 * @authors: Jonah Ingler, Garrett Inouye, Logan Machida, Connor Morgan
 *
 */
public class ChessGameState extends GameState implements Serializable {
    //8x8 array of pieces
    private Piece[][] board;
    //whose turn it is
    private int playerTurn;

    //different from playerTurn, it holds the current player rather than
    //the whole game switching between players to make sure it is
    //that players turn
    private int currPlayer;

    //variables to see if either player is "checked"
    private boolean isCheckedWhite;
    private boolean isCheckedBlack;
    //point tally for each player
    private int pointsWhite;
    private int pointsBlack;

    //is the game paused
    public boolean isPaused;
    //is there a checkmate
    private boolean isCheckedmateWhite;
    private boolean isCheckedmateBlack;
    //booleans to work with onClick method and check if valid
    public boolean gameStarted;
    public boolean isQuitPressed;
    public boolean isDrawPressed;
    public boolean isForfeitPressed;

    //variables that may hold a pool of valid moves, may change to methods later
    public boolean highlightedPawnMove;
    public boolean highlightedKnightMove;
    public boolean highlightedRookMove;
    public boolean highlightedBishopMove;
    public boolean highlightedKingMove;
    public boolean highlightedQueenMove;

    public boolean castlingRightBlack;
    public boolean castlingRightWhite;
    public boolean castlingLeftBlack;
    public boolean castlingLeftWhite;

    public boolean enPWhiteL;
    public boolean enPWhiteR;
    public boolean enPBlackL;
    public boolean enPBlackR;

    //initialize the board full of pieces in the starting position
    //both kings are stored in the array below as well (black then white)
    Piece[] kings = new Piece[2];
    public int[] kingLocationWhite = new int[2];
    public int[] kingLocationBlack = new int[2];
    private boolean computerHasMoved;
    private int startingColor;

    //variables to set up the timer to work with the human player
     private String playerTimerText = "10:00";
    private String opposingTimerText = "10:00";
    private boolean playerTimerRunning;
    private boolean opposingTimerRunning;

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
        //fills the board
        King kingB = new King(true);
        kingB.setHasMoved(false);
        King kingW = new King(false);
        kingW.setHasMoved(false);

        Pawn pawnW1 = new Pawn(false);

        //black side
        board[0][0] = new Rook(true);
        board[0][1] = new Knight(true);
        board[0][2] = new Bishop(true);
        board[0][3] = new Queen(true);
        board[0][4] = kingB;
        board[0][5] = new Bishop(true);
        board[0][6] = new Knight(true);
        board[0][7] = new Rook(true);
        //pawns
        board[1][0] = new Pawn(true);
        board[1][1] = new Pawn(true);
        board[1][2] = new Pawn(true);
        board[1][3] = new Pawn(true);
        board[1][4] = new Pawn(true);
        board[1][5] = new Pawn(true);
        board[1][6] = new Pawn(true);
        board[1][7] = new Pawn(true);
        //white side
        board[7][0] = new Rook(false);
        board[7][1] = new Knight(false);
        board[7][2] = new Bishop(false);
        board[7][4] = kingW;
        board[7][3] = new Queen(false);
        board[7][5] = new Bishop(false);
        board[7][6] = new Knight(false);
        board[7][7] = new Rook(false);
        //pawns
        board[6][0] = new Pawn(false);
        board[6][1] = new Pawn(false);
        board[6][2] = new Pawn(false);
        board[6][3] = new Pawn(false);
        board[6][4] = new Pawn(false);
        board[6][5] = new Pawn(false);
        board[6][6] = new Pawn(false);
        board[6][7] = new Pawn(false);
        Piece kingBlack = getPiece(0,4);
        if (kingBlack instanceof King) {
            kings[0] = kingBlack;
        }
        Piece kingWhite = getPiece(7,3);
        if (kingBlack instanceof King) {
            kings[1] = kingWhite;
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

        isPaused = false;

        castlingLeftBlack = false;
        castlingRightBlack = false;
        castlingRightWhite = false;
        castlingLeftWhite = false;

        enPWhiteR = false;
        enPWhiteL = false;
        enPBlackL = false;
        enPBlackR = false;
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
          // update whether game is paused
          isPaused = original.isPaused;
          // new variables
          gameStarted = original.gameStarted;
          isDrawPressed = original.isDrawPressed;
          isForfeitPressed = original.isForfeitPressed;
          isQuitPressed = original.isQuitPressed;
          currPlayer = original.currPlayer;
          highlightedPawnMove = original.highlightedPawnMove;
          highlightedKnightMove = original.highlightedKnightMove;
          highlightedRookMove = original.highlightedRookMove;
          highlightedBishopMove = original.highlightedBishopMove;
          highlightedKingMove = original.highlightedKingMove;
          highlightedQueenMove = original.highlightedQueenMove;

          castlingLeftBlack = original.castlingLeftBlack;
          castlingRightBlack = original.castlingRightBlack;
          castlingRightWhite = original.castlingRightWhite;
          castlingLeftWhite = original.castlingLeftWhite;


          enPBlackR = original.enPBlackR;
          enPBlackL = original.enPWhiteL;
          enPWhiteL = original.enPWhiteR;
          enPWhiteR = original.enPBlackL;

      }// copy constructor

    /**
     * Move piece method
     * @param row
     *      current row
     * @param col
     *      current col
     * @param selectedRow
     *      dest row
     * @param selectedCol
     *      dest col
     * @param piece
     *      piece to move
     */

    public void movePiece(int row, int col, int selectedRow, int selectedCol, Piece piece){
        if(board[row][col] instanceof King){
            System.out.println("king location at: " + selectedRow + " " + selectedCol);
            setKingLocation(selectedRow, selectedCol);
            ((King) board[row][col]).setHasMoved(true);
        }

        // promote a pawn
        if(board[row][col] instanceof Pawn && selectedRow == 0) {
            System.out.println("pawn promoted to Queen");
            setPiece(selectedRow, selectedCol, new Queen(false));
            setPiece(row, col, null);
        }
        else if(board[row][col] instanceof Pawn && selectedRow == 7) {
            System.out.println("pawn promoted to Queen");
            setPiece(selectedRow, selectedCol, new Queen(true));
            setPiece(row, col, null);
        }
        // make a regular move
        else{
            System.out.println("moved piece");
            setPiece(selectedRow, selectedCol, board[row][col]);
            setPiece(row, col, null);
        }
    }

    public void pawnMovesTwo(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] instanceof Pawn){
                    if(i != 1 && i != 6) {
                        if((i == 3 || i == 4) && !((Pawn) getPiece(i,j)).getHasMoved()) {
                            ((Pawn) getPiece(i,j)).setJustMoved2(true);
                            //Log.e("justMoved2", "set to true: i = " + i + " j = " + j);
                        }
                        ((Pawn) getPiece(i,j)).setHasMoved(true);
                        //Log.e("hasMoved", "set to true: i = " + i + " j = " + j);
                    }
                }
            }
        }
    }

    /**
     * getPiece returns the specific piece at the place in the board
     * @param row
     * @param col
     * @return
     */
    public Piece getPiece(int row, int col){
        if(board[row][col] == null|| row < 0 || col < 0) {
            return null;
        }
        if(row >= board.length || col >= board[row].length){
            return null;
        }
        return board[row][col];
    }

    /**
     * setPiece sets the piece given as a parameter on a place in the board
     * @param row
     * @param col
     * @param piece
     */
    public void setPiece(int row, int col, Piece piece){
        if(row < 0 || col < 0) {
            return;
        }
        if(row >= 8 || col >= 8) {
            return;
        }
        board[row][col] = piece;
    }

    /**
     * setKingLocation keeps track of where the king is for each color
     * @param row
     * @param col
     */
    public void setKingLocation(int row, int col){
          kingLocationWhite[0] = row;
          kingLocationWhite[1] = col;
    }

//    public void kingIsMoving(Piece piece) {
//        if(piece instanceof King){
//            ((King) piece).setHasMoved(true);
//            Log.e("KingMove", "KING HAS MOVED");
//        }
//    }

    public void kingSearch(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j] instanceof King){
                    if((i != 0 || i != 7) && j !=4){
                        ((King) getPiece(i,j)).setHasMoved(true);
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
                "Current player: " + currPlayer + "\n" +
                "Black points: " + pointsBlack + "\n" +
                "White points: " + pointsWhite + "\n" +
                "Black checked: " + isCheckedBlack + "\n" +
                "White checked: " + isCheckedWhite + "\n" +
                "Black checkmated: " + isCheckedmateBlack + "\n" +
                "White checkmated: " + isCheckedmateWhite + "\n" +
                "Game paused: " + isPaused + "\n" +
                "Game forfeited: " + isForfeitPressed + "\n" +
                "Player Timer: " + playerTimerText + "\n" +
                "Opposing Timer: " + opposingTimerText + "\n";
    }

    /**
     * getter and setter methods for communicating with other classes
     *
     */
    //buttons
    public void setGameStarted(boolean gameStarted) { this.gameStarted = gameStarted; }
    public boolean getGameStarted() { return gameStarted; }
    public void setQuitPressed(boolean quitPressed) { isQuitPressed = quitPressed; }
    public boolean isQuitPressed() { return isQuitPressed; }
    public void setDrawPressed(boolean drawPressed) { isDrawPressed = drawPressed; }
    public boolean isDrawPressed() { return isDrawPressed; }
    public void setForfeitPressed(boolean forfeitPressed) { isForfeitPressed = forfeitPressed; }
    public boolean isForfeitPressed() { return isForfeitPressed; }
    public boolean getPaused() { return isPaused; }
    public void setPaused(boolean paused) { isPaused = paused; }

    //game conditions
    public int getPlayerTurn() {return playerTurn;}
    public void setPlayerTurn(int player){playerTurn = player;}
    public int getPointsBlack() {return pointsBlack;}
    public void setPointsBlack(int pointsBlack) {this.pointsBlack = pointsBlack;}
    public int getPointsWhite() {return pointsWhite;}
    public void setPointsWhite(int pointsWhite) {this.pointsWhite = pointsWhite;}
    public boolean isCheckedBlack() {return isCheckedBlack;}
    public void setCheckedBlack(boolean checkedBlack) {isCheckedBlack = checkedBlack;}
    public boolean isCheckedWhite() {return isCheckedWhite;}
    public void setCheckedWhite(boolean checkedWhite) {isCheckedBlack = checkedWhite;}
    public boolean isCheckedmateBlack() {return isCheckedmateBlack;}
    public void setCheckedmateBlack(boolean checkedmateBlack) { isCheckedmateBlack = checkedmateBlack; }
    public boolean isCheckedmateWhite() {return isCheckedmateWhite; }
    public void setCheckedmateWhite(boolean checkedmateWhite) { isCheckedmateWhite = checkedmateWhite; }

    //timer
    public String getPlayerTimerText(){ return playerTimerText; }
    public void setPlayerTimerText(String text){ playerTimerText = text;}
    public String getOpposingTimerText(){ return opposingTimerText; }
    public void setOpposingTimerText(String text){ opposingTimerText = text;}

    public boolean getPlayerTimerRunning(){ return playerTimerRunning; }
    public void setPlayerTimerRunning(boolean run){ playerTimerRunning = run; }
    public boolean getOpposingTimerRunning(){ return opposingTimerRunning; }
    public void setOpposingTimerRunning(boolean run){ opposingTimerRunning = run; }

    public int getStartingColor(){ return startingColor;}
    public void setStartingColor(int color){ startingColor = color;}
//GameState class
}
