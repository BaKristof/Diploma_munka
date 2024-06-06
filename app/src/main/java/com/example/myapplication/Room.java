package com.example.myapplication;

import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Room {
    private ArrayList<BGBlock> blocks = new ArrayList<>();
    private float[] matrix = new float[16];
    private float[] courners = new float[4];
    private  ArrayList<Spawner> spawners = new ArrayList<>();
    private Random random = new Random();
    private static final ArrayList<Tiles[]> horizontalConnection = new ArrayList<>(Arrays.asList(
            new Tiles[][]{
                    {Tiles.Right_Top_Floor_Corner,Tiles.Top_Wall,Tiles.Top_Wall,Tiles.Left_Top_Floor_Corner},
                    {Tiles.Floor,Tiles.Floor,Tiles.Floor,Tiles.Floor,},
                    {Tiles.Right_Bottom_Floor_Corner,Tiles.Connection_Left,Tiles.Connection_Right,Tiles.Left_Bottom_Floor_Corner},
            }
    ));
    private static final ArrayList<Tiles[]> verticalConnection = new ArrayList<>(Arrays.asList(
            new Tiles[][]{
                    {Tiles.Left_Bottom_Floor_Corner,Tiles.Connection_Right,Tiles.Top_Wall,Tiles.Left_Top_Floor_Corner},
                    {Tiles.Floor,Tiles.Floor,Tiles.Floor,Tiles.Floor,},
                    {Tiles.Right_Bottom_Floor_Corner,Tiles.Connection_Left,Tiles.Top_Wall,Tiles.Right_Top_Floor_Corner},
            }
    ));
    private static final ArrayList<Tiles[]> room7x7 = new ArrayList<>(Arrays.asList(
            new Tiles[][]{
                    {Tiles.Left_Top_Wall_Corner,    Tiles.Top_Wall,                 Tiles.Top_Wall,     Tiles.Top_Wall,     Tiles.Top_Wall,     Tiles.Top_Wall,                 Tiles.Right_Top_Wall_Corner},
                    {Tiles.Left_Wall,               Tiles.Left_Top_Floor_Corner,    Tiles.Top_Floor,    Tiles.Top_Floor,    Tiles.Top_Floor,    Tiles.Right_Top_Floor_Corner,   Tiles.Right_Wall,},
                    {Tiles.Left_Wall,               Tiles.Left_Floor,               Tiles.Floor,        Tiles.Floor,        Tiles.Floor,        Tiles.Right_Floor,              Tiles.Right_Wall,},
                    {Tiles.Left_Wall,               Tiles.Left_Floor,               Tiles.Floor,        Tiles.Floor,        Tiles.Floor,        Tiles.Right_Floor,              Tiles.Right_Wall,},
                    {Tiles.Left_Wall,               Tiles.Left_Floor,               Tiles.Floor,        Tiles.Floor,        Tiles.Floor,        Tiles.Right_Floor,              Tiles.Right_Wall,},
                    {Tiles.Left_Wall,               Tiles.Left_Bottom_Floor_Corner, Tiles.Bottom_Floor, Tiles.Bottom_Floor, Tiles.Bottom_Floor, Tiles.Right_Bottom_Floor_Corner,Tiles.Right_Wall,},
                    {Tiles.Left_Bottom_Wall_Corner, Tiles.Bottom_Wall,              Tiles.Bottom_Wall,  Tiles.Bottom_Wall,  Tiles.Bottom_Wall,  Tiles.Bottom_Wall,              Tiles.Right_Bottom_Wall_Corner},
            }
    ));
    ArrayList<Tiles[]> valid ;
    ArrayList<StaticObject> containdObjects = new ArrayList<>();
    Tiles[][] Room;
    int size_up;
    public Room(int size_up) {
        ArrayList<Tiles[]> roomNxN = new ArrayList<>();
        this.size_up = size_up;
        Room = new Tiles[size_up][size_up];
        Tiles[] local= new Tiles[size_up];
        int mod =(int) Math.pow(size_up,2);
        int number =0;
        for (int k = 0; k <size_up; k++) {

            for (int l = 0; l <size_up ; l++) {

                local[l]=Tiles.Floor;

                if ((number%mod)>7&&(number%mod)<(size_up*2)-1)                     local[l]=Tiles.Top_Floor ;//felső padló
                if (number%size_up==1)                                              local[l]=Tiles.Left_Floor;//bal padló
                if (number%size_up==size_up-2)                                      local[l]=Tiles.Right_Floor;//jobb padló
                if ((number%mod)>(mod-size_up*2)&&(number%mod)<mod-size_up-2)       local[l]=Tiles.Bottom_Floor;//alsó padló

                if (number%mod==(mod-(size_up+2)))              local[l]=Tiles.Right_Bottom_Floor_Corner  ;//jobbalsó belső
                if (number%mod==(mod-(size_up*2)+1))                    local[l]=Tiles.Left_Bottom_Floor_Corner;//balalsó belső
                if (number%mod==size_up+(size_up-2))                        local[l]=Tiles.Right_Top_Floor_Corner;//jobbfelső belső
                if (number%mod==size_up+1)                              local[l]=Tiles.Left_Top_Floor_Corner;//balfelső belső

                if ((number%mod)>0&&(number%mod)<size_up)               local[l]=Tiles.Top_Wall ;//felsőfal
                if (number%size_up==0)                                  local[l]=Tiles.Left_Wall;//balfal
                if (number%size_up==size_up-1)                          local[l]=Tiles.Right_Wall;//jobbfal
                if ((number%mod)>(mod-size_up)&&(number%mod)<mod-1)     local[l]=Tiles.Bottom_Wall;//alsófal

                //sarok
                if (number%mod==0)                  local[l] =Tiles.Left_Top_Wall_Corner ;//balfelső
                if (number%mod==(mod-1))            local[l] =Tiles.Right_Bottom_Wall_Corner ;//jobbalso
                if (number%mod==size_up-1)          local[l] =Tiles.Right_Top_Wall_Corner;//jobbfelső
                if (number%mod==(mod-size_up))      local[l] =Tiles.Left_Bottom_Wall_Corner ;//balalsó

                number++;
            }
            roomNxN.add(local.clone());
        }
        for (Tiles[] tiles : roomNxN) {
            Log.e("valami",Arrays.toString(tiles));
        }
        valid = roomNxN;
    }

    public Room() {
        valid = room7x7;
    }
    public static void insterHorozontal(Tiles[][] finale, int i, int j, Maze maze, int size_up){

        for (int k = 0; k < size_up-2; k++) {
            int l=0;
            if(k==0){
                maze.setMovementpoints(new Integer[]{k+i+1,l+j+1});
                for (Tiles tiles : horizontalConnection.get(0)) {
                    finale[k+i][l+j]= tiles;
                    l++;
                }
                maze.setMovementpoints(new Integer[]{k+i+1,l+j-2});
            }
            else if (k==(size_up-3)){

                maze.setMovementpoints(new Integer[]{k+i-1,l+j+1});//itt van első az alagőtban
                for (Tiles tiles : horizontalConnection.get(2)) {
                    finale[k+i][l+j]= tiles;
                    l++;
                }
                maze.setMovementpoints(new Integer[]{k+i-1,l+j-2});//ez pedig az alagút vége mint összekötési pontok


            }
            else {
                for (Tiles tiles : horizontalConnection.get(1)) {
                    finale[k+i][l+j]= tiles;
                    l++;
                }
            }
        }
    }
    public static void insterVertical(Tiles[][] finale,int i, int j,Maze maze,int size_up){
        for (int k = 0; k < size_up-2; k++) {
            int l=0;
            if(k==0){
                maze.setMovementpoints(new Integer[]{l+i+1,k+j+1});
                for (Tiles tiles : verticalConnection.get(0)) {
                    finale[l+i][k+j]= tiles;
                    l++;
                }
                maze.setMovementpoints(new Integer[]{l+i-2,k+j+1});

            }
            else if (k==(size_up-3)){
                maze.setMovementpoints(new Integer[]{l+i+1,k+j-1});
                for (Tiles tiles : verticalConnection.get(2)) {
                    finale[l+i][k+j]= tiles;
                    l++;
                }
                maze.setMovementpoints(new Integer[]{l+i-2,k+j-1});
            }
            else {
                for (Tiles tiles : verticalConnection.get(1)) {
                    finale[l+i][k+j]= tiles;
                    l++;
                }
            }
        }
    }
    public void roomfill(Tiles[][] finale, int i, int j){

        int l=0;
        for (Tiles[] tiles : valid ) {
            int k =0;
            for (Tiles tile : tiles) {
                finale[i+l][j+k]= tile;
                k++;
            }
            l++;
        }

    }
    public Room setCourners(int size_up,BGBlock bgBlock){
        courners[0] =0.0f; // x
        courners[1] =0.0f; // y
        courners[2] =bgBlock.getHeight()*size_up;
        courners[3] =bgBlock.getHeight()*size_up*(-1);
        return this;
    }
    public Room setMatrix(float[] matrix) {
        this.matrix = matrix;
        return this;
    }
    public void setBlocks(BGBlock blocks) {
        this.blocks.add(blocks) ;
    }

    public ArrayList<BGBlock> getBlocks() {
        return blocks;
    }
    public ArrayList<BGBlock> getWalls(){
        return blocks.stream().filter(BGBlock::isHitable).collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<BGBlock> getFloors(){
        return blocks.stream().filter(i -> !i.isHitable()).collect(Collectors.toCollection(ArrayList::new));

    }
    public float[] getCourners() {
        return courners;
    }

    public float[] getMatrix() {
        float[] local = new float[16];
        Matrix.multiplyMM(local,0, matrix,0,Game.getMove(), 0);
        return local.clone();
    }
    public BGBlock getRandomFloorBlock(){
        return getFloors().get(random.nextInt(getFloors().size()));
    }
    public void addSpawners(Spawner spawner) {
        this.spawners.add(spawner);
    }
    public void drawSpawner(float[] mvpMatrix){
        for (Spawner spawner : spawners) {
            spawner.draw(mvpMatrix);
        }
    }
}
