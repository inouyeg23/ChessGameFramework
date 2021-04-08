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
 * @versio2 July 2013 
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
        /*
        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;
        Logger.log("ChessComputer", "My turn!");

        // make a random move
        ChessGameState CGS = new ChessGameState();
        int randomX = (int) (Math.random()*8);
        int randomY = (int) (Math.random()*8);
        Piece randomPiece = CGS.getPiece(randomX, randomY);

        for(int x=0; x<8; x++) {
            for(int y = 0; y<8; y++) {
                if (randomPiece.isBlack()) {
                    if(randomPiece.canMove(randomX, randomY, x, y)){

                        // delay for a second to make opponent think we're thinking
                        sleep(1);

                        // Submit our move to the game object. We haven't even checked it it's
                        // our turn, or that that position is unoccupied. If it was not our turn,
                        // we'll get a message back that we'll ignore. If it was an illegal move,
                        // we'll end up here again (and possibly again, and again). At some point,
                        // we'll end up randomly pick a move that is legal.
                        Logger.log("ChessComputer", "Sending move");
                        game.sendAction(new ChessMoveAction(this,randomX, randomY, x, y));


                    }
                }
            }
        }

         */





    }



}
