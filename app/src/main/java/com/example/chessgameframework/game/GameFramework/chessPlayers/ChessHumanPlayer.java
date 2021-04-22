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
import com.example.chessgameframework.game.GameFramework.chessActionMessage.ChessMoveAction;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameInfo;
import com.example.chessgameframework.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.chessgameframework.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgameframework.game.GameFramework.players.GameHumanPlayer;
import com.example.chessgameframework.game.GameFramework.utilities.Logger;


public class ChessHumanPlayer extends GameHumanPlayer implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "ChessHumanPlayer";
    private int layoutId;
    // These variables will reference widgets that will be modified during play
    private TextView playerNameTextView = null;
    private TextView opposingNameTextView = null;
    private Button quitButton = null;
    private Button forfeitButton = null;
    private Button offerDrawButton = null;
    private Button pauseButton = null;
    private Button undoButton = null;
    private ChessSurfaceView chessView = null;
    private boolean pieceSelected = false;
    private int selX = -1;
    private int selY = -1;
    private MoveBoard selectedPieceMB = null;
    private boolean doubleClick = false;

    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     *
     * @param name
     * @param layoutId
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
     * called when player gets a message
     * @param info
     *      the message
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
     * this method gets called when the user clicks the die or hold button. It
     * creates a new PigRollAction or PigHoldAction and sends it to the game.
     *
     * @param button the button that was clicked
     */
    @Override
    public void onClick(View button) {
        ChessGameState gameState = (ChessGameState) game.getGameState();

        if (button == quitButton) {
            gameState.isQuitPressed = true;
            game.sendAction(new ChessButtonAction(this));


        } else if (button == forfeitButton) {
            gameState.isForfeitPressed = true;
            game.sendAction(new ChessButtonAction(this));

        } else if (button == offerDrawButton) {
            gameState.isDrawPressed = true;
            game.sendAction(new ChessButtonAction(this));

        } else if (button == pauseButton) {
            gameState.isPaused = true;
            game.sendAction(new ChessButtonAction(this));

        } else if (button == undoButton) {
            gameState.isUndoPressed = true;
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
        if (event.getAction() == MotionEvent.ACTION_UP)
            return true;
        //check if a piece was pressed
        float x = event.getX();
        float y = event.getY();
        float boxWidth = v.getWidth() / 8;
        float boxHeight = v.getHeight() / 8;
        int ysquare = (int) (x / boxWidth);
        int xsquare = (int) (y / boxHeight);
        System.out.println("clicked on box :" + xsquare + ", " + ysquare);
        ChessGameState gameState = (ChessGameState) game.getGameState();
        if (!pieceSelected) {
            if (gameState.getPiece(xsquare, ysquare) != null) {
                if ((gameState.getPiece(xsquare, ysquare).isBlack() && playerNum == 1) || (!(gameState.getPiece(xsquare, ysquare).isBlack()) && playerNum == 0)) {
                    //we have a piece so now we want to draw all of the possible moves

                    selX = xsquare;
                    selY = ysquare;
                    selectedPieceMB = new MoveBoard();
                    selectedPieceMB.findMoves(gameState, xsquare, ysquare);

                    chessView.setPlayerTouched(selX, selY);
                    chessView.setPieceTouched(gameState.getPiece(selX, selY));
                    chessView.invalidate();

                    if (selectedPieceMB.getNumMoves() > 0) {
                        pieceSelected = true;
                        System.out.println("we successfully selected a piece that has moves");

                    }
                }
            }
        } else {
            //we clicked where we want the piece to go
            if (selectedPieceMB.getCanMove(xsquare, ysquare)) {
                System.out.println("send a move to: " + xsquare + ", " + ysquare);
                ChessMoveAction action = new ChessMoveAction(this, selX, selY, xsquare, ysquare, gameState.getPiece(selX, selY));
                game.sendAction(action);
                chessView.invalidate();
                System.out.println("we have sent a move");

            }
            pieceSelected = false;
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
        this.quitButton = (Button) activity.findViewById(R.id.quitButton);
        this.forfeitButton = (Button) activity.findViewById(R.id.forfeitButton);
        this.offerDrawButton = (Button) activity.findViewById(R.id.offerdrawButton);
        this.pauseButton = (Button) activity.findViewById(R.id.pauseButton);
        this.undoButton = (Button) activity.findViewById(R.id.undoButton);
        this.chessView = (ChessSurfaceView) activity.findViewById(R.id.chessSurfaceView);
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
