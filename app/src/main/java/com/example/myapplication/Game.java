package com.example.myapplication;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Game {
    private static float[] move = new float[16];
    private final float[] foo = new float[16];
    public BG BackGround;
    private static Game game;
    public EnemyCharacter enemyCharacter;
    public Player player;
    private final ArrayList<EnemyCharacter> enemys;
    private final ArrayList<BGBlock> hitField = new ArrayList<>();
    private ArrayList<Triangle> invisible_pooints =new ArrayList<>();
    private static Graph<Specifications, DefaultWeightedEdge> graph;

    private ArrayList<Triangle> teszt = new ArrayList<>();
    private ArrayList<Triangle> teszt2 = new ArrayList<>();



    private Game() {

        Matrix.setIdentityM(move,0);
        Maze maze = new Maze();

        maze = new Maze(7);
        BackGround = new BG(maze,2,2);
        graph = BackGround.getGraph();
        enemys = new ArrayList<>();
        player = new Player();
        enemyCharacter = new EnemyCharacter(BackGround.getboxmidel(new  int[]{1,1}));
       // enemyCharacter.findPath(BackGround.getGraph(),player);
      //  addenemy(enemyCharacter);
        //MyGLRenderer.addmargin(player);

    }
    public static Game getInstance(){
        if (game== null){
            game = new Game();
        }
        return game;
    }
    public void befordraw(){

        readininput();
        findPath(player,enemyCharacter);
        FillHitfield();
        fillinvis();
        //enemymovment();

    }
    public void readininput(){
        if(MainActivity.getLeft().isWorking()) {
            float joystick = (float) MainActivity.getLeft().getAngle();
            float dx = (float) Math.cos(joystick);
            float dy = (float) Math.sin(joystick);

            //x tengelyen való hit

            Matrix.translateM(move, 0, (dx * -0.004f), 0, 0);
            for (BGBlock a : hitField) {
                BoundingBox b = new BoundingBox(player);
                BoundingBox c =new BoundingBox(a);
                if (b.intersects(c)){
                 //  Log.e("hit","hit Y tengelyen"+new BoundingBox(a).toString()+"   "+new BoundingBox(player));
                    Matrix.translateM(move, 0, (dx * 0.004f), 0, 0);
                }
            }

            //y tengelyen való hit
            Matrix.translateM(move, 0, 0, (dy * 0.004f), 0);
            for (BGBlock a : hitField) {
                if (new BoundingBox(player).intersects(new BoundingBox(a))){
                  //  Log.e("hit","hit x tengelyen"+new BoundingBox(a).toString()+"   "+new BoundingBox(player));
                    Matrix.translateM(move, 0, 0, (dy * -0.004f), 0);
                };
            }

            float percent = MainActivity.getLeft().getDisplace();
            player.setIrany(whatisirany(dx, dy));

        }
    }
    public void draw(float[]mvpMatrix){
        Matrix.multiplyMM(foo, 0, mvpMatrix, 0, move, 0);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        BackGround.draw(foo);
        //for (EnemyCharacter enemy : enemys) {enemy.draw(foo);}
        for (Triangle triangle : teszt) {
            triangle.draw(foo);
        }

        for (Triangle invisiblePooint : invisible_pooints) {
            invisiblePooint.draw(foo);
        }
        enemyCharacter.move();
        enemyCharacter.draw(foo);
        player.draw(mvpMatrix);
        GLES20.glDisable(GLES20.GL_BLEND);

       // MyGLRenderer.setStoprender();
    }
    public void enemymovment(){
            for (BGBlock bgb : hitField) {
                for (EnemyCharacter enemy: enemys) {
                    enemy.move();
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
    public void FillHitfield() {
            hitField.clear();
            hitField.addAll(Arrays.asList(BackGround.loadablechunks()));

            if (teszt.size()<hitField.size()){
                for (int i = 0; i < Math.abs(teszt.size()-hitField.size()); i++) {
                    teszt.add(new Triangle(hitField.get(teszt.size()+i).getOwnPositionM()).setColor(new float[]{ 1.0f, 1.0f, 1.0f, 1.0f }));
                }
            }
            else {
                int i =0;
                for (BGBlock bgBlock : hitField) {
                teszt.get(i++).setMatrix(bgBlock.getOwnPositionM());
                }
            }

    }
    public void fillinvis(){

        ArrayList<BGBlock> movementpoints = BackGround.getMovementpoints();

        if (invisible_pooints.size()<movementpoints.size()){
            for (int i = 0; i < Math.abs(invisible_pooints.size()-movementpoints.size()); i++) {
                invisible_pooints.add(new Triangle(movementpoints.get(invisible_pooints.size()+i).getOwnPositionM()).setColor(new float[]{ 0.0f, 0.0f, 1.0f, 1.0f }));
            }
        }
        else {
            int i =0;
            for (BGBlock bgBlock : movementpoints) {
                invisible_pooints.get(i++).setMatrix(bgBlock.getOwnPositionM());
            }
        }
    }
    public static List<Specifications> findPath( Character player, Character enemy){

        BGBlock nearestToEnemy = Game.getInstance().getnearpoint(enemy);
        BGBlock nearestToPlayer = Game.getInstance().getnearpoint(player);
        graph.addVertex(enemy);
        graph.addVertex(player);
        graph.addEdge(enemy,nearestToEnemy);
        graph.setEdgeWeight(enemy,nearestToEnemy,nearestToEnemy.distance(enemy));
        graph.addEdge(player,nearestToPlayer);
        graph.setEdgeWeight(player,nearestToPlayer,nearestToPlayer.distance(player));

        DijkstraShortestPath<Specifications, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Specifications, DefaultWeightedEdge> Paths = dijkstraAlg.getPaths(enemy);

        List<Specifications> utvonal = Paths.getPath(player).getVertexList();
        graph.removeVertex(enemy);
        graph.removeVertex(player);
        return utvonal;
    }


    public BG getBackGround() {
        return BackGround;
    }
    public float[] getMatrix() {
        Matrix.multiplyMM(foo,0,MyGLRenderer.getvPMatrix(),0,move,0);
        return foo;
    }
    public void addEnemy(EnemyCharacter enemyCharacter,float[] xy){
        enemys.add(new EnemyCharacter(xy));
    }
    public static float[] getMove() {
        return move;
    }

    public Player getPlayer() {
        return player;
    }
    public BGBlock getnearpoint(Character character){
        return BackGround.NearestMovmentPoint(character);
    }
    public ArrayList<BGBlock> getHitField() {
        return hitField;
    }

    public static void setMove(float[] move) {
        float[] local = new float[16];
        Matrix.invertM(local,0,move,0);
       System.arraycopy(local,0,Game.move,0,local.length);


    }
}

