package com.andrew.JavaGame.entity.enemies;

import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.tileMap.TileMap;

public class Arni extends Enemy {
    public Arni(TileMap tm) {
        super(tm);

        moveSpeed = 3;
        maxSpeed = 1;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 40;
        height = 43;
        cwidth = 40;
        cheight = 43;

        health = maxHealth = 9;
        damage = 2;

        spritesPath = "/Sprites/Enemies/arni.gif";
        countOfFrames = 7;
        animationDelay = 8;

        loadSprites();
    }
}
