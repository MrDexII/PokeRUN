package com.andrew.JavaGame.entity.things;

import com.andrew.JavaGame.entity.MapObject;
import com.andrew.JavaGame.tileMap.TileMap;

import java.awt.*;

public class Explosion extends MapObject {
    public Explosion(TileMap tm) {
        super(tm);

        width = 57;
        height = 61;

        spritesPath = "/Sprites/Enemies/explosion1.gif";
        countOfFrames = 7;
        animationDelay = 7;

        loadSprites();
    }

    public void update() {
        super.update();
        if (animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);
    }
}
