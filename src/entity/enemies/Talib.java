package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;

public class Talib extends Enemy {
	
	private BufferedImage[] sprites;
	
	public Talib (TileMap tm){
		
		super (tm);
		
		moveSpeed = 3;
		maxSpeed = 1;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 33;
		height = 35;
		cwidth = 33;
		cheight = 35;
		
		health = maxHealth = 3;
		damage = 2;
		
		// load sprites
		try{
			
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/Talib.gif"
							)
					);
				sprites = new BufferedImage[7];
				for(int i = 0; i < sprites.length; i++){
					sprites[i] = spritesheet.getSubimage(
							i * width,
							0,
							width,
							height
						);
				}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(15);
		
		right = true;
		facingRight = true;
		
	}
	
	private void getNextPosition() {
		
		// movement
		/*		if(left) {
					dx -= moveSpeed;
					if(dx < -maxSpeed) {
						dx = -maxSpeed;
					}
				}
				else if(right) {
					dx += moveSpeed;
					if(dx > maxSpeed) {
						dx = maxSpeed;
					}
				}
				if(falling){
					dy += fallSpeed;
				}*/
		
		if(left) dx = -moveSpeed;
		else if(right) dx = moveSpeed;
		else dx = 0;
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		if(jumping && !falling) {
			dy = jumpStart;
		}
		
	
		}
	
	public void update() {
		
		/*// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		//check flinching
		if(flinching){
			long elapsed = 
					(System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400){
				flinching = false;
			}
		}
		// if it hits a wall, go other direction
		if(right && dx == 0){
			right = false;
			left = true;
			facingRight = false;
			
		}
		else if(left && dx == 0){
			right = true;
			left = false;
			facingRight = true;
		}
		
		// update animation
		animation.update();
		
	*/
		
		
		
		
		getNextPosition();
		checkTileMapCollision();
		calculateCorners(x, ydest + 1);
		if(!bottomLeft) {
			left = false;
			right = facingRight = true;
		}
		if(!bottomRight) {
			left = true;
			right = facingRight = false;
		}
		setPosition(xtemp, ytemp);
		
		if(dx == 0) {
			left = !left;
			right = !right;
			facingRight = !facingRight;
		}
		//check flinching
				if(flinching){
					long elapsed = 
							(System.nanoTime() - flinchCount) / 1000000;
					if(elapsed > 400){
						flinching = false;
					}
				}
		
		// update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g){
		
		setMapPosition();
		
		super.draw(g);
		
	}

}
