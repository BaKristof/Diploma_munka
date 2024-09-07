package com.example.myapplication.SuperClasses;

import android.opengl.Matrix;
import android.util.Log;

import com.example.myapplication.BackGround.BGBlock;
import com.example.myapplication.HitBoxes.BoundingBox;
import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.R;
import com.example.myapplication.Enemys.Spawners.Spawner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;

public class EnemyCharacter extends Character {


    Queue<Specifications> utvonal = new LinkedList<>();
    Specifications nextpoint ;
    Spawner owner;


    public EnemyCharacter(float[] startingmatrix) {
        super();
        //Matrix.multiplyMM(ownPositionM,0,check,0, ownPositionM,0);
        System.arraycopy(startingmatrix,0,ownPositionM,0,startingmatrix.length);

        setSpriteSheets(R.drawable.enemy_place_holder,64,64);

    }

    @Override
    public String getName() {
        return "EnemyCharacter";
    }

    public  void move() {
        boolean valami = true;
        for (BGBlock bgBlock : Game.getInstance().getHitField()) {
            if (new BoundingBox(bgBlock).doesLineIntersect(Game.getInstance().getPlayer(), this)){
                valami = false;
                //Log.e("enemy", "player nem látható ");
                //Game.invisible_pooints.add(0,new Triangle(bgBlock.getOwnPositionM()).setColor(new float[]{1.0f, 0.0f, 0.0f, 1.0f}));
                break;
            }
        }
        if (!new BoundingBox(this).intersects(new BoundingBox(Game.getInstance().getPlayer()))){
            if (valami) {
                //   Log.e("egyensen","egynes lehet itt csuzsik el");
                float[] dxdy =this.dxdy(Game.getInstance().getPlayer());
                Matrix.translateM(ownPositionM, 0, dxdy[0] * 0.002f, dxdy[1] * 0.002f, 0); //Player felé mozdul
                utvonal.clear();
            }
            //utvonal keresés
            /*else {
                // Log.e("ez itt jó ","vagy ez nem jó");
                if (utvonal.isEmpty()){
                    utvonal.addAll( Game.findPath(Game.getInstance().getPlayer(), this));
                    nextpoint = utvonal.remove();
                }
                if (nextpoint.near(this,0.005f)){
                    nextpoint = utvonal.remove();
                }
                float[] dxdy =this.dxdy(nextpoint);
                irany = Game.whatisirany(dxdy[0], dxdy[1]);
                Matrix.translateM(ownPositionM, 0, dxdy[0] * 0.002f, dxdy[1] * 0.002f, 0);
            }*/
        }
    }

   /* public void findPath(Graph<Point, DefaultWeightedEdge> graph, Player playerObj){
        Point enemy = new Point(this);
        Point player = new Point(playerObj);
        Point nearestToEnemy = Game.getInstance().getnearpoint(this);
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
        this.utvonal = Paths.getPath(player).getVertexList();
        graph.removeVertex(enemy);
        graph.removeVertex(player);
        Log.e("valami", Arrays.toString(utvonal.toArray()));

    }*/
   public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
       Random random = new Random();
       int x = random.nextInt(Objects.requireNonNull(clazz.getEnumConstants()).length);
       return clazz.getEnumConstants()[x];
   }

    public void setOwner(Spawner owner) {
        this.owner = owner;
    }

    public Spawner getOwner() {
        return owner;
    }
    public static float percent(float original, float now ){
       return (now*100/original)/100;
    }
}
