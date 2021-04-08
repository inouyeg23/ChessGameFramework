package com.example.chessgameframework.game.GameFramework.chessPlayers;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessMoveAction;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameInfo;
import com.example.chessgameframework.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgameframework.game.GameFramework.players.GameComputerPlayer;
import com.example.chessgameframework.game.GameFramework.utilities.Logger;

/**
 * This is a really dumb computer player that always just makes a random move
 * it's so stupid that it sometimes tries to make moves on non-blank spots.
 * 
 * @author Steven R. Vegdahl
 * @version July 2013
 */
public class ChessComputerPlayerEasy extends GameComputerPlayer
{
    /*
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
     * @param info
     * 		the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;
        Logger.log("ChessComputer", "My turn!");

        if (!(info instanceof ChessGameState)) return;
        ChessGameState gameState = (ChessGameState)info;

        // move a piece at random
        Piece randomPiece = null;
        int randomX = -1, randomY = -1;

        while(randomPiece == null) {

            // select the random piece
            while (randomPiece == null) {
                randomX = (int) (Math.random() * 8);
                randomY = (int) (Math.random() * 8);
                if (gameState.currPlayer == 0 && randomPiece.isBlack() == false) {
                    randomPiece = gameState.getPiece(randomX, randomY);
                } else if (gameState.currPlayer == 1 && randomPiece.isBlack() == true) {
                    randomPiece = gameState.getPiece(randomX, randomY);
                }
            }

            // find and make a move for piece, otherwise find another piece to move
            for (int moveX = 0; moveX < 8; moveX++) {
                for (int moveY = 0; moveY < 8; moveY++) {

                    if (randomPiece.canMove(randomX, randomY, moveX, moveY)) {
                        sleep(1);
                        // move to that location
                        Logger.log("ChessComputerPlayerEasy", "Sending move");
                        game.sendAction(new ChessMoveAction(this, randomX, randomY, moveX, moveY, randomPiece));
                    }
                    else{
                        randomPiece = null;
                    }
                }
            }
        }

    }
}
