import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.MouseInfo;
import java.util.*;

public class PokemonBattle extends JFrame implements ActionListener{
        public static ArrayList<Pokemon> myPokes;
        public static ArrayList<Pokemon> enemyPokes;
        private Pokemon curPoke1, curPoke2;
        private boolean fleeable;
        javax.swing.Timer myTimer;
        GamePanel2 game;
    public PokemonBattle(ArrayList < Pokemon > mine, ArrayList < Pokemon > enemy) {
        super("Pokemon Battle");
        myPokes = mine;
        enemyPokes = enemy;
        myTimer = new javax.swing.Timer(10, this);     // trigger every 10 ms
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(956, 795);
        game = new GamePanel2(this);
        add(game);
        setResizable(false);
        setVisible(true);
    }
    public void start () {
        myTimer.start();
    }
    public int[] getPokes(){
        int [] pokeIndexes = new int[2];
        pokeIndexes[0] = curPoke1.getNum();
        pokeIndexes[1] = curPoke2.getNum();
        return pokeIndexes;
    }
    public void actionPerformed (ActionEvent evt){
        game.move();
        game.repaint();
    }
    public boolean getAlive(ArrayList<Pokemon> pokes){
        boolean MPAlive = false;
        for (Pokemon item: pokes){
            if (item.getHP() > 0){
                MPAlive = true;
            }
        }
        return MPAlive;
    }
    /*
    public void start(){
        curPoke1 = myPokes.get(0);
        curPoke2 = enemyPokes.get(0);
        while (getAlive(myPokes) == true && getAlive(enemyPokes) == true){
            if (curPoke1.getSpeed() > curPoke2.getSpeed()){
                userTurn();
                AITurn();
            }
            else{
                AITurn();
                userTurn();
            }
        }
    }
    */
    public static void main (String[]arguments){
        PokemonBattle frame = new PokemonBattle(myPokes,enemyPokes);
    }
}

class GamePanel2 extends JPanel implements KeyListener{
    private Image[] battleSprites;
    private int boxx,boxy;
    private boolean []keys;
    private Image back;
    private PokemonBattle mainFrame;
    public GamePanel2(PokemonBattle m){
        keys = new boolean[KeyEvent.KEY_LAST+1];
        back = new ImageIcon("Images/Backgrounds/background2.png").getImage();
        back= back.getScaledInstance(956,795,Image.SCALE_SMOOTH);
        mainFrame = m;
        boxx = 170;
        boxy = 170;
        setSize(800,600);
        addKeyListener(this);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void move(){
        if(keys[KeyEvent.VK_RIGHT] ){
            boxx += 5;
        }
        if(keys[KeyEvent.VK_LEFT] ){
            boxx -= 5;
        }
        if(keys[KeyEvent.VK_UP] ){
            boxy -= 5;
        }
        if(keys[KeyEvent.VK_DOWN] ){
            boxy += 5;
        }

        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void paintComponent(Graphics g){
        g.drawImage(back,0,-15,null);
        battleSprites = MasseyMon.frame.getPokeImages(mainFrame.getPokes());
        g.drawImage(battleSprites[0],50,550,null);
        g.drawImage(battleSprites[1],500,150,null);
    }
}
