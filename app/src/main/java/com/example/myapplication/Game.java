package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public final class Game {
    private Maze maze = new Maze();
    private static Game game;
    public EnemyCharacter enemyCharacter;
    public Character character;
    public Player player;
    public Point playerpoint;
    public BG BackGround;
    private static float[] move = new float[16];

    ArrayList<EnemyCharacter> enemys;

    public Point StartingPoint;
    private ArrayList<BGBlock> hitfield = new ArrayList<>();

    public void addenemy(int type,float x,float y){
        enemys.add(new EnemyCharacter(new float[]{x,y}));
    }
    public void addenemy(EnemyCharacter enemy){
        enemys.add(enemy);
    }

    public static float[] getMove() {
        return move;
    }

    public Point getPlayerpoint() {
        return playerpoint;
    }
    private Game() {
        Log.e("vajon innen jön a dolog","valami construktor");
        Matrix.setIdentityM(move,0);
        maze = new Maze();
        character = new Character();
        BackGround = new BG(maze);
        enemys = new ArrayList<>();
        player = new Player();
        StartingPoint = new Point(player);
        enemyCharacter = new EnemyCharacter(BackGround.getboxmidel(new  int[]{0,1}));
        addenemy(enemyCharacter);
        float[] check = BackGround.getboxmidel(maze.getStartingpoint()).clone();
        Matrix.invertM(check,0,check,0);
        System.arraycopy(check,0,move,0,move.length);
    }
    public static Game getInstance(){
        if (game== null){
            game = new Game();
        }
        return game;
    }
    public void befordraw(){
        playerpoint = new Point(player);
        FillHitfield();
        //enemymovment();

    }
    public void draw(float[]mvpMatrix){
        float[] foo = new float[16];
        Matrix.multiplyMM(foo, 0, mvpMatrix, 0, move, 0);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        BackGround.draw(foo);
        BackGround.drawmovementpoints(foo);

        for (BGBlock bg : hitfield) {
            new Triangle(bg.getMatrix());
        }
        for (EnemyCharacter enemy : enemys) {enemy.draw(foo);}

        player.draw(mvpMatrix);
        GLES20.glDisable(GLES20.GL_BLEND);
        MyGLRenderer.checkGLError("draw van e probléma");
    }
    public void move(float dx,float dy){
        int[] directions = new int[]{1,1};//player.getBoundingBox().intersectwithwall(hitfield);
        player.setIrany(whatisirany(dx,dy));
        Matrix.translateM(move,0,(dx* -0.004f)*directions[0],(dy* -0.004f)*directions[1],0);
    }
    public void enemymovment(){
            for (BGBlock bgb : hitfield) {
                for (EnemyCharacter enemy: enemys) {
                    enemyCharacter.move( new BoundingBox().Lineintersect(new Point(player),new Point(enemy)));
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
            hitfield.clear();
            hitfield.addAll(Arrays.asList(BackGround.loadablechunks(new Point(player))));
    }

   /* public boolean CheckForWallHit(){
        for (BGBlock bgb: hitfield) {
            if(new BoundingBox().intersects(getPlayerMatrix(),player.getBoundingBox())) return true;
        }
        return false;
    }*/

    public BG getBackGround() {
        return BackGround;
    }
}
