package com.example.myapplication;


import java.util.Objects;
import java.util.Random;

public class Spawner extends StaticObject{
    SpawnerTypes type;
    public Spawner(int resourceID, int width, int height) {
        super(new SpriteSheets(resourceID,width,height,4));
        type = randomEnum(SpawnerTypes.class);
    }
    public void spawn(){
        Game.getInstance().addEnemy(new FlyingFire(this.ownPositionM));
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

}
