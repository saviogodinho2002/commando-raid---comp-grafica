import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public static BufferedImage spriteSheet;
	public static BufferedImage playerFront, cannonGun, cannonBase, projectile,bomb;
	public static BufferedImage wallTile;
	public static BufferedImage enemy,explosion;
	public static BufferedImage airPlane;
	public static BufferedImage paraChute;
	public SpriteSheet() {
		try {
			spriteSheet = ImageIO.read(getClass().getResource("res/spritesheet.png"));
		} catch (IOException error) {
			
			
		}
		projectile =  getSprite(138, 185, 16, 16);

		cannonGun =  getSprite(261, 237, 16, 16);
		cannonBase =  getSprite(259, 259, 16, 8);

		paraChute =  getSprite(260, 269, 16, 16);
		enemy =  getSprite(231, 11, 16, 16);
		airPlane = getSprite(285, 272, 45, 13);

		explosion =  getSprite(191, 185, 16, 16);

		bomb =  getSprite(129, 183, 8, 16);

		wallTile = getSprite(283, 238, 16, 16);

	}
	public static BufferedImage getSprite(int x, int y, int width, int height) {
		return spriteSheet.getSubimage(x, y, width, height);
		
	}
	public static BufferedImage reverseImage(BufferedImage bufferedImage){
		AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
		affineTransform.translate(-bufferedImage.getWidth(), 0);
		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		return  affineTransformOp.filter(bufferedImage, null);

	}
	public  static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	public static BufferedImage changeAlpha(BufferedImage source, float newAlpha){

		BufferedImage alteredImage = new BufferedImage(source.getWidth(), source.getHeight(),BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = alteredImage.createGraphics();

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, newAlpha/255f));


		g2d.drawImage(source, 0, 0, null);

		g2d.dispose();
		return alteredImage;
	}
	public static BufferedImage changeColor(BufferedImage image, String color, int newValue) {
		color = color.toUpperCase();

		int width = image.getWidth();
		int height = image.getHeight();

		image = deepCopy(image);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				int rgbColor = image.getRGB(x, y);

				int alpha = (rgbColor >> 24) & 0xFF; // Extrai o valor do canal alfa

				if (alpha == 0) {
					continue;
				}

				Color c = new Color(rgbColor);
				c = switch (color) {
					case "R" -> new Color(newValue, c.getGreen(), c.getBlue());
					case "G" -> new Color(c.getRed(), newValue, c.getBlue());
					case "B" -> new Color(c.getRed(), c.getGreen(), newValue);
					default -> throw new IllegalArgumentException("Color must be 'R', 'G', or 'B'");
				};

				image.setRGB(x, y, c.getRGB());
			}
		}

		return image;
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
