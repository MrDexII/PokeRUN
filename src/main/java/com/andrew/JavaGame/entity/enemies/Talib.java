package com.andrew.JavaGame.entity.enemies;

import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.tileMap.TileMap;

public class Talib extends Enemy {
    public Talib(TileMap tm) {
        super(tm);

        moveSpeed = 3;
        maxSpeed = 1;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 33;
        height = 35;
        cwidth = 33;
        cheight = 35;

        health = maxHealth = 3;
        damage = 2;

        spritesPath = "/Sprites/Enemies/talib.gif";
        countOfFrames = 7;
        animationDelay = 15;

        loadSprites();
    }
}
