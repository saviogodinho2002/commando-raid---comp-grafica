import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class AirPlane extends Rectangle {
    BufferedImage airPlane;
    int airPlaneSpeed ;
    boolean fromLeft;
    LinkedBlockingQueue<Enemy> enemies;
    Random random;
    public AirPlane(boolean fromLeft, LinkedBlockingQueue<Enemy> enemies){
        super(fromLeft?0:Game.WIDTH, new  Random().nextInt((int)(Game.HEIGHT*0.25)), 64*2,32*2);
        this.fromLeft  = fromLeft;

        airPlane = SpriteSheet.airPlane;
        if(fromLeft){

            airPlane = SpriteSheet.reverseImage(airPlane);
        }
        airPlaneSpeed = 4;
        this.enemies = enemies;
        random = new Random();
    }

    public void tick() {

        if(fromLeft){
            this.x += airPlaneSpeed;
        }else {
            this.x -= airPlaneSpeed;
        }
        double rate = random.nextDouble(100)+1;
        if(rate < 2){
            enemies.add(new Enemy(this.x,this.y));
        }
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
        graphics.drawImage(airPlane, this.x,this.y,this.width,this.height, null);
    }
}
