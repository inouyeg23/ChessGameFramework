package com.example.chessgameframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.Bishop;
import com.example.chessgameframework.game.GameFramework.Pieces.King;
import com.example.chessgameframework.game.GameFramework.Pieces.Knight;
import com.example.chessgameframework.game.GameFramework.Pieces.Pawn;
import com.example.chessgameframework.game.GameFramework.Pieces.Queen;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;
import com.example.chessgameframework.game.GameFramework.utilities.Logger;

public class ChessSurfaceView extends SurfaceView{
    // Initializing each piece
    private Bitmap blackPawn =      BitmapFactory.decodeResource(getResources(), R.drawable.black_pawn);
    private Bitmap blackKnight =    BitmapFactory.decodeResource(getResources(), R.drawable.black_knight);
    private Bitmap blackRook =      BitmapFactory.decodeResource(getResources(), R.drawable.black_rook);
    private Bitmap blackBishop =    BitmapFactory.decodeResource(getResources(), R.drawable.black_bishop);
    private Bitmap blackKing =      BitmapFactory.decodeResource(getResources(), R.drawable.black_king);
    private Bitmap blackQueen =     BitmapFactory.decodeResource(getResources(), R.drawable.black_queen);
    private Bitmap whitePawn =      BitmapFactory.decodeResource(getResources(), R.drawable.white_pawn);
    private Bitmap whiteKnight =    BitmapFactory.decodeResource(getResources(), R.drawable.white_knight);
    private Bitmap whiteRook =      BitmapFactory.decodeResource(getResources(), R.drawable.white_rook);
    private Bitmap whiteBishop =    BitmapFactory.decodeResource(getResources(), R.drawable.white_bishop);
    private Bitmap whiteKing =      BitmapFactory.decodeResource(getResources(), R.drawable.white_king);
    private Bitmap whiteQueen =     BitmapFactory.decodeResource(getResources(), R.drawable.white_queen);

    private Bitmap chessBoard =     BitmapFactory.decodeResource(getResources(), R.drawable.chessemptyboard);

    // The size of the chess board itself
    int dimen;
    int scaledShape;


    // the game's state
    protected ChessGameState gameState;

    public ChessSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);
    }

    public void setState(ChessGameState state) {
        this.gameState = state;
    }

    protected void onDraw(Canvas g){
        // if we don't have any state, there's nothing more to draw, so return
        if (gameState == null) {
            return;
        }

        updateDimensions(g);

        drawBoard(g);

<<<<<<< Updated upstream
        //gameState.setPiece(0,0, new Pawn(true));
=======
        gameState.setPiece(0,0, new Pawn(true));
>>>>>>> Stashed changes



        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece result = gameState.getPiece(row, col); // get piece
                drawPiece(g, result, row, col);
            }
        }


        /*

        //player side
        drawPawn(g, 'b', 6, 0);
        drawPawn(g, 'b', 6, 1);
        drawPawn(g, 'b', 6, 2);
        drawPawn(g, 'b', 6, 3);
        drawPawn(g, 'b', 6, 4);
        drawPawn(g, 'b', 6, 5);
        drawPawn(g, 'b', 6, 6);
        drawPawn(g, 'b', 6, 7);
        drawRook(g, 'b', 7, 0);
        drawKnight(g, 'b', 7, 1);
        drawBishop(g, 'b', 7, 2);
        drawKing(g, 'b', 7, 3);
        drawQueen(g, 'b', 7, 4);
        drawBishop(g, 'b', 7, 5);
        drawKnight(g, 'b', 7, 6);
        drawRook(g, 'b', 7, 7);

        //opposing side
        drawPawn(g, 'w', 1, 0);
        drawPawn(g, 'w', 1, 1);
        drawPawn(g, 'w', 1, 2);
        drawPawn(g, 'w', 1, 3);
        drawPawn(g, 'w', 1, 4);
        drawPawn(g, 'w', 1, 5);
        drawPawn(g, 'w', 1, 6);
        drawPawn(g, 'w', 1, 7);
        drawRook(g, 'w', 0, 0);
        drawKnight(g, 'w', 0, 1);
        drawBishop(g, 'w', 0, 2);
        drawKing(g, 'w', 0, 3);
        drawQueen(g, 'w', 0, 4);
        drawBishop(g, 'w', 0, 5);
        drawKnight(g, 'w', 0, 6);
        drawRook(g, 'w', 0, 7);

         */




        //add images corresponding to drawables


    }
    /**
     * update the instance variables that relate to the drawing surface
     *
     * @param g
     * 		an object that references the drawing surface
     */
    private void updateDimensions(Canvas g) {

        int width = g.getWidth();
        int height = g.getHeight();

        //use both width or height to create a perfect square
        if (width > height) {
            dimen = height;
        } else {
            dimen = width;
        }

        scaledShape = (dimen/8)-5;
    }

    protected void drawBoard(Canvas g) {
        Paint background = new Paint();
        background.setColor(Color.WHITE);

        Bitmap resizedchessboard = Bitmap.createScaledBitmap(chessBoard, dimen, dimen, false);
        g.drawBitmap(resizedchessboard, 0.0f, 0.0f, background);
    }

    protected void drawPiece(Canvas g, Piece piece, int row, int col){
        if(piece == null){
            return;
        }
        if(piece instanceof Pawn){
            if(piece.isBlack()){
                drawPawn(g, 'b', row, col);
            } else {
                drawPawn(g, 'w', row, col);
            }
            return;
        }
        if(piece instanceof Knight){
            if(piece.isBlack()){
                drawKnight(g, 'b', row, col);
            } else {
                drawKnight(g, 'w', row, col);
            }
            return;
        }
        if(piece instanceof Rook){
            if(piece.isBlack()){
                drawRook(g, 'b', row, col);
            } else {
                drawRook(g, 'w', row, col);
            }
            return;
        }
        if(piece instanceof Bishop){
            if(piece.isBlack()){
                drawBishop(g, 'b', row, col);
            } else {
                drawBishop(g, 'w', row, col);
            }
            return;
        }
        if(piece instanceof King){
            if(piece.isBlack()){
                drawKing(g, 'b', row, col);
            } else {
                drawKing(g, 'w', row, col);
            }
            return;
        }
        if(piece instanceof Queen){
            if(piece.isBlack()){
                drawQueen(g, 'b', row, col);
            } else {
                drawQueen(g, 'w', row, col);
            }
            return;
        }
    }


    protected void drawPawn(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedpawn = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedpawn= Bitmap.createScaledBitmap(blackPawn, scaledShape, scaledShape, false);
                break;
            case 'w':
                resizedpawn= Bitmap.createScaledBitmap(whitePawn, scaledShape, scaledShape, false);
                break;
        }
        g.drawBitmap(resizedpawn, (dimen/8)*col, (dimen/8)*row, background);

    } //drawPawn

    protected void drawKnight(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedknight = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedknight= Bitmap.createScaledBitmap(blackKnight, scaledShape, scaledShape, false);
                break;
            case 'w':
                resizedknight= Bitmap.createScaledBitmap(whiteKnight, scaledShape, scaledShape, false);
                break;
        }
        g.drawBitmap(resizedknight, (dimen/8)*col, (dimen/8)*row, background);

    } //drawKnight

    protected void drawRook(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedrook = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedrook= Bitmap.createScaledBitmap(blackRook, scaledShape, scaledShape, false);
                break;
            case 'w':
                resizedrook= Bitmap.createScaledBitmap(whiteRook, scaledShape, scaledShape, false);
                break;
        }
        g.drawBitmap(resizedrook, (dimen/8)*col, (dimen/8)*row, background);

    } //drawRook

    protected void drawBishop(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedbishop = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedbishop= Bitmap.createScaledBitmap(blackBishop, scaledShape, scaledShape, false);
                break;
            case 'w':
                resizedbishop= Bitmap.createScaledBitmap(whiteBishop, scaledShape, scaledShape, false);
                break;
        }
        g.drawBitmap(resizedbishop, (dimen/8)*col, (dimen/8)*row, background);

    } //drawBishop

    protected void drawKing(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedking = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedking= Bitmap.createScaledBitmap(blackKing, scaledShape, scaledShape, false);
                break;
            case 'w':
                resizedking= Bitmap.createScaledBitmap(whiteKing, scaledShape, scaledShape, false);
                break;
        }
        g.drawBitmap(resizedking, (dimen/8)*col, (dimen/8)*row, background);

    } //drawKing

    protected void drawQueen(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedqueen = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedqueen= Bitmap.createScaledBitmap(blackQueen, scaledShape, scaledShape, false);
                break;
            case 'w':
                resizedqueen= Bitmap.createScaledBitmap(whiteQueen, scaledShape, scaledShape, false);
                break;
        }
        g.drawBitmap(resizedqueen, (dimen/8)*col, (dimen/8)*row, background);

    } //drawQueen



}
