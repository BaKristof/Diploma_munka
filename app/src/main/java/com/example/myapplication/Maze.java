package com.example.myapplication;

import android.service.quicksettings.Tile;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Maze {
   static int[][] maze;
   static boolean[][] bitmaze;
   static List<int[]> unvisitedcell;
   public int[] startingpoint;
   public int size_up;
   public ArrayList<Integer[]> Movementpoints = new ArrayList<>();
   public ArrayList<Integer[]> LodingPoints = new ArrayList<>();
    ArrayList<Tiles[]> horizontalConnection = new ArrayList<>(Arrays.asList(
           new Tiles[][]{
                   {Tiles.Right_Top_Floor_Corner,Tiles.Top_Wall,Tiles.Top_Wall,Tiles.Left_Top_Floor_Corner},
                   {Tiles.Floor,Tiles.Floor,Tiles.Floor,Tiles.Floor,},
                   {Tiles.Right_Bottom_Floor_Corner,Tiles.Connection_Left,Tiles.Connection_Right,Tiles.Left_Bottom_Floor_Corner},
    }
    ));
    ArrayList<Tiles[]> verticalConnection = new ArrayList<>(Arrays.asList(
            new Tiles[][]{
                    {Tiles.Left_Bottom_Floor_Corner,Tiles.Connection_Right,Tiles.Top_Wall,Tiles.Left_Top_Floor_Corner},
                    {Tiles.Floor,Tiles.Floor,Tiles.Floor,Tiles.Floor,},
                    {Tiles.Right_Bottom_Floor_Corner,Tiles.Connection_Left,Tiles.Top_Wall,Tiles.Right_Top_Floor_Corner},
            }
    ));

    Tiles[][] Room;
    Tiles[][] finale;
    public Maze(int size_up) {
        this.size_up = size_up;
        Room = new Tiles[size_up][size_up];
        int mod =(int) Math.pow(size_up,2);
        int number =0;
        for (int k = 0; k <size_up; k++) {

            for (int l = 0; l <size_up ; l++) {

                Room[k][l]=Tiles.Floor;

                if (number%mod==(mod-size_up)-(size_up-1))              Room[k][l]=Tiles.Right_Bottom_Floor_Corner  ;//jobbalsó belső Todo: nem jó meg az alső sem
                if (number%mod==(mod-(size_up*2)+1))                    Room[k][l]=Tiles.Left_Bottom_Floor_Corner;//balalsó belső
                if (number%mod==((size_up*2)-1))                        Room[k][l]=Tiles.Right_Top_Floor_Corner;//jobbfelső belső
                if (number%mod==size_up+1)                              Room[k][l]=Tiles.Left_Top_Floor_Corner;//balfelső belső

                if ((number%mod)>0&&(number%mod)<size_up)               Room[k][l]=Tiles.Top_Wall ;//felsőfal
                if (number%size_up==0)                                  Room[k][l]=Tiles.Left_Wall;//balfal
                if (number%size_up==size_up-1)                          Room[k][l]=Tiles.Right_Wall;//jobbfal
                if ((number%mod)>(mod-size_up)&&(number%mod)<mod-1)     Room[k][l]=Tiles.Bottom_Wall;//alsófal

                //sarok
                if (number%mod==0)                  Room[k][l] =Tiles.Left_Top_Wall_Corner ;//balfelső
                if (number%mod==(mod-1))            Room[k][l] =Tiles.Right_Bottom_Wall_Corner ;//jobbalso
                if (number%mod==size_up-1)          Room[k][l] =Tiles.Right_Top_Wall_Corner;//jobbfelső
                if (number%mod==(mod-size_up))      Room[k][l] =Tiles.Left_Bottom_Wall_Corner ;//balalsó

                number++;
            }
        }
        for (Tiles[] tiles : Room) {
            Log.e("valami",Arrays.toString(tiles));
        }
    }

    public void setMovementpoints(Integer[] movementpoints) {
        this.Movementpoints.add(movementpoints);
    }

    public Maze() {
        this.size_up = 5;
    }
    private void insterHorozontal(int i, int j){
        for (int k = 0; k < size_up-2; k++) {
            int l=0;
            if(k==0){
                Movementpoints.add(new Integer[]{k+i+1,l+j+1});
                for (Tiles tiles : horizontalConnection.get(0)) {
                        finale[k+i][l+j]= tiles;
                        l++;
                }
                Movementpoints.add(new Integer[]{k+i+1,l+j-2});
            }
            else if (k==(size_up-3)){

                Movementpoints.add(new Integer[]{k+i-1,l+j+1});//itt van elsős az alagutban
                for (Tiles tiles : horizontalConnection.get(2)) {
                        finale[k+i][l+j]= tiles;
                        l++;
                }
                Movementpoints.add(new Integer[]{k+i-1,l+j-2});//ez pedig az alagut vége mintösszekötési pontok


            }
            else {
                for (Tiles tiles : horizontalConnection.get(1)) {
                        finale[k+i][l+j]= tiles;
                        l++;
                }
            }
        }
    }
    private void insterVertical(int i, int j){
        for (int k = 0; k < size_up-2; k++) {
            int l=0;
            if(k==0){
                Movementpoints.add(new Integer[]{l+i+1,k+j+1});
                for (Tiles tiles : verticalConnection.get(0)) {
                    finale[l+i][k+j]= tiles;
                    l++;
                }
                Movementpoints.add(new Integer[]{l+i-2,k+j+1});

            }
            else if (k==(size_up-3)){
                Movementpoints.add(new Integer[]{l+i+1,k+j-1});
                for (Tiles tiles : verticalConnection.get(2)) {
                    finale[l+i][k+j]= tiles;
                    l++;
                }
                Movementpoints.add(new Integer[]{l+i-2,k+j-1});
            }
            else {
                for (Tiles tiles : verticalConnection.get(1)) {
                    finale[l+i][k+j]= tiles;
                    l++;
                }
            }
        }
    }
    private void roomfill(int i, int j){
        int l=0;
        for (Tiles[] tiles : Room) {
            int k =0;
            for (Tiles tile : tiles) {
                finale[i+l][j+k]= tile;
                k++;
            }
            l++;
        }
        LodingPoints.add(new Integer[]{i+(int)Math.floor((float)size_up/2),j+(int)Math.floor((float)size_up/2)});
    }

    public Tiles[][] generate(int lenght, int hight){
        Random r = new Random();
        this.finale = new Tiles[lenght*size_up][hight*size_up];

        unvisitedcell = new ArrayList<>();
        maze= new int[lenght][hight];
        bitmaze = new boolean[lenght][hight];
        startingpoint = new int[]{r.nextInt(lenght),r.nextInt(hight)};
        bitmaze[startingpoint[0]][startingpoint[1]] = true;


        for (int i = 0; i < bitmaze.length; i++){
            for (int j = 0; j < bitmaze[0].length; j++){
                if(!bitmaze[i][j]){
                    unvisitedcell.add(new int[]{i,j});
                    maze[i][j]=99;
                }
                if (bitmaze[i][j]) maze[i][j]=9;
            }
        }

        int kezd=0;
        while (unvisitedcell.size()>0) {
            kezd =r.nextInt(unvisitedcell.size());
            int cellx = unvisitedcell.get(kezd)[0];
            int celly = unvisitedcell.get(kezd)[1];
            while (!bitmaze[cellx][celly]){
                int foo = r.nextInt(4);
                maze[cellx][celly] = foo;
                //0 =fel
                //1=le
                //2=jobb
                //3=bal
                if (foo == 0 && cellx -1 >= 0)                  cellx-- ;
                if (foo == 1 && cellx < maze.length-1)          cellx++;
                if (foo == 2 && celly < maze[0].length-1)       celly++;
                if (foo == 3 && celly -1 >= 0)                  celly--;
            }
            cellx = unvisitedcell.get(kezd)[0];
            celly = unvisitedcell.get(kezd)[1];
            while (!bitmaze[cellx][celly]){
                bitmaze[cellx][celly] = true;
                switch (maze[cellx][celly]){
                    case 0:cellx--; break;
                    case 1:cellx++; break;
                    case 2:celly++; break;
                    case 3:celly--; break;

                }
            }
            unvisitedcell.clear();
            for (int i = 0; i < bitmaze.length; i++){
                for (int j = 0; j < bitmaze[0].length; j++){
                    if(!bitmaze[i][j]){
                        unvisitedcell.add(new int[] {i,j});
                        maze[i][j]=0;
                    }
                }
            }

        }
        int mod =(int) Math.pow(size_up,2);
        int number =0;
        for (int i = 0; i < finale.length; i+=size_up) {
            for (int j = 0; j < finale[0].length; j+=size_up) {
                roomfill(i,j);
            }
        }
        number = 0;
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < hight; j++) {
                switch (maze[i][j]){
                    case 0: //fel
                        insterVertical((i*size_up)-2,(j*size_up)+1);
                        break;
                    case 1://le
                        insterVertical((i*size_up)+(size_up-2),(j*size_up)+1);
                        break;
                    case 2: //jobbra
                        insterHorozontal((i*size_up)+1,(j*size_up)+(size_up-2));
                        break;
                    case 3://balra
                        insterHorozontal((i*size_up)+1,(j*size_up)-2);
                        break;
                }
            }
        }
        Log.println(Log.ERROR,"Bakos vagyok", Arrays.deepToString(maze));

        for (Tiles[] a: finale) {
            Log.println(Log.ERROR,"final maze",Arrays.toString(a)+"\n");
        }
        return finale;
    }

    public ArrayList<Integer[]> getMovementpoints() {
        return Movementpoints;
    }
    public int[] getStartingpoint() {
        return startingpoint;
    }

    public ArrayList<Integer[]> getLodingPoints() {
        return LodingPoints;
    }
    private int getRandomNumberFromArray(int[] a){
        Random r = new Random();
        return a[r.nextInt(a.length)];
    }
    public float getLoadingDistance(){
        return (float)Math.ceil( Math.sqrt(Math.pow((double) size_up/2,2)*2))*Specifications.blocksize;
    }

    public int getSize_up() {
        return size_up;
    }

}

