package com.example.chessgameframework.game.GameFramework.chessPlayers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.nfc.Tag;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.ChessSurfaceView;
import com.example.chessgameframework.R;
import com.example.chessgameframework.game.GameFramework.GameMainActivity;
import com.example.chessgameframework.game.GameFramework.gameConfiguration.GameConfig;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameInfo;


import com.example.chessgameframework.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.chessgameframework.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgameframework.game.GameFramework.players.GameComputerPlayer;
import com.example.chessgameframework.game.GameFramework.players.GameHumanPlayer;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;
import com.example.chessgameframework.game.GameFramework.utilities.Logger;
;

public class ChessHumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener {

    private static final String TAG = "ChessHumanPlayer";

    // These variables will reference widgets that will be modified during play
    private TextView    playerNameTextView      = null;
    private TextView    opposingNameTextView    = null;
    private Button      quitButton              = null;
    private Button      forfeitButton           = null;
    private Button      offerDrawButton         = null;
    private Button      pauseButton             = null;
    private Button      undoButton              = null;
    private ChessSurfaceView chessView          = null;
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes

    // the android activity that we are running
    private GameMainActivity myActivity;

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
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
        if (chessView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            flash(Color.RED, 50);
        }
        else if (!(info instanceof ChessGameState))
            // if we do not have a ChessGameState, ignore
            return;

        else {
            chessView.setState((ChessGameState)info);
            chessView.invalidate();
            Logger.log(TAG, "receiving");
        }

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

        //Initialize the widget reference member variables
        this.playerNameTextView = (TextView)activity.findViewById(R.id.playerName);
        this.opposingNameTextView = (TextView)activity.findViewById(R.id.opposingName);
        this.quitButton = (Button)activity.findViewById(R.id.quitButton);
        this.forfeitButton = (Button)activity.findViewById(R.id.forfeitButton);
        this.offerDrawButton = (Button)activity.findViewById(R.id.offerdrawButton);
        this.pauseButton = (Button)activity.findViewById(R.id.pauseButton);
        this.undoButton = (Button)activity.findViewById(R.id.undoButton);
        this.chessView = (ChessSurfaceView)activity.findViewById(R.id.chessSurfaceView);

        //Listen for button presses
        quitButton.setOnClickListener(this);
        forfeitButton.setOnClickListener(this);
        offerDrawButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        undoButton.setOnClickListener(this);

        //Listen for touch presses
        chessView.setOnTouchListener(this);

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
