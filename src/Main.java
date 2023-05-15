import javax.swing.JFrame;
import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		JFrame frame = new JFrame();
		Game game = new Game(frame);

		frame.add(game);
		frame.setTitle("Comando Raid");
		frame.setLocationRelativeTo(null);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setFocusable(true);
		game.requestFocus();

		frame.setResizable(false);
		frame.setVisible(true);

		Thread thread = new Thread(game);
		
		thread.start();
	}
}
