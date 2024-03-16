package com.example.myapplication;

import android.opengl.Matrix;
import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Arrays;
import java.util.List;

public class EnemyCharacter extends Character{

    private Point MovementPoint;
    List<Point> utvonal;

    public EnemyCharacter(float[] startingmatrix) {
        super();
        //Matrix.multiplyMM(ownPositionM,0,check,0, ownPositionM,0);
        System.arraycopy(startingmatrix,0,ownPositionM,0,startingmatrix.length);
        setSingleTexture(R.drawable.enemy_place_holder);

    }
    @Override
    public String getName() {
        return "EnemyCharacter";
    }


    public void move(boolean a){
        //Log.e("faszom","enemy move");
        if(a) {
            if (new Point(this).distance(MovementPoint) < 0.005f){
                Game.getInstance().getBackGround().NearestMovmentPoint(this);
            }
        }
        else MovementPoint = Game.getInstance().getPlayerpoint();
        Point me = new Point(this);
        irany = Game.whatisirany(MovementPoint.x,MovementPoint.y);
        double foo =Math.atan2((double) MovementPoint.y - me.y,(double) MovementPoint.x - me.x);
        float dx = (float) Math.cos(foo);
        float dy = (float) Math.sin(foo*-1);
        Matrix.translateM(ownPositionM,0,dx*0.0004f,dy*0.0004f,0);
    }
    public void setMovementPoint(Point movementPoint) {
        MovementPoint = movementPoint;
    }
    //TODO enemy characters
    // enemy movement by Pathfinding (pipa+++++++++)
    // attacks
    // Damage
    public void findPath(Graph<Point, DefaultWeightedEdge> graph, Player playerObj){
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

    }



}
