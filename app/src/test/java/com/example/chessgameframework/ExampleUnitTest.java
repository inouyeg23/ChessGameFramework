package com.example.chessgameframework;

import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.Bishop;
import com.example.chessgameframework.game.GameFramework.Pieces.King;
import com.example.chessgameframework.game.GameFramework.Pieces.Knight;
import com.example.chessgameframework.game.GameFramework.Pieces.MoveBoard;
import com.example.chessgameframework.game.GameFramework.Pieces.Pawn;
import com.example.chessgameframework.game.GameFramework.Pieces.Queen;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    //Connor
    @Test
    public void setPiece(){
        ChessGameState gameState = new ChessGameState();
        gameState.setPiece(0,0,new Rook(false));
        Piece p = gameState.getPiece(0,0);
        assertEquals(p.isBlack(),false);
        assertTrue(p instanceof Rook);
    }

    //Connor
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

    //Garrett
    @Test
    public void isStartPressed(){
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.getGameStarted());
        //assuming the button onClick is working and the user has pressed the quit button
        gameState.setGameStarted(true);
        assertTrue(gameState.getGameStarted());
    }

    //Garrett
    @Test
    public void isQuitPressed(){
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.isQuitPressed());
        //assuming the button onClick is working and the user has pressed the quit button
        gameState.setQuitPressed(true);
        assertTrue(gameState.isQuitPressed());
    }

    //Garrett
    @Test
    public void isForfeitPressed(){
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.isForfeitPressed());
        //assuming the button onClick is working and the user has pressed the quit button
        gameState.setForfeitPressed(true);
        assertTrue(gameState.isForfeitPressed());
    }

    //Garrett
    @Test
    public void isDrawPressed(){
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.isDrawPressed());
        //assuming the button onClick is working and the user has pressed the quit button
        gameState.setDrawPressed(true);
        assertTrue(gameState.isDrawPressed());
    }

    //Logan
    @Test
    public void getPlayerTurn(){
        ChessGameState gameState = new ChessGameState();
        assertEquals(0, gameState.getPlayerTurn());
    }

    //Logan
    @Test
    public void setPlayerTurn(){
        ChessGameState gameState = new ChessGameState();
        gameState.setPlayerTurn(1);
        assertEquals(1, gameState.getPlayerTurn());
    }

    //Logan
    @Test
    public void setPointsBlack() {
        ChessGameState gameState = new ChessGameState();
        gameState.setPointsBlack(10);
        assertEquals(10, gameState.getPointsBlack());
    }

    //Logan
    @Test
    public void setPointsWhite() {
        ChessGameState gameState = new ChessGameState();
        gameState.setPointsWhite(10);
        assertEquals(10, gameState.getPointsWhite());
    }

    //Jonah
    @Test
    public void isPausePressed(){
        ChessGameState gameState = new ChessGameState();
        assertTrue(!gameState.getPaused());
        //assuming the button onClick is working and the user has pressed the quit button
        gameState.setPaused(true);
        assertTrue(gameState.getPaused());
    }

//    @Test
//    public void findMoves(){
//        MoveBoard moveBoard = new MoveBoard();
//        ChessGameState gameState = new ChessGameState();
//        resetBoards();
//
//        //Testing pieces in the immediate area
//        ArrayList<Piece> testPieces = new ArrayList<>();
//        testPieces.add(new Rook(true));
//        testPieces.add(new Bishop(true));
//        testPieces.add(new Pawn(true));
//        testPieces.add(new King(true));
//        testPieces.add(new Queen(true));
//        testPieces.add(new Knight(true));
//
////        gameState.board[1][1] = new Rook(true);
////        gameState.board[0][0] = new Pawn(true);
////        gameState.board[0][1] = new Pawn(true);
////        gameState.board[0][2] = new Pawn(true);
////        gameState.board[1][0] = new Pawn(true);
////        gameState.board[1][2] = new Pawn(true);
////        gameState.board[2][0] = new Pawn(true);
////        gameState.board[2][1] = new Pawn(true);
////        gameState.board[2][2] = new Pawn(true);
//
//        for (Piece p : testPieces) {
//            moveBoard.findMoves(gameState, 1, 1);
//            assertFalse(moveBoard.board[0][0]);
//            assertFalse(moveBoard.board[0][1]);
//            assertFalse(moveBoard.board[0][2]);
//            assertFalse(moveBoard.board[1][0]);
//            assertFalse(moveBoard.board[1][2]);
//            assertFalse(moveBoard.board[2][0]);
//            //assertFalse(moveBoard.board[2][1]);
//            assertFalse(moveBoard.board[2][2]);
//            System.out.println(" " + p);
//            if(p instanceof Knight){
//                //assertTrue(moveBoard.board[3][2]);
//                //assertTrue(moveBoard.board[2][3]);
//                //assertTrue(moveBoard.board[3][0]);
//                //assertTrue(moveBoard.board[0][3]);
//            }
//        }
//    }
//
//    public void resetBoards(){
//        MoveBoard moveBoard = new MoveBoard();
//        ChessGameState gameState = new ChessGameState();
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                gameState.board[i][j] = null;
//            }
//        }
//        for(int i = 0; i < 8; i++){
//            for(int j = 0; j < 8; j++){
//                moveBoard.board[i][j] = false;
//            }
//        }
//    }
}