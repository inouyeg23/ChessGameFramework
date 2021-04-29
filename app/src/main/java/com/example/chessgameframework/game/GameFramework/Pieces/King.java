package com.example.chessgameframework.game.GameFramework.Pieces;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;

/**
 * @authors: Jonah Ingler
 *
 */

public class King extends Piece {
    private boolean hasMoved;
    public King(boolean isBlack) {
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
