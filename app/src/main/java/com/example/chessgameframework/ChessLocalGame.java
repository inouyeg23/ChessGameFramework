package com.example.chessgameframework;

import com.example.chessgameframework.game.GameFramework.LocalGame;
import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.actionMessage.GameAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessButtonAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessCastlingAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessMoveAction;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;

public class ChessLocalGame extends LocalGame {
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
        p.sendInfo(new ChessGameState(state));
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
        if(action instanceof ChessButtonAction){
            return true;
        } else if ( action instanceof ChessMoveAction) {
            ChessGameState CGS = (ChessGameState) super.state;
            ChessMoveAction CMA = (ChessMoveAction) action;
            int row = CMA.getRow();
            int col = CMA.getCol();
            int selectedCol = CMA.getSelectedCol();
            int selectedRow = CMA.getSelectedRow();
            Piece piece = CMA.getSelectedPiece();
            // get the ID of our player
            int playerID = getPlayerIdx(CMA.getPlayer());
            // if there is a friendly piece, return false
            // get the 0/1 id of the player whose move it is
            int playerTurn = CGS.getPlayerTurn();

            // place the player's piece on the selected square
            if(playerID != playerTurn)
                return false;
            CGS.movePiece(col, row, selectedCol, selectedRow, piece);
            if(action instanceof ChessCastlingAction){
                //we need to move the other piece now too
                if(selectedRow > row){
                    //we castled to the right so we need to move the rook to the left
                    CGS.movePiece(col, row + 3, col, row+1,CGS.getPiece(row,col));
                }
                else{
                    //we castled to the left
                    CGS.movePiece(col, row - 4, col, row-1,CGS.getPiece(row,col));
                }
            }
            if(!CGS.gameStarted)
                CGS.gameStarted = true;
            // make it the other player's turn
            CGS.setPlayerTurn(1 - playerTurn);
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
        if(!state.checkIfTwoKings()) {
            return "Game Over. ";
        } else if(state.isForfeitPressed()){
            return playerNames[0] + " forfeited. " + playerNames[1] + " won. ";
        } else if(state.isQuitPressed()) {
            return "";
        } else if(state.isDrawPressed()) {
            return playerNames[1] + " has accepted your draw offer. ";
        } else {
            return null;
        }
    }
}
