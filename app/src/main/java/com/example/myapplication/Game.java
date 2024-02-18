package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.util.ArrayList;
import java.util.Arrays;

public final class Game {
    private static Game game;
    public EnemyCharacter enemyCharacter;
    public Player player;
    public Point playerpoint;
    public BG BackGround;
    private final float[] move = new float[16];
    private final float[] foo = new float[16];
    ArrayList<EnemyCharacter> enemys;

    public Point StartingPoint = new Point(365.0f,365.0f);
    private ArrayList<BGBlock> hitfield;//TODO

    public ArrayList<BGBlock> getHitfield() {
        return hitfield;
    }
    public void addenemy(int type,float x,float y){
        enemys.add(new EnemyCharacter(new float[]{x,y}));
    }
    public float[] getPlayerMatrix() {
        return foo;
    }

    public Point getPlayerpoint() {
        return playerpoint;
    }

    private Game() {
        Matrix.setIdentityM(move,0);
        enemys = new ArrayList<>();
        BackGround = new BG(3,3);
        player = new Player();
        //character = new Character();
        enemyCharacter = new EnemyCharacter(BackGround.getboxmidel(2,2));
     //   hitfield = BoundingBox.valami(BackGround.foundnearblocks(moveX,moveY));

    }
    public static Game getInstance(){
        if (game== null){
            game = new Game();
        }
        return game;
    }


    public void befordraw(){
        playerpoint = new Point(player);
        enemymovment();

    }
    public void draw(float[]mvpMatrix){
        Matrix.multiplyMM(foo, 0, mvpMatrix, 0, move, 0);
        BackGround.draw(foo);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        enemyCharacter.draw(foo);
        for (EnemyCharacter enemy : enemys) {
            enemy.draw(foo);
        }
        player.draw(mvpMatrix);
        MyGLRenderer.checkGLError("draw van e probléma");
        GLES20.glDisable(GLES20.GL_BLEND);

    }
    public void move(float dx,float dy){
        player.setIrany(whatisirany(dx,dy));
        Matrix.translateM(move,0,(dx* -0.004f),(dy* -0.004f),0);
    }
    public void enemymovment(){
            for (BGBlock bgb : hitfield) {
                for (EnemyCharacter enemy: enemys) {
                    enemyCharacter.move( new BoundingBox(bgb.toGameObj()).Lineintersect(new Point(player),new Point(enemy)));
                }
            }
    }
    public static int whatisirany(float dx,float dy){
        int irany =0;
        if(Math.cos(Math.toRadians(45))<dx && Math.sin(Math.toRadians(225))<dy && dy<Math.sin(Math.toRadians(135))) irany=2;
        else if(Math.cos(Math.toRadians(135))>dx && Math.sin(Math.toRadians(315))<dy && dy<Math.sin(Math.toRadians(45))) irany=3;
        else if(Math.sin(Math.toRadians(315))>dy && Math.cos(Math.toRadians(225))<dx && dx<Math.cos(Math.toRadians(315))) irany=1;
        return irany;
    }
    public void FillHitfield() { //TODO nem biztos hogy jó (nem hiszem)
        Point kell = new Point(player);
        if (kell.distance(StartingPoint) > GameObj.blocksize) hitfield.addAll(Arrays.asList(BackGround.foundnearblocks(kell.x, kell.y)));
    }
    public boolean CheckForWallHit(){
        for (BGBlock bgb: hitfield) {
            if(new BoundingBox(bgb.toGameObj()).intersects(new BoundingBox(player))) return true;
        }
        return false;
    }

    public BG getBackGround() {
        return BackGround;
    }
}
