package com.example.chessgameframework;

import android.view.View;


import com.example.chessgameframework.game.GameFramework.GameMainActivity;
import com.example.chessgameframework.game.GameFramework.infoMessage.GameInfo;


import com.example.chessgameframework.game.GameFramework.gameConfiguration.GamePlayerType;
import com.example.chessgameframework.game.GameFramework.players.GameHumanPlayer;
import com.example.chessgameframework.game.GameFramework.players.GamePlayer;;

public class HumanPlayer extends GamePlayerType {

    public HumanPlayer(String typeName) {
        super(typeName);
    }

    //Hello Connor
    @Override
    public GamePlayer createPlayer(String name) {
        return new GameHumanPlayer(name) {
            @Override
            public View getTopView() {
                return null;
            }
            @Override
            public void receiveInfo(GameInfo info) {
            }
            @Override
            public void setAsGui(GameMainActivity activity) {
            }
        };
    }
}
