package com.andrew.JavaGame.entity.things;

import com.andrew.JavaGame.entity.Animation;
import com.andrew.JavaGame.entity.MapObject;
import com.andrew.JavaGame.tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Explosion extends MapObject {
    public Explosion(TileMap tm) {
        super(tm);

        width = 57;
        height = 61;

        spritesPath = "/Sprites/Enemies/explosion1.gif";
        countOfFrames = 7;
        animationDelay = 6;

        loadSprites();
    }

    public void update() {
        if (animation.hasPlayedOnce()) {
            remove = true;
        }
        super.update();
    }

//    public void setMapPosition(int x, int y) {
//        xmap = x;
//        ymap = y;
//    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
//        g.drawImage(
//                animation.getImage(),
//                x + xmap - width / 2,
//                y + ymap - height / 2,
//                null);
    }
}
