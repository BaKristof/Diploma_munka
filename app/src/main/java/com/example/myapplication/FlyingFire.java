package com.example.myapplication;

public class FlyingFire extends EnemyCharacter {
    public FlyingFire(float[] startingmatrix) {
        super(startingmatrix);
        setSpriteSheets(R.drawable.no_1_enemy_spritesheet, 64, 64);
    }
}
