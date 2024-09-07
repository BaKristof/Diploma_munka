package com.example.myapplication.Player;

import com.example.myapplication.BackGround.BG;
import com.example.myapplication.BackGround.Maze;
import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.R;
import com.example.myapplication.SuperClasses.Character;

public class Player extends Character implements PlayerListener {

    public float[] movingangle;
    public int health =100;
    public Player(BG bg, Maze maze) {
        super();
        setSpriteSheets(R.drawable.spritesheet_main_charater,64,64);
        setOwnPositionM(bg.getboxmidel(maze.startingpoint));
    }

    @Override
    public String getName(){return "Playes";}
    public float[] getMovingangle() {
        return movingangle;
    }

    public void setMovingangle(float[] movingangle) {
        this.movingangle = movingangle;
    }

        @Override
        public void playerAction(PlayerActions playerAction) {
            switch (playerAction){
                case Hit: Hit(); break;
                case Teleport: break;
            }
        }
        private void Hit(){
            health=-10;
            Game.getGuiListener().setHealthBar(health);
        }

    /*public float[] movemntprediction(FlyingFire enemy){
        float distance = this.distance(enemy);
        float[] dxdy = this.dxdy(enemy);
        float angle =(float) Math.atan2(movingangle[1], movingangle[0]);
        float angle2 =(float) Math.atan2(dxdy[1], dxdy[0]);
        float finalangle = angle-angle2;
        //todo implementálni kéne a dolgot de lehet kicsit bonyi
        return new float[]{0.0f,0.0f};
    }*/

}
