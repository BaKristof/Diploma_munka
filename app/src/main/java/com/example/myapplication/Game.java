package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public final class Game {
    private static float[] move = new float[16];
    private final float[] foo = new float[16];
    private Maze maze ;
    public BG BackGround;
    private static Game game;
    public EnemyCharacter enemyCharacter;
    public Player player;
    public Point StartingPoint;
    public Point playerpoint;
    private ArrayList<EnemyCharacter> enemys;
    private ArrayList<BGBlock> hitfield = new ArrayList<>();
    private ArrayList<Triangle> invisible_pooints =new ArrayList<>();

    private byte[] direction = new byte[2];

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

    public Player getPlayer() {
        return player;
    }

    private Game() {
        Log.e("vajon innen jön a dolog","valami construktor");
        Matrix.setIdentityM(move,0);
        maze = new Maze();

        BackGround = new BG(maze);
        enemys = new ArrayList<>();
        player = new Player();
        enemyCharacter = new EnemyCharacter(BackGround.getboxmidel(new  int[]{0,1}));
      //  addenemy(enemyCharacter);
        float[] check = BackGround.getboxmidel(maze.getStartingpoint()).clone();

        Matrix.invertM(check,0,check,0);
        System.arraycopy(check,0,move,0,move.length);
       // Matrix.translateM(move,0,(Specifications.blocksize/2)*-1*9,0,0);

    }
    public static Game getInstance(){
        if (game== null){
            game = new Game();
        }
        return game;
    }
    public void befordraw(){
        fillinvis();
        readininput();

        playerpoint = new Point(player);

        FillHitfield();
        //enemymovment();

    }
    public void readininput(){
        if(MainActivity.getLeft().isWorking()) {
            float a = (float) MainActivity.getLeft().getAngle();
            float percent = MainActivity.getLeft().getDisplace();
            float dx = (float) Math.cos(a);
            float dy = (float) Math.sin(a);
            direction = player.getBoundingBox().intersectwithwall(hitfield);
            player.setIrany(whatisirany(dx, dy));
            Matrix.translateM(move, 0, (dx * -0.004f)*direction[0], (dy * 0.004f)*direction[1], 0);
        }
    }
    public void draw(float[]mvpMatrix){
        Matrix.multiplyMM(foo, 0, mvpMatrix, 0, move, 0);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        BackGround.draw(foo);
        for (Triangle a : invisible_pooints) {
            a.draw(foo);
        }

        for (EnemyCharacter enemy : enemys) {enemy.draw(foo);}

        player.draw(mvpMatrix);
        GLES20.glDisable(GLES20.GL_BLEND);
        MyGLRenderer.checkGLError("draw van e probléma");
       // MyGLRenderer.setStoprender();
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
            hitfield.addAll(Arrays.asList(BackGround.loadablechunks()));
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

    public void setDirection(byte[] direction) {
        this.direction = direction;
    }

    public float[] getMatrix() {
        Matrix.multiplyMM(foo,0,MyGLRenderer.getvPMatrix(),0,move,0);
        return foo;
    }
    public void fillinvis(){
        for (Specifications a: BackGround.getLodingpoints()) {
            invisible_pooints.add(new Triangle(a.getOwnPositionM()));
        }
    }
    
}
