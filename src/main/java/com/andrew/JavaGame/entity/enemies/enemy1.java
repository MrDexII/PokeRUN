package com.andrew.JavaGame.entity.enemies;


import com.andrew.JavaGame.entity.Animation;
import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class enemy1 extends Enemy {

    private BufferedImage[] sprites;

    public enemy1(TileMap tm) {

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

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream(
                            "/Sprites/Enemies/Samu.gif"
                    )
            );
            sprites = new BufferedImage[9];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(10);

        right = true;
        facingRight = true;

    }

    private void getNextPosition() {
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
        animation.update();

    }

    public void draw(Graphics2D g) {

        setMapPosition();

        super.draw(g);

    }

}
