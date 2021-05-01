package com.example.chessgameframework;

import android.content.pm.ActivityInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.chessgameframework.game.GameFramework.GameMainActivity;
import com.example.chessgameframework.game.GameFramework.LocalGame;
import com.example.chessgameframework.game.GameFramework.chessPlayers.ChessComputerPlayerEasy;
import com.example.chessgameframework.game.GameFramework.chessPlayers.ChessHumanPlayer;
import com.example.chessgameframework.game.GameFramework.gameConfiguration.GameConfig;
import com.example.chessgameframework.game.GameFramework.gameConfiguration.GamePlayerType;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameState;
import com.example.chessgameframework.game.GameFramework.chessPlayers.ChessComputerPlayerHard;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;

import java.util.ArrayList;

/**
 *  ChessMainActivity works with GameMainActivity to update who's playing the game and to set up
 *  the default config.  It also creates a new local game when called.
 *
 * @authors: Logan Machida, Garrett Inouye
 */

public class ChessMainActivity extends GameMainActivity {

    /**
     * Sets up a default of one human and one computer player
     * @return
     *      gameplayer object
     */
    @Override
    public GameConfig createDefaultConfig() {
        //locks the screen to be portrait all the time
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Define allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        //Adds the allowed player types
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) { return new ChessHumanPlayer(name, R.id.chessSurfaceView); }});
        playerTypes.add(new GamePlayerType("Easy Computer") {
            public GamePlayer createPlayer(String name) { return new ChessComputerPlayerEasy(name); }});
        playerTypes.add(new GamePlayerType("Hard Computer") {
            public GamePlayer createPlayer(String name) { return new ChessComputerPlayerHard(name); }});

        //create a game configuration class for chess
        GameConfig defaultConfig = new GameConfig(playerTypes,1,2,"chess", 8080);
        defaultConfig.addPlayer("Human Player", 0); //player 1: a human player
        defaultConfig.addPlayer("Computer", 1); //player 2: a human player

        //returns the default configuration
        return defaultConfig;

    }
    /**
     * createLocalGame
     *
     * Creates a new game that runs on the server tablet,
     * @param gameState
     * 				the gameState for this game or null for a new game
     *
     * @return a new, game-specific instance of a sub-class of the LocalGame
     *         class.
     **/

     @Override
     public LocalGame createLocalGame(GameState gameState) {
        if(gameState == null) return new ChessLocalGame();
        return new ChessLocalGame((ChessGameState) gameState);
     }

}