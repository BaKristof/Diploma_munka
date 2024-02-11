package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Animation {
    //TODO movement in different direction
    //TODO use animation by sprite sheet
    List<Integer> animation;
    int counter;
    public Animation(int[] TextureResource ) {
        animation = new ArrayList<>();
        for (int foo : TextureResource) {
            animation.add(MyGLRenderer.loadTexture(foo));
        }
        counter = 0;
    }
    public int NextFrame(){
        int a = animation.get(counter);
        counter ++;
        return a;
    }
}
