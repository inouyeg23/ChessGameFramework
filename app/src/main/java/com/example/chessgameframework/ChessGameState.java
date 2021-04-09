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
    public boolean isUndoPressed;

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
        board[7][3] = new King(false);
        board[7][4] = new Queen(false);
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

        //time starts at 10 minutes, or 600 seconds
        secondsBlack = 600;
        secondsWhite = 600;

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
          isUndoPressed = original.isUndoPressed;
          isQuitPressed = original.isQuitPressed;

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

    public int[] getKingLoc(int k){
          int[] kingLoc =  new int[2];
        for (int row1 = 0; row1 < 8; row1++) {
            for (int col1 = 0; col1 < 8; col1++) {
                if (getPiece(row1, col1).equals(kings[k])) {
                    kingLoc[0] = row1;
                    kingLoc[1] = col1;
                }
            }
        }
        return kingLoc;
    }

    //determines whether the kings are checked or not, and returns the boolean value of which king is checked.
    public boolean inCheck(int[] kingLocation) {
        Piece king = getPiece(kingLocation[0], kingLocation[1]);
        //iterate through to search for a piece that can "take" the king
        for (int row2 = 0; row2 < 8; row2++) {
            for (int col2 = 0; col2 < 8; col2++) {
                //piece
                Piece targetPiece = getPiece(row2, col2);
                if (targetPiece.canMove(row2, col2, kingLocation[0], kingLocation[1])) {
                    //puts the pieces in check if an opposing piece can move into it's spot
                    //sets the checks accordingly
                    if (king.isBlack() && !targetPiece.isBlack()) {
                        return true;
                    }
                    if (!king.isBlack() && targetPiece.isBlack()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void inCheck() {
        int[] kingLocation = getKingLoc(0);
        if(inCheck(kingLocation)) {
            setCheckedBlack(true);
        }
        kingLocation = getKingLoc(1);
        if(inCheck(kingLocation)) {
            setCheckedWhite(true);
        }
    }

//    public boolean inCheckMate(int[] kingLocation) {
//        Piece king = getPiece(kingLocation[0], kingLocation[1]);
//          for (int row1 = 0; row1 < 8; row1++) {
//            for (int col1 = 0; col1 < 8; col1++) {
//              if(king.canMove(kingLocation[0], kingLocation[1], row1, col1)){
//                    int[] kingLocation2;
//                    kingLocation2
//
//              }
//          }
//    }

//
//                //Piece king
//        //Piece king;
//
//        //iterate though to search for a king
//        for (int row1 = 0; row1 < 8; row1++) {
//            for (int col1 = 0; col1 < 8; col1++) {
//                //checks to see if the piece is a king
//                if (getPiece(row1, col1) instanceof King) {
//                    king = getPiece(row1,col1);
//                    //iterate through to search for a piece that can "take" the king
//                    for (int row2 = 0; row2 < 8; row2++) {
//                        for (int col2 = 0; col2 < 8; col2++) {
//                            //piece
//                            Piece targetPiece = getPiece(row2, col2);
//                            if (targetPiece.canMove(row2, col2, row1, col1)) {
//                                //puts the pieces in check if an opposing piece can move into it's spot
//                                //sets the checks accordingly
//                                if (king.isBlack() && !targetPiece.isBlack()) {
//                                    setCheckedBlack(true);
//                                }
//                                if (!king.isBlack() && targetPiece.isBlack()){
//                                    setCheckedWhite(true);
//                                }
//                                //searches for a possible way out of the the check
//                                boolean escape = true;
//                                int count1 = 0;
//                                Piece targetPiece2;
//                                //searches for spaces the king can move to
//                                for (int row3 = 0; row3 < 8; row3++) {
//                                    for (int col3 = 0; col3 < 8; col3++){
//                                        if(king.canMove(row1,col1,row3,col3)){
//                                            count1++;
//                                            //searches for pieces in the game, that can move to one of the place
//                                            for (int row4 = 0; row4 < 8; row4++) {
//                                                for (int col4 = 0; col4 < 8; col4++) {
//                                                    targetPiece2 = getPiece(row4, col4);
//                                                    //if target piece can move
//                                                    if (targetPiece2.canMove(row4, col4, row3, col3)){
//                                                        escape = true;
//                                                    }
//                                                    else if (!targetPiece2.canMove(row4,col4,row3,col3)){
//                                                        escape = false;
//                                                    }
//
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                                if (king.isBlack() && !targetPiece.isBlack() && escape == false){
//                                    isCheckedmateBlack = true;
//                                }
//                                if (!king.isBlack() && targetPiece.isBlack() && escape == false){
//                                    isCheckedmateWhite = true;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }



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
    public boolean isStartPressed(){
        if(gameStarted){
            //this will be implemented using game framework; not required for game
            //state assignment
            return true;
        } else {
            return false;
        }
    }

    public boolean isPausePressed(){
        //this will be implemented using game framework; not required for game
        //state assignment
        if(isPaused){
            //this will be implemented using game framework; not required for game
            //state assignment
            return true;
        } else {
            return false;
        }
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


    //forfeitInitiated would turn true or false based on button onClick
    public boolean isForfeitPressed(){
        if(isForfeitPressed){
            //this will be implemented using game framework; not required for game
            //state assignment
            return true;
        } else {
            return false;
        }
    }

    //playAgainInitiated would turn true or false based on button onClick
    public boolean isUndoPressed(){
        if(isUndoPressed){
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
