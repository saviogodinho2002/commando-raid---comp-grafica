import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class Sound {
    public static Clip shoot;

    public static LinkedBlockingQueue<Clip> shootsSound;
    public static AudioInputStream shootInputStream;
    public  Sound() {
        shootsSound = new LinkedBlockingQueue<>();
    }
    public static void streamShoot(){
        try {

            shootInputStream = AudioSystem.getAudioInputStream(
                    new File("res/shoot.wav"));

            AudioFormat audioFormat = shootInputStream.getFormat();


            DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);


            Clip cshoot = (Clip) AudioSystem.getLine(info);


            cshoot.open(shootInputStream);

            cshoot.start();

            shootsSound.add(cshoot);
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
}
