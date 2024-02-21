package com.example.myapplication;

import android.opengl.Matrix;
import android.util.Log;

public class EnemyCharacter extends Character{

    private Point MovementPoint;
    public EnemyCharacter(float[] startingmatrix) {
        super();
        float[] check = startingmatrix.clone();
        Matrix.multiplyMM(plsmove,0,check,0,plsmove,0);
    }
    @Override
    public String getName() {
        return "EnemyCharacter";
    }

    public void move(boolean a){
        //Log.e("faszom","enemy move");
        if(a) if(new Point(this).distance(MovementPoint)<0.005f) Game.getInstance().getMaze().NearestMovmentPoint(this);
        else MovementPoint = Game.getInstance().getPlayerpoint();
        Point me = new Point(this);
        irany = Game.whatisirany(MovementPoint.x,MovementPoint.y);
        double foo =Math.atan2((double) MovementPoint.y - me.y,(double) MovementPoint.x - me.x);
        float dx = (float) Math.cos(foo);
        float dy = (float) Math.sin(foo*-1);
        Matrix.translateM(plsmove,0,dx*0.0004f,dy*0.0004f,0);
    }
    public void setMovementPoint(Point movementPoint) {
        MovementPoint = movementPoint;
    }
    //TODO enemy characters
    // enemy movement by Pathfinding (pipa)
    // attacks
    // Damage


}
