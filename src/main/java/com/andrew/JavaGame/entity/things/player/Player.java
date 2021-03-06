package com.andrew.JavaGame.entity.things.player;

import com.andrew.JavaGame.audio.JukeBox;
import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.entity.MapObject;
import com.andrew.JavaGame.entity.things.FireBall;
import com.andrew.JavaGame.tileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Player extends MapObject {
    // player stuff
    private int health;
    private final int maxHealth;
    private int fire;
    private final int maxFire;
    private boolean flinching;
    private long flinchCount;

    // fireball
    private boolean firing;
    private final int fireCost;
    private final int fireBallDamage;
    private List<FireBall> fireBalls;

    // scratch
    private boolean scratching;
    private final int scratchDamage;
    private final int scratchRange;

    private boolean teleporting;

    // animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] NUMFRAMES = {
            1, 6, 4, 4, 3, 2, 1, 3
    };
    private final int[] FRAMEWIDTHS = {
            23, 19, 19, 25, 32, 31, 23, 37
    };
    private final int[] FRAMEHEIGHTS = {
            33, 33, 37, 31, 34, 32, 33, 29
    };
    private final int[] SPRITEDELAYS = {
            1, 5, 9, 9, 7, 5, 5, 27
    };

    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int FIREBALL = 4;
    private static final int SCRATCHING = 5;
    private static final int TELEPORTING = 6;
    private static final int DYING = 7;

    public Player(TileMap tm) {
        super(tm);

        // animation actions
        Rectangle ar = new Rectangle(0, 0, 0, 0);
        ar.width = 30;
        ar.height = 20;

        Rectangle cr = new Rectangle(0, 0, 0, 0);
        cr.width = 50;
        cr.height = 40;

        width = 40;
        height = 40;
        cwidth = 30;
        cheight = 30;

        moveSpeed = 0.3;
        maxSpeed = 3.0;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -5.3;
        stopJumpSpeed = 0.3;

        facingRight = true;

        health = 5;
        maxHealth = 5;
        fire = maxFire = 1000;

        fireCost = 200;
        fireBallDamage = 1;
        fireBalls = new ArrayList<>();

        scratchDamage = 1;
        scratchRange = 50;

        spritesPath = "/Sprites/Player/playersprites.gif";

        loadSprites();

        // load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(spritesPath));
            int count = 0;
            sprites = new ArrayList<>();
            for (int i = 0; i < NUMFRAMES.length; i++) {
                BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
                for (int j = 0; j < NUMFRAMES[i]; j++) {
                    bi[j] = spritesheet.getSubimage(
                            j * FRAMEWIDTHS[i],
                            count,
                            FRAMEWIDTHS[i],
                            FRAMEHEIGHTS[i]
                    );
                }
                sprites.add(bi);
                count += FRAMEHEIGHTS[i];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setAnimation(IDLE);

        JukeBox.load("/SFX/kniveThrow.mp3.mp3", "kniveThrow");
        JukeBox.load("/SFX/weGotHit.mp3.mp3", "weGotHit");
        JukeBox.load("/SFX/punch.mp3.mp3", "punch");
        JukeBox.load("/SFX/jumpCharacter.mp3.mp3", "jumpCharacter");
        JukeBox.load("/SFX/jumpLanding.mp3.mp3", "jumpLanding");
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setTeleporting(boolean b) {
        teleporting = b;
    }

    public int getFire() {
        return fire;
    }

    public int getMaxFire() {
        return maxFire;
    }

    public void loseLife() {
        health--;
    }

    public int getLife() {
        return health = 5;
    }

    public void setFiring() {
        JukeBox.play("kniveThrow");
        firing = true;
    }

    public void setScratching() {
        JukeBox.play("punch");
        scratching = true;
    }

    public void setDead() {
        health = 0;
        stop();
    }

    public void setHealth(int i) {
        health = i;
    }

    public void checkAttack(ArrayList<Enemy> enemies) {
        // loop through enemies
        for (Enemy e : enemies) {
            //scratch attack
            if (scratching) {
                if (facingRight) {
                    if (
                            e.getx() > x &&
                                    e.getx() < x + scratchRange &&
                                    e.gety() > y - height / 2 &&
                                    e.gety() < y + height / 2
                    ) {
                        e.hit(scratchDamage);
                    }
                } else {
                    if (
                            e.getx() < x &&
                                    e.getx() > x - scratchRange &&
                                    e.gety() > y - height / 2 &&
                                    e.gety() < y + height / 2
                    ) {
                        e.hit(scratchDamage);
                    }
                }
            }

            // fireballs
            for (FireBall fireBall : fireBalls) {
                if (fireBall.intersects(e)) {
                    e.hit(fireBallDamage);
                    fireBall.setHit();
                    break;
                }
            }

            // check enemy collisions
            if (intersects(e)) {
                hit(e.getDamage());
            }
        }
    }

    public void hit(int damage) {
        if (flinching) return;
        JukeBox.play("weGotHit");
        health -= damage;
        if (health < 0) health = 0;
        flinching = true;
        flinchCount = 0;
    }

    public void reset() {
        health = maxHealth;
        facingRight = true;
        currentAction = -1;
        stop();
    }

    public void stop() {
        left = right = up = down = flinching = jumping = scratching = false;
    }

    private void getNextPosition() {
        // movement
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if (dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }
        // cannot move while attacking, except in air
        if ((currentAction == SCRATCHING || currentAction == FIREBALL) && !(jumping || falling)) {
            dx = 0;
        }

        // cannot firing while scratching
        if (scratching) {
            firing = false;
        }

        // jumping
        if (jumping && !falling) {
            dy = jumpStart;
            falling = true;
            JukeBox.play("jumpCharacter");
        }

        // falling
        if (falling) {
            if (dy > 0) dy += fallSpeed;
            else dy += fallSpeed;

            if (dy > 0) jumping = false;
            if (dy < 0 && !jumping) dy += stopJumpSpeed;

            if (dy > maxFallSpeed) dy = maxFallSpeed;
        }
    }

    private void setAnimation(int i) {
        currentAction = i;
        animation.setFrames(sprites.get(currentAction));
        animation.setDelay(SPRITEDELAYS[currentAction]);
        width = FRAMEWIDTHS[currentAction];
        height = FRAMEHEIGHTS[currentAction];
    }

    public void update() {
        // update position
        boolean isFalling = falling;
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp, ytemp);
        if (isFalling && !falling) {
            JukeBox.play("jumpLanding");
        }

        //check attack has stopped
        if (currentAction == SCRATCHING) {
            if (animation.hasPlayedOnce()) scratching = false;
        }
        if (currentAction == FIREBALL) {
            if (animation.hasPlayedOnce()) firing = false;
        }
        if (dx == 0) x = (int) x;

        // fireball attack
        fire += 1;
        if (fire > maxFire) fire = maxFire;
        if (firing && currentAction != FIREBALL) {
            if (fire > fireCost) {
                fire -= fireCost;
                FireBall fb = new FireBall(tileMap, facingRight);
                fb.setPosition(x, y);
                fireBalls.add(fb);
            }
        }

        // update fireballs
        for (int i = 0; i < fireBalls.size(); i++) {
            fireBalls.get(i).update();
            if (fireBalls.get(i).shouldRemove()) {
                fireBalls.remove(i);
                i--;
            }
        }

        // check done flinching
        if (flinching) {
            flinchCount++;
            if (flinchCount > 120) {
                flinching = false;
            }
        }

        if (scratching) {
            if (currentAction != SCRATCHING) {
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(SPRITEDELAYS[SCRATCHING]);
                width = FRAMEWIDTHS[SCRATCHING];
                height = FRAMEHEIGHTS[SCRATCHING];
            }
        } else if (teleporting) {
            if (currentAction != TELEPORTING) {
                currentAction = TELEPORTING;
                animation.setFrames(sprites.get(TELEPORTING));
                animation.setDelay(SPRITEDELAYS[6]);
                width = FRAMEWIDTHS[6];
                height = FRAMEHEIGHTS[6];
            }
        } else if (firing) {
            if (currentAction != FIREBALL) {
                currentAction = FIREBALL;
                animation.setFrames(sprites.get(FIREBALL));
                animation.setDelay(SPRITEDELAYS[4]);
                width = FRAMEWIDTHS[4];
                height = FRAMEHEIGHTS[4];
            }
        } else if (dy > 0) {
            if (currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(SPRITEDELAYS[3]);
                width = FRAMEWIDTHS[3];
                height = FRAMEHEIGHTS[3];
            }
        } else if (dy < 0) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(SPRITEDELAYS[2]);
                width = FRAMEWIDTHS[2];
                height = FRAMEHEIGHTS[2];
            }
        } else if (left || right) {
            if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(SPRITEDELAYS[1]);
                width = FRAMEWIDTHS[1];
                height = FRAMEHEIGHTS[1];
            }
        } else if (health == 0) {
            if (currentAction != DYING) {
                currentAction = DYING;
                animation.setFrames(sprites.get(DYING));
                animation.setDelay(SPRITEDELAYS[7]);
                width = FRAMEWIDTHS[7];
                height = FRAMEHEIGHTS[7];
            }
        } else {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(SPRITEDELAYS[0]);
                width = FRAMEWIDTHS[0];
                height = FRAMEHEIGHTS[0];
            }
        }

        // set direction
        if (currentAction != SCRATCHING && currentAction != FIREBALL) {
            if (right) facingRight = true;
            if (left) facingRight = false;
        }

        super.update();
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        // draw fireballs
        for (MapObject fireBall : fireBalls) {
            fireBall.draw(g);
        }
        // draw player
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchCount) / 1000000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }
        super.draw(g);
    }
}