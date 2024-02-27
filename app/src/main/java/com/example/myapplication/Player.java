package com.example.myapplication;

import android.opengl.GLES20;

public class Player extends  Character{
    private final int[] backward = new int[]{R.drawable.player_forward_1,R.drawable.player_forward_2,R.drawable.player_forward_3,R.drawable.player_forward_4};
    private final int[] left = new int[]{R.drawable.player_left_1,R.drawable.player_left_2,R.drawable.player_left_3,R.drawable.player_left_4};
    private final int[] forward = new int[]{R.drawable.karakter};
    private final int[] right = new int[]{R.drawable.player_right_1,R.drawable.player_right_2,R.drawable.player_right_3,R.drawable.player_right_4};

    public Player() {
        super();
        setAnimation(forward,left,backward,right);
    }

    @Override
    public float[] getMatrix() {
        return MyGLRenderer.vPMatrix;
    }

    @Override
    public String getName(){return "Playes";}
}
