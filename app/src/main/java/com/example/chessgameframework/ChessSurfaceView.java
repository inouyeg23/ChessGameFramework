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

    protected void onDraw(Canvas g){
        updateDimensions(g);

        drawBoard(g);

        //player side
        drawPawn(g, 'b', 7, 1);
        drawPawn(g, 'b', 7, 2);
        drawPawn(g, 'b', 7, 3);
        drawPawn(g, 'b', 7, 4);
        drawPawn(g, 'b', 7, 5);
        drawPawn(g, 'b', 7, 6);
        drawPawn(g, 'b', 7, 7);
        drawPawn(g, 'b', 7, 8);
        drawRook(g, 'b', 8, 1);
        drawKnight(g, 'b', 8, 2);
        drawBishop(g, 'b', 8, 3);
        drawKing(g, 'b', 8, 4);
        drawQueen(g, 'b', 8, 5);
        drawBishop(g, 'b', 8, 6);
        drawKnight(g, 'b', 8, 7);
        drawRook(g, 'b', 8, 8);

        //opposing side
        drawPawn(g, 'w', 2, 1);
        drawPawn(g, 'w', 2, 2);
        drawPawn(g, 'w', 2, 3);
        drawPawn(g, 'w', 2, 4);
        drawPawn(g, 'w', 2, 5);
        drawPawn(g, 'w', 2, 6);
        drawPawn(g, 'w', 2, 7);
        drawPawn(g, 'w', 2, 8);
        drawRook(g, 'w', 1, 1);
        drawKnight(g, 'w', 1, 2);
        drawBishop(g, 'w', 1, 3);
        drawKing(g, 'w', 1, 4);
        drawQueen(g, 'w', 1, 5);
        drawBishop(g, 'w', 1, 6);
        drawKnight(g, 'w', 1, 7);
        drawRook(g, 'w', 1, 8);

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
        g.drawBitmap(resizedpawn, ((dimen/8)*(col-1)), ((dimen/8)*(row-1)), background);

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
        g.drawBitmap(resizedknight, ((dimen/8)*(col-1)), ((dimen/8)*(row-1)), background);

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
        g.drawBitmap(resizedrook, ((dimen/8)*(col-1)), ((dimen/8)*(row-1)), background);

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
        g.drawBitmap(resizedbishop, ((dimen/8)*(col-1)), ((dimen/8)*(row-1)), background);

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
        g.drawBitmap(resizedking, ((dimen/8)*(col-1)), ((dimen/8)*(row-1)), background);

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
        g.drawBitmap(resizedqueen, ((dimen/8)*(col-1)), ((dimen/8)*(row-1)), background);

    } //drawQueen

    public void setState(ChessGameState state) {
        this.gameState = state;
    }

}
