package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;

public final class Game {
    private static Game game;
    public GameObj gm;
   // public Character character;
    public Player player;
    public BG BackGround;
    private float[] move = new float[16];
    private float[] foo = new float[16];
    private float moveX =0.0f;
    private float moveY =0.0f;
    private BoundingBox[] hitfield;//TODO

    private Game() {

        player = new Player();
      //  character = new Character();
        BackGround = new BG(1,1);
        hitfield = BoundingBox.valami(BackGround.foundnearblocks(moveX,moveY));
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
       //  character.draw(mvpMatrix);
        GLES20.glDisable(GLES20.GL_BLEND);
        MyGLRenderer.checkGLError("draw van e probléma");

    }
    public void move(float dx,float dy){
        moveX+=dx*-0.000008f;
        moveY+=dy* 0.000008f;
        Matrix.translateM(foo,0,moveX,moveY,0);
    }

    public float[] getMatrix() {
        return move;
    }
}
