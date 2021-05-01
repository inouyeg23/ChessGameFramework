package com.example.chessgameframework.game.GameFramework.chessPlayers;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.chessgameframework.ChessGameState;
import com.example.chessgameframework.ChessSurfaceView;
import com.example.chessgameframework.R;
import com.example.chessgameframework.game.GameFramework.GameMainActivity;
import com.example.chessgameframework.game.GameFramework.Pieces.MoveBoard;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessButtonAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessCastlingAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessMoveAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessPauseAction;
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessResumeAction;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameInfo;


import com.example.chessgameframework.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.chessgameframework.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgameframework.game.GameFramework.players.GameHumanPlayer;
import com.example.chessgameframework.game.GameFramework.utilities.Logger;
;
import java.util.Random;

/**
 * ChessHumanPlayer is the human player. It sets up the GUI at the start of the game. It also handles
 * button clicks and touches by the user.
 *
 * @authors: Logan Machida, Connor Morgan, Garrett Inouye
 * @date: 4/29/21
 */

public class ChessHumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "ChessHumanPlayer";
    private int layoutId;
    // These variables will reference widgets that will be modified during play
    private TextView playerNameTextView = null;
    private TextView opposingNameTextView = null;
    private TextView playerTimeTextView = null;
    private TextView opposingTimeTextView = null;
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
            playerTimeTextView.setText(((ChessGameState) info).getPlayerTimerText());
            opposingTimeTextView.setText(((ChessGameState) info).getOpposingTimerText());
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
            if(!(gameState.getOpposingTimerRunning())) {
                if (gameState.getPlayerTimerRunning()) {
                    game.sendAction(new ChessPauseAction(this));
                    pauseButton.setText("RESUME");
                } else {
                    game.sendAction(new ChessResumeAction(this));
                    pauseButton.setText("PAUSE");
                }
            }
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
            if(gameState.getPaused()){
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
                            System.out.println("we successfully selected a piece that has moves");
                        }
                    }
                }
            }
            else {
                //we clicked where we want the piece to go
                if (selectedPieceMB.getCanMove(xsquare, ysquare)) {

                    System.out.println("send a move to: " + xsquare + ", " + ysquare);


                    if(gameState.castlingLeftBlack || gameState.castlingLeftWhite || gameState.castlingRightBlack || gameState.castlingRightWhite){
                        ChessCastlingAction chessAction = new ChessCastlingAction(this,selX,selY,xsquare,ysquare,gameState.getPiece(selX,selY));
                        game.sendAction(chessAction);
                    }
                    else{
                        ChessMoveAction action = new ChessMoveAction(this, selX, selY, xsquare, ysquare, gameState.getPiece(selX, selY));
                        game.sendAction(action);
                    }


                    System.out.println("we have sent a move");

                }
                pieceSelected = false;
                chessView.drawHighlight = false;

            }

            chessView.invalidate();
        }
        return true;
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
