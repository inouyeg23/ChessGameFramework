package com.example.chessgameframework.game.GameFramework.chessPlayers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.R;
import com.example.chessgameframework.game.GameFramework.GameMainActivity;
import com.example.chessgameframework.game.GameFramework.gameConfiguration.GameConfig;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameInfo;


import com.example.chessgameframework.game.GameFramework.players.GameComputerPlayer;
import com.example.chessgameframework.game.GameFramework.players.GameHumanPlayer;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;
;

public class ChessHumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener {

    // These variables will reference widgets that will be modified during play
    private TextView    playerNameTextView      = null;
    private TextView    opposingNameTextView    = null;
    private Button      quitButton              = null;
    private Button      forfeitButton           = null;
    private Button      offerDrawButton         = null;
    private Button      pauseButton             = null;
    private Button      undoButton              = null;

    // Variables dealing with pieces
    private ImageButton blackPawn = null;

    // the android activity that we are running
    private GameMainActivity myActivity;

    public ChessHumanPlayer(String name) {
        super(name);


    }
    /**
     * Returns the GUI's top view object
     *
     * @return
     * 		the top object in the GUI's view heirarchy
     */

    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }
    
    public void receiveInfo(GameInfo info) {
        MyRunnable updateName = new MyRunnable(info, true);
        new Thread(updateName).start();

    }

    /**
     * this method gets called when the user clicks the die or hold button. It
     * creates a new PigRollAction or PigHoldAction and sends it to the game.
     *
     * @param button
     * 		the button that was clicked
     */
    @Override
    public void onClick(View button) {
        ChessGameState newChessGameState = new ChessGameState();
        if(button == quitButton){
            newChessGameState.isQuitPressed = true;
        }
        if(button == forfeitButton){
            newChessGameState.isForfeitPressed = true;
        }
        if(button == offerDrawButton){
            newChessGameState.isDrawPressed = true;
        }
        if(button == pauseButton){
            newChessGameState.isPaused = true;
        }
        if(button == undoButton){
            newChessGameState.isUndoPressed = true;
        }
    }

    /**
     * callback method when the screen it touched. We're
     * looking for a screen touch
     *
     * @param event
     * 		the motion event that was detected
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {
        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.chess_game_layout);

        //sets up the start screen image view
        ImageView backgroundScreen = (ImageView)activity.findViewById(R.id.backgroundScreen);
        backgroundScreen.setImageResource(R.drawable.chessstartscreen);

        //ImageView chessBoard = (ImageView)activity.findViewById(R.id.chessBoard);
        //chessBoard.setImageResource(R.drawable.chessemptyboard);

        //Initialize the widget reference member variables
        this.playerNameTextView = (TextView)activity.findViewById(R.id.playerName);
        this.opposingNameTextView = (TextView)activity.findViewById(R.id.opposingName);
        this.quitButton = (Button)activity.findViewById(R.id.quitButton);
        this.forfeitButton = (Button)activity.findViewById(R.id.forfeitButton);
        this.offerDrawButton = (Button)activity.findViewById(R.id.offerdrawButton);
        this.pauseButton = (Button)activity.findViewById(R.id.pauseButton);
        this.undoButton = (Button)activity.findViewById(R.id.undoButton);

        //Listen for button presses
        quitButton.setOnClickListener(this);
        forfeitButton.setOnClickListener(this);
        offerDrawButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        undoButton.setOnClickListener(this);

    }

    /**
     * perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    protected void initAfterReady() {
        playerNameTextView.setText(allPlayerNames[0]);
        opposingNameTextView.setText(allPlayerNames[1]);
    }


}
