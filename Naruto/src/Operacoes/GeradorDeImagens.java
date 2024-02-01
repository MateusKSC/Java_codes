package Operacoes;

import Ninjas.Personagem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

/**
 * Classe responsável por todas as exibições gráficas executadas durante
 * o jogo.
 * @author mateus.scheuermann
 * @version 1.0.0
 */
public class GeradorDeImagens {
    /**
     * Este método é responsável por fazer as buscas na internet das gifs que são exibidas sempre
     * que um ninja está prestes a executar um jutso ou desvia. A função busca por http o link
     * o qual é fornecido a ela na hora de ser invocada e, caso o endereço seja inacessível, a
     * mesma abre e exibe uma imagem presente na pasta "images" deste projeto, onde a informação
     * "image not available" é exibida.
     * @param urlGif é a URL em formato String da GIF que será exibida.
     */
    public void mostraGif(String urlGif) {
        int delay = 7000;
        Image image = null;

        try {
            URL ImageUrl = new URL(urlGif);
            Icon imageIcon = new ImageIcon(ImageUrl);
            JLabel label = new JLabel(imageIcon);
            JFrame frame = new JFrame("Animation");
            frame.getContentPane().add(label);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            Timer timer = new Timer(delay, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false); // Hide the first frame
                }
            });
            timer.start();
        } catch (Exception e) {
            System.out.println("A URL obtida da GIF está inacessível!");
            try {
                image = ImageIO.read(new File("images/image_not_available.png"));
                JFrame frame = new JFrame();
                frame.setSize(3000, 3000);
                JLabel label = new JLabel(new ImageIcon(image));
                frame.add(label);
                frame.setVisible(true);
            } catch (Exception e2) {
                System.out.println("Não foi possivel resolver o erro relacionado à exibição de GIFs!");
            }
        }
    }

    /**
     * Este método é responsável por fazer as buscas na internet das imagens dos retratos de cada ninja
     * que é escolhido no início do jogo. A função busca por http o link, o qual é fornecido a ela
     * na hora de ser invocada e, caso o endereço seja inacessível, a mesma abre e exibe uma imagem
     * presente na pasta "images" deste projeto, onde a informação "image not available" é exibida.
     * @param retrato é a URL em formato String da imagem que será exibida.
     */
    public void mostraRetratos(String retrato) {
        Image image = null;

        try {
            URL url = new URL(retrato);
            image = ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("Não foi possivel encontrar retratos");

            try {
                image = ImageIO.read(new File("images/image_not_available.png"));
            } catch (Exception e2) {
                System.out.println("Não foi possivel resolver o erro relacionado à exibição de retratos!");
            }
        }
        JFrame frame = new JFrame();
        frame.setSize(3000, 3000);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    }
}
