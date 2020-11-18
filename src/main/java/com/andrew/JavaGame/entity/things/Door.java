package com.andrew.JavaGame.entity.things;

import com.andrew.JavaGame.entity.MapObject;
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

        spritesPath = "/Sprites/Player/door.gif";
        countOfFrames = 1;
        animationDelay = 1;

        loadSprites();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }
}