package gameState;

import audio.JukeBox;
import entity.*;
import entity.enemies.*;
import handlers.Keys;
import main.GamePanel;
import tileMap.Background;
import tileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class Level2State extends GameState {


    //private Background wave;
    private Background bg;
    private Background clouds;

    private Player player;
    private TileMap tileMap;
    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;

    private HUD hud;
    private Teleport teleport;
    private Door door1;
    private Door door2;

    //events
    private boolean blockInput = false;
    private int eventCount = 0;
    private boolean eventStart;
    private boolean eventFinish;
    private boolean eventDead;
    private ArrayList<Rectangle> tb;

    public Level2State(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {

        // backgrounds
        bg = new Background("/Backgrounds/chmury2.gif", 0);
        //wave = new Background("/Backgrounds/wave.gif", 0.1);
        clouds = new Background("/Backgrounds/miasto2.gif", 0.2);

        //tilemap
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/12345.gif");
        tileMap.loadMap("/Maps/12345.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(0.7);

        //player
        player = new Player(tileMap);
        player.setPosition(62, 500);

        // enemies
        enemies = new ArrayList<Enemy>();
        populateEnemies();

        // explosions
        explosions = new ArrayList<Explosion>();

        hud = new HUD(player);

        // teleport
        teleport = new Teleport(tileMap);
        teleport.setPosition(3750, 534);
        door1 = new Door(tileMap);
        door1.setPosition(165, 480);
        door2 = new Door(tileMap);
        door2.setPosition(2445, 480);

        // start event
        eventStart = true;
        tb = new ArrayList<Rectangle>();
        eventStart();

        JukeBox.load("/Music/endLevel.mp3.mp3", "endLevel");
        JukeBox.stop("level1");
        JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);
    }

    private void populateEnemies() {

        enemies = new ArrayList<Enemy>();

        caterpie c;
        Point[] pointsc = new Point[]{
                new Point(510, 305),
        };

        for (int i = 0; i < pointsc.length; i++) {
            c = new caterpie(tileMap);
            c.setPosition(pointsc[i].x, pointsc[i].y);
            enemies.add(c);

        }
        pidgey p;
        Point[] pointsp = new Point[]{
                new Point(530, 460),
        };

        for (int i = 0; i < pointsp.length; i++) {
            p = new pidgey(tileMap);
            p.setPosition(pointsp[i].x, pointsp[i].y);
            enemies.add(p);
        }
        enemy1 enemy;
        Point[] pointsenemy = new Point[]{
                new Point(2090, 500),
                new Point(3450, 500),
        };

        for (int i = 0; i < pointsenemy.length; i++) {
            enemy = new enemy1(tileMap);
            enemy.setPosition(pointsenemy[i].x, pointsenemy[i].y);
            enemies.add(enemy);
        }

        zubat z;
        Point[] zubatz = new Point[]{
                new Point(939, 305),
                new Point(1850, 500),
                new Point(2360, 500),
                new Point(3070, 500),
        };
        for (int i = 0; i < zubatz.length; i++) {
            z = new zubat(tileMap);
            z.setPosition(zubatz[i].x, zubatz[i].y);
            enemies.add(z);
        }
        Belger b;
        Point[] Belgerb = new Point[]{
                new Point(360, 500),
                new Point(3440, 305),

        };
        for (int i = 0; i < Belgerb.length; i++) {
            b = new Belger(tileMap);
            b.setPosition(Belgerb[i].x, Belgerb[i].y);
            enemies.add(b);
        }

        Talib t;
        Point[] Talibt = new Point[]{
                new Point(710, 500),
                new Point(740, 500),
                new Point(1964, 360),
                new Point(1584, 360),
        };
        for (int i = 0; i < Talibt.length; i++) {
            t = new Talib(tileMap);
            t.setPosition(Talibt[i].x, Talibt[i].y);
            enemies.add(t);
        }

        Arni a;
        Point[] Arnia = new Point[]{
                new Point(2300, 305),
                new Point(2850, 305),
        };
        for (int i = 0; i < Arnia.length; i++) {
            a = new Arni(tileMap);
            a.setPosition(Arnia[i].x, Arnia[i].y);
            enemies.add(a);
        }
    }


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
        //check doors
        if (door1.contains(player)) {
            player.setPosition(player.getx(), 300);
        }
        if (door2.contains(player)) {
            player.setPosition(player.getx(), 300);
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

        //set background
        clouds.setPosition(tileMap.getx(), tileMap.gety());

        // update all enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                JukeBox.play("enemyDies");
                enemies.remove(i);
                i--;
                explosions.add(
                        new Explosion(e.getx(), e.gety()));
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
        // draw bg
        bg.draw(g);
        //wave.draw(g);
        clouds.draw(g);

        // draw tilemap
        tileMap.draw(g);

        // draw player
        player.draw(g);

        //draw dors
        door1.draw(g);
        door2.draw(g);

        // draw enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }
        // draw explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setMapPosition(
                    (int) tileMap.getx(), (int) tileMap.gety());
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
        //player.setScratching(Keys.keyState[Keys.BUTTON2]);
        if (Keys.isPressed(Keys.BUTTON3)) player.setFiring();
        if (Keys.isPressed(Keys.BUTTON2)) player.setScratching();
    }
    //events

    // reset level
    private void reset() {
        player.reset();
        player.setPosition(100, 100);
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
                gsm.setState(GameStateManager.LEVEL2STATE);
            } else {
                eventDead = blockInput = false;
                eventCount = 0;
                player.loseLife();
                reset();
            }
        }
    }

    // finished level
    private void eventFinish() {
        JukeBox.stop("level1");
        JukeBox.play("endLevel");
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

            gsm.setState(GameStateManager.GAMEOVER);
        }
    }
}