package com.andrew.JavaGame.entity;

import com.andrew.JavaGame.tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Door extends MapObject {
    public Door(TileMap tm) {
        super(tm);

        facingRight = true;
        width = 30;
        height = 60;
        cwidth = 50;
        cheight = 60;

        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(
                    "/Sprites/Player/door.gif"));
            BufferedImage[] sprites = new BufferedImage[1];
            sprites[0] = spritesheet.getSubimage(0, 0, width, height);
            animation.setFrames(sprites);
            animation.setDelay(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        animation.update();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }
}