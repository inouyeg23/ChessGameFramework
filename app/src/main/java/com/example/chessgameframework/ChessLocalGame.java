package com.example.chessgameframework;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import com.example.chessgameframework.game.GameFramework.LocalGame;
import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.King;
import com.example.chessgameframework.game.GameFramework.Pieces.MoveBoard;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;
import com.example.chessgameframework.game.GameFramework.actionMessage.GameAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessButtonAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessCastlingAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessMoveAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessPauseAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessResumeAction;
import com.example.chessgameframework.game.GameFramework.players.GameHumanPlayer;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;

import java.util.Locale;

/**
 * ChessLocalGame handles all the possible game actions that can occur.  This includes: Button Action,
 * Move Action, Pause Action, Resume Action, Castling Action. Another game action that it deals
 * with is checking if the game is over. It also holds the methods for the game timer.
 *
 * @authors: Logan Machida, Connor Morgan, Garrett Inouye
 * @date: 4/30/21
 */

public class ChessLocalGame extends LocalGame {

    // variables needed for time
    private CountDownTimer playerCountDownTimer;
    private CountDownTimer opposingCountDownTimer;
    private long playerTimeLeftInMillis = 600000;
    private long opposingTimeLeftInMillis = 600000;

    private boolean playerTimerDone = false;
    private boolean opposingTimerDone = false;

    private int humanPlayerNum = 0;

    /**
     * Constructor for the ChessLocalGame.
     */
    public ChessLocalGame() {

        // perform superclass initialization
        super();

        // create a new, unfilled-in TTTState object
        super.state = new ChessGameState();
    }

    /**
     * Constructor for the ChessLocalGame with loaded chessState
     * @param chessState
     */
    public ChessLocalGame(ChessGameState chessState){
        super();
        super.state = new ChessGameState(chessState);

    }
    /**
     * Notify the given player that its state has changed. This should involve sending
     * a GameInfo object to the player. If the game is not a perfect-information game
     * this method should remove any information from the game that the player is not
     * allowed to know.
     *
     * @param p
     * 			the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(state);
    }

    /**
     * Tell whether the given player is allowed to make a move at the
     * present point in the game.
     *
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return
     * 		true iff the player is allowed to move
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((ChessGameState)state).getPlayerTurn();
    }

    /**
     * Makes a move on behalf of a player.
     *
     * @param action
     * 			The move that the player has sent to the game
     * @return
     * 			Tells whether the move was a legal one.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        if(action instanceof ChessButtonAction) { //for button actions like quit/forfeit/draw
            return true;
        } else if(action instanceof ChessPauseAction) { //for button actions
            playerPauseTimer();
            state.setPaused(true);
        } else if(action instanceof ChessResumeAction){
            playerStartTimer();
            state.setPaused(false);
        } else if ( action instanceof ChessMoveAction) {
            ChessGameState CGS = (ChessGameState) super.state;
            ChessMoveAction CMA = (ChessMoveAction) action;
            int row = CMA.getRow();
            int col = CMA.getCol();
            int selectedCol = CMA.getSelectedCol();
            int selectedRow = CMA.getSelectedRow();
            Piece piece = CGS.getPiece(row,col);

            // get the ID of our player
            int playerID = getPlayerIdx(CMA.getPlayer());
            // if there is a friendly piece, return false
            // get the 0/1 id of the player whose move it is
            int playerTurn = CGS.getPlayerTurn();

            // place the player's piece on the selected square
            if(playerID != playerTurn)
                return false;
            if(piece instanceof Rook) {
                ((Rook) piece).setHasMoved(true);
                //System.out.println(((Rook) piece).getHasMoved() + "_______________________________________________________________________");
            }
            else if(piece instanceof King)
                ((King) piece).setHasMoved(true);
            CGS.movePiece(col, row, selectedCol, selectedRow, piece);
            if(CGS.castlingRightWhite || CGS.castlingRightBlack || CGS.castlingLeftWhite || CGS.castlingLeftBlack){

                //System.out.println("---------------------------------------------------------\nwe found a castling action\n-----------------------------------");
                //we need to move the other piece now too
                if(CGS.castlingRightWhite || CGS.castlingRightBlack){
                    //we castled to the right so we need to move the rook to the left
                    System.out.println("row: "+ row + ", col: " + col);

                    CGS.movePiece(col, row + 3, col, row + 1,CGS.getPiece(row,col));
                    //System.out.println(CGS.getPiece(col+3,row));
                    CGS.castlingRightBlack = false;
                    CGS.castlingRightWhite = false;
                }
                else{
                    //we castled to the left
                    System.out.println("castling left side");
                    CGS.movePiece(col, row - 4, col, row - 1,CGS.getPiece(row,col));
                    CGS.castlingLeftBlack = false;
                    CGS.castlingLeftWhite = false;
                }
            }

            //checking for enpassant and which color move it is for
            if(CGS.enPWhiteR){
                CGS.setPiece(selectedCol + 1, selectedRow, null);
                CGS.enPWhiteR = false;
                //Log.e("EP Move", "En Passant white right move. Selected row = " + (selectedRow) + ". Selected col = " + selectedCol);
            }
            else if (CGS.enPWhiteL){
                CGS.setPiece(selectedCol + 1, selectedRow, null);
                CGS.enPWhiteL = false;
                //Log.e("EP Move", "En Passant white left move. Selected row = " + (selectedRow) + ". Selected col = " + selectedCol);
            }
            else if(CGS.enPBlackL){
                CGS.setPiece(selectedCol - 1, selectedRow, null);
                CGS.enPBlackL = false;
                //Log.e("EP Move", "En Passant black left move. Selected row = " + (selectedRow) + ". Selected col = " + selectedCol);
            }
            else if (CGS.enPBlackR){
                CGS.setPiece(selectedCol - 1, selectedRow, null);
                CGS.enPBlackR = false;
                //Log.e("EP Move", "En Passant black right move. Selected row = " + (selectedRow) + ". Selected col = " + selectedCol);
            }

            if(!CGS.getGameStarted())
                CGS.setGameStarted(true);

            if(playerTurn == 0 || playerTurn == 1){
                int numMoves = 0;
                for(int i = 0; i < 8;i++){
                    for(int j = 0; j < 8; j++){
                        if(CGS.getPiece(i,j) != null && CGS.getPiece(i,j).isBlack() == true) {
                            MoveBoard mb = new MoveBoard();
                            mb.findMoves(CGS,i,j);
                            numMoves += mb.getNumMoves();
                        }
                    }
                }

                if(numMoves == 0)
                    state.setCheckedmateBlack(true);


            }
            else{

                int numMoves = 0;
                for(int i = 0; i < 8;i++){
                    for(int j = 0; j < 8; j++){
                        if(CGS.getPiece(i,j) != null && CGS.getPiece(i,j).isBlack() == false) {
                            MoveBoard mb = new MoveBoard();
                            mb.findMoves(CGS,i,j);
                            numMoves += mb.getNumMoves();
                        }
                    }
                }
                if(numMoves == 0)
                    state.setCheckedmateWhite(true);
            }
            // make it the other player's turn
            CGS.setPlayerTurn(1 - playerTurn);

            //determining the player at which to return the gamestate to later on
            if (players[0] instanceof GameHumanPlayer){
                humanPlayerNum = 0;
            } else {
                humanPlayerNum = 1;
            }

            //starting the timer
            if(CGS.getPlayerTurn() == 0){ //if it's the human player's turn
                playerStartTimer();
                if(state.getOpposingTimerRunning()) {
                    opposingPauseTimer();
                }
            } else {
                opposingStartTimer();
                if(state.getPlayerTimerRunning()){
                    playerPauseTimer();
                }
            }
            //update the gamestate to refer to the human player to make the change to the timer
            playerUpdateCountDownText();
            opposingUpdateCountDownText();


            // return true, indicating the it was a legal move
            System.out.println("made it the other player's turn");
            return true;
        }
        return false;
    } //makeMove

    /**
     * Check if the game is over. It is over, return a string that tells
     * who the winner(s), if any, are. If the game is not over, return null;
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        if(state.isCheckedmateBlack()) {
            return "White wins. ";
        } else if(state.isCheckedWhite()){
            return "Black wins. ";
        } else if(state.isForfeitPressed()){
            return playerNames[0] + " forfeited. " + playerNames[1] + " won. ";
        } else if(state.isQuitPressed()) {
            return "";
        } else if(state.isDrawPressed()) {
            return playerNames[1] + " has accepted your draw offer. ";
        } else if (playerTimerDone){
            return playerNames[0] + " has run out of time. " + playerNames[1] + " won. ";
        } else if (opposingTimerDone) {
            return playerNames[1] + " has run out of time. " + playerNames[0] + " won. ";
        } else {
            return null;
        }
    }

    /**
     * The following methods are taken from an external citation
     * Date:    4 April 2021
     * Problem: Did not know how to make a timer
     * Resource: https://www.youtube.com/watch?v=MDuGwI6P-X8
     * Solution: followed along the video and made minor adjustments to fit our
     * game
     */
    //timer specifically for the player
    public void playerStartTimer() {
        playerCountDownTimer = new CountDownTimer(playerTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished){
                playerTimeLeftInMillis = millisUntilFinished;
                playerUpdateCountDownText();
                //updating the textview every tick
                sendUpdatedStateTo(players[humanPlayerNum]);
            }

            @Override
            public void onFinish(){
                state.setPlayerTimerRunning(false);
                playerTimerDone = true;
            }
        }.start();
        state.setPlayerTimerRunning(true);
    }
    public void playerPauseTimer(){
        playerCountDownTimer.cancel();
        state.setPlayerTimerRunning(false);
    }

    public void playerUpdateCountDownText() {
        int minutes = (int) playerTimeLeftInMillis / 1000 / 60;
        int seconds = (int) playerTimeLeftInMillis / 1000 % 60;
        String playerTimeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        state.setPlayerTimerText(playerTimeLeft);
    }

    //timer specifically for the computer or opposing player
    public void opposingStartTimer() {
        opposingCountDownTimer = new CountDownTimer(opposingTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished){
                opposingTimeLeftInMillis = millisUntilFinished;
                opposingUpdateCountDownText();
                //updating the textview every tick
                sendUpdatedStateTo(players[humanPlayerNum]);
            }
            @Override
            public void onFinish(){
                state.setOpposingTimerRunning(false);
                opposingTimerDone = true;
            }
        }.start();
        state.setOpposingTimerRunning(true);
    }

    public void opposingPauseTimer(){
        opposingCountDownTimer.cancel();
        state.setOpposingTimerRunning(false);
    }

    public void opposingUpdateCountDownText() {
        int minutes = (int) opposingTimeLeftInMillis / 1000 / 60;
        int seconds = (int) opposingTimeLeftInMillis / 1000 % 60;
        String opposingTimeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        state.setOpposingTimerText(opposingTimeLeft);
    }
}
