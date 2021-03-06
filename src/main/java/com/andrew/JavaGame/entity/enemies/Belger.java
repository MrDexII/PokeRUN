package com.andrew.JavaGame.entity.enemies;

import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.tileMap.TileMap;

public class Belger extends Enemy {
    public Belger(TileMap tm) {
        super(tm);

        moveSpeed = 0.7;
        maxSpeed = 1;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 36;
        height = 35;
        cwidth = 36;
        cheight = 35;

        health = maxHealth = 20;
        damage = 3;

        spritesPath = "/Sprites/Enemies/belger.gif";
        countOfFrames = 7;
        animationDelay = 10;

        loadSprites();
    }
}
