import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener {

	public static int WIDTH = 960, HEIGHT = 960;
	
	private Graphics graphics;
	private Player player;
	private TileMap tileMap;

	Point mouseLocation;
	Point frameLocation;


	private LinkedBlockingQueue<Projectile> projectiles;
	private LinkedBlockingQueue<Enemy> enemies;

	private LinkedBlockingQueue<AirPlane> airPlanes;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		new SpriteSheet();
		player = new Player(WIDTH/2, HEIGHT-32*2);
		tileMap = new TileMap();

		projectiles = new LinkedBlockingQueue<>();
		enemies = new LinkedBlockingQueue<>();
		airPlanes = new LinkedBlockingQueue<>();
		this.addKeyListener(this);
		this.addMouseListener(this);
	}
	
	public void tick() { //verifica estado

		mouseLocation = MouseInfo.getPointerInfo().getLocation();
		frameLocation = this.getLocationOnScreen();
		Point mousePointXCenter = new Point(mouseLocation.x - frameLocation.x - (WIDTH / 2),mouseLocation.y - frameLocation.y);
		//System.out.println(mousePoint);
		for (Projectile currentProjectile : projectiles) {
			currentProjectile.tick();
			if (spriteOutOfScreen(currentProjectile) ) {
				projectiles.remove(currentProjectile);
				System.out.println("saiu bala");
			}
		}
		for (AirPlane airPlane:
				airPlanes){
			airPlane.tick();
			if (spriteOutOfScreen(airPlane) ) {
				airPlanes.remove(airPlane);
				System.out.println("saiu airplano");
			}
		}
		for (Enemy enemy :
				enemies) {
			enemy.tick(projectiles,tileMap,player);
			if(enemy.isDead()){
				enemies.remove(enemy);
			}
		}

		tileMap.tick();
		player.tick(tileMap,mousePointXCenter);



	}
	public boolean spriteOutOfScreen(Rectangle sprite){
		return sprite.y <= 0 || sprite.x <= 0 || sprite.x >= WIDTH || sprite.y >= HEIGHT;
	}
	
	public void render() { //desenha estado
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if(bufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}
			
		graphics = bufferStrategy.getDrawGraphics();
		
		
		drawGraphic(0, 0, WIDTH, HEIGHT, Color.DARK_GRAY);
		
		// como as setas do teclado
		tileMap.render(graphics);

		for (Projectile projectile:
			 projectiles){
			projectile.render(graphics);

		}
		player.render(graphics);
		for (Enemy enemy:
				enemies){
			enemy.render(graphics);

		}
		for (AirPlane airPlane:
				airPlanes){
			airPlane.render(graphics);

		}
		
		bufferStrategy.show();
		
	}
	public void drawGraphic(int positionX, int positionY,int width ,int height, Color color) {
		graphics.setColor(color);
		graphics.fillRect(positionX, positionY, width, height);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		Random random = new Random();
		new Timer().scheduleAtFixedRate(
				new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						tick();
						render();
						double rate = random.nextDouble(100);
						if(rate < 1){
							airPlanes.add(new AirPlane(random.nextBoolean(),enemies));
						}
						
					}
				}, 0, 1000/60);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		/*if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.setCannonGun(SpriteSheet.playerRight);
			player.setPlayerRight(true);
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.setCannonGun(SpriteSheet.playerLeft);
			player.setPlayerLeft(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.setCannonGun(SpriteSheet.playerBack);
			player.setPlayerDown(true);
		}else if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.setCannonGun(SpriteSheet.playerFront);
			player.setPlayerUp(true);
		}*/
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

		// TODO Auto-generated method stub
		/*if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.setPlayerRight(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.setPlayerLeft(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.setPlayerDown(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.setPlayerUp(false);
		}*/
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {

		Point mousePoint = new Point(mouseLocation.x - frameLocation.x ,mouseLocation.y - frameLocation.y);

		if(mouseEvent.getButton() == MouseEvent.BUTTON1){

			projectiles.add(new Projectile(player.x,player.y,player.height,mousePoint ));
		} else {
			//enemies.add(new Enemy(mousePoint.x,mousePoint.y));

		}
		System.out.println(mousePoint);
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}
}
