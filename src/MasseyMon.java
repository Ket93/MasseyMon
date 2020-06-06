import jdk.swing.interop.SwingInterOpUtils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.*;

public class MasseyMon extends JFrame {
	GamePanel game;
	javax.swing.Timer myTimer;
	public static boolean inBattle;
	public static MasseyMon frame;
	public static ArrayList<pokeMap> maps = new ArrayList<pokeMap>();
	public static ArrayList<ArrayList<pokeMapMini>> miniMaps = new ArrayList<ArrayList<pokeMapMini>>();
	public static ArrayList<NPC> trainers = new ArrayList<NPC>();
	public static ArrayList<ArrayList<NPC>> battleTrainers  = new ArrayList<ArrayList<NPC>>();
	private ArrayList<Pokemon> myPokes = new ArrayList<Pokemon>();
	private ArrayList<Pokemon> enemyPokes = new ArrayList<Pokemon>();
	private ArrayList<ArrayList<Pokemon>> allEncounters = new ArrayList<ArrayList<Pokemon>>();
	private ArrayList<ArrayList<ArrayList<Pokemon>>> trainerPokemon = new ArrayList<ArrayList<ArrayList<Pokemon>>>();
	private ArrayList<ArrayList<Boolean>> battledTrainers = new ArrayList<ArrayList<Boolean>>();
	private Pokemon bulbasaur,charmander,squirtle;
	public static Image [] starters = new Image [3];
	private PokemonBattle pokeBattle;
	public MasseyMon() throws IOException{
		super("MasseyMon");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myTimer = new javax.swing.Timer(10,new TickListener());
		load();
		game = new GamePanel();
		add(game);
		pack();
		setVisible(true);
		setResizable(false);
		start();
		inBattle = false;
	}
	public static void main(String[] args) throws IOException{
		frame = new MasseyMon();
	}
	public ArrayList<ArrayList<Boolean>> getBattles(){
		return battledTrainers;
	}
	public ArrayList<ArrayList<ArrayList<Pokemon>>> getTrainerPokes(){
		return trainerPokemon;
	}
	public GamePanel getGame() {
		return game;
	}
	public ArrayList<ArrayList<Pokemon>> getAllEncounters(){
		return allEncounters;
	}
	public ArrayList<Pokemon> getEnemyPokes(){
		return enemyPokes;
	}
	public ArrayList<ArrayList<Pokemon>> makeEncounters() throws FileNotFoundException {
		ArrayList<ArrayList<Pokemon>> allEncounters = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<Pokemon> firstEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> secondEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> thirdEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> fourthEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> fifthEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> sixthEncounters = new ArrayList<Pokemon>();
		ArrayList<ArrayList<Pokemon>> firstTrainers = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> secondTrainers = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> thirdTrainers = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> fourthTrainers = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> fifthTrainers = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> sixthTrainers = new ArrayList<ArrayList<Pokemon>>();
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/Moves.txt")));
		Scanner inFile2 = new Scanner(new BufferedReader(new FileReader("Data/Pokemon2.txt")));
		Pokemon caterpie = null,golbat = null, magneton = null, kabuto = null, kangaskhan = null,growlithe = null, weedle = null, ekans2 = null,vulpix = null, oddish = null, meowth = null, caterpie2= null, weedle2 = null, paras = null, ekans = null, caterpie3 = null, paras2 = null,weedle3 = null, rattata = null,diglett2 = null,diglett = null,machop2 = null, machop = null, onix = null, onix2 = null,ryhorn2 = null,ryhorn = null;
		Pokemon caterpieEnc = null,golbatEnc = null, magnetonEnc = null, kabutoEnc = null, kangaskhanEnc = null,caterpieEnc2 = null,growlitheEnc = null,weedleEnc = null,weedleEnc2 = null,pidgeyEnc = null,rattataEnc = null,parasEnc = null,ekansEnc = null,vulpixEnc = null, oddishEnc = null,meowthEnc = null,abraEnc = null, ekansEnc2 = null,diglettEnc = null, machopEnc = null, onixEnc = null, ryhornEnc = null;
		Attack tackle = null,bodySlam = null,lick = null,cut = null, thunderPunch = null,psybeam = null, wingAttack = null, peck = null,pin = null, smog = null, bite = null, headbutt = null, karate = null, firePunch = null, hornAttack = null, rockThrow = null, boneClub = null,absorb = null, ember = null, bubble = null,scratch = null,stomp = null,confusion = null;
		for (int i = 0; i < 84; i++){
			String line2 = inFile.nextLine();
			if (i == 0){
				absorb = new Attack(line2);
			}
			else if (i == 5){
				bite = new Attack(line2);
			}
			else if (i == 7){
				bodySlam = new Attack(line2);
			}
			else if (i ==9){
				boneClub = new Attack(line2);
			}
			else if (i == 11){
				bubble = new Attack(line2);
			}
			else if(i == 14){
				confusion = new Attack(line2);
			}
			else if (i == 17){
				cut = new Attack(line2);
			}
			else if (i == 27){
				ember = new Attack(line2);
			}
			else if(i == 30){
				firePunch = new Attack(line2);
			}
			else if(i == 37){
				headbutt = new Attack(line2);
			}
			else if(i == 39){
				hornAttack = new Attack(line2);
			}
			else if(i == 46){
				karate = new Attack(line2);
			}
			else if(i == 47){
				lick = new Attack(line2);
			}
			else if (i == 53){
				peck = new Attack(line2);
			}
			else if (i == 55){
				pin = new Attack(line2);
			}
			else if(i == 58){
				psybeam = new Attack(line2);
			}
			else if(i == 65){
				rockThrow = new Attack(line2);
			}
			else if (i == 67){
				scratch = new Attack(line2);
			}
			else if(i == 74){
				smog = new Attack(line2);
			}
			else if(i == 77){
				stomp = new Attack(line2);
			}
			else if (i == 83){
				tackle = new Attack(line2);
			}
			else if (i == 87){
				thunderPunch = new Attack(line2);
			}
			else if(i == 95){
				wingAttack = new Attack(line2);
			}
		}
		Scanner inFile3 = new Scanner(new BufferedReader(new FileReader("Data/Pokemon2.txt")));
		String dumInp = inFile3.nextLine();
		int count = 0;
		while (inFile3.hasNext()) {
			String line3 = inFile3.nextLine();
			if (count == 0){
				bulbasaur = new Pokemon(line3);
				bulbasaur.learnMove(tackle);
				bulbasaur.learnMove(absorb);
			}
			else if(count == 3){
				charmander = new Pokemon(line3);
				charmander.learnMove(tackle);
				charmander.learnMove(ember);
			}
			else if(count == 6){
				squirtle = new Pokemon(line3);
				squirtle.learnMove(tackle);
				squirtle.learnMove(bubble);
			}
			count++;
		}
		String line = inFile2.nextLine();
		for (int i = 0; i < 148; i++){
			line = inFile2.nextLine();
			if (i == 9){
				caterpieEnc = new Pokemon(line);
				caterpieEnc.learnMove(tackle);
				caterpieEnc.learnMove(pin);
				firstEncounters.add(caterpieEnc);
				caterpieEnc2 = new Pokemon(line);
				caterpieEnc2.learnMove(tackle);
				caterpieEnc2.learnMove(pin);
				thirdEncounters.add(caterpieEnc2);
				caterpie = new Pokemon(line);
				caterpie.learnMove(tackle);
				caterpie.learnMove(pin);
				caterpie.setLevel(3);
				caterpie2 = new Pokemon(line);
				caterpie2.learnMove(tackle);
				caterpie2.learnMove(pin);
				caterpie2.setLevel(4);
			}
			else if (i == 12){
				weedle = new Pokemon(line);
				weedle.learnMove(tackle);
				weedle.learnMove(pin);
				weedleEnc = new Pokemon(line);
				weedleEnc.learnMove(tackle);
				weedleEnc.learnMove(pin);
				firstEncounters.add(weedleEnc);
				weedleEnc2 = new Pokemon(line);
				weedleEnc2.learnMove(tackle);
				weedleEnc2.learnMove(pin);
				thirdEncounters.add(weedleEnc2);
				weedle2 = new Pokemon(line);
				weedle2.learnMove(tackle);
				weedle2.learnMove(pin);
				weedle2.learnMove(absorb);
				weedle3 = new Pokemon(line);
				weedle3.learnMove(tackle);
				weedle3.learnMove(pin);
				weedle3.learnMove(absorb);
			}
			else if (i == 15){
				pidgeyEnc = new Pokemon(line);
				pidgeyEnc.learnMove(tackle);
				pidgeyEnc.learnMove(peck);
				firstEncounters.add(pidgeyEnc);
			}
			else if (i == 18){
				rattataEnc = new Pokemon(line);
				rattataEnc.learnMove(tackle);
				rattataEnc.learnMove(bite);
				firstEncounters.add(rattataEnc);
				rattata = new Pokemon(line);
				rattata.learnMove(tackle);
				rattata.learnMove(bite);
			}
			else if(i == 22){
				ekansEnc = new Pokemon(line);
				ekansEnc.learnMove(smog);
				ekansEnc.learnMove(bite);
				thirdEncounters.add(ekansEnc);
				ekans = new Pokemon(line);
				ekans.learnMove(smog);
				ekans.learnMove(bite);
				ekansEnc2 = new Pokemon(line);
				ekansEnc2.learnMove(smog);
				ekansEnc2.learnMove(bite);
				ekans2 = new Pokemon(line);
				ekans2.learnMove(smog);
				ekans2.learnMove(bite);
			}
			else if(i == 34){
				vulpixEnc = new Pokemon(line);
				vulpixEnc.learnMove(scratch);
				vulpixEnc.learnMove(ember);
				secondEncounters.add(vulpixEnc);
				vulpix = new Pokemon(line);
				vulpix.learnMove(scratch);
				vulpix.learnMove(ember);
			}
			else if(i == 39){
				golbat = new Pokemon(line);
				golbat.learnMove(wingAttack);
				golbat.learnMove(psybeam);
				golbat.learnMove(scratch);
				golbatEnc = new Pokemon(line);
				golbatEnc.learnMove(wingAttack);
				golbatEnc.learnMove(psybeam);
				golbatEnc.learnMove(scratch);
				sixthEncounters.add(golbatEnc);
			}
			else if(i == 40){
				oddishEnc = new Pokemon(line);
				oddishEnc.learnMove(tackle);
				oddishEnc.learnMove(absorb);
				secondEncounters.add(oddishEnc);
				oddish = new Pokemon(line);
				oddish.learnMove(tackle);
				oddish.learnMove(absorb);
			}
			else if(i == 43){
				parasEnc = new Pokemon(line);
				parasEnc.learnMove(pin);
				parasEnc.learnMove(absorb);
				parasEnc.learnMove(bite);
				thirdEncounters.add(parasEnc);
				paras = new Pokemon(line);
				paras.learnMove(pin);
				paras.learnMove(absorb);
				paras.learnMove(bite);
				paras2 = new Pokemon(line);
				paras2.learnMove(pin);
				paras2.learnMove(absorb);
				paras2.learnMove(bite);
			}
			else if(i == 49){
				meowthEnc = new Pokemon(line);
				meowthEnc.learnMove(scratch);
				meowthEnc.learnMove(stomp);
				secondEncounters.add(meowthEnc);
				meowth = new Pokemon(line);
				meowth.learnMove(scratch);
				meowth.learnMove(stomp);
			}
			else if(i == 47){
				diglettEnc = new Pokemon(line);
				diglettEnc.learnMove(tackle);
				diglettEnc.learnMove(boneClub);
				diglettEnc.learnMove(rockThrow);
				fifthEncounters.add(diglettEnc);
				diglett = new Pokemon(line);
				diglett.learnMove(tackle);
				diglett.learnMove(boneClub);
				diglett.learnMove(rockThrow);
				diglett2 = new Pokemon(line);
				diglett2.learnMove(tackle);
				diglett2.learnMove(boneClub);
				diglett2.learnMove(rockThrow);
			}
			else if (i == 55){
				growlithe = new Pokemon(line);
				growlithe.learnMove(scratch);
				growlithe.learnMove(ember);
				growlithe.learnMove(bite);
				growlitheEnc = new Pokemon(line);
				growlitheEnc.learnMove(scratch);
				growlitheEnc.learnMove(ember);
				growlitheEnc.learnMove(bite);
				fourthEncounters.add(growlitheEnc);
			}
			else if(i == 60){
				abraEnc = new Pokemon(line);
				abraEnc.learnMove(tackle);
				abraEnc.learnMove(confusion);
				secondEncounters.add(abraEnc);
			}
			else if(i == 63){
				machopEnc = new Pokemon(line);
				machopEnc.learnMove(karate);
				machopEnc.learnMove(headbutt);
				fifthEncounters.add(machopEnc);
				machop = new Pokemon(line);
				machop.learnMove(karate);
				machop.learnMove(headbutt);
				machop2 = new Pokemon(line);
				machop2.learnMove(karate);
				machop2.learnMove(headbutt);
			}
			else if(i == 79){
				magneton = new Pokemon(line);
				magneton.learnMove(thunderPunch);
				magneton.learnMove(psybeam);
				magneton.learnMove(tackle);
				magnetonEnc = new Pokemon(line);
				magnetonEnc.learnMove(thunderPunch);
				magnetonEnc.learnMove(psybeam);
				magnetonEnc.learnMove(tackle);
				sixthEncounters.add(magnetonEnc);
			}
			else if(i == 92){
				onix = new Pokemon(line);
				onix.learnMove(rockThrow);
				onix.learnMove(firePunch);
				onix2 = new Pokemon(line);
				onix2.learnMove(rockThrow);
				onix2.learnMove(firePunch);
				onixEnc = new Pokemon(line);
				onixEnc.learnMove(rockThrow);
				onixEnc.learnMove(firePunch);
				fifthEncounters.add(onixEnc);
			}
			else if(i == 108){
				ryhorn = new Pokemon(line);
				ryhorn.learnMove(hornAttack);
				ryhorn.learnMove(rockThrow);
				ryhorn2 = new Pokemon(line);
				ryhorn2.learnMove(hornAttack);
				ryhorn2.learnMove(rockThrow);
				ryhornEnc = new Pokemon(line);
				ryhornEnc.learnMove(hornAttack);
				ryhornEnc.learnMove(rockThrow);
				fifthEncounters.add(ryhornEnc);
			}
			else if(i == 112){
				kangaskhan = new Pokemon(line);
				kangaskhan.learnMove(karate);
				kangaskhan.learnMove(bodySlam);
				kangaskhan.learnMove(lick);
				sixthEncounters.add(kangaskhanEnc);
				kangaskhanEnc = new Pokemon(line);
				kangaskhanEnc.learnMove(karate);
				kangaskhanEnc.learnMove(bodySlam);
				kangaskhanEnc.learnMove(lick);
			}
			else if(i == 137){
				kabuto = new Pokemon(line);
				kabuto.learnMove(rockThrow);
				kabuto.learnMove(boneClub);
				kabuto.learnMove(cut);
				kabutoEnc = new Pokemon(line);
				kabutoEnc.learnMove(rockThrow);
				kabutoEnc.learnMove(boneClub);
				kabutoEnc.learnMove(cut);
				sixthEncounters.add(kabuto);
			}
		}
		allEncounters.add(firstEncounters);
		allEncounters.add(secondEncounters);
		allEncounters.add(thirdEncounters);
		allEncounters.add(fourthEncounters);
		allEncounters.add(fifthEncounters);

		ArrayList<Pokemon> newTrainer = new ArrayList<Pokemon> ();
		newTrainer.add(caterpie);
		firstTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(weedle);
		secondTrainers.add(newTrainer);
		thirdTrainers.add(null);
		fourthTrainers.add(null);
		fifthTrainers.add(null);
		sixthTrainers.add(null);

		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(vulpix);
		firstTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(oddish);
		newTrainer.add(meowth);
		secondTrainers.add(newTrainer);
		thirdTrainers.add(null);
		fourthTrainers.add(null);
		fifthTrainers.add(null);
		sixthTrainers.add(null);

		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(caterpie2);
		firstTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(weedle2);
		secondTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(paras);
		thirdTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(ekans);
		fourthTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(caterpie2);
		newTrainer.add(ekans2);
		fifthTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(weedle3);
		newTrainer.add(paras2);
		sixthTrainers.add(newTrainer);

		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(growlithe);
		firstTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		secondTrainers.add(null);
		thirdTrainers.add(null);
		fourthTrainers.add(null);
		fifthTrainers.add(null);
		sixthTrainers.add(null);

		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(diglett);
		newTrainer.add(ryhorn2);
		firstTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(machop);
		newTrainer.add(ryhorn);
		secondTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(onix);
		newTrainer.add(diglett2);
		thirdTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(machop2);
		newTrainer.add(onix2);
		fourthTrainers.add(newTrainer);
		fifthTrainers.add(null);
		sixthTrainers.add(null);

		trainerPokemon.add(firstTrainers);
		trainerPokemon.add(secondTrainers);
		trainerPokemon.add(thirdTrainers);
		trainerPokemon.add(fourthTrainers);
		trainerPokemon.add(fifthTrainers);
		trainerPokemon.add(sixthTrainers);
		return allEncounters;
	}
	public JTextArea getTextArea2(){
		game.makeNewArea();
		return game.getTextArea();
	}
	public void setPokeBattleNull(){
		pokeBattle = null;
	}
	public void setPokeBattle(PokemonBattle battle){
		pokeBattle = battle;
	}
	public void startBattle(Graphics g, Player myGuy) throws IOException {
		pokeBattle = new PokemonBattle(myPokes, enemyPokes, myGuy);
		for (ArrayList<Pokemon> item: allEncounters){
			for (Pokemon item2: item){
				if(item2 == enemyPokes.get(0)){
					pokeBattle.setFleeable(true);
				}
			}
		}
		pokeBattle.Start(g);
	}
	public void setEnemyPokes(ArrayList<Pokemon> newPokes){
		enemyPokes = newPokes;
	}
	public ArrayList<ArrayList<Pokemon>> getMap1Trainers() throws FileNotFoundException {
		Scanner inFile2 = new Scanner(new BufferedReader(new FileReader("Data/Pokemon2.txt")));
		String line2 = "";
		String line3 = "";
		for (int i = 0; i < 14; i++){
			line2 = inFile2.nextLine();
		}
		for (int i = 0; i < 17; i++){
			line3 = inFile2.nextLine();
		}
		ArrayList<Pokemon> newPokes = new ArrayList<Pokemon>();
		ArrayList<Pokemon> newPokes2 = new ArrayList<Pokemon>();
		ArrayList<ArrayList<Pokemon>> allPokes = new ArrayList<ArrayList<Pokemon>>();
		Pokemon newPoke = new Pokemon(line2);
		Pokemon newPoke2 = new Pokemon(line2);
		Pokemon newPoke3 = new Pokemon(line3);
		newPokes.add(newPoke);
		newPokes2.add(newPoke2);
		newPokes2.add(newPoke3);
		allPokes.add(newPokes);
		allPokes.add(newPokes2);
		inFile2.close();
		return allPokes;
	}
	public PokemonBattle getPokeBattle(){ return pokeBattle; }
	public void load() throws IOException {
		starters [0] = ImageIO.read(new File("Sprites/Pokemon/P1.png")).getScaledInstance(200,200,Image.SCALE_SMOOTH);
		starters [1] = ImageIO.read(new File("Sprites/Pokemon/P4.png")).getScaledInstance(200,200,Image.SCALE_SMOOTH);
		starters [2] = ImageIO.read(new File("Sprites/Pokemon/P7.png")).getScaledInstance(200,200,Image.SCALE_SMOOTH);
		allEncounters = makeEncounters();
		for (int i = 0; i < 14; i++){
			ArrayList<Boolean> newBooleans = new ArrayList<Boolean>();
			for (int j = 0; j < 6; j++){
				newBooleans.add(false);
			}
			battledTrainers.add(newBooleans);
		}
		myPokes.add(bulbasaur);
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/PlayerPositions.txt")));
		for (int i = 0; i < 15; i++) {
			String line = inFile.nextLine();
			String path = String.format("%s/%s/%s%d.png", "Images", "Backgrounds", "Background", i);
			String pathMask = String.format("%s/%s/%s%d%s.png", "Images", "Masks", "Background", i,"Mask");
			try {
				ArrayList<ArrayList<Pokemon>> mapTrainers = new ArrayList<ArrayList<Pokemon>>();
				Image pic = ImageIO.read(new File(path));
				BufferedImage picMask = ImageIO.read(new File(pathMask));
				mapTrainers = getMap1Trainers();
				maps.add(new pokeMap(pic,picMask,line,mapTrainers));
			} catch (IOException e) {}
		}
		inFile = new Scanner(new BufferedReader(new FileReader("Data/miniPlayerPositions")));
		for (int k = 0; k <15;k++) {
			miniMaps.add(new ArrayList<pokeMapMini>());
			for (int i = 0; i < 5; i++) {
				String line = inFile.nextLine();
				String path = String.format("%s/%s/%s%d/%s%d.png", "Images", "MiniBackgrounds", "Town ", k, "MiniBackground", i);
				String pathMask = String.format("%s/%s/%s%d/%s%d%s.png", "Images", "MiniMasks","Town ",k, "Background", i, "Mask");
				try {
					Image pic = ImageIO.read(new File(path));
					BufferedImage picMask = ImageIO.read(new File(pathMask));
					miniMaps.get(k).add(new pokeMapMini(pic, picMask, line));
				}
				catch (IOException e) {
				}
			}
		}

		for (int i =0; i<18;i++){
			String path = String.format("%s/%s/%s%d.png", "Images", "NPCs", "Trainer", i);
			Image pic = ImageIO.read(new File(path));
			try {
				trainers.add(new NPC(pic));
			}
			catch (Exception e) {
			}
		}
	}

	public void healPokes(){
		for (Pokemon item : myPokes ){
			item.setHP(item.getMaxHP());
		}
	}

	public static NPC getTrainers(int n){
		return trainers.get(n);
	}

	public static pokeMap getMap(int n){
		return maps.get(n);
	}

	public static pokeMapMini getMiniMap( int n ,int k ) {
		return miniMaps.get(n).get(k);
	}
	public static NPC getBattleTrainers(int n,int v){
		return battleTrainers.get(n).get(v);
	}
	public ArrayList<Pokemon> getMyPokes(){
		return myPokes;
	}
	public Pokemon getBulbasaur(){
		return bulbasaur;
	}
	public Pokemon getCharmander(){
		return charmander;
	}
	public Pokemon getSquirtle(){
		return squirtle;
	}

	public void start(){
		myTimer.start();
	}
	class TickListener implements ActionListener{
		public void actionPerformed(ActionEvent evt){
			if(game!= null){
				if (!inBattle) {
					game.move();
				}
				if (game.inGrass && !inBattle){
					game.checkGrass();
				}
				game.repaint();
			}
		}
	}
}

class GamePanel extends JPanel {
	public static final int INTERVAL = 5, STARTING = 114;
	private static int offsetX,offsetY;
	private int mx,my,picIndex,miniPicIndex,npc1,npc2,starterIndex,trainerTextIndex,progress,routeIndex;
	private boolean pokemon;
	private boolean bag;
	private boolean menu;
	private boolean oakTalked,oneTimeTalk,talkDone;
	public static boolean inGrass;
	private boolean spacePressed,movable,talking,hasStarter,trainerText,brockTalking,mistyTalking,giovanniTalking,titleScreen,championTalking;
	private int direction,frameEvo;
	private boolean ready = true;
	private static boolean mini,starter;
	private boolean onePressed,twoPressed,threePressed,fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed;
	private boolean[] keys;
	private Image [] starters;
	private Image selectBox,evoBack,prof,hallOfFame,endScreen;
	private boolean animating;
	private float frame;
	private Composite var;
	pokeMap myMap;
	pokeMapMini myMiniMap;
	Textbox myTextBox;
	PokemonMenu myPokeMenu;
	TitleScreen myTitle;
	Items myItem;
	Menu myMenu;
	Player myGuy;
	NPC myNPC;
	private int x,y, dir;
	private int DIRDOWN = 1,DIRRIGHT = 2, DIRUP = 3, DIRLEFT = 4;
	private int alpha = 0;
	private boolean started,started2,started3,doneEvo;
	public static final int IDLE = 0, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10;
	private JTextArea myArea, myArea2;
	private Sound battleSound,evolutionMusic;
	public GamePanel() throws IOException{
		setLayout(null);
		makeNewArea();
		myArea2 = new JTextArea();
		myArea2.setBackground(new Color(0,0,0,0));
		myArea2.setBounds(40,635,600,125);
		myArea2.setVisible(false);
		myArea2.setEditable(false);
		myArea2.setHighlighter(null);
		myArea2.setWrapStyleWord(true);
		myArea2.setLineWrap(true);
		myArea2.setFont(new Font("Font/gameFont.ttf",Font.BOLD,30));
		add(myArea2);
		movable = false;
		offsetX = 0;
		offsetY = 0;
		starterIndex = 0;
		picIndex = 2;
		miniPicIndex = -1;
		hasStarter = false;
		spacePressed = false;
		trainerText = false;
		pokemon = false;
		bag = false;
		menu = false;
		mini = false;
		talking = false;
		started2 = false;
		started3 = false;
		inGrass = false;
		doneEvo = false;
		titleScreen = true;
		oakTalked = false;
		oneTimeTalk = false;
		talkDone = false;
		onePressed = false;
		twoPressed = false;
		threePressed = false;
		fourPressed = false;
		fivePressed = false;
		sixPressed = false;
		sevenPressed = false;
		eightPressed = false;
		ninePressed = false;
		starters = MasseyMon.starters;
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		myGuy = new Player(0);
		myNPC = new NPC(MasseyMon.getTrainers(0).getSprite());
		myTitle = new TitleScreen();
		myPokeMenu = new PokemonMenu();
		myMenu = new Menu();
		myItem = new Items();
		myTextBox = new Textbox();
		myMap = (MasseyMon.getMap(picIndex));
		started = false;
		frame = (float)(frame);
		//myMiniMap = (MasseyMon.getMiniMap(picIndex,miniPicIndex+1));
		selectBox = ImageIO.read(new File("Images/Text/SelectBox.jpg")).getScaledInstance(300,300,Image.SCALE_SMOOTH);
		evoBack = ImageIO.read(new File("Images/Battles/evoBack.png")).getScaledInstance(956,790,Image.SCALE_SMOOTH);
		prof = ImageIO.read(new File("Images/TitleScreen/ProfessorOak.png"));
		hallOfFame = ImageIO.read(new File("Images/TitleScreen/hallOfFame.png"));
		endScreen = ImageIO.read(new File("Images/TitleScreen/endScreen.png"));
		setPreferredSize(new Dimension(956,795));
		addMouseListener(new clickListener());
		addKeyListener(new moveListener());
		battleSound = new Sound("Music/Battle/pokeBattleMusic.wav",75);
		evolutionMusic = new Sound("Music/Battle/evolutionMusic.wav",75);
	}
	public void addNotify() {
		super.addNotify();
		requestFocus();
		ready = true;
	}
	public void checkGrass(){
		int x = randint(1,350);
		if (x == 1 && MasseyMon.frame.inBattle == false && movable){
			ArrayList<Pokemon> encounters = new ArrayList<Pokemon>();
			int low = 0;
			int high = 0;
			if (picIndex == 1){
				encounters = MasseyMon.frame.getAllEncounters().get(0);
				low = 3;
				high = 5;
			}
			else if(picIndex == 3){
				encounters = MasseyMon.frame.getAllEncounters().get(1);
				low = 5;
				high = 8;
			}
			else if (picIndex == 4){
				encounters = MasseyMon.frame.getAllEncounters().get(2);
				low = 8;
				high = 11;
			}
			else if (picIndex == 5){
				encounters = MasseyMon.frame.getAllEncounters().get(3);
				low = 8;
				high = 11;
			}
			else if(picIndex == 7){
				encounters = MasseyMon.frame.getAllEncounters().get(4);
				low = 11;
				high = 14;
			}
			else if(picIndex == 9 && picIndex == 10){
				encounters = MasseyMon.frame.getAllEncounters().get(5);
				low = 16;
				high = 17;
			}
			for (Pokemon item: encounters){
				item.setHP(item.getMaxHP());
				for (Attack atk: item.getMoves()){
					atk.setPP(atk.getMaxPP());
				}
			}
			int y = randint(0,encounters.size()-1);
			ArrayList<Pokemon> enemyPoke = new ArrayList<Pokemon>();
			enemyPoke.add(encounters.get(y));
			int random = randint(low,high);
			enemyPoke.get(0).setLevel(random);
			MasseyMon.frame.setEnemyPokes(enemyPoke);
			MasseyMon.frame.inBattle = true;
		}
	}
	public void makeNewArea(){
		myArea = new JTextArea();
		myArea.setBackground(new Color(0,0,0,0));
		myArea.setBounds(40,635,390,125);
		myArea.setVisible(true);
		myArea.setEditable(false);
		myArea.setHighlighter(null);
		myArea.setWrapStyleWord(true);
		myArea.setLineWrap(true);
		myArea.setFont(new Font("Font/gameFont.ttf",Font.BOLD,30));
		myArea.setText("");
		add(myArea);
	}
	public void draw(Graphics g) {
		if (MasseyMon.frame.inBattle == false){
			myArea.setVisible(false);
			if (MasseyMon.frame.getPokeBattle() != null){
				MasseyMon.frame.getPokeBattle().getMyArea().setVisible(false);
			}
			started = false;
			started2 = false;
			alpha = 0;
		}
		if (titleScreen) {
			TitleScreen.draw(g);
		}
		else {
			if (!talking && !starter) {
				movable = true;
				g.setColor(new Color(0, 0, 0));
				g.fillRect(0, 0, 956, 795);
				offsetX = 0;
				offsetY = 0;
				if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
					offsetX = myGuy.getScreenX() - myGuy.getWorldX();
				}
				if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
					offsetY = myGuy.getScreenY() - myGuy.getWorldY();
				}
				if (mini) {
					g.drawImage(MasseyMon.getMiniMap(picIndex, miniPicIndex).getMap(), MasseyMon.getMiniMap(picIndex, miniPicIndex).getMapX(), MasseyMon.getMiniMap(picIndex, miniPicIndex).getMapY(), this);
				} else {
					if (offsetX == 0 && offsetY == 0) {
						g.drawImage(MasseyMon.getMap(picIndex).getMap(), MasseyMon.getMap(picIndex).getMapX(), MasseyMon.getMap(picIndex).getMapY(), this);
					} else if (offsetX != 0 && offsetY == 0) {
						g.drawImage(MasseyMon.getMap(picIndex).getMap(), offsetX, MasseyMon.getMap(picIndex).getMapY(), this);
					} else if (offsetX == 0 && offsetY != 0) {
						g.drawImage(MasseyMon.getMap(picIndex).getMap(), MasseyMon.getMap(picIndex).getMapX(), offsetY, this);
					} else {
						g.drawImage(MasseyMon.getMap(picIndex).getMap(), offsetX, offsetY, this);
					}
				}
				myGuy.draw(g);
			}
			if (picIndex == 0 && miniPicIndex == 1) {
				if (!oakTalked) {
					if (Textbox.getTextWriting()) {
						if (talking) {
							Textbox.display(g, 1, spacePressed,onePressed,twoPressed,threePressed,
									fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed);
							movable = false;
							spacePressed = false;
						}
					}
					else {
						if (!movable) {
							talking = false;
							starter = false;
							g.setColor(new Color(0, 0, 0));
							g.fillRect(0, 0, 956, 795);
							g.drawImage(MasseyMon.getMiniMap(0, 1).getMap(), MasseyMon.getMiniMap(0, 1).getMapX(), MasseyMon.getMiniMap(0, 1).getMapY(), this);
							if (!spacePressed) {
								starter = true;
								g.setColor(new Color(255, 255, 255));
								g.fillRect(350, 250, 300, 300);
								g.drawImage(selectBox, 350, 250, this);
								g.drawImage(starters[starterIndex], 420, 300, this);
								movable = false;
								hasStarter = true;
							}
							if (spacePressed) {
								Pokemon select = null;
								if (starterIndex == 0) {
									select = MasseyMon.frame.getBulbasaur();
								} else if (starterIndex == 1) {
									select = MasseyMon.frame.getCharmander();
								} else if (starterIndex == 2) {
									select = MasseyMon.frame.getSquirtle();
								}
								oakTalked = true;
								MasseyMon.frame.getMyPokes().add(select);
							}
						}
					}
				}
			}
			else if (pokeCenter()) {
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g, 2, spacePressed,onePressed,twoPressed,threePressed,
								fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed);
						movable = false;
						spacePressed = false;
						MasseyMon.frame.healPokes();
					}
				} else {
					g.setColor(new Color(0, 0, 0));
					g.fillRect(0, 0, 956, 795);
					g.drawImage(MasseyMon.getMiniMap(2, 0).getMap(), MasseyMon.getMiniMap(2, 0).getMapX(), MasseyMon.getMiniMap(2, 0).getMapY(), this);
					talking = false;
					movable = true;
				}
			}

			else if (pokeShop()) {
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g, 3, spacePressed,onePressed,twoPressed,threePressed,
						fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed);
						movable = false;
						spacePressed = false;
					}
				} else {
					g.setColor(new Color(0, 0, 0));
					g.fillRect(0, 0, 956, 795);
					g.drawImage(MasseyMon.getMiniMap(2, 1).getMap(), MasseyMon.getMiniMap(2, 1).getMapX(), MasseyMon.getMiniMap(2, 1).getMapY(), this);
					talking = false;
					movable = true;
				}
			}

			else if (picIndex == 0 && miniPicIndex == 0){
				if (Textbox.getTextWriting()) {
					if (talking) {
						MasseyMon.frame.healPokes();
						Textbox.display(g, 12, spacePressed,onePressed,twoPressed,threePressed,
								fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed);
						movable = false;
						spacePressed = false;
					}
				} else {
					g.setColor(new Color(0, 0, 0));
					g.fillRect(0, 0, 956, 795);
					g.drawImage(MasseyMon.getMiniMap(0, 0).getMap(), MasseyMon.getMiniMap(0, 0).getMapX(), MasseyMon.getMiniMap(0, 0).getMapY(), this);
					myGuy.draw(g);
					talking = false;
					movable = true;
				}
			}

			else if (brockTalking){
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g, 13, spacePressed,onePressed,twoPressed,threePressed,
								fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed);
						movable = false;
						spacePressed = false;
					}
				} else {
					g.setColor(new Color(0, 0, 0));
					g.fillRect(0, 0, 956, 795);
					g.drawImage(MasseyMon.getMiniMap(6, 4).getMap(), MasseyMon.getMiniMap(6, 4).getMapX(), MasseyMon.getMiniMap(6, 4).getMapY(), this);
					myGuy.draw(g);
					talking = false;
					movable = true;
				}
			}
			else if (mistyTalking){
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g, 14, spacePressed,onePressed,twoPressed,threePressed,
								fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed);
						movable = false;
						spacePressed = false;
					}
				} else {
					g.setColor(new Color(0, 0, 0));
					g.fillRect(0, 0, 956, 795);
					g.drawImage(MasseyMon.getMiniMap(12, 3).getMap(), MasseyMon.getMiniMap(12, 3).getMapX(), MasseyMon.getMiniMap(12, 3).getMapY(), this);
					myGuy.draw(g);
					talking = false;
					movable = true;
				}
			}
			else if (giovanniTalking){
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g, 15, spacePressed,onePressed,twoPressed,threePressed,
								fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed);
						movable = false;
						spacePressed = false;
					}
				} else {
					g.setColor(new Color(0, 0, 0));
					g.fillRect(0, 0, 956, 795);
					g.drawImage(MasseyMon.getMiniMap(2, 4).getMap(), MasseyMon.getMiniMap(2, 4).getMapX(), MasseyMon.getMiniMap(2, 4).getMapY(), this);
					myGuy.draw(g);
					talking = false;
					movable = true;
				}
			}

			else if (championTalking){
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g, 16, spacePressed,onePressed,twoPressed,threePressed,
								fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed);
						movable = false;
						spacePressed = false;
					}
				} else {
					g.setColor(new Color(0, 0, 0));
					g.fillRect(0, 0, 956, 795);
					g.drawImage(MasseyMon.getMap(14).getMap(), MasseyMon.getMap(14).getMapX(), MasseyMon.getMap(14).getMapY(), this);
					myGuy.draw(g);
					talking = false;
					movable = true;
				}
			}

			else if (trainerText){
				if (Textbox.getTextWriting()) {
					if (talking) {
						Textbox.display(g,trainerTextIndex, spacePressed,onePressed,twoPressed,threePressed,
								fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed);
						movable = false;
						spacePressed = false;
					}
				}
				else {
					talking = false;
					movable = true;
					}
				}


			if (movable) {
				if (picIndex == 0 && miniPicIndex == 1) {
					g.drawImage(MasseyMon.getTrainers(0).getSprite(), 475, 300, this);
				}
				if (pokeCenter()) {
					g.drawImage(MasseyMon.getTrainers(1).getSprite(), 462, 290, this);
					myGuy.draw(g);
				}
				if (pokeShop()) {
					g.drawImage(MasseyMon.getTrainers(2).getSprite(), 358, 350, this);
					myGuy.draw(g);
				}
				if (pokeHouse()) {
					g.drawImage(MasseyMon.getTrainers(npc1).getSprite(), 407, 385, this);
					g.drawImage(MasseyMon.getTrainers(npc2).getSprite(), 612, 360, this);
					myGuy.draw(g);
				}
			}
			if (menu) {
				Menu.display(g);
				if (bag) {
					Items.display(g);
				}
				if (pokemon) {
					PokemonMenu.display(g);
				}
			}
		}
	}
	public JTextArea getTextArea(){
		return myArea;
	}
	public void paintComponent(Graphics g) {
		if (!MasseyMon.frame.inBattle) {
			if (battleSound.isPlaying()){
				battleSound.stop();
			}
			if (MasseyMon.frame.getPokeBattle() != null){
				if (MasseyMon.frame.getPokeBattle().getEvolutions().size() == 0){
					draw(g);
				}
				else{
					if (battleSound.isPlaying()){
						battleSound.stop();
					}
					g.drawImage(evoBack,0,0,this);
					Pokemon pokeEvo = MasseyMon.frame.getPokeBattle().getEvolutions().get(0);
					if (!started3){
						String myString = String.format("What?\n%s is evolving!",pokeEvo.getName());
						myArea2.setVisible(true);
						myArea2.setText(myString);
						progress = 0;
						frameEvo = 1;
						evolutionMusic.play();
						started3 = true;
						doneEvo = false;
					}
					else{
						if (doneEvo){
							if (!evolutionMusic.isPlaying()){
								myArea2.setVisible(false);
								MasseyMon.frame.getPokeBattle().getEvolutions().remove(0);
								started3 = false;
								evolutionMusic.stop();
							}
						}
						if (!(progress ==STARTING)){
							if (frameEvo%(STARTING-progress) == 0){
								progress+= 3;
								pokeEvo.drawEvoNext(g);
								if (progress == STARTING){
									String curName = pokeEvo.getName();
									try { pokeEvo.evolve();}
									catch (IOException e) {}
									String myString = String.format("%s evolved into %s!",curName,pokeEvo.getName());
									myArea2.setText(myString);
								}
							}
							else{
								pokeEvo.drawEvo(g);
							}
							frameEvo++;
						}
						else{
							pokeEvo.drawEvoNext(g);
						}
					}
				}

			}
			else{
				draw(g);
			}
		}
		else{
			if (!battleSound.isPlaying()){
				battleSound.play();
			}
			if (started == false) {
				Color myColor = new Color(0,0,0,alpha);
				g.setColor(myColor);
				if (alpha == 100) {
					started = true;
				} else {
					alpha += 1;
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			}
			else{
				if (started2 == false){
					try {
						MasseyMon.frame.startBattle(g,myGuy);
						makeNewArea();
						started2 = true;
					}
					catch (IOException e) {}
					started2 = true;
				}
				else{
					MasseyMon.frame.getPokeBattle().Start(g);
				}
			}
		}
		spacePressed = false;
	}

	public void drawEndScreen(Graphics g){
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0,0,956,795);
		g.drawImage(hallOfFame,0,130,null);
		g.drawImage(prof,450,0,null);
		g.drawImage(endScreen,180,150,null);


	}
	class clickListener implements MouseListener {
		public void mouseEntered(MouseEvent e) {

		}
		public void mouseExited(MouseEvent e) {

		}
		public void mouseReleased(MouseEvent e) {

		}
		public void mouseClicked(MouseEvent e) {

		}
		public void mousePressed(MouseEvent e) {
			mx = e.getX();
			my = e.getY();
			if (MasseyMon.frame.inBattle){
				if (!MasseyMon.frame.getPokeBattle().getStopGame()){
					MasseyMon.frame.getPokeBattle().checkCollision();
				}
			}
		}
	}

	class moveListener implements KeyListener {
		public void keyTyped(KeyEvent e) {
			if (keys[KeyEvent.VK_SPACE]){
				if (MasseyMon.frame.inBattle) {
					if (started2){
						MasseyMon.frame.getPokeBattle().goNext();
					}
				}
				if (!evolutionMusic.isPlaying()){
					doneEvo = true;
				}
			}
		}
		public void keyPressed(KeyEvent e) {
			if (titleScreen) {
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
					TitleScreen.setPosY(505);
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){
					TitleScreen.setPosY(305);
				}
				else if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) && TitleScreen.getInstructionMenu()){
					TitleScreen.setInstructionMenu(false);
				}
				else if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) && TitleScreen.getPosY() == 505){
					TitleScreen.setInstructionMenu(true);
				}
				else if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) && TitleScreen.getPosY() == 305){
					titleScreen = false;
				}
			}
			else {
				if (pokeShop()){
					if (e.getKeyCode() == KeyEvent.VK_1){
						onePressed = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_2){
						twoPressed = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_3){
						threePressed = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_4){
						fourPressed = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_5){
						fivePressed = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_6){
						sixPressed = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_7){
						sevenPressed = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_8){
						eightPressed = true;
					}
					if (e.getKeyCode() == KeyEvent.VK_9){
						ninePressed = true;
					}
				}
				if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && !movable) {
					starterIndex -= 1;
					if (starterIndex == -1) {
						starterIndex = 2;
					}
				}
				if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && !movable) {
					starterIndex += 1;
					if (starterIndex == 3) {
						starterIndex = 0;
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_M && keys[e.getKeyCode()] == false) {
					menu = true;
				}
				if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && keys[e.getKeyCode()] == false && menu && !bag && !pokemon) {
					Menu.setPosY(40);
				}
				if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && keys[e.getKeyCode()] == false && menu && !bag && !pokemon) {
					Menu.setPosY(-40);
				}
				if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && keys[e.getKeyCode()] == false && bag) {
					Items.setPosY(40);
				}
				if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && keys[e.getKeyCode()] == false && bag) {
					Items.setPosY(-40);
				}
				if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && keys[e.getKeyCode()] == false && pokemon) {
					PokemonMenu.setPosY();
					PokemonMenu.setPosX();
				}
				if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && keys[e.getKeyCode()] == false && pokemon) {
					PokemonMenu.setPosYUP();
					PokemonMenu.setPosX();
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE && keys[e.getKeyCode()] == false && Menu.getPosY() == 186 && !pokemon && menu) {
					PokemonMenu.resetPosXY();
					pokemon = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE && keys[e.getKeyCode()] == false && Menu.getPosY() == 226 && !bag) {
					Items.resetPosY();
					bag = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE && keys[e.getKeyCode()] == false && Menu.getPosY() == 266 && !bag && !pokemon) {
					Menu.resetPosY();
					menu = false;
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE && keys[e.getKeyCode()] == false && Items.getPosY() == 287 && bag) {
					Menu.resetPosY();
					bag = false;
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE && keys[e.getKeyCode()] == false && PokemonMenu.getDisplayButton() && pokemon) {
					Menu.resetPosY();
					pokemon = false;
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					spacePressed = true;
				}
				keys[e.getKeyCode()] = true;
			}
		}

		public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
		}
	}


	public void move() {
		if (!titleScreen) {
			if (!menu) {
				if (movable) {
					if (picIndex == 1){
						routeIndex = 0;
					}
					else if(picIndex == 3){
						routeIndex = 1;
					}
					else if(picIndex == 4){
						routeIndex = 2;
					}
					else if(picIndex == 5){
						routeIndex = 3;
					}
					inGrass = false;
					if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W]) && clear(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 19, myGuy.getWorldY() - 1) && (checkLedge(myGuy.getWorldX(), myGuy.getWorldY() - 2, myGuy.getWorldX() + 19, myGuy.getWorldY() - 2)
							&& checkLedgeLeft(myGuy.getWorldX(), myGuy.getWorldY() - 2, myGuy.getWorldX() + 19, myGuy.getWorldY() - 2) && checkLedgeRight(myGuy.getWorldX(), myGuy.getWorldY() - 2, myGuy.getWorldX() + 19, myGuy.getWorldY() - 2))) {
						direction = UP;
						myGuy.move(direction, picIndex, miniPicIndex, mini);
						if (checkBuilding(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							mini = true;
							miniPicIndex += 2;
							npc1 = randint(3, 17);
							npc2 = randint(3, 17);
							myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							Textbox.setTextWriting(true);
						} else if (checkBuilding2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							mini = true;
							miniPicIndex += 1;
							npc1 = randint(3, 17);
							npc2 = randint(3, 17);
							myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							Textbox.setTextWriting(true);
						} else if (checkBuilding3(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							mini = true;
							miniPicIndex += 3;
							npc1 = randint(3, 17);
							npc2 = randint(3, 17);
							myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						} else if (checkBuilding4(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							mini = true;
							miniPicIndex += 4;
							npc1 = randint(3, 17);
							npc2 = randint(3, 17);
							myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						} else if (checkBuilding5(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							mini = true;
							miniPicIndex += 5;
							npc1 = randint(3, 17);
							npc2 = randint(3, 17);
							myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						} else if (checkNextRoute(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							if (MasseyMon.frame.getMyPokes().size() != 0) {
								picIndex += 1;
								myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
								myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
								if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {

									if (myGuy.getWorldY() == 397 || myGuy.getWorldY() == 900 || myGuy.getWorldY() == 860) {
										myGuy.setScreenY(398);
									} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
										myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));

									} else {
										myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));
									}
								} else {
									myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY());
								}
								if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
									if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
										myGuy.setScreenX(478);
									} else {
										myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX()));
									}
								} else {
									myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX());
								}
							}
						} else if (checkPrevRoute(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							picIndex -= 1;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX7());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY7());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
								if (myGuy.getWorldY() == 290 || myGuy.getWorldY() == 300 || myGuy.getWorldY() == 420) {
									myGuy.setScreenY(300);
								} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(0);
								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY7()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY7());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX7()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX7());
							}
						} else if (checkTrainer1(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(0).get(routeIndex) == false) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
								oneTimeTalk = true;
							}
							else{
								talkDone = true;
							}
							if (talkDone) {
								MasseyMon.frame.getBattles().get(0).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(0).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkTrainer2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(1).get(routeIndex) == false) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
								oneTimeTalk = true;
							}
							else{
								talkDone = true;
							}
							if (talkDone) {
								MasseyMon.frame.getBattles().get(1).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(1).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkTrainer3(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(2).get(routeIndex) == false) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
								oneTimeTalk = true;
							}
							else{
								talkDone = true;
							}
							if (talkDone) {
								MasseyMon.frame.getBattles().get(2).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(2).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkTrainer4(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(3).get(routeIndex) == false) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
							}
							else{
								talkDone = true;
							}
							if (talkDone) {
								MasseyMon.frame.getBattles().get(3).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(3).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkTrainer5(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(4).get(routeIndex) == false) {
							System.out.println("gwge");
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								System.out.println("hi");
								MasseyMon.frame.getBattles().get(4).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(4).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkBrock(myGuy.getWorldX()+10, myGuy.getWorldY() - 5, myGuy.getWorldX() + 10, myGuy.getWorldY() - 5)) {
							brockTalking = true;
							talking = true;
							Textbox.setTextWriting(true);
						} else if (checkMisty(myGuy.getWorldX()+10, myGuy.getWorldY() - 10, myGuy.getWorldX() + 10, myGuy.getWorldY() - 10)) {
							mistyTalking = true;
							talking = true;
							Textbox.setTextWriting(true);
						} else if (checkGiovanni(myGuy.getWorldX()+10, myGuy.getWorldY() - 5, myGuy.getWorldX() + 10, myGuy.getWorldY() - 5)) {
							giovanniTalking = true;
							talking = true;
							Textbox.setTextWriting(true);
						}
						else if (checkChampion(myGuy.getWorldX()+10, myGuy.getWorldY() - 5, myGuy.getWorldX() + 10, myGuy.getWorldY() - 5)) {
							championTalking = true;
							talking = true;
							Textbox.setTextWriting(true);
						}

						else if (checkWildEncounter(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							inGrass = true;
						}
					} else if ((keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]) && clear(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27) && (checkLedge(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27)
							&& checkLedgeLeft(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27) && checkLedgeRight(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27))) {
						direction = DOWN;
						myGuy.move(direction, picIndex, miniPicIndex, mini);

						if (checkExit1(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							mini = false;
							miniPicIndex -= 2;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX3());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY3());

							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
								if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(398);
								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY3()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY3());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX3()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX3());
							}
						} else if (checkExit2(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							mini = false;
							miniPicIndex -= 1;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX2());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY2());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
								if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(398);
								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY2()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY2());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX2()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX2());
							}
						} else if (checkExit3(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							mini = false;
							miniPicIndex -= 3;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX4());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY4());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
								if (myGuy.getWorldY() == 975) {
									myGuy.setScreenY(500);
								} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(398);
								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY4()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY4());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() == 285) {
									myGuy.setScreenX(200);
								} else if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX4()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX4());
							}
						} else if (checkExit4(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							mini = false;
							miniPicIndex -= 4;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX5());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY5());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
								if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(398);
								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY5()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY5());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX5()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX5());
							}
						} else if (checkExit5(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							mini = false;
							miniPicIndex -= 5;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX6());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY6());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
								if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(398);
								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY6()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY6());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX6()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX6());
							}
						} else if (checkPrevRoute(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							picIndex -= 1;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX7());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY7());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
								if (myGuy.getWorldY() == 290 || myGuy.getWorldY() == 300 || myGuy.getWorldY() == 420) {
									myGuy.setScreenY(300);
								} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(0);
								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY7()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY7());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX7()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX7());
							}
						} else if (checkNextRoute(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							picIndex += 1;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {

								if (myGuy.getWorldY() == 397 || myGuy.getWorldY() == 900 || myGuy.getWorldY() == 860) {
									myGuy.setScreenY(398);
								} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));

								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX());
							}
						} else if (checkTrainer1(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkTrainer2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkTrainer4(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkTrainer6(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(5).get(routeIndex) == false) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								MasseyMon.frame.getBattles().get(5).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(5).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkWildEncounter(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							inGrass = true;
						}

					} else if ((keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) && clear(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 26) && (checkLedge(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 20)
							&& checkLedgeLeft(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 20) && checkLedgeRight(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 20))) {
						direction = RIGHT;
						myGuy.move(direction, picIndex, miniPicIndex, mini);

						if (checkNextRoute(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 26)) {
							picIndex += 1;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {

								if (myGuy.getWorldY() == 860 || myGuy.getWorldY() == 740) {
									myGuy.setScreenY(398);
								} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));

								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX());
							}
						}
						else if (checkPrevRoute(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 26)) {
							picIndex -= 1;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX7());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY7());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
								if (myGuy.getWorldY() == 650 || myGuy.getWorldY() == 300 || myGuy.getWorldY() == 420) {
									myGuy.setScreenY(300);
								} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(0);
								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY7()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY7());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() == 1460) {
									myGuy.setScreenX(900);
								} else if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX7()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX7());
							}
						}
						else if (checkChampRouteBack(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 26)) {
							picIndex -= 11;
							myGuy.setWorldX(20);
							myGuy.setWorldY(555);
							myGuy.setScreenX(20);
							myGuy.setScreenY(398);
						}
						else if (checkTrainer1(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkTrainer2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkTrainer3(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkWildEncounter(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							inGrass = true;
						}
					} else if ((keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) && clear(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 26) && (checkLedge(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 20)
							&& checkLedgeLeft(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 20) && checkLedgeRight(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 20))) {
						direction = LEFT;
						myGuy.move(direction, picIndex, miniPicIndex, mini);

						if (checkPrevRoute(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 26)) {
							picIndex -= 1;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX7());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY7());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {
								if (myGuy.getWorldY() == 650 || myGuy.getWorldY() == 300 || myGuy.getWorldY() == 420) {
									myGuy.setScreenY(300);
								} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(0);
								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY7()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY7());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() == 1460) {
									myGuy.setScreenX(900);
								} else if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX7()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX7());
							}
						} else if (checkNextRoute(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 26)) {
							picIndex += 1;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
							if (MasseyMon.getMap(picIndex).getMapHeight() > 795) {

								if (myGuy.getWorldY() == 397 || myGuy.getWorldY() == 900 || myGuy.getWorldY() == 860) {
									myGuy.setScreenY(398);
								} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));

								} else {
									myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY()));
								}
							} else {
								myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY());
							}
							if (MasseyMon.getMap(picIndex).getMapWidth() > 956) {
								if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
									myGuy.setScreenX(478);
								} else {
									myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX()));
								}
							} else {
								myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX());
							}
						}
						else if (checkChampRoute(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 26)) {
							picIndex += 11;
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
							myGuy.setScreenX(800);
							myGuy.setScreenY(275);
						}
						else if (checkTrainer4(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkWildEncounter(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							inGrass = true;
						}
					} else {
						myGuy.resetExtra();
						myGuy.idle(direction);

						if (direction == UP && (hasStarter || mini)) {
							if (checkTalk(myGuy.getWorldX() + 7, myGuy.getWorldY() - 20, myGuy.getWorldX() + 14, myGuy.getWorldY() - 20)) {
								talking = true;
							}
						}
						if (direction == DOWN) {
							if (checkTalk(myGuy.getWorldX(), myGuy.getWorldY() + 37, myGuy.getWorldX() + 20, myGuy.getWorldY() + 37)) {
								talking = true;
							}
						}
						if (direction == RIGHT) {
							if (checkTalk(myGuy.getWorldX() + 35, myGuy.getWorldY() + 8, myGuy.getWorldX() + 35, myGuy.getWorldY() + 18)) {
								talking = true;
							}
						}
						if (direction == LEFT) {
							if (checkTalk(myGuy.getWorldX() - 10, myGuy.getWorldY(), myGuy.getWorldX() - 10, myGuy.getWorldY() + 26)) {
								talking = true;
							}
						}
					}
				}
			}
		}
	}

	private boolean clear ( int x, int y, int x2, int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY = MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF0000FF;
		if (x < (0 + posX) || x >=  (maskPic.getWidth(null) + posX) || y < (0 + posY) || y >= (maskPic.getHeight(null) + posY)) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c != WALL && d!=WALL;
	}

	private boolean checkBuilding ( int x, int y,int x2,int y2){
		if (!mini) {
			BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
			int posX = MasseyMon.getMap(picIndex).getMapX();
			int posY = MasseyMon.getMap(picIndex).getMapY();

			int WALL = 0xFF00FF00;
			int c = maskPic.getRGB(x - posX, y - posY);
			int d = maskPic.getRGB(x2 - posX, y2 - posY);
			return c == WALL && d == WALL;
		}
		return false;
	}

	private boolean checkExit1 ( int x, int y,int x2, int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}
		int WALL = 0xFFFF0000;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkExit2 ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF00FFFF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkBuilding2 ( int x, int y, int x2, int y2) {
		if (!mini) {
			BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
			int posX = MasseyMon.getMap(picIndex).getMapX();
			int posY = MasseyMon.getMap(picIndex).getMapY();

			int WALL = 0xFFFF00FF;
			int c = maskPic.getRGB(x - posX, y - posY);
			int d = maskPic.getRGB(x2 - posX, y2 - posY);
			return c == WALL && d == WALL;
		}
		return false;
	}

	private boolean checkNextRoute ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFFFF00;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkPrevRoute ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();
		int WALL = 0xFF800000;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkLedge(int x, int y, int x2, int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF000080;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		if (c!= WALL && d!= WALL){
			return true;
		}
		else{
			if (keys[KeyEvent.VK_UP]|| keys[KeyEvent.VK_W] || keys[KeyEvent.VK_LEFT]|| keys[KeyEvent.VK_A] || keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				return false;
			}
		}
		return true;
	}

	private boolean checkLedgeLeft(int x, int y, int x2, int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF8080FF;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		if (c!= WALL && d!= WALL){
			return true;
		}
		else{
			if (keys[KeyEvent.VK_UP]|| keys[KeyEvent.VK_W] || keys[KeyEvent.VK_DOWN]|| keys[KeyEvent.VK_S] || keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				return false;
			}
		}
		return true;
	}

	private boolean checkLedgeRight(int x, int y, int x2, int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFF8080;
		if (x < 0 || x >= maskPic.getWidth(null) + posX || y < 0 || y >= maskPic.getHeight(null) + posY) {
			return false;
		}
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		if (c!= WALL && d!= WALL){
			return true;
		}
		else{
			if (keys[KeyEvent.VK_UP]|| keys[KeyEvent.VK_W] || keys[KeyEvent.VK_LEFT]|| keys[KeyEvent.VK_A] || keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]){
				return false;
			}
		}
		return true;
	}

	private boolean checkBuilding3 ( int x, int y, int x2,int y2) {
		if (!mini) {
			BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
			int posX = MasseyMon.getMap(picIndex).getMapX();
			int posY = MasseyMon.getMap(picIndex).getMapY();
			int WALL = 0xFF008000;
			int c = maskPic.getRGB(x - posX, y - posY);
			int d = maskPic.getRGB(x2 - posX, y2 - posY);
			return c == WALL && d == WALL;
		}
		return false;
	}

	private boolean checkExit3 ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF808000;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkBuilding4 ( int x, int y, int x2,int y2){
		if (!mini) {
			BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
			int posX = MasseyMon.getMap(picIndex).getMapX();
			int posY = MasseyMon.getMap(picIndex).getMapY();
			int WALL = 0xFF808080;
			int c = maskPic.getRGB(x - posX, y - posY);
			int d = maskPic.getRGB(x2 - posX, y2 - posY);
			return c == WALL && d == WALL;
		}
		return false;
	}

	private boolean checkExit4 ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF008080;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkBuilding5 ( int x, int y, int x2,int y2) {
		if (!mini) {
			BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
			int posX = MasseyMon.getMap(picIndex).getMapX();
			int posY = MasseyMon.getMap(picIndex).getMapY();
			int WALL = 0xFF80FF00;
			int c = maskPic.getRGB(x - posX, y - posY);
			int d = maskPic.getRGB(x2 - posX, y2 - posY);
			return c == WALL && d == WALL;
		}
		return false;
	}

	private boolean checkExit5 ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFFFF8000;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkChampRoute ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF01FFFF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkChampRouteBack ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF01FF01;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	private boolean checkTalk ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF80FF80;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL && spacePressed;
	}

	private boolean checkTrainer1 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFF80FF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkTrainer2 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFFFF80;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkTrainer3 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF0080FF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkTrainer4 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFF0080;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkTrainer5 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF00FF80;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkTrainer6 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF8000FF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkBrock ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF010100;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkMisty ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}
		int WALL = 0xFF010101;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkGiovanni ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}
		int WALL = 0xFF000101;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkChampion ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}
		int WALL = 0xFF010001;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	private boolean checkWildEncounter ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF010000;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}


	public boolean getMenu () {
		return menu;
	}

	public static int randint(int low, int high){
		return (int)(Math.random()*(high-low+1)+low);
	}

	public boolean pokeCenter (){
		if (picIndex == 2 && miniPicIndex == 0 || picIndex == 6 && miniPicIndex == 0 || picIndex == 8 && miniPicIndex == 0 || picIndex == 12 && miniPicIndex == 0){
			return true;
		}
		return false;
	}

	public boolean pokeShop(){
		if (picIndex == 2 && miniPicIndex == 1 || picIndex == 6 && miniPicIndex == 1 || picIndex == 12 && miniPicIndex == 1){
			return true;
		}
		return false;
	}

	public boolean pokeHouse(){
		if (picIndex == 2){
			if (miniPicIndex == 2 || miniPicIndex == 3){
				return true;
			}
		}
		if (picIndex == 6){
			if (miniPicIndex == 2 || miniPicIndex == 3){
				return true;
			}
		}
		return false;
	}

	public void dieInBattle(){
		picIndex = 0;
		miniPicIndex = -1;
		myGuy.setWorldX(286);
		myGuy.setWorldY(280);
		myGuy.setScreenX(286);
		myGuy.setScreenY(280);
		MasseyMon.frame.healPokes();
	}

	public static void setStarter(boolean temp){
		starter = temp;
	}

}
