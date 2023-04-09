import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;

public class Game extends Canvas implements Runnable, MouseListener , KeyListener{

	public static int WIDTH = 960, HEIGHT = 960;

	public static int points;

	private  Graphics graphics;
	private Player player;
	public static TileMap tileMap;

	private Color skyColor;
	private Font gameFont;

	Point mouseLocation;
	Point frameLocation;


	public static LinkedBlockingQueue<Projectile> projectiles;
	public static LinkedBlockingQueue<Enemy> enemies;

	public static LinkedBlockingQueue<AirPlane> airPlanes;

	public static LinkedBlockingQueue<Explosion> explosions;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		new SpriteSheet();
		player = new Player(WIDTH/2, HEIGHT-32*2);
		tileMap = new TileMap();
		skyColor = new Color(0x2e,0x95,0xf4);
		gameFont = new Font("Arial", Font.PLAIN, 20);

		projectiles = new LinkedBlockingQueue<>();
		enemies = new LinkedBlockingQueue<>();
		airPlanes = new LinkedBlockingQueue<>();
		explosions = new LinkedBlockingQueue<>();

		points = 0;
		this.addMouseListener(this);

		this.addKeyListener(this);
	}
	
	public void tick() { //verifica estado

		mouseLocation = MouseInfo.getPointerInfo().getLocation();
		frameLocation = this.getLocationOnScreen();
		Point mousePointRelativeByWindow = new Point(mouseLocation.x - frameLocation.x,mouseLocation.y - frameLocation.y);
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
			enemy.tick(player);

			if(enemy.isDead() || enemy.intersects(player)){
				if(enemy.isDeadOnFloor() || enemy.intersects(player) || enemy.isDeadByMissile()){
					explosions.add(new Explosion(enemy.x,enemy.y,player,enemy.isDeadByMissile(),enemies));
				}
				enemies.remove(enemy);
			}
		}
		for (Explosion explosion:
			 explosions) {

			explosion.tick();
			if(explosion.end())
				explosions.remove(explosion);
			if(player.getLife() <= 0){

				JOptionPane.showMessageDialog(this,"Pontos: "+points,"VOCÊ PERDEU!",JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}
		}

		tileMap.tick();
		player.tick(mousePointRelativeByWindow);



	}
	public boolean spriteOutOfScreen(Rectangle sprite){
		return sprite.y <= 0 || sprite.x <= 0 || sprite.x >= WIDTH || sprite.y >= HEIGHT;
	}
	
	public void render() { //desenha estado
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if(bufferStrategy == null) {
			this.createBufferStrategy(5);
			return;
		}
			
		graphics = bufferStrategy.getDrawGraphics();
		
		
		drawGraphic(0, 0, WIDTH, HEIGHT,skyColor );
		
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
		for (Explosion explosion:
				explosions) {
			explosion.render(graphics);
		}
		//graphics.getFont().s
		graphics.setFont(gameFont); // Define a fonte com tamanho 20
		graphics.setColor(Color.WHITE); // Define a cor como branca
		graphics.drawString("Pontos: " + String.valueOf(points), 50, 50);
		graphics.drawString("Vida: " + String.valueOf(player.getLife()), 50, 75);


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
							airPlanes.add(new AirPlane(random.nextBoolean()));
						}

						
					}

				}, 0, 1000/60);

		
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {

		Point mousePoint = new Point(mouseLocation.x - frameLocation.x ,mouseLocation.y - frameLocation.y);

		if(mouseEvent.getButton() == MouseEvent.BUTTON1){

			projectiles.add(new Bullet(player.x+player.width/4,player.y,player.height,mousePoint ));
		} else {
			//enemies.add(new Enemy(mousePoint.x,mousePoint.y));
			if(player.isCanBomb()){

				projectiles.add(new Bomb(player.x+player.width/4,player.y,player.height,mousePoint ));
				player.setCanBomb(false);
			}
		}
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

	@Override
	public void keyPressed(KeyEvent e) {

		player.setToRight(e.getKeyCode() == KeyEvent.VK_D);
		player.setToLeft(e.getKeyCode() == KeyEvent.VK_A);

	}

	@Override
	public void keyReleased(KeyEvent e) {

		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.setToRight(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			player.setToLeft(false);;
		}


	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}


}
