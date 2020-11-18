package com.andrew.JavaGame.entity.enemies;

import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.tileMap.TileMap;

public class Flame extends Enemy {

    public Flame(TileMap tm) {
        super(tm);

        moveSpeed = 0.5;
        maxSpeed = 0.5;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 180;
        height = 85;
        cwidth = 180;
        cheight = 85;

        health = maxHealth = 20000;
        damage = 10;
        up = true;

        spritesPath = "/Sprites/Enemies/flame.gif";
        countOfFrames = 3;
        animationDelay = 10;

        loadSprites();
    }

    @Override
    protected void getNextPosition() {
    }

    @Override
    public void update() {
        animation.update();
    }
}
