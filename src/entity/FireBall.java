package entity;

import tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FireBall extends MapObject {

    private boolean hit;
    private boolean remove;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;

    public FireBall(TileMap tm, boolean right) {

        super(tm);

        facingRight = right;

        moveSpeed = 3.8;
        if (right) dx = moveSpeed;
        else dx = -moveSpeed;

        width = 14;
        height = 14;
        cwidth = 14;
        cheight = 14;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(
                    getClass().getResourceAsStream("/Sprites/Player/knife.gif")
            );
            sprites = new BufferedImage[4];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(
                        i * width,
                        0,
                        width,
                        height
                );

                hitSprites = new BufferedImage[3];
                for (int i1 = 0; i1 < hitSprites.length; i1++) {
                    hitSprites[i1] = spritesheet.getSubimage(
                            i1 * width,
                            height,
                            width,
                            height
                    );
                }
                animation = new Animation();
                animation.setFrames(sprites);
                animation.setDelay(10);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHit() {
        if (hit) return;
        hit = true;
        animation.setFrames(hitSprites);
        animation.setDelay(1);
        dx = 0;
    }

    public boolean shoulRemove() {
        return remove;
    }

    public void update() {

        checkTileMapCollision();
        setPosition(xtemp, ytemp);

        if (dx == 0 && !hit) {
            setHit();
        }

        animation.update();
        if (hit && animation.hasPlayedOnce()) {
            remove = true;
        }

    }

    public void draw(Graphics2D g) {

        setMapPosition();

        super.draw(g);
    }
}
