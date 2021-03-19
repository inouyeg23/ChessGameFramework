package com.example.chessgameframework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chessgameframework.game.GameFramework.GameMainActivity;
import com.example.chessgameframework.game.GameFramework.LocalGame;
import com.example.chessgameframework.game.GameFramework.gameConfiguration.GameConfig;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameState;

public class ChessMainActivity extends GameMainActivity {
    /**
     *  We need to override the abstract methods from GameMainActivity.
     *  I can see what that looks like in th eTTTMainActivity
     */

    /**
     * Sets up a default of one human and one computer player
     * @return
     *      gameplayer object
     */
    @Override
    public GameConfig createDefaultConfig() {
        return null;
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
     */
    @Override
    public LocalGame createLocalGame(GameState gameState) {
        if(gameState == null) return new ChessLocalGame();
        return new ChessLocalGame((ChessGameState) gameState);
    }


}