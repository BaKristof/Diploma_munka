package com.example.myapplication;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animation {
    ArrayList<Integer> backward;
    ArrayList<Integer> left;
    ArrayList<Integer> right;
    ArrayList<Integer> forward;
    Map<Integer,ArrayList<Integer>> movement = new HashMap<>();
    int FPS;
    int irany;
    int counter;
    public Animation(int[] backward,int[] left,int[] forward,int[] right) {
        //0 =fel
        //1=le
        //2=jobb
        //3=bal
        this.backward = new ArrayList<>();
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
        this.forward = new ArrayList<>();
        for (int foo : backward) {
            this.backward.add(MyGLRenderer.loadTexture(foo));
        }
        for (int foo : left) {
            this.left.add(MyGLRenderer.loadTexture(foo));
        }
        for (int foo : right) {
            this.right.add(MyGLRenderer.loadTexture(foo));
        }
        for (int foo : forward) {
            this.forward.add(MyGLRenderer.loadTexture(foo));
        }
        //0 =fel
        //1=le
        //2=jobb
        //3=bal
        movement.put(0,this.backward);
        movement.put(1,this.forward);
        movement.put(2,this.right);
        movement.put(3,this.left);
        counter = 0;
        irany =1;
    }
    public Animation(int[] singelanimation) {

        this.backward = new ArrayList<>();
        for (int foo : singelanimation) {
            this.backward.add(MyGLRenderer.loadTexture(foo));
        }
        movement.put(0,backward);
        counter = 0;
    }
    public Animation() {}
    public int NextFrame(int irany){
        int a = Game.getInstance().getEnemyPlaceholder();
        if(!movement.isEmpty()){
            if (movement.get(irany)!=null){
                if (movement.get(irany).size()<=counter) counter=0;
                a = movement.get(irany).get(counter);
        }
        else {
            if (movement.get(0).size()<=counter) counter=0;
            a = movement.get(0).get(counter);
        }
        counter ++;
        }
        return a;
    }
}
