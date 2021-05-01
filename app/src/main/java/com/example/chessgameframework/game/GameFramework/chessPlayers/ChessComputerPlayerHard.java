package com.example.chessgameframework.game.GameFramework.chessPlayers;
import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.Bishop;
import com.example.chessgameframework.game.GameFramework.Pieces.Knight;
import com.example.chessgameframework.game.GameFramework.Pieces.MoveBoard;
import com.example.chessgameframework.game.GameFramework.Pieces.Pawn;
import com.example.chessgameframework.game.GameFramework.Pieces.Queen;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;
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
public class ChessComputerPlayerHard extends GameComputerPlayer {
    /*
     * Constructor for the ChessComputerPlayerEasy class
     */
    public ChessComputerPlayerHard(String name) {
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

        // take the best piece possible

        int bestMove = -1; // value of the piece we take
        int bestRow = -1;  // location of the piece we take
        int bestCol = -1;
        int startRow = -1; // location of the piece doing the taking
        int startCol = -1;
        Piece piece = null;

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){

                piece = gameState.getPiece(row, col);
                if(piece != null){
                    if(piece.isBlack() == shouldBeBlack){

                        MoveBoard moveBoard = new MoveBoard();
                        moveBoard.findMoves(gameState, row, col);

                        for (int moveRow = 0; moveRow < 8; moveRow++) {
                            for (int moveCol = 0; moveCol < 8; moveCol++) {

                                if (moveBoard.getCanMove(moveRow, moveCol)) {

                                    Piece opponentPiece = gameState.getPiece(moveRow,moveCol);

                                    if(opponentPiece != null){
                                        if(opponentPiece.isBlack() != shouldBeBlack) {
                                            if (opponentPiece instanceof Pawn && bestMove < 1) {
                                                bestMove = 1;
                                                bestRow = moveRow;
                                                bestCol = moveCol;
                                                startRow = row;
                                                startCol = col;
                                            }
                                            if (opponentPiece instanceof Bishop && bestMove < 3) {
                                                bestMove = 3;
                                                bestRow = moveRow;
                                                bestCol = moveCol;
                                                startRow = row;
                                                startCol = col;
                                            }
                                            if (opponentPiece instanceof Knight && bestMove < 3) {
                                                bestMove = 3;
                                                bestRow = moveRow;
                                                bestCol = moveCol;
                                                startRow = row;
                                                startCol = col;
                                            }
                                            if (opponentPiece instanceof Rook && bestMove < 5) {
                                                bestMove = 5;
                                                bestRow = moveRow;
                                                bestCol = moveCol;
                                                startRow = row;
                                                startCol = col;
                                            }
                                            if (opponentPiece instanceof Queen && bestMove < 9) {
                                                bestMove = 9;
                                                bestRow = moveRow;
                                                bestCol = moveCol;
                                                startRow = row;
                                                startCol = col;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if(bestMove != -1){
            sleep(1);
            // move to that location
            Logger.log("ChessComputerPlayerHard", "Sending move");
            game.sendAction(new ChessMoveAction(this, startRow, startCol, bestRow, bestCol, piece));
            return;
        }

        else {

            // move a piece in danger if possible
            for(int row = 0; row < 8; row++){
                for(int col = 0; col < 8; col++){

                    Piece attackPiece = gameState.getPiece(row,col);
                    if(attackPiece != null){
                        if(attackPiece.isBlack() != shouldBeBlack){
                            MoveBoard attackBoard = new MoveBoard();
                            attackBoard.findMoves(gameState, row, col);

                            for(int moveRow = 0; moveRow < 8; moveRow++) {
                                for (int moveCol = 0; moveCol < 8; moveCol++) {
                                    if (attackBoard.getCanMove(moveRow, moveCol)) {
                                        Piece defendPiece = gameState.getPiece(moveRow,moveCol);
                                        if(defendPiece != null){
                                            // move our endangered piece
                                            MoveBoard defendBoard = new MoveBoard();
                                            defendBoard.findMoves(gameState, moveRow,moveCol);

                                            for(int escRow = 0; escRow < 8; escRow++){
                                                for(int escCol = 0; escCol < 8; escCol++){
                                                    if(defendBoard.getCanMove(escRow,escCol)){
                                                        sleep(1);
                                                        // move to that location
                                                        Logger.log("ChessComputerPlayerHard", "Sending move");
                                                        game.sendAction(new ChessMoveAction(this,moveRow,moveCol,
                                                                escRow,escCol,defendPiece));
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // else make a random move
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
                if (potentialIndex != 0) {
                    int random = (int) (Math.random() * potentialIndex);
                    sleep(1);
                    // move to that location
                    Logger.log("ChessComputerPlayerHard", "Sending move");
                    game.sendAction(new ChessMoveAction(this, randomRow, randomCol,
                            potentialRow[random], potentialCol[random], randomPiece));
                    return;
                } else {
                    randomPiece = null;
                }
            }
        }
    }

}