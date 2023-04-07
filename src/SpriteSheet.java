import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public static BufferedImage spriteSheet;
	public static BufferedImage playerFront,playerBack,playerLeft,playerRight, cannonGun, cannonBase, projectile;
	public static BufferedImage wallTile;
	public static BufferedImage enemyFront;
	public SpriteSheet() {
		try {
			spriteSheet = ImageIO.read(new File("res/spritesheet.png"));
		} catch (IOException error) {
			
			
		}
		projectile =  this.getSprite(138, 185, 16, 16);

		cannonGun =  this.getSprite(261, 237, 16, 16);
		cannonBase =  this.getSprite(259, 259, 16, 16);


		
		wallTile = getSprite(283, 238, 16, 16);

	}
	public static BufferedImage getSprite(int x, int y, int width, int height) {
		return spriteSheet.getSubimage(x, y, width, height);
		
	}
	public static  BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	public static void setSpriteSheet(BufferedImage spriteSheet) {
		spriteSheet = spriteSheet;
	}
	public  static BufferedImage getPlayerFront() {
		return playerFront;
	}
	public static void setPlayerFront(BufferedImage playerFront) {
		playerFront = playerFront;
	}
	
	
	
}
