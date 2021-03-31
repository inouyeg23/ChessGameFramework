package com.example.chessgameframework.game.GameFramework.chessActionMessage;

import com.example.chessgameframework.game.GameFramework.actionMessage.GameAction;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;

public class ChessMoveAction extends GameAction {

    private int row;
    private int col;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ChessMoveAction(GamePlayer player) {
        super(player);
        // set the row and column as passed to us
        this.row = Math.max(0, Math.min(7, row));
        this.col = Math.max(0, Math.min(7, col));
    }
    /**
     * get the object's row
     *
     * @return the row selected
     */
    public int getRow() { return row; }

    /**
     * get the object's column
     *
     * @return the column selected
     */
    public int getCol() { return col; }

}
