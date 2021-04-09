package com.example.chessgameframework.game.GameFramework.Pieces;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;

/**
 * @authors: Jonah Ingler
 *
 */

public class Knight extends Piece {
    public Knight(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public boolean canMove(int row, int col, int selectRow, int selectCol) {
        //boolean value for whether there is an ally in the target space or not
        boolean noAlly = false;
        //checks to see if their is an "ally" at the destination
        //if there is no ally, then isEmpty is true
        ChessGameState chessGameState = new ChessGameState();
        if (!(chessGameState.getPiece(selectRow,selectCol).isBlack() == this.isBlack())){
            noAlly = true;
        }

        //make sure that the booleans for check are up to date
        //chessGameState.inCheck();

        int colFinal = Math.abs(selectCol - col);
        int rowFinal = Math.abs(selectRow - row);

        if((colFinal * rowFinal == 6) && colFinal == 3 || colFinal == 2 && noAlly) {
            return true;
        }
        return false;
    }

}