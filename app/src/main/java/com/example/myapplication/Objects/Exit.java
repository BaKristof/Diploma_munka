package com.example.myapplication.Objects;

import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.MainClasses.SpriteSheets;
import com.example.myapplication.Player.Player;
import com.example.myapplication.SuperClasses.Specifications;
import com.example.myapplication.SuperClasses.StaticObject;

public class Exit extends StaticObject {
    boolean isunlocked = false;

    public Exit(SpriteSheets spriteSheets) {
        super(spriteSheets);
    }

    @Override
    public void hit(Specifications specifications) {
        if(isunlocked&& specifications instanceof Player){
            Game.getInstance().nextLevel();
            Game.getGuiListener().resetGUI();
        }
    }

    public void setIsunlocked() {
        this.isunlocked = true;
    }
}
