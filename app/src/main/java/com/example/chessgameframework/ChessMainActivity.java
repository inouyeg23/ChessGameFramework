package com.example.chessgameframework;

import com.example.chessgameframework.game.GameFramework.GameMainActivity;
import com.example.chessgameframework.game.GameFramework.LocalGame;
import com.example.chessgameframework.game.GameFramework.actionMessage.GameAction;
import com.example.chessgameframework.game.GameFramework.gameConfiguration.GameConfig;
import com.example.chessgameframework.game.GameFramework.gameConfiguration.GamePlayerType;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameState;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;

import java.util.ArrayList;

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
        ArrayList<GamePlayerType> al = new ArrayList<GamePlayerType>();
        al.add(new HumanPlayer("Human"));
        return new GameConfig(al,1,1,"chess", 8080);
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
        return new LocalGame() {
            @Override
            protected void sendUpdatedStateTo(GamePlayer p) {

            }

            @Override
            protected boolean canMove(int playerIdx) {
                return false;
            }

            @Override
            protected String checkIfGameOver() {
                return null;
            }

            @Override
            protected boolean makeMove(GameAction action) {
                return false;
            }
        };
    }


}