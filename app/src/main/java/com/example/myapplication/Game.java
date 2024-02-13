package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public final class Game {
    private static Game game;
    public GameObj gm;
    public Character character;
    public Player player;
    public BG BackGround;
    private float[] move = new float[16];
    private float[] foo = new float[16];
    private float moveX =0.0f;
    private float moveY =0.0f;

    private BoundingBox[] hitfield;//TODO
    private int irany=1;

    private Game() {

        player = new Player();
        character = new Character();
        BackGround = new BG(10,10);
     //   hitfield = BoundingBox.valami(BackGround.foundnearblocks(moveX,moveY));
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
       // character.draw(mvpMatrix);
        player.draw(mvpMatrix,irany);
        //MyGLRenderer.checkGLError("draw van e probléma");
        GLES20.glDisable(GLES20.GL_BLEND);

    }
    public void move(float dx,float dy,int irany){
        this.irany = irany;
        Matrix.translateM(foo,0,(dx* -0.004f),(dy* -0.004f),0);

    }

    public float[] getMatrix() {
        return move;
    }

    public int getIrany() {
        return irany;
    }
}
