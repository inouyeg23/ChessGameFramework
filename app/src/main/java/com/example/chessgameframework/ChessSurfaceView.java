package com.example.chessgameframework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.Pieces.Bishop;
import com.example.chessgameframework.game.GameFramework.Pieces.King;
import com.example.chessgameframework.game.GameFramework.Pieces.Knight;
import com.example.chessgameframework.game.GameFramework.Pieces.Pawn;
import com.example.chessgameframework.game.GameFramework.Pieces.Queen;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;

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
    int scaleShape;
    int scaleOffset;

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

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece result = gameState.getPiece(row, col); // get piece
                drawPiece(g, result, row, col);
            }
        }
        drawHighlightedSquare(g, 1, 3);
        drawHighlightedSquare(g, 0, 3);
        drawHighlightedSquare(g, 7, 7);

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

        scaleOffset = 20;
        scaleShape = (dimen/8) - scaleOffset;
    }

    protected void drawBoard(Canvas g) {
        Paint background = new Paint();
        background.setColor(Color.WHITE);

        Bitmap resizedchessboard = Bitmap.createScaledBitmap(chessBoard, dimen, dimen, false);
        g.drawBitmap(resizedchessboard, 0.0f, 0.0f, background);
    }

    protected void drawHighlightedSquare(Canvas g, int row, int col){

        //setting up the transparent color
        Paint transparentPaint = new Paint();
        transparentPaint.setColor(Color.YELLOW);
        transparentPaint.setAlpha(75);

        //drawing the circle
        //g.drawCircle(((dimen/8)*row) + radius , ((dimen/8)*col) + radius, radius,  transparentPaint);

        g.drawRect((dimen/8)*col + col/2, (dimen/8)*row + row/2,
                (dimen/8)*col + (dimen/8), (dimen/8)*row + (dimen/8), transparentPaint );
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
                resizedpawn= Bitmap.createScaledBitmap(blackPawn, scaleShape, scaleShape, false);
                break;
            case 'w':
                resizedpawn= Bitmap.createScaledBitmap(whitePawn, scaleShape, scaleShape, false);
                break;
        }
        g.drawBitmap(resizedpawn, (dimen/8)*col + (scaleOffset/2), (dimen/8)*row + (scaleOffset/2), background);

    } //drawPawn

    protected void drawKnight(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedknight = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedknight= Bitmap.createScaledBitmap(blackKnight, scaleShape, scaleShape, false);
                break;
            case 'w':
                resizedknight= Bitmap.createScaledBitmap(whiteKnight, scaleShape, scaleShape, false);
                break;
        }
        g.drawBitmap(resizedknight, (dimen/8)*col + (scaleOffset/2), (dimen/8)*row + (scaleOffset/2), background);

    } //drawKnight

    protected void drawRook(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedrook = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedrook= Bitmap.createScaledBitmap(blackRook, scaleShape, scaleShape, false);
                break;
            case 'w':
                resizedrook= Bitmap.createScaledBitmap(whiteRook, scaleShape, scaleShape, false);
                break;
        }
        g.drawBitmap(resizedrook, (dimen/8)*col + (scaleOffset/2), (dimen/8)*row + (scaleOffset/2), background);

    } //drawRook

    protected void drawBishop(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedbishop = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedbishop= Bitmap.createScaledBitmap(blackBishop, scaleShape, scaleShape, false);
                break;
            case 'w':
                resizedbishop= Bitmap.createScaledBitmap(whiteBishop, scaleShape, scaleShape, false);
                break;
        }
        g.drawBitmap(resizedbishop, (dimen/8)*col + (scaleOffset/2), (dimen/8)*row + (scaleOffset/2), background);

    } //drawBishop

    protected void drawKing(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedking = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedking= Bitmap.createScaledBitmap(blackKing, scaleShape, scaleShape, false);
                break;
            case 'w':
                resizedking= Bitmap.createScaledBitmap(whiteKing, scaleShape, scaleShape, false);
                break;
        }
        g.drawBitmap(resizedking, (dimen/8)*col + (scaleOffset/2), (dimen/8)*row + (scaleOffset/2), background);

    } //drawKing

    protected void drawQueen(Canvas g, char color, int row, int col){
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        Bitmap resizedqueen = null;

        switch(color) {
            case 'b':
                //          (image, left, top, color)
                resizedqueen= Bitmap.createScaledBitmap(blackQueen, scaleShape, scaleShape, false);
                break;
            case 'w':
                resizedqueen= Bitmap.createScaledBitmap(whiteQueen, scaleShape, scaleShape, false);
                break;
        }
        g.drawBitmap(resizedqueen, (dimen/8)*col + (scaleOffset/2), (dimen/8)*row + (scaleOffset/2), background);

    } //drawQueen



}
