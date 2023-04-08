import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends Rectangle {
	private int playerWidth, playerHeight;
	private boolean playerRight, playerLeft, playerDown, playerUp;
	double mousex = 0;
	double mousey = 0;
	private double cannonAngle;
	private double cannonAngleSpeed;
	private double mouseX,mouseY,directionX,directionY;
	private int playerSpeed;
	private BufferedImage cannonGun;
	private BufferedImage rotateCannonGun;
	private BufferedImage cannonBase;
	

	public Player(int positionX, int positonY) {
		super(positionX, positonY-36,46,46	);
		cannonGun = SpriteSheet.cannonGun;
		rotateCannonGun = SpriteSheet.cannonGun;
		cannonBase = SpriteSheet.cannonBase;
		cannonAngle = 90;
		playerSpeed = 4;
	}

	public void tick(TileMap tileMap, Point mousePoint) {
		verifyColission(tileMap);
		setDirection(mousePoint);
		changeAngleCannon(mousePoint);

	}
	private void changeAngleCannon(Point mousePoint){
		if(  mousePoint.x > -Game.WIDTH/2 && mousePoint.x < Game.WIDTH/2 )
			mousex = mousePoint.x  ;

		mousey = mousePoint.y  ;
		double angle = 90/480.0;
		angle = angle*mousex;

		cannonAngle = Math.toRadians(angle);


	}
	private void setDirection(Point mousePoint){

		mouseX = (mousePoint.x ) ;
		mouseY = (mousePoint.y );

		directionX = (mousePoint.x - this.x) ;
		directionY = (mousePoint.y - this.y);
		double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);
		if(magnitude!=0){

			directionX = directionX /magnitude ;
			directionY = directionY /magnitude;
		}


	}
	private void verifyColission(TileMap tileMap){
		if(playerRight && tileMap.isFree(this.x  + this.playerSpeed, this.y, this)) {

			this.x += playerSpeed;
		}else if(playerLeft && tileMap.isFree(this.x  - this.playerSpeed, this.y, this)) {

			this.x -= playerSpeed;
		}

		if(playerDown && tileMap.isFree(this.x  , this.y + this.playerSpeed, this)) {

			this.y += playerSpeed;
		}else if(playerUp && tileMap.isFree(this.x  , this.y - this.playerSpeed, this)) {
			this.y -= playerSpeed;
		}
	}

	public void render(Graphics graphics) {

		Graphics2D g2d = (Graphics2D) graphics;


		AffineTransform transform = g2d.getTransform();

		g2d.translate(x + width / 2, y + height / 2);


		g2d.rotate(cannonAngle,  0 ,height/3); // Ajustado o ponto de translação

		g2d.drawImage(rotateCannonGun, -width / 2, -height / 2, width, height, null);
		g2d.setTransform(transform);
		//graphics.drawImage(rotateCannonGun,this.x,this.y-this.width/2,this.width,this.height,null);

		graphics.drawImage(cannonBase,this.x,this.y+this.width/2,this.width,this.height,null);
	}



	public int getPlayerWidth() {
		return playerWidth;
	}

	public void setPlayerWidth(int playerWidth) {
		this.playerWidth = playerWidth;
	}

	public int getPlayerHeight() {
		return playerHeight;
	}

	public void setPlayerHeight(int playerHeight) {
		this.playerHeight = playerHeight;
	}

	public int getPlayerSpeed() {
		return playerSpeed;
	}

	public void setPlayerSpeed(int playerSpeed) {
		this.playerSpeed = playerSpeed;
	}

	public BufferedImage getCannonGun() {
		return cannonGun;
	}

	public void setCannonGun(BufferedImage cannonGun) {
		this.cannonGun = cannonGun;
	}

	public boolean isPlayerRight() {
		return this.playerRight;
	}

	public void setPlayerRight(boolean playerRight) {
		this.playerRight = playerRight;
	}

	public boolean isPlayerLeft() {
		return this.playerLeft;
	}

	public void setPlayerLeft(boolean playerLeft) {
		this.playerLeft = playerLeft;
	}

	public boolean isPlayerDown() {
		return this.playerDown;
	}

	public void setPlayerDown(boolean playerDown) {
		this.playerDown = playerDown;
	}

	public boolean isPlayerUp() {
		return this.playerUp;
	}

	public void setPlayerUp(boolean playerUp) {
		this.playerUp = playerUp;
	}

	public int getplayerSpeed() {
		return this.playerSpeed;
	}

	public void setplayerSpeed(int playerSpeed) {
		this.playerSpeed = playerSpeed;
	}


	public BufferedImage getCannonBase() {
		return cannonBase;
	}

	public void setCannonBase(BufferedImage cannonBase) {
		this.cannonBase = cannonBase;
	}
}
