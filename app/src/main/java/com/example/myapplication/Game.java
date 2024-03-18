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
    public Point playerpoint;
    private final ArrayList<EnemyCharacter> enemys;
    private final ArrayList<BGBlock> hitField = new ArrayList<>();
    private ArrayList<Triangle> invisible_pooints =new ArrayList<>();
    private static Graph<Point, DefaultWeightedEdge> graph;

    private ArrayList<Triangle> teszt = new ArrayList<>();
    private ArrayList<Triangle> teszt2 = new ArrayList<>();



    private Game() {
        Log.e("vajon innen jön a dolog","valami construktor");
        Matrix.setIdentityM(move,0);
        Maze maze = new Maze();

        maze = new Maze(7);
        BackGround = new BG(maze);
        graph = BackGround.getGraph();
        enemys = new ArrayList<>();
        player = new Player();
        enemyCharacter = new EnemyCharacter(BackGround.getboxmidel(new  int[]{0,0}));
       // enemyCharacter.findPath(BackGround.getGraph(),player);
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

        readininput();
        findPath(player,enemyCharacter);
        playerpoint = new Point(player);

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
                if (new BoundingBox(player).intersects(new BoundingBox(a))){
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
        for (BGBlock bgBlock : hitField) {
            if (new BoundingBox(bgBlock).Lineintersect(new Point(Game.getInstance().getPlayer()), new Point(enemyCharacter))) {

                Log.e("break","break"+new BoundingBox(bgBlock).Lineintersect(new Point(Game.getInstance().getPlayer()), new Point(enemyCharacter)));
                break;
            }
        }


       // enemyCharacter.move();
        enemyCharacter.draw(foo);
        player.draw(mvpMatrix);
        GLES20.glDisable(GLES20.GL_BLEND);
        MyGLRenderer.checkGLError("draw van e probléma");
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
    public void FillHitfield() { //TODO nem biztos hogy jó (nem hiszem)
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




           // Log.e("hitfild size","   "+hitField.size());
    }
    public void fillinvis(){

        ArrayList<BGBlock> valami = BackGround.getMovementpoints();

        if (invisible_pooints.size()<valami.size()){
            for (int i = 0; i < Math.abs(invisible_pooints.size()-valami.size()); i++) {
                invisible_pooints.add(new Triangle(valami.get(invisible_pooints.size()+i).getOwnPositionM()).setColor(new float[]{ 0.0f, 0.0f, 1.0f, 1.0f }));
            }
        }
        else {
            int i =0;
            for (BGBlock bgBlock : valami) {
                invisible_pooints.get(i++).setMatrix(bgBlock.getOwnPositionM());
            }
        }





    }
    public static List<Point> findPath( Character playerObj, Character enemyObj){
        Point enemy = new Point(enemyObj);
        Point player = new Point(playerObj);
        Point nearestToEnemy = Game.getInstance().getnearpoint(enemyObj);
        Point nearestToPlayer = Game.getInstance().getnearpoint(playerObj);
        graph.addVertex(enemy);
        graph.addVertex(player);
        graph.addEdge(enemy,nearestToEnemy);
        graph.setEdgeWeight(enemy,nearestToEnemy,nearestToEnemy.distance(enemy));
        graph.addEdge(player,nearestToPlayer);
        graph.setEdgeWeight(player,nearestToPlayer,nearestToPlayer.distance(player));

        DijkstraShortestPath<Point, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Point, DefaultWeightedEdge> Paths = dijkstraAlg.getPaths(enemy);
        //System.out.println(Paths.getPath(player) + "\n");
        List<Point> utvonal = Paths.getPath(player).getVertexList();
        graph.removeVertex(enemy);
        graph.removeVertex(player);
       // Log.e("valami", Arrays.toString(utvonal.toArray()));
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
    public Point getPlayerpoint() {
        return playerpoint;
    }
    public Player getPlayer() {
        return player;
    }
    public Point getnearpoint(Character character){
        return BackGround.NearestMovmentPoint(character);
    }
    public ArrayList<BGBlock> getHitField() {
        return hitField;
    }
}
