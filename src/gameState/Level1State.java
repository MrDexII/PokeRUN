package GameState;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;


import Audio.JukeBox;
import Entity.Door;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.PlayerSave;
import Entity.Teleport;
import Entity.Enemies.Arni;
import Entity.Enemies.Belger;
import Entity.Enemies.Talib;
import Entity.Enemies.caterpie;
import Entity.Enemies.enemy1;
import Entity.Enemies.pidgey;
import Entity.Enemies.zubat;
import Handlers.Keys;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;


public class Level1State extends GameState {
	
	
	//private Background wave;
	private Background bg;
	private Background balloon;
	private Background balloon1;
	private Background bird;
	
	
	private Player player;
	private TileMap tileMap;
//	private ArrayList<EnemyProjectile> eprojectiles;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	private Teleport teleport;
	private Door door1;
	private Door door2;
	private Door door3;
	
	//events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private boolean eventFinish;
	private boolean eventDead;
	private ArrayList<Rectangle> tb;
	
	public Level1State(GameStateManager gsm) {
		super(gsm);
		init();
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
		
		//player
		player = new Player(tileMap);
		player.setPosition(100, 100);
		//player.setPosition(4000, 600);
		player.setHealth(PlayerSave.getHealth());
	
		
		
		// enemies
		enemies = new ArrayList<Enemy>();
//		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();
		
		// explosions
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
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
		
		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
		
		//SFX
		JukeBox.load("/SFX/kniveHits.mp3.mp3", "kniveHits");
		JukeBox.load("/SFX/enemyDies.mp3.mp3", "enemyDies");
		JukeBox.load("/SFX/teleport.mp3.mp3", "teleport");
		
		// music
		JukeBox.load("/Music/inGameMusicLowQuality.mp3.mp3", "level1");
		JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);
		JukeBox.stop("menuoption");
	}
	
	private void populateEnemies(){
		
		enemies = new ArrayList<Enemy>();
		
		caterpie c;
		Point[] pointsc= new Point[] {
			new Point(700, 600),
			new Point(1875, 215),
		};
		
		for(int i = 0; i < pointsc.length; i++){
			c = new caterpie(tileMap);
			c.setPosition(pointsc[i].x, pointsc[i].y);
			enemies.add(c);     
                   
		}
         pidgey p;
         Point[] pointsp= new Point[] {
        		new Point(150, 350),
        		new Point(820, 550),
        		new Point(1620, 630),
        		new Point(1980, 630),
		};
	
		for(int i = 0; i < pointsp.length; i++){
			p = new pidgey(tileMap);
			p.setPosition(pointsp[i].x, pointsp[i].y);
			enemies.add(p);
                }
		enemy1 enemy;
        Point[] pointsenemy= new Point[] {
        		new Point(1266, 620),
        		new Point(3206, 186),
		};
	
		for(int i = 0; i < pointsenemy.length; i++){
			enemy = new enemy1(tileMap);
			enemy.setPosition(pointsenemy[i].x, pointsenemy[i].y);
			enemies.add(enemy);
               }
		
			zubat z;
			Point[] zubatz= new Point[] {
	        		new Point(1185, 568),
	        		new Point(1069, 568),
			};
			for(int i = 0; i < zubatz.length; i++){
				z = new zubat(tileMap);
				z.setPosition(zubatz[i].x, zubatz[i].y);
				enemies.add(z);
			}
			Belger b;
			Point[] Belgerb= new Point[] {
	        		new Point(1158, 153),
	        		new Point(1847, 541),
			};
			for(int i = 0; i < Belgerb.length; i++){
				b = new Belger(tileMap);
				b.setPosition(Belgerb[i].x, Belgerb[i].y);
				enemies.add(b);
			}
			
			Talib t;
			Point[] Talibt= new Point[] {
	        		new Point(631, 143),
	        		new Point(2169, 516),
	        		new Point(4030, 638),
	        		new Point(3600, 641),
			};
			for(int i = 0; i < Talibt.length; i++){
				t = new Talib(tileMap);
				t.setPosition(Talibt[i].x, Talibt[i].y);
				enemies.add(t);
			}
			
			Arni a;
			Point[] Arnia= new Point[] {
	        		new Point(631, 143),
	        		new Point(3201, 638),
			};
			for(int i = 0; i < Arnia.length; i++){
				a = new Arni(tileMap);
				a.setPosition(Arnia[i].x, Arnia[i].y);
				enemies.add(a);
			}
			
	}

	
	public void update() {
		
		//System.out.println(player.getx());
		//System.out.println(player.gety());
		// check keys
		handleInput();
		
		// check if end of level event should start
		if(teleport.contains(player)) {
			eventFinish = blockInput = true;
		}
		//check doors
		if(door1.contains(player)){
			player.setPosition(player.getx(), 100);
		}
		if(door2.contains(player)){
			player.setPosition(player.getx(), 75);
		}
		if(door3.contains(player)){
			player.setPosition(player.getx(), 75);
		}
		
		// check if player dead event should start
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();
		
		// update player
		player.update();
		
		// update tilemap
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);

		//set background
		balloon.setPosition(tileMap.getx(),tileMap.gety());
		balloon1.setPosition(tileMap.getx(),tileMap.gety());
		bird.setPosition(tileMap.getx(),tileMap.gety());
		
		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++){
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()){
				JukeBox.play("enemyDies");
				enemies.remove(i);
				i--;
				explosions.add(
						new Explosion(e.getx(), e.gety()));
			}
		}
	/*	// update enemy projectiles
				for(int i = 0; i < eprojectiles.size(); i++) {
					EnemyProjectile ep = eprojectiles.get(i);
					ep.update();
					if(ep.shouldRemove()) {
						eprojectiles.remove(i);
						i--;
					}
				}*/
		// update explosions
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()){
				explosions.remove(i);
				i--;
			}
		}
		// attack enemies
		player.checkAttack(enemies);
		
		// update teleport
		teleport.update();
		//door1.update();
		//door2.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		balloon.draw(g);
		balloon1.draw(g);
		bird.draw(g);
	
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		// draw enemies 
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
	/*	// draw enemy projectiles
		for(int i = 0; i < eprojectiles.size(); i++) {
			eprojectiles.get(i).draw(g);
		}*/
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++){
			explosions.get(i).setMapPosition(
					(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		// draw teleport
		teleport.draw(g);
		
		//draw dors
		door1.draw(g);
		door2.draw(g);
		door3.draw(g);
		
		// draw hud
		hud.draw(g);
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
		
	}
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		if(blockInput || player.getHealth() == 0) return;
		player.setUp(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setJumping(Keys.keyState[Keys.BUTTON1]);
		//player.setScratching(Keys.keyState[Keys.BUTTON2]);
		if(Keys.isPressed(Keys.BUTTON3)) player.setFiring();
		if(Keys.isPressed(Keys.BUTTON2)) player.setScratching();
	}
	
	/*public void keyPressed(int k) {
		if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W || k == KeyEvent.VK_UP) player.setJumping(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_SPACE) player.setFiring();
		if(k == KeyEvent.VK_ESCAPE) gsm.setState(GameStateManager.MENUSTATE);;
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W || k == KeyEvent.VK_UP) player.setJumping(false);
	}*/
	
	
	//events
	
	// reset level
		private void reset() {
			player.reset();
			player.setPosition(100, 100);
			populateEnemies();
			blockInput = true;
			eventCount = 0;
			//tileMap.setShaking(false, 0);
			eventStart = true;
			eventStart();
			//title = new Title(hageonText.getSubimage(0, 0, 178, 20));
			//title.sety(60);
			//subtitle = new Title(hageonText.getSubimage(0, 33, 91, 13));
			//subtitle.sety(85);
		}
	
		// level started
		private void eventStart() {
			eventCount++;
			if(eventCount == 1) {
				tb.clear();
				tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
				tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
				tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
				tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			}
			if(eventCount > 1 && eventCount < 60) {
				tb.get(0).height -= 4;
				tb.get(1).width -= 6;
				tb.get(2).y += 4;
				tb.get(3).x += 6;
			}
			if(eventCount == 60) {
				eventStart = blockInput = false;
				eventCount = 0;
				tb.clear();
			}
		}
		// player has died
		private void eventDead() {
			eventCount++;
			if(eventCount == 1) {
				player.setDead();
				player.stop();
			}
			if(eventCount == 60) {
				tb.clear();
				tb.add(new Rectangle(
					GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
			}
			else if(eventCount > 60) {
				tb.get(0).x -= 6;
				tb.get(0).y -= 4;
				tb.get(0).width += 12;
				tb.get(0).height += 8;
			}
			if(eventCount >= 120) {
				if(player.getHealth() == 0) {
					gsm.setState(GameStateManager.LEVEL1STATE);
				}
				else {
					eventDead = blockInput = false;
					eventCount = 0;
					player.loseLife();
					reset();
				}
			}
		}
			
			// finished level
			private void eventFinish() {
				eventCount++;
				if(eventCount == 1) {
					JukeBox.play("teleport");
					player.setTeleporting(true);
					player.stop();
				}
				else if(eventCount == 120) {
					tb.clear();
					tb.add(new Rectangle(
						GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
				}
				else if(eventCount > 120) {
					tb.get(0).x -= 6;
					tb.get(0).y -= 4;
					tb.get(0).width += 12;
					tb.get(0).height += 8;
					JukeBox.stop("teleport");
				}
				if(eventCount == 180) {
					PlayerSave.setHealth(player.getHealth());
					
					gsm.setState(GameStateManager.LEVEL2STATE);
				}
		}
	
	
	
}