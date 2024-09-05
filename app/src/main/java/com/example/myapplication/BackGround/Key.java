package com.example.myapplication.BackGround;

import com.example.myapplication.GUI.PlayerInformationGUI;
import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.Player.Player;
import com.example.myapplication.SuperClasses.Specifications;
import com.example.myapplication.SuperClasses.StaticObject;

public class Key extends StaticObject {
    @Override
    public void hit(Specifications specifications) {
        if (specifications instanceof Player) Game.getInstance().removeStaticObject(this);
        PlayerInformationGUI.setKeycount();
    }
}
