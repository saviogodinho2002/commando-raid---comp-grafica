import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.LinkedBlockingQueue;

public class Enemy extends Rectangle{
	private int enemySpeed = 4;
	private int enemyGravity = 2;
	private boolean dead;
	private boolean deadOnFloor;
	private boolean onGround;
	ParaChute paraChute;
	public Enemy(int enemyX,int enemyY) {
		super(enemyX,enemyY, 32,32);
		paraChute = new ParaChute(enemyX-(int)(this.width*0.75),enemyY-this.height*2);
		dead = false;
		onGround = false;
		deadOnFloor = false;
	}
	public void tick(LinkedBlockingQueue<Projectile> projectiles, TileMap tileMap, Player player) {
		for (Projectile projectile:
			 projectiles) {
			if(this.intersects(projectile)){
				this.dead = true;
				destroyParaChute();
				projectiles.remove(projectile);
			} else if (!paraChute.destroyed && paraChute.intersects(projectile)) {
				paraChute.destroyed = true;
				this.enemyGravity *= 4;
				if(this.y < 2*Game.HEIGHT/3){
					deadOnFloor = true;
				}
			}
		}
		if(!onGround){

			verifyColission(tileMap);
			paraChute.tick(this);
		}
		else {
			if(deadOnFloor){
				this.dead = true;
			}else if(this.x < player.x) {
				this.x += enemySpeed;
			}else if(this.x > player.x) {
				this.x -= enemySpeed;
			}else {
				///TODO dar uma coisada no player
			}
		}

	}
	private void destroyParaChute(){
		paraChute.paraChute = null;
		paraChute.destroyed = true;
	}
	private void verifyColission(TileMap tileMap){


		if(tileMap.isFree(this.x  , this.y + this.enemyGravity, this)) {

			this.y += enemyGravity;
		}else {
			onGround = true;
			destroyParaChute();

		}
	}
	public void render(Graphics graphics) {
		graphics.drawImage(SpriteSheet.enemy, this.x,this.y,this.width,this.height, null);
		paraChute.render(graphics);
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	class ParaChute extends Rectangle{
			boolean destroyed;
			BufferedImage paraChute;
			public ParaChute(int paraChuteX,int paraChuteY){
				super(paraChuteX,paraChuteY, 64,64);
				destroyed = false;
				paraChute = SpriteSheet.paraChute;
			}
			public void tick(Enemy enemy) {
				this.y += enemy.enemyGravity;
			/*if(this.x < player.x) {
				this.x += enemySpeed;
			}else if(this.x > player.x) {
				this.x -= enemySpeed;
			}

			if(this.y > player.y) {
				this.y -= enemySpeed;
			}else if(this.y < player.y) {
				this.y += enemySpeed;
			}*/

			}
			public void render(Graphics graphics) {
				if(!destroyed)
					graphics.drawImage(paraChute, this.x,this.y,this.width,this.height, null);

			}
	}

}
