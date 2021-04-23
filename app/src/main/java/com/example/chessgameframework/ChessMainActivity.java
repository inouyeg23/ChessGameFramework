package com.example.chessgameframework;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.example.chessgameframework.game.GameFramework.GameMainActivity;
import com.example.chessgameframework.game.GameFramework.LocalGame;
import com.example.chessgameframework.game.GameFramework.actionMessage.GameAction;
import com.example.chessgameframework.game.GameFramework.chessPlayers.ChessComputerPlayerEasy;
import com.example.chessgameframework.game.GameFramework.chessPlayers.ChessHumanPlayer;
import com.example.chessgameframework.game.GameFramework.gameConfiguration.GameConfig;
import com.example.chessgameframework.game.GameFramework.gameConfiguration.GamePlayerType;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameState;
import com.example.chessgameframework.game.GameFramework.players.ChessComputerPlayerHard;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;
import com.example.chessgameframework.game.GameFramework.utilities.Logger;

import java.util.ArrayList;

public class ChessMainActivity extends GameMainActivity {
    /**
     *  We need to override the abstract methods from GameMainActivity.
     *  I can see what that looks like in the TTTMainActivity
     */

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
            public GamePlayer createPlayer(String name) {
                return new ChessHumanPlayer(name, R.id.chessSurfaceView);
            }});
        playerTypes.add(new GamePlayerType("Easy Computer") {
            public GamePlayer createPlayer(String name) { return new ChessComputerPlayerEasy(name); }});
        playerTypes.add(new GamePlayerType("Hard Computer") {
            public GamePlayer createPlayer(String name) {
                return new ChessComputerPlayerHard(name);
            }});
        //create a game configuration class for chess
        GameConfig defaultConfig = new GameConfig(playerTypes,1,2,"chess", 8080);
        defaultConfig.addPlayer("Human Player", 0); //player 1: a human player
        defaultConfig.addPlayer("Trash Computer", 1); //player 2: a human player

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