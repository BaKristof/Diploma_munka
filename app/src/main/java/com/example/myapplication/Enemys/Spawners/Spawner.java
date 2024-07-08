package com.example.myapplication.Enemys.Spawners;


import com.example.myapplication.Enemys.EnemyCharacterWithOwner;
import com.example.myapplication.Enemys.FlyingFire;
import com.example.myapplication.MainClasses.Game;
import com.example.myapplication.MainClasses.SpriteSheets;
import com.example.myapplication.SuperClasses.StaticObject;

import java.util.Objects;
import java.util.Random;

public class Spawner extends StaticObject {
    SpawnerTypes type;
    public Spawner(int resourceID, int width, int height) {
        super(new SpriteSheets(resourceID,width,height,4));
        type = randomEnum(SpawnerTypes.class);
    }

    public Spawner() {
        super();
    }

    public void spawn(){
        if(codition()){
        Game.getInstance().addEnemy(new EnemyCharacterWithOwner(new FlyingFire(this.ownPositionM),this));
        }
    }
    public Spawner setPosition (float[] position){
        this.setOwnPositionM(position);
        return this;
    }
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        Random random = new Random();
        int x = random.nextInt(Objects.requireNonNull(clazz.getEnumConstants()).length);
        return clazz.getEnumConstants()[x];
    }
    private  boolean codition() {
        return  Game.getInstance().enemys.stream().filter(n ->n.getOwner()==this).count()<2; //todo át kéne alakitani
    }

}
