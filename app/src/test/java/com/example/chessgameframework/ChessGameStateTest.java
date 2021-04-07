package com.example.chessgameframework;

import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.King;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChessGameStateTest {

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

    @Test
    public void setPiece() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void movePiece() {


    }

    @Test
    public void gameStart() {
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.gameStarted);
        gameState.gameStart();
        assertTrue(gameState.gameStarted);

    }
}