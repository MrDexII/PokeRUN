package com.andrew.JavaGame.gameState;

import com.andrew.JavaGame.GamePanel;
import com.andrew.JavaGame.audio.JukeBox;
import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.entity.HUD;
import com.andrew.JavaGame.entity.MapObject;
import com.andrew.JavaGame.entity.things.Explosion;
import com.andrew.JavaGame.entity.things.Teleport;
import com.andrew.JavaGame.entity.things.player.Player;
import com.andrew.JavaGame.entity.things.player.PlayerSave;
import com.andrew.JavaGame.handlers.Keys;
import com.andrew.JavaGame.tileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractLevelState extends GameState {
    protected Player player;
    protected TileMap tileMap;
    protected ArrayList<Enemy> enemies;
    protected ArrayList<Explosion> explosions;

    protected HUD hud;
    protected Teleport teleport;

    //events
    protected boolean blockInput = false;
    protected int eventCount = 0;
    protected boolean eventStart;
    protected int nextGameState;
    protected int currentGameState;
    protected boolean eventFinish;
    protected boolean eventDead;
    protected ArrayList<Rectangle> tb;


    public AbstractLevelState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        // explosions
        explosions = new ArrayList<>();

        hud = new HUD(player);

        // start event
        eventStart = true;
        tb = new ArrayList<Rectangle>();
        eventStart();
    }

    protected abstract void populateEnemies();

    public void update() {
        // check keys
        handleInput();

        // check if end of level event should start
        if (teleport.contains(player)) {
            eventFinish = blockInput = true;
        }
        // check if player dead event should start
        if (player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
            eventDead = blockInput = true;
        }
        // play events
        if (eventStart) eventStart();
        if (eventDead) eventDead();
        if (eventFinish) eventFinish();

        // update player
        player.update();

        // update tilemap
        tileMap.setPosition(
                GamePanel.WIDTH / 2 - player.getx(),
                GamePanel.HEIGHT / 2 - player.gety()
        );

        // update all enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                JukeBox.play("enemyDies");
                enemies.remove(i);
                i--;
                MapObject explosion = new Explosion(tileMap);
                explosion.setPosition(e.getx(), e.gety());
                explosions.add((Explosion) explosion);
            }
        }

        // update explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).shouldRemove()) {
                explosions.remove(i);
                i--;
            }
        }
        // attack enemies
        player.checkAttack(enemies);

        // update teleport
        teleport.update();
    }

    public void draw(Graphics2D g) {
        // draw player
        player.draw(g);

        // draw enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        // draw explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g);
        }
        // draw teleport
        teleport.draw(g);

        // draw hud
        hud.draw(g);

        // draw transition boxes
        g.setColor(java.awt.Color.BLACK);
        for (int i = 0; i < tb.size(); i++) {
            g.fill(tb.get(i));
        }

    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
        if (blockInput || player.getHealth() == 0) return;
        player.setUp(Keys.keyState[Keys.UP]);
        player.setLeft(Keys.keyState[Keys.LEFT]);
        player.setDown(Keys.keyState[Keys.DOWN]);
        player.setRight(Keys.keyState[Keys.RIGHT]);
        player.setJumping(Keys.keyState[Keys.BUTTON1]);
        if (Keys.isPressed(Keys.BUTTON3)) player.setFiring();
        if (Keys.isPressed(Keys.BUTTON2)) player.setScratching();
    }

    //events

    // reset level
    protected void reset() {
        populateEnemies();
        blockInput = true;
        eventCount = 0;
        eventStart = true;
        eventStart();
    }

    // level started
    private void eventStart() {
        eventCount++;
        if (eventCount == 1) {
            tb.clear();
            tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
            tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
            tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
        }
        if (eventCount > 1 && eventCount < 60) {
            tb.get(0).height -= 4;
            tb.get(1).width -= 6;
            tb.get(2).y += 4;
            tb.get(3).x += 6;
        }
        if (eventCount == 60) {
            eventStart = blockInput = false;
            eventCount = 0;
            tb.clear();
        }
    }

    // player has died
    private void eventDead() {
        eventCount++;
        if (eventCount == 1) {
            player.setDead();
            player.stop();
        }
        if (eventCount == 60) {
            tb.clear();
            tb.add(new Rectangle(
                    GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 60) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
        }
        if (eventCount >= 120) {
            if (player.getHealth() == 0) {
                gsm.setState(currentGameState);
            } else {
                eventDead = blockInput = false;
                eventCount = 0;
                player.loseLife();
                reset();
            }
        }
    }

    // finished level
    protected void eventFinish() {
        if (nextGameState == GameStateManager.GAMEOVER) {
            JukeBox.stop("level1");
            JukeBox.play("endLevel");
        }
        eventCount++;
        if (eventCount == 1) {
            JukeBox.play("teleport");
            player.setTeleporting(true);
            player.stop();
        } else if (eventCount == 120) {
            tb.clear();
            tb.add(new Rectangle(
                    GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
        } else if (eventCount > 120) {
            tb.get(0).x -= 6;
            tb.get(0).y -= 4;
            tb.get(0).width += 12;
            tb.get(0).height += 8;
            JukeBox.stop("teleport");
        }
        if (eventCount == 180) {
            PlayerSave.setHealth(player.getHealth());
            gsm.setState(nextGameState);
        }
    }
}
