
package Entity.Enemies;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class pidgey extends Enemy {
    
    private BufferedImage[] sprites;
	
	public pidgey (TileMap tm){
		super (tm);
		
		moveSpeed = 0.5;
		maxSpeed = 0.5;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 180;
		height = 85;
		cwidth = 180;
		cheight = 85;
		
		health = maxHealth = 20000;
		damage = 10;
		
		// load sprites
		try{
			
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream(
							"/Sprites/Enemies/alien1.gif"
							)
					);
				sprites = new BufferedImage[3];
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
		animation.setDelay(10);
		
		up = true;
		facingRight = true;
		
	}
	
	
	public void update() {
		

		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
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
