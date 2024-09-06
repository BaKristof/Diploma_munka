package com.example.myapplication.MainClasses;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.example.myapplication.BackGround.BG;
import com.example.myapplication.BackGround.BGBlock;
import com.example.myapplication.BackGround.Maze;
import com.example.myapplication.Enemys.Spawners.Spawner;
import com.example.myapplication.Player.Bullett;
import com.example.myapplication.Player.Player;
import com.example.myapplication.R;
import com.example.myapplication.SuperClasses.Character;
import com.example.myapplication.HitBoxes.BoundingBox;
import com.example.myapplication.SuperClasses.EnemyCharacter;
import com.example.myapplication.Enemys.EnemyCharacterWithOwner;
import com.example.myapplication.SuperClasses.Line;
import com.example.myapplication.SuperClasses.Projectile;
import com.example.myapplication.SuperClasses.Specifications;
import com.example.myapplication.SuperClasses.StaticObject;
import com.example.myapplication.SuperClasses.Triangle;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class Game {
    // private static float[] move = new float[16];
    //private final float[] foo = new float[16];
    public BG BackGround;
    private static Game game;
    public EnemyCharacter enemyCharacter;
    public Player player;
    public final ArrayList<EnemyCharacterWithOwner> enemys;
    private final ArrayList<BGBlock> hitField = new ArrayList<>();
    public static ArrayList<Triangle> invisible_pooints = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<StaticObject> staticObjects= new ArrayList<>();
    private static Graph<Specifications, DefaultWeightedEdge> graph;
    //private ArrayList<Triangle> teszt = new ArrayList<>();
    private Queue<Runnable> taskQueue = new LinkedList<>();
    private ArrayList<Line> lines = new ArrayList<>();


    private Game() {

        //Matrix.setIdentityM(move,0);
        Maze maze = new Maze();
        maze = new Maze(7);
        BackGround = new BG(maze, 2, 2);
        graph = BackGround.getGraph();
        enemys = new ArrayList<>();
        player = new Player(BackGround,maze);
        enemyCharacter = new EnemyCharacter(BackGround.getboxmidel(new int[]{1, 1}));
        BackGround.getRooms().forEach(i -> hitField.addAll(i.getWalls()));

        //invisible_pooints.add(new Triangle(movementpoints.get(invisible_pooints.size() + i).getOwnPositionM()).setColor(new float[]{0.0f, 0.0f, 1.0f, 1.0f}));
        BackGround.getMovementpoints().forEach(i-> invisible_pooints.add(new Triangle(i.getOwnPositionM()).setColor(new float[]{0.0f, 0.0f, 1.0f, 1.0f})));
        //enemyCharacter.findPath(BackGround.getGraph(),player);
        //addenemy(enemyCharacter);
        //MyGLRenderer.addmargin(player);

        staticObjects.add(new StaticObject(new SpriteSheets(R.drawable.key,16,16,4)).setPosition(BackGround.randomFloorElement()));
        staticObjects.add(new Spawner(R.drawable.spawing_fire_animation,64,64).setPosition(BackGround.randomFloorElement()));

    }

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public void beforDraw() {

        readInput();
//        findPath(player,enemyCharacter);
       // fillinvis();
        BackGround.spawn();
        enemymovment();
        enemyCharacter.move();

        //Log.e("menyiseg", "spawner: " + enemys.size());
        while (!taskQueue.isEmpty()) {
            Runnable task = taskQueue.poll();
            if (task != null) {
                task.run();
            }
        }
        //enemymovment();
    }

    public void readInput() {
        ArrayList<BGBlock> playerhitField = hitField; //new ArrayList<>(Arrays.asList(BackGround.loadChunks()));
        if (MainActivity.getLeft().isWorking()) {
            float joystick = (float) MainActivity.getLeft().getAngle();
            float dx = (float) Math.cos(joystick);
            float dy = (float) Math.sin(joystick);
            float[] playerInertia = new float[]{dx * -0.004f, dy * 0.004f};


            //x tengelyen való hit
            Matrix.translateM(player.getOwnPositionM(), 0, (dx * 0.004f), 0, 0);
            for (BGBlock a : playerhitField) {
                if (new BoundingBox(player).intersects(new BoundingBox(a))) {
                    //  Log.e("hit","hit Y tengelyen"+new BoundingBox(a).toString()+"   "+new BoundingBox(player));
                    Matrix.translateM(player.getOwnPositionM(), 0, (dx * -0.004f), 0, 0);
                    playerInertia[0] = 0;
                }
            }

            //y tengelyen való hit
            Matrix.translateM(player.getOwnPositionM(), 0, 0, (dy * -0.004f), 0);
            for (BGBlock a : playerhitField) {
                if (new BoundingBox(player).intersects(new BoundingBox(a))) {
                    //  Log.e("hit","hit x tengelyen"+new BoundingBox(a).toString()+"   "+new BoundingBox(player));
                    Matrix.translateM(player.getOwnPositionM(), 0, 0, (dy * 0.004f), 0);
                    playerInertia[1] = 0;
                }
                ;
            }

            float percent = MainActivity.getLeft().getDisplace();
            player.setIrany(whatisirany(dx, dy));
            player.setMovingangle(playerInertia);
        }
    }

    public void draw(float[] mvpMatrix) {

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        BackGround.draw(mvpMatrix);
        for (EnemyCharacterWithOwner enemy : enemys) {
            enemy.asEnemyCharcater().draw(mvpMatrix);
        }

        for (Triangle invisiblePooint : invisible_pooints) {
            invisiblePooint.draw(mvpMatrix);
        }
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).draw(mvpMatrix);
        }
        MyGLRenderer.checkGLError("valami nem jó");
        //enemyCharacter.move();
        enemyCharacter.draw(mvpMatrix);
        player.draw(mvpMatrix);
        for (Line line : lines) {
            line.draw(mvpMatrix);
        }
        GLES20.glDisable(GLES20.GL_BLEND);

        //
        //MyGLRenderer.setStoprender();
    }

    public void enemymovment() {
        for (EnemyCharacterWithOwner enemy : enemys) {
            enemy.asEnemyCharcater().move();
        }
    }

    public static int whatisirany(float dx, float dy) {
        int irany = 0;
        if (Math.cos(Math.toRadians(45)) < dx && Math.sin(Math.toRadians(225)) < dy && dy < Math.sin(Math.toRadians(135)))
            irany = 2;
        else if (Math.cos(Math.toRadians(135)) > dx && Math.sin(Math.toRadians(315)) < dy && dy < Math.sin(Math.toRadians(45)))
            irany = 3;
        else if (Math.sin(Math.toRadians(315)) > dy && Math.cos(Math.toRadians(225)) < dx && dx < Math.cos(Math.toRadians(315)))
            irany = 1;
        return irany;
    }

    public void fillinvis() {

        ArrayList<BGBlock> movementpoints = BackGround.getMovementpoints();

        if (invisible_pooints.size() < movementpoints.size()) {
            for (int i = 0; i < Math.abs(invisible_pooints.size() - movementpoints.size()); i++) {
                invisible_pooints.add(new Triangle(movementpoints.get(invisible_pooints.size() + i).getOwnPositionM()).setColor(new float[]{0.0f, 0.0f, 1.0f, 1.0f}));
            }
        } else {
            int i = 0;
            for (BGBlock bgBlock : movementpoints) {
                invisible_pooints.get(i++).setMatrix(bgBlock.getOwnPositionM());
            }
        }
    }

    public static List<Specifications> findPath(Character player, Character enemy) {

        BGBlock nearestToEnemy = Game.getInstance().getnearpoint(enemy);
        BGBlock nearestToPlayer = Game.getInstance().getnearpoint(player);
        graph.addVertex(enemy);
        graph.addVertex(player);
        graph.addEdge(enemy, nearestToEnemy);
        graph.setEdgeWeight(enemy, nearestToEnemy, nearestToEnemy.distance(enemy));
        graph.addEdge(player, nearestToPlayer);
        graph.setEdgeWeight(player, nearestToPlayer, nearestToPlayer.distance(player));

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

    public void addEnemy(EnemyCharacterWithOwner enemyCharacterWithOwner) {
        enemys.add(enemyCharacterWithOwner);
    }

    public Player getPlayer() {
        return player;
    }

    public BGBlock getnearpoint(Character character) {
        return BackGround.nearestMovmentPoint(character);
    }

    public ArrayList<BGBlock> getHitField() {
        return hitField;
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.remove(projectile);
    }

    public void addProjectile(float angle, Character character) {
        taskQueue.add(new Runnable() {
            @Override
            public void run() {
                projectiles.add(new Projectile(angle, character));
            }
        });
    }

    public void addBullet(float angle, Character character) {
        taskQueue.add(new Runnable() {
            @Override
            public void run() {
                projectiles.add(new Bullett(angle, character));
            }
        });
    }

    public void addLine(Line line) {
        taskQueue.add(new Runnable() {
            @Override
            public void run() {
                lines.add(line);
            }
        });
    }

    public static void addInvisible_pooints(Triangle triangle) {
        invisible_pooints.add(triangle);
    }
    public void removeStaticObject (StaticObject staticObject){ staticObjects.remove(staticObject);}
}


