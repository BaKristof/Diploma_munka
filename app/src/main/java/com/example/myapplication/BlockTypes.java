package com.example.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class BlockTypes {
    private boolean isHitable = false;
    private int texture;
    private static final ArrayList<Integer[]> multipleTexture = new ArrayList<>(Arrays.asList(
            new Integer[][]{
                    {R.drawable._4,R.drawable._5,R.drawable._6},
                    {R.drawable._13,R.drawable._14,R.drawable._15},
                    {R.drawable._11,R.drawable._12,R.drawable._3},
                    {R.drawable._8,R.drawable._9,R.drawable._10},
                    {R.drawable._16,R.drawable._18},
                    {R.drawable._17,R.drawable._19},
            }
    ));
    private static final int[] namesForSingelTexture = new int[]{
            R.drawable._5,
            R.drawable._7,
            R.drawable._13,
            R.drawable._1,
            R.drawable._t1,
            R.drawable._t0,
            R.drawable._t5,
            R.drawable._t8,
            R.drawable._t2,
            R.drawable._t6,
            R.drawable._t7,
            R.drawable._t3,
            R.drawable._t4,
            R.drawable._t9,
    };
    private static final Tiles[] namesForMultiTexture = new Tiles[]{
            Tiles.Left_Wall,
            Tiles.Right_Wall,
            Tiles.Top_Wall,
            Tiles.Bottom_Wall,
            Tiles.Connection_Left,
            Tiles.Connection_Right,
    };
    private static final Tiles[] namesSingelTexture = new Tiles[]{
            Tiles.Left_Top_Wall_Corner,
            Tiles.Left_Bottom_Wall_Corner,
            Tiles.Right_Top_Wall_Corner,
            Tiles.Right_Bottom_Wall_Corner,

            Tiles.Left_Top_Floor_Corner,
            Tiles.Left_Bottom_Floor_Corner,
            Tiles.Right_Top_Floor_Corner,
            Tiles.Right_Bottom_Floor_Corner,

            Tiles.Bottom_Floor,
            Tiles.Top_Floor,
            Tiles.Right_Floor,
            Tiles.Left_Floor,

            Tiles.Floor,
            Tiles.Black_Back_Ground,
    };

    private static final EnumMap<Tiles,Integer> singelitem = new EnumMap<>(Tiles.class);
    private static final EnumMap<Tiles,Integer[]> walls = new EnumMap<>(Tiles.class);

    public BlockTypes() {

        if(singelitem.isEmpty()){

            int i=0;
            for (int name : namesForSingelTexture) {
                singelitem.put(namesSingelTexture[i++],MyGLRenderer.loadTexture(name));
            }
        }
        if (walls.isEmpty()){

            int j =0;
            for (Integer[] a : multipleTexture) {
                ArrayList<Integer> foo = new ArrayList<>();
                for (Integer integer : a) {
                    foo.add(MyGLRenderer.loadTexture(integer));
                }
                walls.put(namesForMultiTexture[j++],foo.toArray(new Integer[0]));

            }
        }
    }

    public void setTexture(Tiles tiles) {
        switch (tiles){
            case Right_Wall:
            case Left_Wall:
            case Top_Wall:
            case Bottom_Wall:
            case Connection_Left:
            case Connection_Right:
                texture = getRandomNumberFromArray(Objects.requireNonNull(walls.get(tiles)));
                isHitable = true;
                break;
            case Left_Top_Wall_Corner:
            case Right_Top_Wall_Corner:
            case Left_Bottom_Wall_Corner:
            case Right_Bottom_Wall_Corner:
                texture = singelitem.get(tiles);
                isHitable = true;
                break;
            default:
                texture = singelitem.get(tiles);
                break;
        }
    }

    public int getTexture() {
        return texture;
    }

    private int getRandomNumberFromArray(Integer[] a){
        Random r = new Random();
        return a[r.nextInt(a.length)];
    }
    public boolean isHitable() {
        return isHitable;
    }
}
