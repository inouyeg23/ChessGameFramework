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

public class ChessSurfaceView extends SurfaceView implements View.OnTouchListener{
    // Initializing each piece
    private Bitmap blackPawn = null;
    private Bitmap blackKnight = null;
    private Bitmap blackRook = null;
    private Bitmap blackBishop = null;
    private Bitmap blackKing = null;
    private Bitmap blackQueen = null;
    private Bitmap whitePawn = null;
    private Bitmap whiteKnight = null;
    private Bitmap whiteRook = null;
    private Bitmap whiteBishop = null;
    private Bitmap whiteKing = null;
    private Bitmap whiteQueen = null;

    // the game's state
    protected ChessGameState gameState;

    public ChessSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);
    }

    protected void onDraw(Canvas canvas){
        // set background color
        Paint background = new Paint();
        background.setColor(Color.WHITE);

        //add images corresponding to drawables
        this.blackPawn =BitmapFactory.decodeResource(getResources(), R.drawable.black_pawn);
        this.blackKnight =BitmapFactory.decodeResource(getResources(), R.drawable.black_knight);
        this.blackRook =BitmapFactory.decodeResource(getResources(), R.drawable.black_rook);
        this.blackBishop =BitmapFactory.decodeResource(getResources(), R.drawable.black_bishop);
        this.blackKing =BitmapFactory.decodeResource(getResources(), R.drawable.black_knight);
        this.blackQueen =BitmapFactory.decodeResource(getResources(), R.drawable.black_queen);
        this.whitePawn =BitmapFactory.decodeResource(getResources(), R.drawable.white_pawn);
        this.whiteKnight =BitmapFactory.decodeResource(getResources(), R.drawable.white_knight);
        this.whiteRook =BitmapFactory.decodeResource(getResources(), R.drawable.white_rook);
        this.whiteBishop =BitmapFactory.decodeResource(getResources(), R.drawable.white_bishop);
        this.whiteKing =BitmapFactory.decodeResource(getResources(), R.drawable.white_knight);
        this.whiteQueen =BitmapFactory.decodeResource(getResources(), R.drawable.white_queen);

        canvas.drawBitmap(blackPawn, 0.0f, 0.0f, background);



    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public void setState(ChessGameState state) {
        this.gameState = state;
    }
}
