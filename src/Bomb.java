import java.awt.*;
import java.awt.image.BufferedImage;

public class Bomb extends Bullet {
    private int bombSpeedY, bombSpeedX;
    private double mouseX,mouseY,directionX,directionY;
    private double playerHeight;
    private BufferedImage spriteBomb;

    private double projectileAngle = 0;

    public Bomb(int positionX, int positonY, double playerHeight, Point mousePoint) {
        super(positionX+16, positonY+(int)playerHeight,16,16);
        spriteBomb = SpriteSheet.bomb;
        setDirection(mousePoint);
        bombSpeedY = 10;
        bombSpeedX = 10;
        this.playerHeight = playerHeight;
    }
    public void tick() {


        this.y +=  bombSpeedY * directionY;
        this.x +=  bombSpeedX *  directionX;


    }

    public void render(Graphics graphics) {
        graphics.drawImage(spriteBomb, this.x,this.y,this.width,this.height, null);

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
