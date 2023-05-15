import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class Sound {

    public static LinkedBlockingQueue<Clip> shootsSound;
    public static LinkedBlockingQueue<Clip> explosionsSound;
    public static AudioInputStream shootInputStream;
    public static AudioInputStream explosionInputStream;
    public  Sound() {
        shootsSound = new LinkedBlockingQueue<>();
        explosionsSound = new LinkedBlockingQueue<>();
    }
    public static void streamShoot(){
        try {

            shootInputStream = AudioSystem.getAudioInputStream(
                    new File("res/shoot.wav"));

            AudioFormat audioFormat = shootInputStream.getFormat();


            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);


            Clip shoot = (Clip) AudioSystem.getLine(info);


            shoot.open(shootInputStream);


            shoot.start();

            shootsSound.add(shoot);
            shootsSound.forEach(clip -> {
                if(!clip.isRunning()){
                    clip.stop();
                    clip.close();
                    shootsSound.remove(clip);
                }
            });

        }catch (Exception e){

        e.printStackTrace();

        }
    }
    public static void streamExplosion(){
        try {

             explosionInputStream = AudioSystem.getAudioInputStream(
                    new File("res/explosion.wav"));

            AudioFormat audioFormat = explosionInputStream.getFormat();


            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);


            Clip explosion = (Clip) AudioSystem.getLine(info);


            explosion.open(explosionInputStream);


            explosion.start();

            explosionsSound.add(explosion);
            explosionsSound.forEach(clip -> {
                if(!clip.isRunning()){
                    clip.stop();
                    clip.close();
                    explosionsSound.remove(clip);
                }
            });

        }catch (Exception e){

            e.printStackTrace();

        }
    }
}
