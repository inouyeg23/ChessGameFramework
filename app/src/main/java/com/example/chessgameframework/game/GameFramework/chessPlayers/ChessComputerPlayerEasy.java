package com.example.chessgameframework.game.GameFramework.chessPlayers;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.MoveBoard;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessMoveAction;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameInfo;
import com.example.chessgameframework.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgameframework.game.GameFramework.players.GameComputerPlayer;
import com.example.chessgameframework.game.GameFramework.utilities.Logger;

/**
 * ChessComputerPlayerEasy is an easy computer player that selects a random piece and the moves it
 * to a random square.
 *
 * @author Logan Machida
 */
public class ChessComputerPlayerEasy extends GameComputerPlayer {
    /**
     * Constructor for the ChessComputerPlayerEasy class
     */
    public ChessComputerPlayerEasy(String name) {
        // invoke superclass constructor
        super(name); // invoke superclass constructor
    }

    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * @param info the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;
        Logger.log("ChessComputer", "My turn!");

        //if (!(info instanceof ChessGameState)) return;
        boolean shouldBeBlack = false;
        if(playerNum == 1)
            shouldBeBlack = true;
        ChessGameState gameState = new ChessGameState((ChessGameState) info);
        // move a piece at random
        Piece randomPiece = null;

        while (randomPiece == null) {
            int randomRow = -1;
            int randomCol = -1;
            // select the random piece
            while (randomPiece == null ) {
                randomRow = (int) (Math.random() * 8);
                randomCol = (int) (Math.random() * 8);
                randomPiece = gameState.getPiece(randomRow, randomCol);
                if(randomPiece != null)
                    if(randomPiece.isBlack() != shouldBeBlack)
                        randomPiece = null;
            }

            MoveBoard moveBoard = new MoveBoard();
            moveBoard.findMoves(gameState, randomRow, randomCol);
            for (int moveRow = 0; moveRow < 8; moveRow++) {
                for (int moveCol = 0; moveCol < 8; moveCol++) {
                    if (moveBoard.getCanMove(moveRow, moveCol)) {
                        sleep(1);
                        // move to that location
                        Logger.log("ChessComputerPlayerEasy", "Sending move");
                        game.sendAction(new ChessMoveAction(this, randomRow, randomCol, moveRow, moveCol, randomPiece));
                        return;
                    }
                }
            }
            randomPiece = null;
        }
    }
}