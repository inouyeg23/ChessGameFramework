package com.example.chessgameframework.game.GameFramework.chessActionMessage;

import com.example.chessgameframework.game.GameFramework.actionMessage.GameAction;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;

/**
 * ChessResumeAction is an action for when the user clicks the pause button
 *
 * @author Garrett Inouye
 * @date: 4/27/21
 */

public class ChessResumeAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ChessResumeAction(GamePlayer player) {
        super(player);
    }
}
