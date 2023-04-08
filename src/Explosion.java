import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Explosion extends Rectangle {
    private BufferedImage explosion;
    private int explosionTime = 60; //1 segundo
    public Explosion(int explosionX,int exposionY){
        super(explosionX,exposionY, 46,46);
        explosion = SpriteSheet.deepCopy(SpriteSheet.explosion);
    }
    public void tick(){
        float alpha = 254/60f;
        alpha = alpha * explosionTime;
        System.out.println("alpha = "+alpha);
        explosion = SpriteSheet.changeAlpha(explosion, alpha);
        explosionTime--;
    }
    public boolean end(){
        return explosionTime == 0;
    }
    public void render(Graphics graphics){
        graphics.drawImage(explosion, this.x,this.y,this.width,this.height, null);
    }
}
