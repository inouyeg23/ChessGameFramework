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
    @Test
    public void checkIfTwoKings(){
        ChessGameState gameState = new ChessGameState();
        King king1 = new King(true);
        King king2 = new King(false);
        gameState.setPiece(0,0, king1);
        gameState.setPiece(0,1, king2);
        assertTrue(gameState.checkIfTwoKings());
    }
    @Test
    public void setSecondsWhite(){
        ChessGameState gameState = new ChessGameState();
        gameState.setSecondsWhite(1000);
        assertEquals(1000, gameState.getSecondsWhite());
    }
    @Test
    public void setSecondsBlack(){
        ChessGameState gameState = new ChessGameState();
        gameState.setSecondsBlack(1000);
        assertEquals(1000, gameState.getSecondsBlack());
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