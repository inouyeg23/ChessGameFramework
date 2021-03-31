package com.example.chessgameframework.game.GameFramework.Pieces;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;

public class Bishop extends Piece {
    public Bishop(boolean isBlack) {
        super(isBlack);
    }
    @Override
    public boolean canMove(int row, int col, int selectRow, int selectCol, Piece piece) {

        //checks to see if their is an "ally" at the destination
        //if there is no ally, then it returns true
        ChessGameState chessGameState = new ChessGameState();
        if (!(chessGameState.getPiece(selectRow,selectCol).isBlack() == this.isBlack())){
            return true;
        }
        //to find the total spaces moved, take the absolute value of the selected place - the final
        //if they are equal to eachother, than the bishop is moving diagonally as it should
        int colFinal = Math.abs(selectCol - col);
        int rowFinal = Math.abs(selectRow - row);

        //returns true if they equal eachother
        if (rowFinal == colFinal){
            return true;
        }
        return false;
    }
}