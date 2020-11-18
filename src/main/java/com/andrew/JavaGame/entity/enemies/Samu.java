package com.andrew.JavaGame.entity.enemies;

import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.tileMap.TileMap;

public class Samu extends Enemy {
    public Samu(TileMap tm) {
        super(tm);

        moveSpeed = 5;
        maxSpeed = 1;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 51;
        height = 44;
        cwidth = 51;
        cheight = 30;

        health = maxHealth = 30;
        damage = 3;

        spritesPath = "/Sprites/Enemies/samu.gif";
        countOfFrames = 9;
        animationDelay = 10;

        loadSprites();
    }
}
