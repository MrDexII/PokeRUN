package com.andrew.JavaGame.entity;

import com.andrew.JavaGame.audio.JukeBox;
import com.andrew.JavaGame.tileMap.TileMap;

import java.awt.*;

public class Enemy extends MapObject {

    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int damage;

    protected boolean flinching;
    protected long flinchCount;

    public Enemy(TileMap tm) {
        super(tm);
        //	remove = false;
        right = true;
    }

    public boolean isDead() {
        return dead;
    }
    //public boolean shouldRemove() { return remove; }

    public int getDamage() {
        return damage;
    }

    public void hit(int damage) {
        if (dead || flinching) return;
        JukeBox.play("kniveHits");
        ;
        health -= damage;
        if (health < 0) health = 0;
        if (health == 0) dead = true;
        //	if(dead) remove = true;
        flinching = true;
        flinchCount = 10;
    }

    protected void getNextPosition() {
        if (left) dx = -moveSpeed;
        else if (right) dx = moveSpeed;
        else dx = 0;
        if (falling) {
            dy += fallSpeed;
            if (dy > maxFallSpeed) dy = maxFallSpeed;
        }
        if (jumping && !falling) {
            dy = jumpStart;
        }
    }

    public void update() {
        getNextPosition();
        checkTileMapCollision();
        calculateCorners(x, ydest + 1);
        if (!bottomLeft) {
            left = false;
            right = facingRight = true;
        }
        if (!bottomRight) {
            left = true;
            right = facingRight = false;
        }
        setPosition(xtemp, ytemp);

        if (dx == 0) {
            left = !left;
            right = !right;
            facingRight = !facingRight;
        }
        //check flinching
        if (flinching) {
            long elapsed =
                    (System.nanoTime() - flinchCount) / 1000000;
            if (elapsed > 400) {
                flinching = false;
            }
        }
        // update animation
        super.update();
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }
}
