package com.example.myapplication.Enemys;

import android.opengl.Matrix;
import android.util.Log;

import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.SuperClasses.EnemyCharacter;
import com.example.myapplication.R;

public class FlyingFire extends EnemyCharacter {

    private float x = 0, y = 0;
    private float velocityX = 0;
    private float velocityY = 0;
    private static final float friction = 0.97f;

    public FlyingFire(float[] startingmatrix) {
        super(startingmatrix);
        setSpriteSheets(R.drawable.no_1_enemy_spritesheet, 64, 64);
    }

    @Override
    public void move() {
        float distance = this.distance(Game.getInstance().getPlayer());
        float player_moving_angle;
        //todo oevrcompensate the trjectorx by the player movment in where itt will be caculated by elapse time and th eplayer mov  ment this way it wont be a circleing around the object
        float[] dxdy = this.dxdy(Game.getInstance().getPlayer());
        velocityX += dxdy[0] * 0.0001f;
        velocityY += dxdy[1] * 0.0001f;
        velocityX *= friction;
        velocityY *= friction;
        x += velocityX;
        y += velocityY;
        Log.e("percent ", " x: " + dxdy[0] + " y:" + dxdy[1]);
        Matrix.translateM(ownPositionM, 0, x, y, 0);
    }
}