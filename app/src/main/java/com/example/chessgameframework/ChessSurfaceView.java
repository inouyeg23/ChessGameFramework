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
import com.example.chessgameframework.game.GameFramework.Pieces.MoveBoard;
import com.example.chessgameframework.game.GameFramework.Pieces.Pawn;
import com.example.chessgameframework.game.GameFramework.Pieces.Queen;
import com.example.chessgameframework.game.GameFramework.Pieces.Rook;

/**
 * ChessSurfaceView extends SurfaceView to draw the chess board, pieces, and the highlighted
 * squares.  It also scales the pixel coordinates according to the surfaceView size and converts
 * the number into an easy to follow row and col system.
 *
 * @author Garrett Inouye
 * @data 4/29/21
 */

public class ChessSurfaceView extends SurfaceView {

    // the game's state
    protected ChessGameState gameState;

    // Initializing each drawable image with a variable

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

    // Variables to assist with scaling chessboard and drawables
    private int viewDim;
    private int scaleShape;
    private int scaleOffset;

    private int hBase;
    private int vBase;

    private int setPixelToRow;
    private int setPixelToColumn;

    //private int to show where the player touched
    private int[] squareTouched = new int[2];

    //private piece class to determine the piece that's selected
    private Piece touchedPiece;



    public boolean drawHighlight;

    public ChessSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);

    }

    public void setState(ChessGameState state) {
        this.gameState = state;
    }

    /**
     * main method for drawing on the surfaceView
     * @param g
     */

    protected void onDraw(Canvas g){
        // if we don't have any state, there's nothing more to draw, so return
        if (gameState == null) {
            return;
        }

        //update dimensions of canvas to match the surface view
        updateDimensions(g);

        //draw the chess board based on surface view
        drawBoard(g);

        //draw all pieces based on getPiece
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece result = gameState.getPiece(row, col); // get piece
                drawPiece(g, result, row, col);

            }
        }

        if(drawHighlight) {
            //draws all highlighted squares based on possible moves
            drawAllHighlightedSquares(g);
        }

    }

    /**
     * update the instance variables that relate to the drawing surface
     *
     * @param g
     * 		an object that references the drawing surface
     */
    private void updateDimensions(Canvas g) {

        //variables to get the width and height of the surfaceView
        int width = g.getWidth();
        int height = g.getHeight();

        // use both width or height to create a perfect square and determine the border
        // or extra space in the surfaceView
        if (width > height) {
            viewDim = height;
            vBase = 0;
            hBase = (width - height) / 2;
        } else {
            viewDim = width;
            hBase = 0;
            vBase = (height - width) / 2;
        }

        // variables to help with scaling the shape correcting to fit the size of the board
        scaleOffset = 20;
        scaleShape = (viewDim /8) - 20;

    }

    /**
     * helper methods to convert the pixel width and height to simple row and col numbers
     * @return
     */
    protected int convertPixelToRow(int row){
        setPixelToRow = (viewDim /8)*row + (scaleOffset/2) + vBase;
        return setPixelToRow;
    }
    protected int convertPixelToCol(int col){
        setPixelToColumn = (viewDim /8)*col + (scaleOffset/2) + hBase;
        return setPixelToColumn;
    }

    /**
     * draws the chess board using a bitmap and resizes
     * using the updated dimensions
     *
     * @param g
     */
    protected void drawBoard(Canvas g) {
        Paint background = new Paint();
        background.setColor(Color.WHITE);

        Bitmap resizedchessboard = Bitmap.createScaledBitmap(chessBoard, viewDim, viewDim, false);
        g.drawBitmap(resizedchessboard, hBase, vBase, background);
    }

    /**
     * method that works in conjunction with ChessHumanPlayer to return the spot the player
     * touched
     * @param row
     * @param col
     */
    public void setPlayerTouched(int row, int col){
        squareTouched[0] = row;
        squareTouched[1] = col;
    }

    /**
     * method that works in conjunction with ChessHumanPlayer to return the piece the player
     * touched
     * @param piece
     */
    public void setPieceTouched(Piece piece){
        if(piece instanceof Pawn){
            touchedPiece = piece;
        }
        if(piece instanceof Knight){
            touchedPiece = piece;
        }
        if(piece instanceof Rook){
            touchedPiece = piece;
        }
        if(piece instanceof Bishop){
            touchedPiece = piece;
        }
        if(piece instanceof King){
            touchedPiece = piece;
        }
        if(piece instanceof Queen){
            touchedPiece = piece;
        }
    }

    /**
     * draws all the highlighted squares based on
     * possible moves from the moveBoard class
     *
     * @param g
     */
    public void drawAllHighlightedSquares(Canvas g){

        if(touchedPiece == null || gameState == null){
            return;
        } else {
            MoveBoard moveBoard = new MoveBoard();
            moveBoard.findMoves(gameState, squareTouched[0], squareTouched[1]);

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (moveBoard.getCanMove(i, j)) {
                        drawHighlightedSquare(g, i, j);
                    }
                }
            }
        }
    }

    /**
     * draws a single highlighted square based on dimensions
     *      possibility of drawing a circle dot instead of a highlighted square (preference)
     *
     * @param g
     * @param row
     * @param col
     */
    public void drawHighlightedSquare(Canvas g, int row, int col){
        //setting up the transparent color
        Paint transparentPaint = new Paint();
        transparentPaint.setColor(Color.YELLOW);
        transparentPaint.setAlpha(75);

        g.drawRect((viewDim /8)*col + col/2 + hBase, (viewDim /8)*row + row/2 + vBase,
                (viewDim /8)*col + (viewDim /8) + hBase, (viewDim /8)*row + (viewDim /8) + vBase, transparentPaint );

        /*
        //drawing the circle
        Paint black = new Paint();
        black.setColor(Color.BLACK);

        int radius = (dimen/8)/4;
        g.drawCircle(((dimen/8)*col)+(dimen/8)/2, ((dimen/8)*row)+(dimen/8)/2, radius, black);
        */
    }


    /**
     * draws a piece from the other draw methods below based on the piece
     * that is given in the parameter
     *
     * @param g
     * @param piece
     * @param row
     * @param col
     */
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

    /**
     * specific methods dealing with drawing the specific piece in the drawPiece method
     * @param g
     * @param color
     * @param row
     * @param col
     */
    // draws a pawn piece
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
        g.drawBitmap(resizedpawn, convertPixelToCol(col), convertPixelToRow(row), background);

    } //drawPawn

    //draws a knight piece
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
        g.drawBitmap(resizedknight, convertPixelToCol(col), convertPixelToRow(row), background);

    } //drawKnight

    //draws a rook piece
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
        g.drawBitmap(resizedrook, convertPixelToCol(col), convertPixelToRow(row), background);

    } //drawRook

    //draws a bishop piece
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
        g.drawBitmap(resizedbishop, convertPixelToCol(col), convertPixelToRow(row), background);

    } //drawBishop

    //draws a king piece
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
        g.drawBitmap(resizedking, convertPixelToCol(col), convertPixelToRow(row), background);

    } //drawKing

    //draws a queen piece
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
        g.drawBitmap(resizedqueen, convertPixelToCol(col), convertPixelToRow(row), background);

    } //drawQueen


    /**
     * getter methods to help scale the onTouch in HumanPlayer the same way
     * it was scaled here
     *
     * @return
     */
    public int getScaledDim() { return viewDim; }

    public int getScaledRow(){ return vBase; }

    public int getScaledCol(){ return hBase; }

}
