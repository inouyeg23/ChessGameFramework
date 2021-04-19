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

public class ChessSurfaceView extends SurfaceView{
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
    private int dimen;
    private int scaleShape;
    private int scaleOffset;

    //private int to show where the player touched
    private int[] squareTouched = new int[2];
    private int touchedRow;
    private int touchedCol;

    //private piece class to determine the piece that's selected
    private Piece touchedPiece;

    // the game's state
    protected ChessGameState gameState;

    public ChessSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);
    }

    public void setState(ChessGameState state) {
        this.gameState = state;
    }

    public void setPlayerTouched(int row, int col){
        squareTouched[0] = row;
        squareTouched[1] = col;
    }

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

        drawAllHighlightedSquares(g);


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

    public void drawAllHighlightedSquares(Canvas g){
        touchedRow = squareTouched[0];
        touchedCol = squareTouched[1];

        if(touchedPiece == null || gameState == null){
            return;
        } else if(touchedPiece instanceof Pawn){
            //pawns can move in the three spaces in front of them
            if(touchedPiece.isBlack()) {
                //moves are +1+1, +0+1, +0+2, -1+1
                if (touchedRow == 1 && gameState.getPiece(touchedRow + 1, touchedCol) == null && gameState.getPiece(touchedRow + 2, touchedCol) == null) {
                    drawHighlightedSquare(g, touchedRow+2, touchedCol);
                }
                if (touchedRow < 7 && gameState.getPiece(touchedRow + 1, touchedCol) == null){
                    drawHighlightedSquare(g, touchedRow+1, touchedCol);
                }
                if(touchedRow < 7 && touchedCol < 7 && gameState.getPiece(touchedRow+1,touchedCol+1) != null && gameState.getPiece(touchedRow+1,touchedCol+1).isBlack() != gameState.getPiece(touchedRow,touchedCol).isBlack()) {
                    drawHighlightedSquare(g, touchedRow+1, touchedCol+1);
                }
                if(touchedRow < 7 && touchedCol > 0 && gameState.getPiece(touchedRow+1,touchedCol-1) != null && gameState.getPiece(touchedRow+1,touchedCol-1).isBlack() != gameState.getPiece(touchedRow,touchedCol).isBlack()) {
                    drawHighlightedSquare(g, touchedRow+1, touchedCol-1);
                }
            } else{
                //moves are -1-1, +0-1,+0-2, +1,-1
                if(touchedRow == 6 && gameState.getPiece(touchedRow-1,touchedCol) == null && gameState.getPiece(touchedRow-2,touchedCol) == null) {
                    drawHighlightedSquare(g, touchedRow-2, touchedCol);
                }
                if(touchedRow > 0 && gameState.getPiece(touchedRow-1,touchedCol) == null) {
                    drawHighlightedSquare(g, touchedRow-1, touchedCol);
                }
                if(touchedRow > 0 && touchedCol < 7 && gameState.getPiece(touchedRow-1,touchedCol+1) != null && gameState.getPiece(touchedRow-1,touchedCol+1).isBlack() != gameState.getPiece(touchedRow,touchedCol).isBlack()) {
                    drawHighlightedSquare(g, touchedRow-1, touchedCol+1);
                }
                if(touchedRow > 0 && touchedCol > 0 && gameState.getPiece(touchedRow-1,touchedCol-1) != null && gameState.getPiece(touchedRow-1,touchedCol-1).isBlack() != gameState.getPiece(touchedRow,touchedCol).isBlack()) {
                    drawHighlightedSquare(g, touchedRow-1, touchedCol-1);
                }
            }
        }
        else if(touchedPiece instanceof King){
            //up
            if(touchedRow > 0 && (gameState.getPiece(touchedRow - 1,touchedCol) == null || gameState.getPiece(touchedRow - 1,touchedCol).isBlack() != touchedPiece.isBlack())) {
                drawHighlightedSquare(g, touchedRow-1, touchedCol);
            }
            //down
            if(touchedRow < 7 && (gameState.getPiece(touchedRow + 1,touchedCol) == null || gameState.getPiece(touchedRow + 1,touchedCol).isBlack() != touchedPiece.isBlack())) {
                drawHighlightedSquare(g, touchedRow+1, touchedCol);
            }
            //right
            if(touchedCol < 7 && (gameState.getPiece(touchedRow ,touchedCol + 1) == null || gameState.getPiece(touchedRow,touchedCol + 1).isBlack() != touchedPiece.isBlack())) {
                drawHighlightedSquare(g, touchedRow, touchedCol+1);
            }
            //left
            if(touchedCol > 0 && (gameState.getPiece(touchedRow ,touchedCol - 1) == null || gameState.getPiece(touchedRow,touchedCol - 1).isBlack() != touchedPiece.isBlack())) {
                drawHighlightedSquare(g, touchedRow, touchedCol-1);
            }
            //up right
            if(touchedRow > 0 && touchedCol < 7 && (gameState.getPiece(touchedRow - 1,touchedCol + 1) == null || gameState.getPiece(touchedRow - 1,touchedCol + 1).isBlack() != touchedPiece.isBlack())) {
                drawHighlightedSquare(g, touchedRow-1, touchedCol+1);
            }
            //up left
            if(touchedRow > 0 && touchedCol > 0 && (gameState.getPiece(touchedRow - 1,touchedCol - 1) == null || gameState.getPiece(touchedRow - 1,touchedCol - 1).isBlack() != touchedPiece.isBlack())) {
                drawHighlightedSquare(g, touchedRow-1, touchedCol-1);
            }
            //down right
            if(touchedRow < 7 && touchedCol< 7 && (gameState.getPiece(touchedRow + 1,touchedCol + 1) == null || gameState.getPiece(touchedRow + 1,touchedCol + 1).isBlack() != touchedPiece.isBlack())) {
                drawHighlightedSquare(g, touchedRow+1, touchedCol+1);
            }
            //down left
            if(touchedRow < 7 && touchedCol > 0 && (gameState.getPiece(touchedRow + 1,touchedCol - 1) == null || gameState.getPiece(touchedRow + 1,touchedCol - 1).isBlack() != touchedPiece.isBlack())) {
                drawHighlightedSquare(g, touchedRow+1, touchedCol-1);
            }

        }
        else if(touchedPiece instanceof Queen){
            //down right
            for(int i = 1; touchedRow+i < 8 && touchedCol + i <8; i++){
                if(gameState.getPiece(touchedRow + i,touchedCol + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow + i,touchedCol + i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow+i, touchedCol+i);
                }
            }
            //down left
            for(int i = 1; touchedRow+i < 8 && touchedCol - i > -1; i++){
                if(gameState.getPiece(touchedRow + i,touchedCol - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow + i,touchedCol - i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow+i, touchedCol-i);
                }
            }
            //up left
            for(int i = 1; touchedRow-i > -1 && touchedCol - i > -1; i++){
                if(gameState.getPiece(touchedRow - i,touchedCol - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow - i,touchedCol - i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow-i, touchedCol-i);
                }
            }
            //up right
            for(int i = 1; touchedRow-i > -1 && touchedCol + i < 8; i++){
                if(gameState.getPiece(touchedRow - i,touchedCol + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow - i,touchedCol + i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow-i, touchedCol+i);
                }

            }

            for(int i = touchedRow + 1; i <= 7; i++){
                if(gameState.getPiece(i,touchedCol) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,touchedCol).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, i, touchedCol);
                }
            }

            for(int i = touchedRow - 1; i >= 0; i--){
                if(gameState.getPiece(i,touchedCol) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,touchedCol).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, i, touchedCol);
                }
            }

            for(int i = touchedCol + 1; i <= 7; i++){
                if(gameState.getPiece(touchedRow,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow,i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow, i);
                }
            }

            for(int i = touchedCol - 1; i >= 0; i--){
                if(gameState.getPiece(touchedRow,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow,i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow, i);
                }
            }

        }
        else if(touchedPiece instanceof Rook){

            for(int i = touchedRow + 1; i <= 7; i++){
                if(gameState.getPiece(i,touchedCol) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,touchedCol).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, i, touchedCol);
                }
            }

            for(int i = touchedRow - 1; i >= 0; i--){
                if(gameState.getPiece(i,touchedCol) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(i,touchedCol).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, i, touchedCol);
                }
            }

            for(int i = touchedCol + 1; i <= 7; i++){
                if(gameState.getPiece(touchedRow,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow,i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow, i);
                }
            }

            for(int i = touchedCol - 1; i >= 0; i--){
                if(gameState.getPiece(touchedRow,i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow,i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow, i);
                }
            }
        }
        else if(touchedPiece instanceof Bishop){
            //down right
            for(int i = 1; touchedRow+i < 8 && touchedCol + i <8; i++){
                if(gameState.getPiece(touchedRow + i,touchedCol + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow + i,touchedCol + i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow+i, touchedCol+i);
                }
            }
            //down left
            for(int i = 1; touchedRow+i < 8 && touchedCol - i > -1; i++){
                if(gameState.getPiece(touchedRow + i,touchedCol - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow + i,touchedCol - i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow+i, touchedCol-i);
                }
            }
            //up left
            for(int i = 1; touchedRow-i > -1 && touchedCol - i > -1; i++){
                if(gameState.getPiece(touchedRow - i,touchedCol - i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow - i,touchedCol - i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow-i, touchedCol-i);
                }
            }
            //up right
            for(int i = 1; touchedRow-i > -1 && touchedCol + i < 8; i++){
                if(gameState.getPiece(touchedRow - i,touchedCol + i) != null){
                    //there is a piece there so we want to break if it is an ally piece because we can't move there anymore
                    if(gameState.getPiece(touchedRow - i,touchedCol + i).isBlack() == touchedPiece.isBlack()) break;
                }
                else {
                    drawHighlightedSquare(g, touchedRow-i, touchedCol+i);
                }
            }
        }
        else if(touchedPiece instanceof Knight){
            //left up
            if(touchedRow > 0 && touchedCol > 1 && (gameState.getPiece(touchedRow - 1,touchedCol - 2) == null || gameState.getPiece(touchedRow - 1,touchedCol - 2).isBlack() != touchedPiece.isBlack())) {
                drawHighlightedSquare(g, touchedRow-1, touchedCol+2);
            }
            //left down
            if(touchedRow < 7 && touchedCol > 1 && (gameState.getPiece(touchedRow + 1,touchedCol - 2) == null || gameState.getPiece(touchedRow + 1,touchedCol - 2).isBlack() != touchedPiece.isBlack())) {
                drawHighlightedSquare(g, touchedRow+1, touchedCol-2);
            }
            //right up
            if(touchedRow < 0 && touchedCol < 6 && (gameState.getPiece(touchedRow - 1,touchedCol + 2) == null || gameState.getPiece(touchedRow - 1,touchedCol + 2).isBlack() != touchedPiece.isBlack())){
                drawHighlightedSquare(g, touchedRow-1, touchedCol+2);
            }
            //right down
            if(touchedRow > 7 && touchedCol < 6 && (gameState.getPiece(touchedRow + 1,touchedCol + 2) == null || gameState.getPiece(touchedRow + 1,touchedCol + 2).isBlack() != touchedPiece.isBlack())){
                drawHighlightedSquare(g, touchedRow+1, touchedCol+2);
            }
            //up right
            if(touchedRow > 1 && touchedCol < 7 && (gameState.getPiece(touchedRow - 2,touchedCol + 1) == null || gameState.getPiece(touchedRow - 2,touchedCol + 1).isBlack() != touchedPiece.isBlack())){
                drawHighlightedSquare(g, touchedRow-2, touchedCol+1);
            }
            //up left
            if(touchedRow > 1 && touchedCol > 0 && (gameState.getPiece(touchedRow - 2,touchedCol - 1) == null || gameState.getPiece(touchedRow - 2,touchedCol - 1).isBlack() != touchedPiece.isBlack())){
                drawHighlightedSquare(g, touchedRow-2, touchedCol-1);
            }
            //down right
            if(touchedRow < 6 && touchedCol < 7 && (gameState.getPiece(touchedRow + 2,touchedCol + 1) == null || gameState.getPiece(touchedRow + 2,touchedCol + 1).isBlack() != touchedPiece.isBlack())){
                drawHighlightedSquare(g, touchedRow+2, touchedCol+1);
            }
            //down left
            if(touchedRow < 6 && touchedCol > 0 && (gameState.getPiece(touchedRow + 2,touchedCol - 1) == null || gameState.getPiece(touchedRow + 2,touchedCol - 1).isBlack() != touchedPiece.isBlack())){
                drawHighlightedSquare(g, touchedRow+2, touchedCol-1);
            }
        }
    }

    public void drawHighlightedSquare(Canvas g, int row, int col){
        //setting up the transparent color
        Paint transparentPaint = new Paint();
        transparentPaint.setColor(Color.YELLOW);
        transparentPaint.setAlpha(75);

        g.drawRect((dimen/8)*col + col/2, (dimen/8)*row + row/2,
                (dimen/8)*col + (dimen/8), (dimen/8)*row + (dimen/8), transparentPaint );

        /*
        //drawing the circle
        Paint black = new Paint();
        black.setColor(Color.BLACK);

        int radius = (dimen/8)/4;
        g.drawCircle(((dimen/8)*col)+(dimen/8)/2, ((dimen/8)*row)+(dimen/8)/2, radius, black);
        */
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
