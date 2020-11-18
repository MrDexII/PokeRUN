package com.andrew.JavaGame.entity.enemies;

import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.tileMap.TileMap;

public class Bandit extends Enemy {
    public Bandit(TileMap tm) {
        super(tm);

        moveSpeed = 0.6;
        maxSpeed = 1;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;

        width = 38;
        height = 44;
        cwidth = 38;
        cheight = 44;

        health = maxHealth = 7;
        damage = 2;

        spritesPath = "/Sprites/Enemies/bandit.gif";
        countOfFrames = 9;
        animationDelay = 10;

        loadSprites();
    }
}
