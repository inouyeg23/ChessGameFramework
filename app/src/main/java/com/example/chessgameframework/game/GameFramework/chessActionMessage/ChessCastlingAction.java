package com.example.chessgameframework.game.GameFramework.chessActionMessage;

import com.example.chessgameframework.game.GameFramework.Piece;
import com.example.chessgameframework.game.GameFramework.actionMessage.GameAction;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;

/**
 * ChessCastlingAction is the action for the special move castling
 *
 * @author Connor Morgan
 * @date: 4/30/21
 */

public class ChessCastlingAction  extends ChessMoveAction {

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ChessCastlingAction(GamePlayer player, int kingCol, int kingRow, int kingDestCol, int kingDestRow, Piece piece) {
        super(player, kingCol,kingRow, kingDestCol,kingDestRow, piece);
    }
}
