import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class StartGameController {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Janela com Botões");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(960, 960);
            frame.setLayout(new GridBagLayout());

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            JButton botao1 = new JButton("Iniciar");
            JButton botao2 = new JButton("Fechar");

            botao1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JFrame gameFrame = new JFrame();
                    Game game = null;
                    try {
                        game = new Game(gameFrame);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    gameFrame.add(game);
                    gameFrame.setTitle("Comando Raid");
                    gameFrame.setLocationRelativeTo(null);
                    gameFrame.pack();

                    gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    game.setFocusable(true);
                    game.requestFocus();

                    gameFrame.setResizable(false);
                    gameFrame.setVisible(true);

                    Thread thread = new Thread(game);

                    thread.start();


                }

            });


            botao2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                   System.exit(0);
                }
            });


            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(botao1, gbc);

            gbc.gridy = 1;
            panel.add(botao2, gbc);

            frame.add(panel);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
