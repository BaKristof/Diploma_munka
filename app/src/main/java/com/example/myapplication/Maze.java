package com.example.myapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Maze {
   static int[][] maze;
   static boolean[][] bitmaze;
   static Random r = new Random();
   static List<int[]> unvisitedcell;
   public int[] startingpoint;
   public static final int size_up=5;
   public ArrayList<int[]> Movementpoints = new ArrayList<>();
   public ArrayList<int[]>LodingPoints = new ArrayList<>();

   int[] leftwall = new int[]{3,4,5};
    int[] righttwall = new int[]{12,13,14};
    int[] topmwall = new int[]{10,11,2};
    int[] bottomwall = new int[]{7,8,9};







    public int[][] generate(int lenght,int hight){

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

/*
        if (number%mod==(mod-size_up)-(size_up-1));//balalsó belső
        if (number%mod==(mod-(size_up*2)+1));//balalsó belső


        if (number%mod==((size_up*2)-1));//jobbfelső belső
        if (number%mod==size_up+1);//balfelső belső


        if ((number%mod)>0&&(number%mod)<size_up);//felsőfal
        if (number%size_up==0);//balfal
        if (number%size_up==4);//jobbfal
        if ((number%mod)>(mod-size_up)&&(number%mod)<mod);//felsőfal

        //sarok
        if (number%mod==0);//balfelső
        if (number%mod==(mod-1));//jobbalso
        if (number%mod==size_up);//jobbfelső
        if (number%mod==(mod-size_up));//balalsó



        */
        int[][] finale = new int[lenght*size_up][hight*size_up];
        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < hight; j++) {
               /* for (int k = 0; k < size_up; k++) {
                    for (int l = 0; l < size_up; l++) {





                        //4 olad falai
                        if(k>0 && l==0) finale[i+k][j+l]=getRandomNumberFromArray(topmwall);
                        if(k==0 && l>0) finale[i+k][j+l]=getRandomNumberFromArray(leftwall);
                        if(k==size_up-1 && l>0) finale[i+k][j+l]=getRandomNumberFromArray(bottomwall);
                        if(k>0 && l==size_up-1) finale[i+k][j+l]=getRandomNumberFromArray(righttwall);

                        //4 sraok
                        if(k==0 && l==0) finale[i+k][j+l]=4;
                        if(k==size_up-1 && l==0) finale[i+k][j+l]=6;
                        if(k==0 && l==size_up-1) finale[i+k][j+l]=12;
                        if(k==size_up-1 && l==size_up-1) finale[i+k][j+l]=0;


                    }
                }*/
                
                
                
                
                
                finale[i*5][j*5] =4;    finale[i*5][j*5+1] =2;      finale[i*5][j*5+2] =10;     finale[i*5][j*5+3] =11;     finale[i*5][j*5+4] =12;
                finale[i*5+1][j*5] =3;  finale[i*5+1][j*5+1] =16;   finale[i*5+1][j*5+2] =21;   finale[i*5+1][j*5+3] =20;   finale[i*5+1][j*5+4] =13;
                finale[i*5+2][j*5] =5;  finale[i*5+2][j*5+1] =18;   finale[i*5+2][j*5+2] =19;   finale[i*5+2][j*5+3] =22;   finale[i*5+2][j*5+4] =14;
                finale[i*5+3][j*5] =3;  finale[i*5+3][j*5+1] =15;   finale[i*5+3][j*5+2] =17;   finale[i*5+3][j*5+3] =23;   finale[i*5+3][j*5+4] =13;
                finale[i*5+4][j*5] =6;  finale[i*5+4][j*5+1] =7;    finale[i*5+4][j*5+2] =8;    finale[i*5+4][j*5+3] =7;    finale[i*5+4][j*5+4] =0;
                LodingPoints.add(new int[]{i*5+2,j*5+2});
            }
        }

        for (int i = 0; i < lenght; i++) {
            for (int j = 0; j < hight; j++) {
                switch (maze[i][j]){
                    case 0: //fel

                                                    finale[i*5-2][j*5+2] =19;
                       finale[i*5-1][j*5+1] =26;    finale[i*5-1][j*5+2] =19;   finale[i*5-1][j*5+3] =25;
                                                    finale[i*5][j*5+2] =19;
                                                    finale[i*5+1][j*5+2] =19;
                       Movementpoints.add(new int[]{i*5-1,j*5+2});
                       Movementpoints.add(new int[]{i*5,j*5+2});
                        break;
                    case 1://le
                                                        finale[i*5+3][j*5+2] =19;
                          finale[i*5+4][j*5+1] =26;     finale[i*5+4][j*5+2] =19;   finale[i*5+4][j*5+3] =25;
                                                        finale[i*5+5][j*5+2] =19;
                                                        finale[i*5+6][j*5+2] =19;
                        Movementpoints.add(new int[]{i*5+4,j*5+2});
                        Movementpoints.add(new int[]{i*5+5,j*5+2});
                        break;
                    case 2: //jobbra
                                                    finale[i*5+1][j*5+4] =10;   finale[i*5+1][j*5+5] =11;
                        finale[i*5+2][j*5+3] =19;   finale[i*5+2][j*5+4] =21;   finale[i*5+2][j*5+5] =21;   finale[i*5+2][j*5+6] =19;
                                                    finale[i*5+3][j*5+4] =27;   finale[i*5+3][j*5+5] =28;
                        Movementpoints.add(new int[]{i*5+2,j*5+4});
                        Movementpoints.add(new int[]{i*5+2,j*5+5});
                        break;
                    case 3://balra
                                                   finale[i*5+1][j*5-1] =11;  finale[i*5+1][j*5] =10;
                        finale[i*5+2][j*5-2] =19;  finale[i*5+2][j*5-1] =21;  finale[i*5+2][j*5] =21;  finale[i*5+2][j*5+1] =19;
                                                   finale[i*5+3][j*5-1] =27;  finale[i*5+3][j*5] =28;
                        Movementpoints.add(new int[]{i*5+2,j*5-1});
                        Movementpoints.add(new int[]{i*5+2,j*5});
                        break;
                }
            }
        }
        Log.println(Log.ERROR,"Bakos vagyok", Arrays.deepToString(maze));

        for (int[] a: finale) {
            Log.println(Log.ERROR,"final maze",Arrays.toString(a)+"\n");
        }

        //Log.println(Log.ERROR,"Bakos vagyok", Arrays.deepToString(finale));

        return finale;
    }

    public ArrayList<int[]> getMovementpoints() {
        return Movementpoints;
    }
    public int[] getStartingpoint() {
        return startingpoint;
    }

    public ArrayList<int[]> getLodingPoints() {
        return LodingPoints;
    }
    private int getRandomNumberFromArray(int[] a){
        Random r = new Random();
        return a[r.nextInt(a.length)];
    }
}

