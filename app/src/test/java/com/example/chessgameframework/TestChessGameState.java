package com.example.chessgameframework;

import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.King;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestChessGameState {
    @Test
    public void getPiece() {
        ChessGameState gameState = new ChessGameState();
        gameState.setPiece(0,0,new Rook(false));
        Piece p = gameState.getPiece(0,0);
        assertEquals(p.isBlack(),false);
        assertTrue(p instanceof Rook);
        gameState.setPiece(1,1,new King(true));
        Piece p1 = gameState.getPiece(1,1);
        assertEquals(true,p1.isBlack());
        assertTrue(p1 instanceof King);
    }

}
