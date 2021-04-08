package com.example.chessgameframework.game.GameFramework.Pieces;

import android.graphics.Canvas;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;

/**
 * @authors: Jonah Ingler
 *
 */

public class Bishop extends Piece {
    public Bishop(boolean isBlack) {
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
        chessGameState.inCheck();

        //to find the total spaces moved, take the absolute value of the selected place - the final
        //if they are equal to eachother, than the bishop is moving diagonally as it should
        int colFinal = Math.abs(selectCol - col);
        int rowFinal = Math.abs(selectRow - row);

        //checks to see if the space it moves though is empty or not
        boolean empty = true;
        if(selectCol - col > 0 && selectRow - row > 0) {
            for (int i = col + 1; i < selectCol; i++) {
                for (int j = row + 1; j < selectRow; j++) {
                    if (chessGameState.getPiece(j, i) == null) {
                        empty = false;
                    }
                }
            }
        }
        else if(selectCol - col < 0 && selectRow - row <0){
            for (int i = col + 1; i < selectCol; i--) {
                for (int j = row + 1; j < selectRow; j--) {
                    if (chessGameState.getPiece(j, i) == null) {
                        empty = false;
                    }
                }
            }
        }
        else if(selectCol - col > 0 && selectRow - row <0){
            for (int i = col + 1; i < selectCol; i++) {
                for (int j = row + 1; j < selectRow; j--) {
                    if (chessGameState.getPiece(j, i) == null) {
                        empty = false;
                    }
                }
            }
        }
        else if(selectCol - col < 0 && selectRow - row > 0){
            for (int i = col + 1; i < selectCol; i--) {
                for (int j = row + 1; j < selectRow; j++) {
                    if (chessGameState.getPiece(j, i) == null) {
                        empty = false;
                    }
                }
            }
        }

        //returns true if they equal eachother
        if ((rowFinal == colFinal) && noAlly && empty) {
            return true;
        }
        return false;
    }

}