package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.util.ArrayList;

public final class Game {
    private static Game game;
    public GameObj gm;
    public Character character;
    public EnemyCharacter enemyCharacter;
    public Player player;
    public BG BackGround;
    private final float[] move = new float[16];

    ArrayList<EnemyCharacter> enemys;

    public int EnemyPlaceholder;

    private ArrayList<BoundingBox> hitfield;//TODO

    private Game() {

        enemys = new ArrayList<>();
        EnemyPlaceholder=MyGLRenderer.loadTexture(R.drawable.enemy_place_holder);
        player = new Player();
        //character = new Character();
        BackGround = new BG(3,3);
        enemyCharacter = new EnemyCharacter(BackGround.getboxmidel(2,2));
     //   hitfield = BoundingBox.valami(BackGround.foundnearblocks(moveX,moveY));
        Matrix.setIdentityM(move,0);

    }
    public static Game getInstance(){
        if (game== null){
            game = new Game();
        }
        return game;
    }
    public void befordraw(){

        float[] PlayerPos = MyGLRenderer.whereareyou(player.getMatrix(),player);
        float[] EnemyPos = MyGLRenderer.whereareyou(enemyCharacter.getMatrix(),enemyCharacter);
        double foo =Math.atan2((double) EnemyPos[1] - PlayerPos[1],(double) EnemyPos[0] - PlayerPos[0]);
        float dx = (float) Math.cos(foo);
        float dy = (float) Math.sin(foo*-1);
        enemymovment(dx,dy);

    }
    public void draw(float[]mvpMatrix){
        Matrix.multiplyMM(move, 0, mvpMatrix, 0, move, 0);
        BackGround.draw(move);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        enemyCharacter.draw(move);
        for (EnemyCharacter enemy : enemys) {
            enemy.draw(move);
        }
        // character.draw(mvpMatrix);
        //player.draw(mvpMatrix,irany);
        //MyGLRenderer.checkGLError("draw van e probléma");
        GLES20.glDisable(GLES20.GL_BLEND);

    }
    public void move(float dx,float dy){
        player.setIrany(whatisirany(dx,dy));
        Matrix.translateM(move,0,(dx* -0.004f),(dy* -0.004f),0);

    }
    public void enemymovment(float dx,float dy){
        /*for (EnemyCharacter enemy :
                enemys) {
        //todo here need to calculate the path to the enemy and give each enemy they own different path
            enemy.move(dx,dy);
        }*/
        enemyCharacter.move(dx,dy);


    }
    public void addenemy(int type,float x,float y){
        enemys.add(new EnemyCharacter(new float[]{x,y}));
    }
    public float[] getMatrix() {
        return move;
    }

    public static int whatisirany(float dx,float dy){
        int irany =0;
        if(Math.cos(Math.toRadians(45))<dx && Math.sin(Math.toRadians(225))<dy && dy<Math.sin(Math.toRadians(135))) irany=2;
        else if(Math.cos(Math.toRadians(135))>dx && Math.sin(Math.toRadians(315))<dy && dy<Math.sin(Math.toRadians(45))) irany=3;
        else if(Math.sin(Math.toRadians(315))>dy && Math.cos(Math.toRadians(225))<dx && dx<Math.cos(Math.toRadians(315))) irany=1;
        return irany;
    }


    public int getEnemyPlaceholder() {
        return EnemyPlaceholder;
    }
    public void FillHitfield(){ //TODO nem biztos hogy jó (nem hiszem)
        if(false){
        BGBlock[] valami2 = BackGround.foundnearblocks(0,0);
        ArrayList<BoundingBox> valami = new ArrayList<>();
        for (BGBlock bgb : valami2) {
            valami.add( new BoundingBox(BackGround,bgb.getMatrix()));
        }
        hitfield= BoundingBox.ConnectBoundingBox(valami);
        }
    }
}
