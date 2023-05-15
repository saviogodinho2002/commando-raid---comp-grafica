import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.LinkedBlockingQueue;

public class Explosion extends Rectangle {
    private BufferedImage explosion;
    private int explosionTime = 60; //1 segundo
    public Explosion(int explosionX, int exposionY, Player player, boolean fromBomb, LinkedBlockingQueue<Enemy> enemies){
        super(explosionX-(64-16),exposionY-64, 128,128);
        explosion = SpriteSheet.deepCopy(SpriteSheet.explosion);
        if(this.intersects(player)){
            player.damage();
        }
        if(fromBomb){
            for (Enemy enemy :
                    enemies) {
                    if (this.intersects(enemy)) {
                        enemies.remove(enemy);
                        Game.score++;
                    }
                }
        }
        Sound.streamExplosion();
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
