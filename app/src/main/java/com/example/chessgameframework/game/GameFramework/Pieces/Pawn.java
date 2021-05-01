package com.example.chessgameframework.game.GameFramework.Pieces;

import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.game.GameFramework.Piece;

/**
 * @authors: Jonah Ingler
 *
 */

public class Pawn extends Piece {
    private boolean justMoved2;
    private boolean hasMoved;
    public Pawn(boolean isBlack) {
        super(isBlack);
        justMoved2 = false;
        this.hasMoved = false;
    }
    public boolean getHasMoved(){
        return this.hasMoved;
    }
    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }

    public boolean getJustMoved2(){
        return this.justMoved2;
    }

    public void setJustMoved2(boolean justMoved2){
        this.justMoved2 = justMoved2;
    }
}
