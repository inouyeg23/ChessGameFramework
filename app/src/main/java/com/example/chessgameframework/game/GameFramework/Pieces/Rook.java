package com.example.chessgameframework.game.GameFramework.Pieces;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;

/**
 * Piece object for the rook
 * @authors: Jonah Ingler
 *
 */

public class Rook extends Piece {
    private boolean hasMoved;
    public Rook(boolean isBlack) {
        super(isBlack);
        this.hasMoved = false;
    }
    public boolean getHasMoved(){
        return this.hasMoved;
    }
    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }
}
