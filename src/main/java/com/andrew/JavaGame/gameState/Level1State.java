package com.andrew.JavaGame.gameState;

import com.andrew.JavaGame.audio.JukeBox;
import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.entity.enemies.*;
import com.andrew.JavaGame.entity.things.Door;
import com.andrew.JavaGame.entity.things.Teleport;
import com.andrew.JavaGame.entity.things.player.Player;
import com.andrew.JavaGame.entity.things.player.PlayerSave;
import com.andrew.JavaGame.tileMap.Background;
import com.andrew.JavaGame.tileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class Level1State extends AbstractLevelState {
    private Background bg;
    private Background balloon;
    private Background balloon1;
    private Background bird;

    private Door door1;
    private Door door2;
    private Door door3;

    public Level1State(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {
        // backgrounds
        bg = new Background("/Backgrounds/back.gif", 0);
        balloon = new Background("/Backgrounds/balloon.gif", 0.1);
        balloon1 = new Background("/Backgrounds/balloon1.gif", 0.2);
        bird = new Background("/Backgrounds/bird.gif", 0.9);

        //tilemap
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/123.gif");
        tileMap.loadMap("/Maps/1.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(0.7);

        currentGameState = GameStateManager.LEVEL1STATE;
        nextGameState = GameStateManager.LEVEL2STATE;

        //player
        player = new Player(tileMap);
        player.setPosition(100, 100);
        //player.setPosition(4000, 600);
        player.setHealth(PlayerSave.getHealth());

        // enemies
        populateEnemies();

        // teleport
        teleport = new Teleport(tileMap);
        teleport.setPosition(4095, 660);

        //doors
        door1 = new Door(tileMap);
        door1.setPosition(135, 660);
        door2 = new Door(tileMap);
        door2.setPosition(1395, 480);
        door3 = new Door(tileMap);
        door3.setPosition(2595, 660);

        //SFX
        JukeBox.load("/SFX/kniveHits.mp3.mp3", "kniveHits");
        JukeBox.load("/SFX/enemyDies.mp3.mp3", "enemyDies");
        JukeBox.load("/SFX/teleport.mp3.mp3", "teleport");

        // music
        JukeBox.load("/Music/inGameMusicLowQuality.mp3.mp3", "level1");
        JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);
        JukeBox.stop("menuoption");

        super.init();
    }

    protected void populateEnemies() {
        enemies = new ArrayList<Enemy>();

        Bandit c;
        Point[] pointsc = new Point[]{
                new Point(700, 600),
                new Point(1875, 215),
        };

        for (int i = 0; i < pointsc.length; i++) {
            c = new Bandit(tileMap);
            c.setPosition(pointsc[i].x, pointsc[i].y);
            enemies.add(c);

        }
        Flame p;
        Point[] pointsp = new Point[]{
                new Point(150, 350),
                new Point(820, 550),
                new Point(1620, 630),
                new Point(1980, 630),
        };

        for (int i = 0; i < pointsp.length; i++) {
            p = new Flame(tileMap);
            p.setPosition(pointsp[i].x, pointsp[i].y);
            enemies.add(p);
        }
        Samu enemy;
        Point[] pointsenemy = new Point[]{
                new Point(1266, 620),
                new Point(3206, 186),
        };

        for (int i = 0; i < pointsenemy.length; i++) {
            enemy = new Samu(tileMap);
            enemy.setPosition(pointsenemy[i].x, pointsenemy[i].y);
            enemies.add(enemy);
        }

        Czesiek z;
        Point[] zubatz = new Point[]{
                new Point(1185, 568),
                new Point(1069, 568),
        };
        for (int i = 0; i < zubatz.length; i++) {
            z = new Czesiek(tileMap);
            z.setPosition(zubatz[i].x, zubatz[i].y);
            enemies.add(z);
        }
        Belger b;
        Point[] Belgerb = new Point[]{
                new Point(1158, 153),
                new Point(1847, 541),
        };
        for (int i = 0; i < Belgerb.length; i++) {
            b = new Belger(tileMap);
            b.setPosition(Belgerb[i].x, Belgerb[i].y);
            enemies.add(b);
        }

        Talib t;
        Point[] Talibt = new Point[]{
                new Point(631, 143),
                new Point(2169, 516),
                new Point(4030, 638),
                new Point(3600, 641),
        };
        for (int i = 0; i < Talibt.length; i++) {
            t = new Talib(tileMap);
            t.setPosition(Talibt[i].x, Talibt[i].y);
            enemies.add(t);
        }

        Arni a;
        Point[] Arnia = new Point[]{
                new Point(631, 143),
                new Point(3201, 638),
        };
        for (int i = 0; i < Arnia.length; i++) {
            a = new Arni(tileMap);
            a.setPosition(Arnia[i].x, Arnia[i].y);
            enemies.add(a);
        }

    }

    public void update() {
        //check doors
        if (door1.contains(player)) {
            player.setPosition(player.getx(), 100);
        }
        if (door2.contains(player)) {
            player.setPosition(player.getx(), 75);
        }
        if (door3.contains(player)) {
            player.setPosition(player.getx(), 75);
        }

        //set background
        balloon.setPosition(tileMap.getx(), tileMap.gety());
        balloon1.setPosition(tileMap.getx(), tileMap.gety());
        bird.setPosition(tileMap.getx(), tileMap.gety());

        super.update();
    }

    public void draw(Graphics2D g) {
        // draw bg
        bg.draw(g);
        balloon.draw(g);
        balloon1.draw(g);
        bird.draw(g);

        tileMap.draw(g);

        //draw doors
        door1.draw(g);
        door2.draw(g);
        door3.draw(g);

        super.draw(g);
    }

    // reset level
    protected void reset() {
        player.reset();
        player.setPosition(100, 100);
        super.reset();
    }
}
