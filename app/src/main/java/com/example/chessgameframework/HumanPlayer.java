package com.example.chessgameframework;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.chessgameframework.game.GameFramework.GameMainActivity;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameInfo;


import com.example.chessgameframework.game.GameFramework.gameConfiguration.GamePlayerType;
import com.example.chessgameframework.game.GameFramework.players.GameHumanPlayer;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;;import org.w3c.dom.Text;

public class HumanPlayer extends GameHumanPlayer implements View.OnClickListener {

    // These variables will reference widgets that will be modified during play
    private TextView    playerNameTextView      = null;
    private TextView    opposingNameTextView    = null;
    private Button      quitButton              = null;
    private Button      forfeitButton           = null;
    private Button      offerDrawButton         = null;
    private Button      pauseButton             = null;
    private Button      undoButton              = null;

    // the android activity that we are running
    private GameMainActivity myActivity;

    public HumanPlayer(String typeName) {
        super(typeName);
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

        ImageView chessBoard = (ImageView)activity.findViewById(R.id.chessBoard);
        chessBoard.setImageResource(R.drawable.chessboard);

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


}
