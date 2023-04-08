import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Rectangle {
    private int projectileSpeedY, projectileSpeedX;
    private double mouseX,mouseY,directionX,directionY;
    private double playerHeight;
    private BufferedImage spriteProjectile;

    private double projectileAngle = 0;

    public Projectile(int positionX, int positonY,double playerHeight, Point mousePoint) {
        super(positionX, positonY+(int)playerHeight,16,16	);
        spriteProjectile = SpriteSheet.projectile;
        setDirection(mousePoint);
        projectileSpeedY = 10;
        projectileSpeedX = 10;
        this.playerHeight = playerHeight;
    }
    public void tick() {


        this.y +=  projectileSpeedY * directionY;
        this.x +=  projectileSpeedX *  directionX;


    }

    public void render(Graphics graphics) {
        graphics.drawImage(spriteProjectile, this.x,this.y,this.width,this.height, null);

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
}
