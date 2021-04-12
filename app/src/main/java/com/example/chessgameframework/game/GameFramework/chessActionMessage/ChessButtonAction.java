package com.example.chessgameframework.game.GameFramework.chessActionMessage;

import com.example.chessgameframework.game.GameFramework.actionMessage.GameAction;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;

public class ChessButtonAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ChessButtonAction(GamePlayer player) {
        super(player);
    }
}
