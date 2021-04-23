package com.example.chessgameframework;

import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.King;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void setPiece(){
        ChessGameState gameState = new ChessGameState();
        gameState.setPiece(0,0,new Rook(false));
        Piece p = gameState.getPiece(0,0);
        assertEquals(p.isBlack(),false);
        assertTrue(p instanceof Rook);
    }

    @Test
    public void getPiece() {
        ChessGameState gameState = new ChessGameState();
        gameState.setPiece(0,0,new Rook(false));
        Piece p = gameState.getPiece(0,0);
        assertEquals(p.isBlack(),false);
        assertTrue(p instanceof Rook);
        gameState.setPiece(7,7,new King(true));
        Piece p1 = gameState.getPiece(7,7);
        assertEquals(p1.isBlack(), true);
        assertTrue(p1 instanceof King);
    }

    @Test
    public void isStartPressed(){
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.isStartPressed());
        //assuming the button onClick is working and the user has pressed the quit button
        gameState.gameStarted = true;
        assertTrue(gameState.isStartPressed());
    }

    @Test
    public void isQuitPressed(){
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.isQuitPressed());
        //assuming the button onClick is working and the user has pressed the quit button
        gameState.isQuitPressed = true;
        assertTrue(gameState.isQuitPressed());
    }

    @Test
    public void isForfeitPressed(){
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.isForfeitPressed());
        //assuming the button onClick is working and the user has pressed the quit button
        gameState.isForfeitPressed = true;
        assertTrue(gameState.isForfeitPressed());
    }

    @Test
    public void isDrawPressed(){
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.isDrawPressed());
        //assuming the button onClick is working and the user has pressed the quit button
        gameState.isDrawPressed = true;
        assertTrue(gameState.isDrawPressed());
    }

    @Test
    public void isPausePressed(){
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.isPausePressed());
        //assuming the button onClick is working and the user has pressed the quit button
        gameState.isPaused = true;
        assertTrue(gameState.isPausePressed());
    }

    @Test
    public void getPlayerTurn(){
        ChessGameState gameState = new ChessGameState();
        assertEquals(0, gameState.getPlayerTurn());
    }

    @Test
    public void setPlayerTurn(){
        ChessGameState gameState = new ChessGameState();
        gameState.setPlayerTurn(1);
        assertEquals(1, gameState.getPlayerTurn());
    }

    @Test
    public void setPointsBlack() {
        ChessGameState gameState = new ChessGameState();
        gameState.setPointsBlack(10);
        assertEquals(10, gameState.getPointsBlack());
    }


    @Test
    public void setPointsWhite() {
        ChessGameState gameState = new ChessGameState();
        gameState.setPointsWhite(10);
        assertEquals(10, gameState.getPointsWhite());
    }


}