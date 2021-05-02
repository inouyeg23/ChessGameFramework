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
 * This is a really dumb computer player that always just makes a random move
 * it's so stupid that it sometimes tries to make moves on non-blank spots.
 * @author Logan Machida
 *
 */
public class ChessComputerPlayerEasy extends GameComputerPlayer {
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
     * @param info the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;
        Logger.log("ChessComputer", "My turn!");
        //if (!(info instanceof ChessGameState)) return;
        boolean shouldBeBlack = false;
        if (playerNum == 1)
            shouldBeBlack = true;
        ChessGameState gameState = new ChessGameState((ChessGameState) info);
        if(gameState.getPlayerTurn() != playerNum)
            return;
        // move a piece at random
        // if the AI cannot take a piece, move a piece at random
        Piece randomPiece = null;
        while (randomPiece == null) {
            int randomRow = -1;
            int randomCol = -1;
            // select the random piece
            while (randomPiece == null) {
                randomRow = (int) (Math.random() * 8);
                randomCol = (int) (Math.random() * 8);
                randomPiece = gameState.getPiece(randomRow, randomCol);
                if (randomPiece != null)
                    if (randomPiece.isBlack() != shouldBeBlack)
                        randomPiece = null;
            }
            // find number of moves possible
            MoveBoard moveBoard = new MoveBoard();
            moveBoard.findMoves(gameState, randomRow, randomCol);
            int potentialIndex = 0;
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (moveBoard.getCanMove(row, col)) {
                        potentialIndex++;
                    }
                }
            }
            // generate arrays and store all possible coordinates
            int[] potentialRow = new int[potentialIndex];
            int[] potentialCol = new int[potentialIndex];
            int index = 0;
            for (int moveRow = 0; moveRow < 8; moveRow++) {
                for (int moveCol = 0; moveCol < 8; moveCol++) {
                    if (moveBoard.getCanMove(moveRow, moveCol)) {
                        potentialRow[index] = moveRow;
                        potentialCol[index] = moveCol;
                        index++;
                    }
                }
            }
            if(potentialIndex != 0) {
                int random = (int) (Math.random() * potentialIndex);
                sleep(1);
                // move to that location
                Logger.log("ChessComputerPlayerHard", "Sending move");
                game.sendAction(new ChessMoveAction(this, randomRow, randomCol,
                        potentialRow[random], potentialCol[random], randomPiece));
                return;
            }
            else{
                randomPiece = null;
            }
        }
    }
}
