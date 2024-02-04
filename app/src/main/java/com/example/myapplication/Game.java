package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;

public final class Game {
    private static Game game;
    public GameObj gm;
    public Character character;
    public BG BackGround;
    private float[] move = new float[16];
    private float[] foo = new float[16];

    private Game() {

        character = new Character(R.drawable.karakter);
        BackGround = new BG(1,1);
        Matrix.setIdentityM(move,0);
        Matrix.setIdentityM(foo,0);
     /*   gm = new GameObj();
        gm.setVertexShader();
        gm.setFragmentShader();
        gm.setProg();
        gm.setVertexBuffer();
        gm.setDrawListBuffer();*/

    }
    public static Game getInstance(){
        if (game== null){
            game = new Game();
        }
        return game;
    }
    public void draw(float[]mvpMatrix){

        Matrix.multiplyMM(move, 0, mvpMatrix, 0, foo, 0);
        BackGround.draw(move);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        character.draw(mvpMatrix);
        GLES20.glDisable(GLES20.GL_BLEND);
        MyGLRenderer.checkGLError("draw van e probléma");

    }
    public void move(float dx,float dy){
        Matrix.translateM(foo,0,dx*-0.0005f,dy*0.0005f,0);
    }
}
