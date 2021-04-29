package com.example.chessgameframework.game.GameFramework.chessPlayers;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.ChessSurfaceView;
import com.example.chessgameframework.R;
import com.example.chessgameframework.game.GameFramework.GameMainActivity;
import com.example.chessgameframework.game.GameFramework.Pieces.MoveBoard;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessButtonAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessMoveAction;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameInfo;


import com.example.chessgameframework.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.chessgameframework.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgameframework.game.GameFramework.players.GameHumanPlayer;
import com.example.chessgameframework.game.GameFramework.utilities.Logger;
;import java.util.Locale;
import java.util.Random;

public class ChessHumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "ChessHumanPlayer";
    private int layoutId;
    // These variables will reference widgets that will be modified during play
    private TextView playerNameTextView = null;
    private TextView opposingNameTextView = null;
    private Button quitButton = null;
    private Button forfeitButton = null;
    private Button offerDrawButton = null;
    private Button pauseButton = null;
    private ChessSurfaceView chessView = null;
    private boolean pieceSelected = false;
    private int selX = -1;
    private int selY = -1;
    private MoveBoard selectedPieceMB = null;
    private boolean doubleClick = false;
    private int random;
    // variables needed for time
    private TextView playerTimeTextView = null;
    private TextView opposingTimeTextView = null;
    private CountDownTimer playerCountDownTimer;
    private CountDownTimer opposingCountDownTimer;
    private long playerTimeLeftInMillis = 600000;
    private long opposingTimeLeftInMillis = 600000;
    private boolean playerTimerRunning;
    private boolean opposingTimerRunning;
    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * Constructor
     */
    public ChessHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    /**
     * Returns the GUI's top view object
     *
     * @return the top object in the GUI's view heirarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * info method
     * @param info
     */
    public void receiveInfo(GameInfo info) {
        if (chessView == null) return;
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            flash(Color.RED, 50);
        } else if (!(info instanceof ChessGameState))
            // if we do not have a ChessGameState, ignore
            return;
        else {
            chessView.setState((ChessGameState) info);
            chessView.invalidate();
            Logger.log(TAG, "receiving");
        }
    }

    /**
     * this method gets called when the user clicks any button. It
     * creates a new ChessButtonAction and sends it to the game.
     *
     * @param button the button that was clicked
     */
    @Override
    public void onClick(View button) {
        ChessGameState gameState = (ChessGameState) game.getGameState();
        if (button == quitButton) {
            // the quit button has been pressed
            gameState.isQuitPressed = true;
            game.sendAction(new ChessButtonAction(this));
        }
        else if (button == forfeitButton) {
            // the forfeit button has been pressed
            gameState.isForfeitPressed = true;
            game.sendAction(new ChessButtonAction(this));
        }
        else if (button == offerDrawButton) {
            // 50/50 chance to either accept the draw or decline it
            random = new Random().nextInt(2);
            if (random == 0){
                //using gameIsOver to display a message
                gameIsOver(allPlayerNames[1] + " has declined your draw offer.");
            } else {
                gameState.isDrawPressed = true;
            }
            game.sendAction(new ChessButtonAction(this));
        }
        else if (button == pauseButton) {
            // the pause button has been pressed
            gameState.isPaused = true;
            if(playerTimerRunning) {
                playerPauseTimer();
                pauseButton.setText("RESUME");
            }
            else {
                playerStartTimer();
                pauseButton.setText("PAUSE");
            }
            game.sendAction(new ChessButtonAction(this));
        }
    }

    /**
     * callback method when the screen it touched. We're
     * looking for a screen touch
     *
     * @param event the motion event that was detected
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            float boxWidth = chessView.getScaledDim() / 8;
            float boxHeight = chessView.getScaledDim() / 8;
            int xsquare = (int) ((y - chessView.getScaledRow()) / boxHeight);
            int ysquare = (int) ((x - chessView.getScaledCol()) / boxWidth);
            System.out.println("clicked on box :" + xsquare + ", " + ysquare);
            ChessGameState gameState = (ChessGameState) game.getGameState();
            // check if the player ever clicks off the board but still on the
            // surface view
            if (xsquare < 0 || xsquare > 7 || ysquare < 0 || ysquare > 7) {
                return false;
            }
            //cannot make move if timer is paused
            if(opposingTimerRunning){
                flash(Color.RED, 50);
                return false;
            }
            if (!pieceSelected) {
                if (gameState.getPiece(xsquare, ysquare) != null) {
                    if ((gameState.getPiece(xsquare, ysquare).isBlack() && playerNum == 1) || (!(gameState.getPiece(xsquare, ysquare).isBlack()) && playerNum == 0)) {
                        //we have a piece so now we want to draw all of the possible moves
                        selX = xsquare;
                        selY = ysquare;
                        selectedPieceMB = new MoveBoard();
                        selectedPieceMB.findMoves(gameState, xsquare, ysquare);

                        if (selectedPieceMB.getNumMoves() > 0) {
                            pieceSelected = true;
                            chessView.drawHighlight = true;
                            chessView.setPlayerTouched(selX, selY);
                            chessView.setPieceTouched(gameState.getPiece(selX, selY));
                            //System.out.println("we successfully selected a piece that has moves");
                        }
                    }
                }
            }
            else {
                //we clicked where we want the piece to go
                if (selectedPieceMB.getCanMove(xsquare, ysquare)) {
                    //System.out.println("send a move to: " + xsquare + ", " + ysquare);
                    ChessMoveAction action = new ChessMoveAction(this, selX, selY, xsquare, ysquare, gameState.getPiece(selX, selY));
                    game.sendAction(action);
                    //System.out.println("we have sent a move");
                    playerPauseTimer();
                    opposingStartTimer();
                }
                pieceSelected = false;
                chessView.drawHighlight = false;

                playerPauseTimer();
                opposingStartTimer();
            }
            chessView.invalidate();
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ChessGameState gameState = (ChessGameState) game.getGameState();
        //sets the hairstyle based on the drop down menu
        gameState.setStartingColor(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //necessary to run code
    }

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {
        // remember the activity
        myActivity = activity;
        // Load the layout resource for our GUI
        activity.setContentView(R.layout.chess_game_layout);
        //sets up the start screen image view
        ImageView backgroundScreen = (ImageView) activity.findViewById(R.id.backgroundScreen);
        backgroundScreen.setImageResource(R.drawable.chessstartscreen);
        //Initialize the widget reference member variables
        this.playerNameTextView = (TextView) activity.findViewById(R.id.playerName);
        this.opposingNameTextView = (TextView) activity.findViewById(R.id.opposingName);
        this.quitButton = (Button) activity.findViewById(R.id.restartButton);
        this.forfeitButton = (Button) activity.findViewById(R.id.forfeitButton);
        this.offerDrawButton = (Button) activity.findViewById(R.id.offerdrawButton);
        this.pauseButton = (Button) activity.findViewById(R.id.pauseButton);
        this.chessView = (ChessSurfaceView) activity.findViewById(R.id.chessSurfaceView);
        //Listen for button presses
        quitButton.setOnClickListener(this);
        forfeitButton.setOnClickListener(this);
        offerDrawButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        // set textViews for the timer
        this.playerTimeTextView = (TextView) activity.findViewById(R.id.playerTime);
        this.opposingTimeTextView = (TextView) activity.findViewById(R.id.opposingTime);
        //sets the surfaceView background color to be a specific shade of green to match
        //the background
        chessView.setBackgroundColor(Color.rgb(78, 110, 87));
        //Listen for touch presses
        chessView.setOnTouchListener(this);
        playerStartTimer();
        playerUpdateCountDownText();
        opposingUpdateCountDownText();
    }

    /**
     * perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    protected void initAfterReady() {
        playerNameTextView.setText(allPlayerNames[0]);
        opposingNameTextView.setText(allPlayerNames[1]);
    }

    /**
     * The following methods are taken from an external citation
     * Date:    4 April 2021
     * Problem: Did not know how to make a timer
     * Resource: https://www.youtube.com/watch?v=MDuGwI6P-X8
     * Solution: followed along the video and made minor adjustments to fit our
     * game
     */
    //timer specifically for the player
    private void playerStartTimer() {
        playerCountDownTimer = new CountDownTimer(playerTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished){
                playerTimeLeftInMillis = millisUntilFinished;
                playerUpdateCountDownText();
            }

            @Override
            public void onFinish(){
                playerTimerRunning = false;
                System.out.println("timer done");
            }
        }.start();
        playerTimerRunning = true;
    }
    private void playerPauseTimer(){
        playerCountDownTimer.cancel();
        playerTimerRunning = false;
    }

    private void playerUpdateCountDownText() {
        int minutes = (int) playerTimeLeftInMillis / 1000 / 60;
        int seconds = (int) playerTimeLeftInMillis / 1000 % 60;
        String playerTimeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        playerTimeTextView.setText(playerTimeLeft);
    }

    //timer specifically for the computer or opposing player
    private void opposingStartTimer() {
        opposingCountDownTimer = new CountDownTimer(opposingTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished){
                opposingTimeLeftInMillis = millisUntilFinished;
                opposingUpdateCountDownText();
            }
            @Override
            public void onFinish(){
                opposingTimerRunning = false;
                System.out.println("timer done");
            }
        }.start();
        opposingTimerRunning = true;
    }

    private void opposingPauseTimer(){
        opposingCountDownTimer.cancel();
        opposingTimerRunning = false;
    }

    private void opposingUpdateCountDownText() {
        int minutes = (int) opposingTimeLeftInMillis / 1000 / 60;
        int seconds = (int) opposingTimeLeftInMillis / 1000 % 60;
        String opposingTimeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        opposingTimeTextView.setText(opposingTimeLeft);
    }
}
