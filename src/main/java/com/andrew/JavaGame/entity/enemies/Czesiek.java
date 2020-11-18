package com.andrew.JavaGame.entity.enemies;

import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.tileMap.TileMap;

public class Czesiek extends Enemy {
    public Czesiek(TileMap tm) {
        super(tm);

        moveSpeed = 1.5;
        maxSpeed = 0.7;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 40;
        height = 43;
        cwidth = 40;
        cheight = 43;

        health = maxHealth = 4;
        damage = 1;

        spritesPath = "/Sprites/Enemies/czesiek.gif";
        countOfFrames = 5;
        animationDelay = 6;

        loadSprites();
    }
}
