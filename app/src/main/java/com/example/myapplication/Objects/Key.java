package com.example.myapplication.Objects;

import android.util.Log;

import com.example.myapplication.GUI.PlayerInformationGUI;
import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.MainClasses.SpriteSheets;
import com.example.myapplication.Player.Player;
import com.example.myapplication.SuperClasses.Specifications;
import com.example.myapplication.SuperClasses.StaticObject;

public class Key extends StaticObject {
    public Key(SpriteSheets spriteSheets) {
        super(spriteSheets);
    }

    @Override
    public void hit(Specifications specifications) {
        Log.e("Bakoskey","key is hit");
        if (specifications instanceof Player) Game.getInstance().removeObejct(this);
        Game.getGuiListener().keyCollect();
    }
}
