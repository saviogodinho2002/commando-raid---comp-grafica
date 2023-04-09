import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends Projectile {
    private int bulletSpeedY, bulletSpeedX;
    private double mouseX,mouseY,directionX,directionY;
    private double playerHeight;
    private BufferedImage spriteBullet;

    private double projectileAngle = 0;

    public Bullet(int positionX, int positonY, double playerHeight, Point mousePoint) {
        super(positionX+16, positonY+(int)playerHeight,16,16	);
        spriteBullet = SpriteSheet.projectile;
        setDirection(mousePoint);
        bulletSpeedY = 10;
        bulletSpeedX = 10;
        this.playerHeight = playerHeight;
    }
    public void tick() {


        this.y +=  bulletSpeedY * directionY;
        this.x +=  bulletSpeedX *  directionX;


    }

    public void render(Graphics graphics) {
        graphics.drawImage(spriteBullet, this.x,this.y,this.width,this.height, null);

    }
    private void setDirection(Point mousePoint){

        mouseX = (mousePoint.x ) ;
        mouseY = (mousePoint.y );

        directionX = (mousePoint.x - this.x) ;
        directionY = (mousePoint.y - this.y);
        double magnitude = Math.sqrt(directionX * directionX + directionY * directionY);


            directionX = directionX /magnitude ;
            directionY = directionY /magnitude;



    }
}
