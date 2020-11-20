package com.andrew.JavaGame.gameState;

import com.andrew.JavaGame.audio.JukeBox;
import com.andrew.JavaGame.entity.Enemy;
import com.andrew.JavaGame.entity.enemies.*;
import com.andrew.JavaGame.entity.things.Door;
import com.andrew.JavaGame.entity.things.Teleport;
import com.andrew.JavaGame.entity.things.player.Player;
import com.andrew.JavaGame.tileMap.Background;
import com.andrew.JavaGame.tileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class Level2State extends AbstractLevelState {
    private Background bg;
    private Background clouds;

    private Door door1;
    private Door door2;

    public Level2State(GameStateManager gsm) {
        super(gsm);
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

        currentGameState = GameStateManager.LEVEL2STATE;
        nextGameState = GameStateManager.GAMEOVER;

        // enemies
        populateEnemies();

        // teleport
        teleport = new Teleport(tileMap);
        teleport.setPosition(3750, 534);

        //doors
        door1 = new Door(tileMap);
        door1.setPosition(165, 480);
        door2 = new Door(tileMap);
        door2.setPosition(2445, 480);

        JukeBox.load("/Music/endLevel.mp3.mp3", "endLevel");
        JukeBox.stop("level1");
        JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);

        super.init();
    }

    protected void populateEnemies() {
        enemies = new ArrayList<Enemy>();

        Bandit c;
        Point[] pointsc = new Point[]{
                new Point(510, 305),
        };

        for (int i = 0; i < pointsc.length; i++) {
            c = new Bandit(tileMap);
            c.setPosition(pointsc[i].x, pointsc[i].y);
            enemies.add(c);

        }
        Flame p;
        Point[] pointsp = new Point[]{
                new Point(530, 460),
        };

        for (int i = 0; i < pointsp.length; i++) {
            p = new Flame(tileMap);
            p.setPosition(pointsp[i].x, pointsp[i].y);
            enemies.add(p);
        }
        Samu enemy;
        Point[] pointsenemy = new Point[]{
                new Point(2090, 500),
                new Point(3450, 500),
        };

        for (int i = 0; i < pointsenemy.length; i++) {
            enemy = new Samu(tileMap);
            enemy.setPosition(pointsenemy[i].x, pointsenemy[i].y);
            enemies.add(enemy);
        }

        Czesiek z;
        Point[] zubatz = new Point[]{
                new Point(939, 305),
                new Point(1850, 500),
                new Point(2360, 500),
                new Point(3070, 500),
        };
        for (int i = 0; i < zubatz.length; i++) {
            z = new Czesiek(tileMap);
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
        //check doors
        if (door1.contains(player)) {
            player.setPosition(player.getx(), 300);
        }
        if (door2.contains(player)) {
            player.setPosition(player.getx(), 300);
        }

        //set background
        clouds.setPosition(tileMap.getx(), tileMap.gety());

        super.update();
    }

    public void draw(Graphics2D g) {
        // draw bg
        bg.draw(g);
        clouds.draw(g);

        tileMap.draw(g);

        //draw doors
        door1.draw(g);
        door2.draw(g);

        super.draw(g);
    }
}
