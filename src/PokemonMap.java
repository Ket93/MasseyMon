import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PokemonMap extends JFrame {
    Timer myTimer;
    GamePanel game;

    public PokemonMap() {
        super ("Pokemon Massey"); // window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,650); // size
        game = new GamePanel(this); // defining game
        myTimer = new Timer(10, new TickListener()); // defining timer
        setResizable(false);
        add(game);
        setVisible(true);
        start(); // starting timer
    }

    public static void main(String[] arguments) throws IOException {
        PokemonMap frame = new PokemonMap();
    }

    class TickListener implements ActionListener { // implementing action listener and tick listener
        public void actionPerformed(ActionEvent evt) {
            if (game != null) { // if the game is still running
                game.repaint(); // re-paints the screen
            }
        }
    }
    public void start() {myTimer.start();} // starts timer
}

class GamePanel extends JPanel {

    private boolean ready = true;
    private Image PalletTown;

    public GamePanel(PokemonMap m){
        PalletTown = new ImageIcon ("Images/FSE - Pallet Town.png").getImage(); // getting the Pallet Town image
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }

    public void paintComponent (Graphics g){
        g.drawImage(PalletTown,0,0,null); // drawing the map of windsor
        PokeCharacter.draw(g);
    }
}
