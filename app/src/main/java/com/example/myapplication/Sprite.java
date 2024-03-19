package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Sprite {
    private Bitmap spriteSheet;
    private final int frameWidth;
    private final int frameHeight;
    private int frameCount;
    private int currentFrame;

    public Sprite(Bitmap spriteSheet, int frameWidth, int frameHeight) {
        this.spriteSheet = spriteSheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameCount = (spriteSheet.getWidth() / frameWidth) * (spriteSheet.getHeight() / frameHeight);
        this.currentFrame = 0;
    }

    public void update() {
        currentFrame = (currentFrame + 1) % frameCount;
    }

    public Rect getSrcRect() {
        int row = currentFrame / (spriteSheet.getWidth() / frameWidth);
        int col = currentFrame % (spriteSheet.getWidth() / frameWidth);

        return new Rect(col * frameWidth, row * frameHeight, (col + 1) * frameWidth, (row + 1) * frameHeight);
    }

    public Rect getDestRect(int width, int height) {
        return new Rect(0, 0, width, height);
    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }
}
