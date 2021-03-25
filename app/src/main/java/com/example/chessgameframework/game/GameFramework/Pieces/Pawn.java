package com.example.chessgameframework.game.GameFramework.Pieces;

import com.example.chessgameframework.game.GameFramework.Piece;

public class Pawn extends Piece {
    public Pawn(boolean isBlack) {
        super(isBlack);
    }

    @Override
    public boolean canMove(int row, int col, int selectRow, int selectCol, Piece piece) {
        if((isBlack() == true) || (selectCol + 1)   ){
        }



            return false;
    }
}
