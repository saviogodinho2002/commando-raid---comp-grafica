

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public  class Player extends Rectangle {
	private int playerWidth, playerHeight;
	private boolean playerRight, playerLeft, playerDown, playerUp;
	double mousex = 0;
	double mousey = 0;
	private double cannonAngle;
	private double cannonAngleSpeed;

	private final double scalePercent = 255/(60.0f*2);

	private boolean canBomb;
	private int timeToBomb;

	private boolean toLeft,toRight;

	private double mouseX,mouseY,directionX,directionY;
	private int playerSpeed;
	private BufferedImage cannonGun;
	private BufferedImage rotateCannonGun;
	private BufferedImage cannonBase;

	private  int life;
	public Player(int positionX, int positonY) {
		super(positionX, positonY-36,46,46	);
		cannonGun = SpriteSheet.cannonGun;
		rotateCannonGun = SpriteSheet.cannonGun;
		cannonBase = SpriteSheet.cannonBase;
		cannonAngle = 90;
		playerSpeed = 4;
		life = 3;
		toLeft = false;
		toRight = false;
		canBomb = true;
		timeToBomb = 2*60;
	}

	public void tick( Point mousePoint) {
		verifyColission(Game.tileMap);
		if(toRight){
			this.x += playerSpeed;
		}
		if(toLeft){
			this.x -= playerSpeed;
		}
		setDirection(mousePoint);

		if(timeToBomb == 0){
			timeToBomb = 60*2;
			canBomb = true;
		}

		if(!canBomb){
			timeToBomb--;
		}

		//changeAngleCannon(mousePoint);

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

		mouseX = mousePoint.x;
		mouseY = mousePoint.y;
		directionX = mouseX - x;
		directionY = mouseY - y;
		double angleInRadians = Math.atan2(directionY, directionX);
		cannonAngle = angleInRadians + Math.toRadians(90);

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
		if(!canBomb){
			double red = scalePercent * (60*2-timeToBomb);
			rotateCannonGun = SpriteSheet.changeColor(cannonGun,"r",255-(int)red);

		}

		Graphics2D g2d = (Graphics2D) graphics;


		AffineTransform transform = g2d.getTransform();

		int centerX = x + width / 2; // Calcula o centro x da imagem
		int centerY = y + height / 2; // Calcula o centro y da imagem

		g2d.translate(centerX, centerY);

		g2d.rotate(cannonAngle, rotateCannonGun.getWidth() / 2.0, 2*rotateCannonGun.getHeight()); // Rotação em torno do centro da imagem


		g2d.drawImage(rotateCannonGun, -rotateCannonGun.getWidth() / 2, -rotateCannonGun.getHeight() / 2, width, height, null); // Desenho da imagem com translação para o centro


		g2d.setTransform(transform);
		//graphics.drawImage(rotateCannonGun,this.x,this.y-this.width/2,this.width,this.height,null);

		graphics.drawImage(cannonBase,this.x+width/4,this.y+this.width,this.width,this.height,null);

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

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	public int damage(){
		return --life;
	}

	public boolean isToRight() {
		return toRight;
	}

	public void setToRight(boolean toRight) {
		this.toRight = toRight;
	}

	public boolean isToLeft() {
		return toLeft;
	}

	public void setToLeft(boolean toLeft) {
		this.toLeft = toLeft;
	}
	public boolean isCanBomb() {
		return canBomb;
	}

	public void setCanBomb(boolean canBomb) {
		this.canBomb = canBomb;
	}

	public int getTimeToBomb() {
		return timeToBomb;
	}

	public void setTimeToBomb(int timeToBomb) {
		this.timeToBomb = timeToBomb;
	}
}
