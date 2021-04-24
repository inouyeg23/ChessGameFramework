package com.example.chessgameframework;

import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.Bishop;
import com.example.chessgameframework.game.GameFramework.Pieces.King;
import com.example.chessgameframework.game.GameFramework.Pieces.MoveBoard;
import com.example.chessgameframework.game.GameFramework.Pieces.Pawn;
import com.example.chessgameframework.game.GameFramework.Pieces.Knight;
import com.example.chessgameframework.game.GameFramework.Pieces.Queen;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameState;

import java.io.Serializable;

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
    public boolean isPaused;
    //is there a checkmate
    private boolean isCheckedmateWhite;
    private boolean isCheckedmateBlack;
    //booleans to work with onClick method and check if valid
    public boolean gameStarted;
    public boolean isQuitPressed;
    public boolean isDrawPressed;
    public boolean isForfeitPressed;
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
    //initialize the board full of pieces in the starting position
    //both kings are stored in the array below as well (black then white)
    Piece[] kings = new Piece[2];
    public int[] kingLocationWhite = new int[2];
    public int[] kingLocationBlack = new int[2];
    private boolean computerHasMoved;
    private int startingColor;

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
        //black side
        board[0][0] = new Rook(true);
        board[0][1] = new Knight(true);
        board[0][2] = new Bishop(true);
        board[0][3] = new Queen(true);
        board[0][4] = new King(true);
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
        board[7][4] = new King(false);
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
        //time starts at 10 minutes
        secondsBlack = 10;
        secondsWhite = 10;
        //game starts paused
        isPaused = false;
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
      }// copy constructor

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

    /**
     * Checks to see if there are two kings
     * @return
     *      true or false depending on the outcome
     */
    public boolean checkIfTwoKings(){
          int c = 0;
          for(int i = 0; i < 8; i++){
              for(int j = 0; j < 8; j++){
                  if(board[i][j] != null && board[i][j] instanceof King){
                      c++;
                  }
              }
          }
          return c == 2;
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
                "Black seconds: " + secondsBlack + "\n" +
                "White seconds: " + secondsWhite + "\n" +
                "Black checked: " + isCheckedBlack + "\n" +
                "White checked: " + isCheckedWhite + "\n" +
                "Black checkmated: " + isCheckedmateBlack + "\n" +
                "White checkmated: " + isCheckedmateWhite + "\n" +
                "Game paused: " + isPaused + "\n" +
                "Game forfeited: " + isForfeitPressed + "\n";
    }

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
        }
        System.out.println("moved piece");
        setPiece(selectedRow,selectedCol,board[row][col]);
        setPiece(row,col,null);
    }

    /**
     * all methods for each of the actions defined in
     * the actions.txt text file. Each method returns a
     * boolean and verifies whether the move is legal and
     * modifies the gamestate to reflect the taken action
     */
    public boolean isStartPressed(){
        if(gameStarted){
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if the paused button is pressed
     * @return
     */
    public boolean isPausePressed(){
        if(isPaused){
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if the paused button is pressed
     * @return
     */
    public boolean isQuitPressed(){
        //quitInitiated would turn true or false based on button onClick
        if(isQuitPressed){
            //this will be implemented using game framework; not required for game
            //state assignment
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if the paused button is pressed
     * @return
     */
    public boolean isDrawPressed(){
        //drawInitiated would turn true or false based on button onClick
        if(isDrawPressed){
            //this will be implemented using game framework; not required for game
            //state assignment
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks if the paused button is pressed
     * @return
     */
    public boolean isForfeitPressed(){
        if(isForfeitPressed){
            System.out.println("forfeit pressed in game state");
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
    public boolean getComputerHasMoved(){ return computerHasMoved; }
    public void setComputerHasMoved(boolean moved){ computerHasMoved = moved; }
    public int getStartingColor(){ return startingColor;}
    public void setStartingColor(int color){ startingColor = color;}
//GameState class
}
