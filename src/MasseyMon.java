// Kevin Cui and Dimitrios Christopoulos
// MasseyMon.java
//Main class for the game. Loads files for player positions, Pokemon, and NPC's. Also creates all trainers and their Pokemon and moves.
//Creates objects for Pokemon, Player, Items, etc. Allows the player to move and checks the coordinates against the mask to
//perform functions such as battling, encountering, talking, etc. Displays text when the player talks to an NPC. Opens the menu
//screen. And also draws battle screen when a battle is entered.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.*;

public class MasseyMon extends JFrame { // MasseyMon class to create trainers and their Pokemon, load files, and add objects
	GamePanel game; // declaring game pannel
	javax.swing.Timer myTimer; // declaring timer
	public static boolean inBattle; // checks if the player is in a battle
	public static MasseyMon frame; // MasseyMon object
	public static ArrayList<pokeMap> maps = new ArrayList<pokeMap>(); // array list for map objects
	public static ArrayList<ArrayList<pokeMapMini>> miniMaps = new ArrayList<ArrayList<pokeMapMini>>(); // array list for mini map objects
	public static ArrayList<Image> trainers = new ArrayList<Image>(); // array list of trainer images
	private ArrayList<Pokemon> myPokes = new ArrayList<Pokemon>(); // array list of player's Pokemon
	private ArrayList<Pokemon> enemyPokes = new ArrayList<Pokemon>(); // array list of enemy Pokemon
	private ArrayList<ArrayList<Pokemon>> allEncounters = new ArrayList<ArrayList<Pokemon>>(); // array list of encounterable Pokemon
	private ArrayList<ArrayList<ArrayList<Pokemon>>> trainerPokemon = new ArrayList<ArrayList<ArrayList<Pokemon>>>(); // array list of trainer's Pokemon
	private ArrayList<ArrayList<ArrayList<Pokemon>>> gymPokemon = new ArrayList<ArrayList<ArrayList<Pokemon>>>(); // array list of Pokemon in a gym
	private ArrayList<ArrayList<Boolean>> battledTrainers = new ArrayList<ArrayList<Boolean>>(); // array list of trainers you've battled
	private ArrayList<ArrayList<Boolean>> battledGymTrainers = new ArrayList<ArrayList<Boolean>>();
	private Pokemon bulbasaur,charmander,squirtle; // starter pokemon
	public static Image [] starters = new Image [3]; // images for starter pokemon
	private PokemonBattle pokeBattle; // PokemonBattle object
	public MasseyMon() throws IOException{ // constructor method
		super("MasseyMon"); // title of the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myTimer = new javax.swing.Timer(10,new TickListener()); // starting timer
		load(); // calling load
		game = new GamePanel();
		add(game);
		pack();
		setVisible(true);
		setResizable(false);
		start();
		inBattle = false; // setting inBattle to false since player spawns in the overworld
	}
	public static void main(String[] args) throws IOException{ // main method
		frame = new MasseyMon(); // creates frame
	}
	public ArrayList<ArrayList<ArrayList<Pokemon>>> getGymPokemon(){return gymPokemon;}
	public ArrayList<ArrayList<Boolean>> getBattles(){
		return battledTrainers;
	} // getter for battled trainers
	public ArrayList<ArrayList<ArrayList<Pokemon>>> getTrainerPokes(){
		return trainerPokemon;
	} // getter for trainer pokemon
	public GamePanel getGame() {
		return game;
	} // getter for game
	public ArrayList<ArrayList<Pokemon>> getAllEncounters(){
		return allEncounters;
	} // getter for encounters
	public ArrayList<Pokemon> getEnemyPokes(){
		return enemyPokes;
	} // getter for enemy pokemon
	public ArrayList<ArrayList<Pokemon>> makeEncounters() throws FileNotFoundException {
		ArrayList<ArrayList<Pokemon>> allEncounters = new ArrayList<ArrayList<Pokemon>>();
		ArrayList <ArrayList<Pokemon>> brockGym = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> mistyGym = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> giovanniGym = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<Pokemon> firstEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> secondEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> thirdEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> fourthEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> fifthEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> sixthEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> seventhEncounters = new ArrayList<Pokemon>();
		ArrayList<Pokemon> eighthEncounters = new ArrayList<Pokemon>();
		ArrayList<ArrayList<Pokemon>> firstTrainers = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> secondTrainers = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> thirdTrainers = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> fourthTrainers = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> fifthTrainers = new ArrayList<ArrayList<Pokemon>>();
		ArrayList<ArrayList<Pokemon>> sixthTrainers = new ArrayList<ArrayList<Pokemon>>();
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/Moves.txt")));
		Scanner inFile2 = new Scanner(new BufferedReader(new FileReader("Data/Pokemon2.txt")));
		Pokemon caterpie = null,weepinBell = null,weepinBell2 = null,poliwhirl = null,poliwhirl2 = null,primeape = null,primeape2 = null,venomoth2 = null,venomoth = null,kabuto5 = null,magneton5 = null,kangaskhan5 = null,golbat4 = null,golbat3 = null,golbat = null,magneton3 = null,magneton4 = null, magneton = null, kabuto = null,magneton2 = null,kabuto2 = null,kabuto3 = null,kabuto4 = null, golbat2 = null,kangaskhan2 = null,kangaskhan3 = null,kangaskhan4 = null, kangaskhan = null,growlithe = null, weedle = null, ekans2 = null,vulpix = null, oddish = null, meowth = null, caterpie2= null, weedle2 = null, paras = null, ekans = null, caterpie3 = null, paras2 = null,weedle3 = null, rattata = null,diglett2 = null,diglett = null,machop2 = null, machop = null, onix = null, onix2 = null,ryhorn2 = null,ryhorn = null;
		Pokemon caterpieEnc = null,gymRyhorn2 = null, gymGolem = null, gymGraveler = null, gymGolem2 = null, gymCubone = null, gymOnix = null,giovRhyhorn = null, giovRhydon = null, goivRhyhorn = null, giovGolem = null, giovGraveler = null,MistyStaryu = null, MistyStarmie = null, MistyPsyduck = null, gymTentacool = null,gymStaryu = null, gymSeel = null, gymKrabby = null,BrockGeodude = null, BrockOnix = null, gymRyhorn = null, gymOmanyte = null,weepinBellEnc = null,poliwhirlEnc = null,primeapeEnc = null,venomothEnc = null, golbatEnc = null, magnetonEnc = null, kabutoEnc = null, kangaskhanEnc = null,caterpieEnc2 = null,growlitheEnc = null,weedleEnc = null,weedleEnc2 = null,pidgeyEnc = null,rattataEnc = null,parasEnc = null,ekansEnc = null,vulpixEnc = null, oddishEnc = null,meowthEnc = null,abraEnc = null, ekansEnc2 = null,diglettEnc = null, machopEnc = null, onixEnc = null, ryhornEnc = null;
		Attack tackle = null,earthquake = null,acid = null,leechLife = null,rollingKick = null, vineWhip = null, waterPulse = null, waterGun = null, bodySlam = null,lick = null,cut = null, thunderPunch = null,psybeam = null, wingAttack = null, peck = null,pin = null, smog = null, bite = null, headbutt = null, karate = null, firePunch = null, hornAttack = null, rockThrow = null, boneClub = null,absorb = null, ember = null, bubble = null,scratch = null,stomp = null,confusion = null;
		for (int i = 0; i < 96; i++){
			String line2 = inFile.nextLine();
			if (i == 0){
				absorb = new Attack(line2);
			}
			else if(i == 1){
				acid = new Attack(line2);
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
			else if(i == 26){
				earthquake = new Attack(line2);
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
			else if(i ==47){
				leechLife = new Attack(line2);
			}
			else if(i == 48){
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
			else if(i == 66){
				rollingKick = new Attack(line2);
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
			else if(i == 91){
				vineWhip = new Attack(line2);
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
				caterpie3 = new Pokemon(line);
				caterpie3.learnMove(tackle);
				caterpie3.learnMove(pin);
				caterpie3.setLevel(4);
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
				golbat2 = new Pokemon(line);
				golbat2.learnMove(wingAttack);
				golbat2.learnMove(psybeam);
				golbat2.learnMove(scratch);
				golbat3 = new Pokemon(line);
				golbat3.learnMove(wingAttack);
				golbat3.learnMove(psybeam);
				golbat3.learnMove(scratch);
				golbat4 = new Pokemon(line);
				golbat4.learnMove(wingAttack);
				golbat4.learnMove(psybeam);
				golbat4.learnMove(scratch);
				golbatEnc = new Pokemon(line);
				golbatEnc.learnMove(wingAttack);
				golbatEnc.learnMove(psybeam);
				golbatEnc.learnMove(scratch);
				sixthEncounters.add(golbatEnc);
				seventhEncounters.add(golbatEnc);
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
			else if(i == 46){
				venomoth = new Pokemon(line);
				venomoth.learnMove(tackle);
				venomoth.learnMove(confusion);
				venomoth.learnMove(leechLife);
				venomoth2 = new Pokemon(line);
				venomoth2.learnMove(tackle);
				venomoth2.learnMove(confusion);
				venomoth2.learnMove(leechLife);
				venomothEnc = new Pokemon(line);
				venomothEnc.learnMove(tackle);
				venomothEnc.learnMove(confusion);
				venomothEnc.learnMove(leechLife);
				eighthEncounters.add(venomothEnc);
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
			else if(i == 51){
				MistyPsyduck = new Pokemon(line);
				MistyPsyduck.learnMove(scratch);
				MistyPsyduck.learnMove(waterGun);
				MistyPsyduck.learnMove(psybeam);
			}
			else if(i == 54){
				primeape = new Pokemon(line);
				primeape.learnMove(scratch);
				primeape.learnMove(karate);
				primeape.learnMove(rollingKick);
				primeape2 = new Pokemon(line);
				primeape2.learnMove(scratch);
				primeape2.learnMove(karate);
				primeape2.learnMove(rollingKick);
				primeapeEnc = new Pokemon(line);
				primeapeEnc.learnMove(scratch);
				primeapeEnc.learnMove(karate);
				primeapeEnc.learnMove(rollingKick);
				eighthEncounters.add(primeapeEnc);

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
			else if(i == 58){
				poliwhirl = new Pokemon(line);
				poliwhirl.learnMove(tackle);
				poliwhirl.learnMove(waterGun);
				poliwhirl.learnMove(bubble);
				poliwhirl2 = new Pokemon(line);
				poliwhirl2.learnMove(tackle);
				poliwhirl2.learnMove(waterGun);
				poliwhirl2.learnMove(bubble);
				poliwhirlEnc = new Pokemon(line);
				poliwhirlEnc.learnMove(tackle);
				poliwhirlEnc.learnMove(waterGun);
				poliwhirlEnc.learnMove(bubble);
				eighthEncounters.add(poliwhirlEnc);
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
			else if(i == 67){
				weepinBell = new Pokemon(line);
				weepinBell.learnMove(tackle);
				weepinBell.learnMove(acid);
				weepinBell.learnMove(vineWhip);
				weepinBell2 = new Pokemon(line);
				weepinBell2.learnMove(tackle);
				weepinBell2.learnMove(acid);
				weepinBell2.learnMove(vineWhip);
				weepinBellEnc = new Pokemon(line);
				weepinBellEnc.learnMove(tackle);
				weepinBellEnc.learnMove(acid);
				weepinBellEnc.learnMove(vineWhip);
				eighthEncounters.add(weepinBellEnc);
			}
			else if(i == 69){
				gymTentacool = new Pokemon(line);
				gymTentacool.learnMove(waterPulse);
				gymTentacool.learnMove(acid);
				gymTentacool.learnMove(tackle);
			}
			else if(i == 71){
				BrockGeodude = new Pokemon(line);
				BrockGeodude.learnMove(tackle);
				BrockGeodude.learnMove(rockThrow);
			}
			else if(i == 72){
				gymGraveler = new Pokemon(line);
				gymGraveler.learnMove(earthquake);
				gymGraveler.learnMove(scratch);
				gymGraveler.learnMove(rockThrow);
				giovGraveler = new Pokemon(line);
				giovGraveler.learnMove(headbutt);
				giovGraveler.learnMove(earthquake);
				giovGraveler.learnMove(rockThrow);
			}
			else if(i == 73){
				gymGolem = new Pokemon(line);
				gymGolem.learnMove(earthquake);
				gymGolem.learnMove(headbutt);
				gymGolem.learnMove(rockThrow);
				gymGolem2 = new Pokemon(line);
				gymGolem2.learnMove(earthquake);
				gymGolem2.learnMove(headbutt);
				gymGolem2.learnMove(rockThrow);
				giovGolem = new Pokemon(line);
				giovGolem.learnMove(headbutt);
				giovGolem.learnMove(earthquake);
				giovGolem.learnMove(rockThrow);
			}
			else if(i == 79){
				magneton = new Pokemon(line);
				magneton.learnMove(thunderPunch);
				magneton.learnMove(psybeam);
				magneton.learnMove(tackle);
				magneton2 = new Pokemon(line);
				magneton2.learnMove(thunderPunch);
				magneton2.learnMove(psybeam);
				magneton2.learnMove(tackle);
				magneton3 = new Pokemon(line);
				magneton3.learnMove(thunderPunch);
				magneton3.learnMove(psybeam);
				magneton3.learnMove(tackle);
				magneton4 = new Pokemon(line);
				magneton4.learnMove(thunderPunch);
				magneton4.learnMove(psybeam);
				magneton4.learnMove(tackle);
				magneton5 = new Pokemon(line);
				magneton5.learnMove(thunderPunch);
				magneton5.learnMove(psybeam);
				magneton5.learnMove(tackle);
				magnetonEnc = new Pokemon(line);
				magnetonEnc.learnMove(thunderPunch);
				magnetonEnc.learnMove(psybeam);
				magnetonEnc.learnMove(tackle);
				sixthEncounters.add(magnetonEnc);
				seventhEncounters.add(magnetonEnc);
			}
			else if(i == 83){
				gymSeel = new Pokemon(line);
				gymSeel.learnMove(waterPulse);
				gymSeel.learnMove(scratch);
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
				BrockOnix = new Pokemon(line);
				BrockOnix.learnMove(headbutt);
				BrockGeodude.learnMove(rockThrow);
				BrockGeodude.learnMove(boneClub);
				gymOnix = new Pokemon(line);
				gymOnix.learnMove(tackle);
				gymOnix.learnMove(earthquake);
				gymOnix.learnMove(rockThrow);
			}
			else if(i == 95){
				gymKrabby = new Pokemon(line);
				gymKrabby.learnMove(scratch);
				gymKrabby.learnMove(waterPulse);
				gymKrabby.learnMove(bite);
			}
			else if(i == 101){
				gymCubone = new Pokemon(line);
				gymCubone.learnMove(boneClub);
				gymCubone.learnMove(headbutt);
				gymCubone.learnMove(rockThrow);
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
				gymRyhorn = new Pokemon(line);
				gymRyhorn.learnMove(headbutt);
				gymRyhorn.learnMove(rockThrow);
				gymRyhorn2 = new Pokemon(line);
				gymRyhorn2.learnMove(headbutt);
				gymRyhorn2.learnMove(rockThrow);
				gymRyhorn2.learnMove(earthquake);
				giovRhyhorn = new Pokemon(line);
				giovRhyhorn.learnMove(headbutt);
				giovRhyhorn.learnMove(earthquake);
				giovRhyhorn.learnMove(rockThrow);
			}
			else if(i == 109){
				giovRhydon = new Pokemon(line);
				giovRhydon.learnMove(headbutt);
				giovRhydon.learnMove(earthquake);
				giovRhydon.learnMove(rockThrow);
			}
			else if(i == 112){
				kangaskhan = new Pokemon(line);
				kangaskhan.learnMove(karate);
				kangaskhan.learnMove(bodySlam);
				kangaskhan.learnMove(lick);
				kangaskhan2 = new Pokemon(line);
				kangaskhan2.learnMove(karate);
				kangaskhan2.learnMove(bodySlam);
				kangaskhan2.learnMove(lick);
				kangaskhan3 = new Pokemon(line);
				kangaskhan3.learnMove(karate);
				kangaskhan3.learnMove(bodySlam);
				kangaskhan3.learnMove(lick);
				kangaskhan4 = new Pokemon(line);
				kangaskhan4.learnMove(karate);
				kangaskhan4.learnMove(bodySlam);
				kangaskhan4.learnMove(lick);
				kangaskhan5 = new Pokemon(line);
				kangaskhan5.learnMove(karate);
				kangaskhan5.learnMove(bodySlam);
				kangaskhan5.learnMove(lick);
				kangaskhanEnc = new Pokemon(line);
				kangaskhanEnc.learnMove(karate);
				kangaskhanEnc.learnMove(bodySlam);
				kangaskhanEnc.learnMove(lick);
				sixthEncounters.add(kangaskhanEnc);
				seventhEncounters.add(kangaskhanEnc);
			}
			else if(i == 117){
				MistyStaryu = new Pokemon(line);
				MistyStaryu.learnMove(tackle);
				MistyStaryu.learnMove(waterPulse);
				gymStaryu = new Pokemon(line);
				gymStaryu.learnMove(tackle);
				gymStaryu.learnMove(waterGun);
			}
			else if(i == 118){
				MistyStaryu = new Pokemon(line);
				MistyStaryu.learnMove(tackle);
				MistyStaryu.learnMove(waterPulse);
				MistyStaryu.learnMove(psybeam);
			}
			else if(i == 135){
				gymOmanyte = new Pokemon(line);
				gymOmanyte.learnMove(tackle);
				gymOmanyte.learnMove(rockThrow);
				gymOmanyte.learnMove(earthquake);
			}
			else if(i == 137){
				kabuto = new Pokemon(line);
				kabuto.learnMove(rockThrow);
				kabuto.learnMove(boneClub);
				kabuto.learnMove(cut);
				kabuto2 = new Pokemon(line);
				kabuto2.learnMove(rockThrow);
				kabuto2.learnMove(boneClub);
				kabuto2.learnMove(cut);
				kabuto3 = new Pokemon(line);
				kabuto3.learnMove(rockThrow);
				kabuto3.learnMove(boneClub);
				kabuto3.learnMove(cut);
				kabuto4 = new Pokemon(line);
				kabuto4.learnMove(rockThrow);
				kabuto4.learnMove(boneClub);
				kabuto4.learnMove(cut);
				kabuto5 = new Pokemon(line);
				kabuto5.learnMove(rockThrow);
				kabuto5.learnMove(boneClub);
				kabuto5.learnMove(cut);
				kabutoEnc = new Pokemon(line);
				kabutoEnc.learnMove(rockThrow);
				kabutoEnc.learnMove(boneClub);
				kabutoEnc.learnMove(cut);
				sixthEncounters.add(kabutoEnc);
				seventhEncounters.add(kabutoEnc);
			}
		}
		allEncounters.add(firstEncounters);
		allEncounters.add(secondEncounters);
		allEncounters.add(thirdEncounters);
		allEncounters.add(fourthEncounters);
		allEncounters.add(fifthEncounters);
		allEncounters.add(sixthEncounters);
		allEncounters.add(seventhEncounters);
		allEncounters.add(eighthEncounters);

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
		newTrainer.add(caterpie3);
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

		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(magneton);
		newTrainer.add(golbat);
		firstTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(kabuto);
		newTrainer.add(kangaskhan);
		secondTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(magneton2);
		newTrainer.add(kangaskhan2);
		thirdTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(kabuto2);
		newTrainer.add(golbat2);
		fourthTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(magneton5);
		newTrainer.add(kangaskhan5);
		fifthTrainers.add(newTrainer);
		sixthTrainers.add(null);

		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(kangaskhan3);
		newTrainer.add(golbat3);
		firstTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(kabuto3);
		newTrainer.add(magneton3);
		secondTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(golbat4);
		newTrainer.add(magneton4);
		thirdTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(kabuto4);
		newTrainer.add(kangaskhan4);
		fourthTrainers.add(newTrainer);
		fifthTrainers.add(null);
		sixthTrainers.add(null);

		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(venomoth);
		newTrainer.add(primeape);
		firstTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(poliwhirl);
		newTrainer.add(weepinBell);
		secondTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(poliwhirl2);
		newTrainer.add(primeape);
		thirdTrainers.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(venomoth);
		newTrainer.add(weepinBell2);
		fourthTrainers.add(newTrainer);

		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(gymOmanyte);
		brockGym.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(gymRyhorn);
		brockGym.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(BrockGeodude);
		newTrainer.add(BrockOnix);
		brockGym.add(newTrainer);

		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(gymSeel);
		newTrainer.add(gymKrabby);
		mistyGym.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(gymTentacool);
		newTrainer.add(gymStaryu);
		mistyGym.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(MistyStarmie);
		newTrainer.add(MistyPsyduck);
		newTrainer.add(MistyStaryu);
		brockGym.add(newTrainer);

		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(gymCubone);
		newTrainer.add(gymOnix);
		giovanniGym.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(gymGraveler);
		newTrainer.add(gymGolem);
		giovanniGym.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(gymRyhorn);
		newTrainer.add(gymGolem);
		giovanniGym.add(newTrainer);
		newTrainer = new ArrayList<Pokemon>();
		newTrainer.add(giovGolem);
		newTrainer.add(giovGraveler);
		newTrainer.add(giovRhydon);
		newTrainer.add(giovRhyhorn);
		giovanniGym.add(newTrainer);

		gymPokemon.add(brockGym);
		gymPokemon.add(mistyGym);
		gymPokemon.add(giovanniGym);

		trainerPokemon.add(firstTrainers);
		trainerPokemon.add(secondTrainers);
		trainerPokemon.add(thirdTrainers);
		trainerPokemon.add(fourthTrainers);
		trainerPokemon.add(fifthTrainers);
		trainerPokemon.add(sixthTrainers);
		return allEncounters;
	}
	public JTextArea getTextArea2(){ // method to create a text area
		game.makeNewArea();
		return game.getTextArea();
	}
	public void startBattle(Graphics g, Player myGuy) throws IOException { // method to start a Pokemon battle
		pokeBattle = new PokemonBattle(myPokes, enemyPokes, myGuy); // making a Pokemon battle object
		for (ArrayList<Pokemon> item: allEncounters){
			for (Pokemon item2: item){
				if(item2 == enemyPokes.get(0)){
					pokeBattle.setFleeable(true);
				}
				else{
					pokeBattle.setFleeable(false);
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
	public void load() throws IOException { // load method for some Pokemon sprites, maps, mini maps, player positions, and NPCs
		// loading the three starter Pokemon images for display and select
		starters [0] = ImageIO.read(new File("Sprites/Pokemon/P1.png")).getScaledInstance(200,200,Image.SCALE_SMOOTH);
		starters [1] = ImageIO.read(new File("Sprites/Pokemon/P4.png")).getScaledInstance(200,200,Image.SCALE_SMOOTH);
		starters [2] = ImageIO.read(new File("Sprites/Pokemon/P7.png")).getScaledInstance(200,200,Image.SCALE_SMOOTH);
		allEncounters = makeEncounters();
		for (int j = 0; j < 8; j++){
			ArrayList<Boolean> newBooleans = new ArrayList<Boolean>();
			for (int i = 0; i < 14; i++){
				newBooleans.add(false);
			}
			battledTrainers.add(newBooleans);
		}
		for (int j = 0; j < 3; j++){
			ArrayList<Boolean> newBooleans = new ArrayList<Boolean>();
			for (int i = 0; i < 3; i++){
				newBooleans.add(false);
			}
			battledGymTrainers.add(newBooleans);
		}
		battledGymTrainers.get(2).add(false);
		myPokes.add(bulbasaur);
		Scanner inFile = new Scanner(new BufferedReader(new FileReader("Data/PlayerPositions.txt"))); // loading the data file for where the player can spawn on each map
		for (int i = 0; i < 15; i++) { // loop through each of the 15 routes and get positions for each of them
			String line = inFile.nextLine(); // getting the next line of the file
			// getting the paths for the image and mask
			String path = String.format("%s/%s/%s%d.png", "Images", "Backgrounds", "Background", i);
			String pathMask = String.format("%s/%s/%s%d%s.png", "Images", "Masks", "Background", i,"Mask");
			try {
				ArrayList<ArrayList<Pokemon>> mapTrainers = new ArrayList<>();
				Image pic = ImageIO.read(new File(path)); // getting the image at the path
				BufferedImage picMask = ImageIO.read(new File(pathMask)); // getting the mask at the path
				mapTrainers = getMap1Trainers();
				maps.add(new pokeMap(pic,picMask,line,mapTrainers)); // making the map object and entering in the parameters
			} catch (IOException e) {}
		}
		inFile = new Scanner(new BufferedReader(new FileReader("Data/miniPlayerPositions"))); // loading the file for possible positions the player can spawn on mini maps
		for (int k = 0; k <15;k++) { // looping through each map
			miniMaps.add(new ArrayList<pokeMapMini>()); // adding array for mini maps
			for (int i = 0; i < 5; i++) { // looping through the maximum 5 mini maps
				String line = inFile.nextLine(); // getting the next line
				// getting image paths
				String path = String.format("%s/%s/%s%d/%s%d.png", "Images", "MiniBackgrounds", "Town ", k, "MiniBackground", i);
				String pathMask = String.format("%s/%s/%s%d/%s%d%s.png", "Images", "MiniMasks","Town ",k, "Background", i, "Mask");
				try {
					Image pic = ImageIO.read(new File(path)); // getting the image
 					BufferedImage picMask = ImageIO.read(new File(pathMask)); // getting the mask
					miniMaps.get(k).add(new pokeMapMini(pic, picMask, line)); // making the mini map object and entering the parameters
				}
				catch (IOException e) {
				}
			}
		}

		for (int i =0; i<18;i++){ // looping through all 18 NPC images
			String path = String.format("%s/%s/%s%d.png", "Images", "NPCs", "Trainer", i); // getting the path
			Image pic = ImageIO.read(new File(path)); // getting the image
			try {
				trainers.add(pic); // adding the images to the array list
			}
			catch (Exception e) {
			}
		}
	}

	public void healPokes(){ // method to heal all Pokemon
		for (Pokemon item : myPokes ){ // going through each Pokemon
			item.setHP(item.getMaxHP()); // setting HP to maximum
		}
	}

	public static Image getTrainers(int n){ // getter for the trainer array list
		return trainers.get(n);
	}

	public static pokeMap getMap(int n){ // getter for the maps array list
		return maps.get(n);
	}

	public static pokeMapMini getMiniMap( int n ,int k ) { // getter for the mini maps array list
		return miniMaps.get(n).get(k);
	}
	public ArrayList<Pokemon> getMyPokes(){ // getter for the array list of the trainers pokemon
		return myPokes;
	}
	public ArrayList<ArrayList<Boolean>> getGymBattles(){
		return battledGymTrainers;
	}
	public Pokemon getBulbasaur(){ // getter for bulbasaur pokemon
		return bulbasaur;
	}
	public Pokemon getCharmander(){ // getter for charmander pokemon
		return charmander;
	}
	public Pokemon getSquirtle(){ // getter for squirtle pokemon
		return squirtle;
	}

	public void start(){ // method to start the timer
		myTimer.start();
	}
	class TickListener implements ActionListener{ // tick listener class
		public void actionPerformed(ActionEvent evt){
			if(game!= null){ // if the game is not null
				if (!inBattle) { // if the player is not in battle then allow the player to move
					game.move();
				}
				if (game.inGrass && !inBattle){
					game.checkGrass();
				}
				game.repaint(); // re-paint the game
			}
		}
	}
}

class GamePanel extends JPanel { // GamePanel class which runs the game and draws the background, player, NPCs, text, allows the player to move and draws battle and menu screen
	public static final int INTERVAL = 5, STARTING = 114;
	private static int offsetX,offsetY; // offsets for backgrounds
	private int picIndex,miniPicIndex,npc1,npc2,starterIndex,trainerTextIndex,progress,routeIndex; // ints for indexes and npc positions
	private boolean pokemon,bag,menu; // booleans for menu
	private boolean oakTalked,oneTimeTalk,talkDone; // booleans for text
	public static boolean inGrass; // boolean for encounter
	private boolean spacePressed,movable,talking,hasStarter,trainerText,brockTalking,mistyTalking,giovanniTalking,titleScreen,championTalking; // booleans for talking and moving
	private int direction,frameEvo,npcText1,npcText2,gymIndex; // ints for frame, direction and talk index
	private boolean ready = true;
	private static boolean mini,starter; // boolean for if has starter and if mini map
	private boolean tileText, npcTalk,npcTalk2,onePressed,twoPressed,threePressed,fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed,checking; // booleans for npc and one time text and 1-9 keys pressed
	private boolean[] keys; // keys array
	private Image [] starters; // starters array
	private Image selectBox,evoBack,prof,hallOfFame,endScreen; // evolution, starter pick, and end screen images
	private float frame; // frame for Pokemon
	//Declaring objects
	pokeMap myMap;
	pokeMapMini myMiniMap;
	Textbox myTextBox;
	PokemonMenu myPokeMenu;
	TitleScreen myTitle;
	Items myItem;
	Menu myMenu;
	Player myGuy;
	private int alpha = 0; // color alpha value
	private boolean started,started2,started3,doneEvo; // booleans for evolution
	public static final int IDLE = 0, UP = 1, RIGHT = 4, DOWN = 7, LEFT = 10; // ints for sprite positions
	private JTextArea myArea, myArea2; // text areas
	private Sound battleSound,evolutionMusic,overworldMusic,titleMusic; // sound files
	public GamePanel() throws IOException{ // consturctor method
		setLayout(null);
		//creating the setting text area
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
		// assigning starting values to all variables
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
		checking = false;
		fivePressed = false;
		sixPressed = false;
		sevenPressed = false;
		eightPressed = false;
		ninePressed = false;
		npcTalk = false;
		npcTalk2 = false;
		gymIndex = -1;
		starters = MasseyMon.starters;
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		myGuy = new Player(0);
		myTitle = new TitleScreen();
		myPokeMenu = new PokemonMenu();
		myMenu = new Menu();
		myItem = new Items();
		myTextBox = new Textbox();
		myMap = (MasseyMon.getMap(picIndex));
		started = false;
		tileText = true;
		frame = (float)(frame);
		// loading image, screen dimensions, mouse and key listener, and sound files
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
		overworldMusic = new Sound("Music/OverworldMusic.wav",50);
		titleMusic = new Sound("Music/TitleScreenMusic.wav",50);
	}
	public void addNotify() {
		super.addNotify();
		requestFocus();
		ready = true;
	}
	public void checkGrass(){ // method to check for wild encounters
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
			else if(picIndex == 9 || picIndex == 10){
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
	public void makeNewArea(){ // method to make a new text area
		myArea = new JTextArea();
		myArea.setBackground(new Color(0,0,0,0)); // setting the color
		myArea.setBounds(40,635,390,125); // setting the bounds
		myArea.setVisible(true);
		myArea.setEditable(false);
		myArea.setHighlighter(null);
		myArea.setWrapStyleWord(true);
		myArea.setLineWrap(true);
		myArea.setFont(new Font("Font/gameFont.ttf",Font.BOLD,30)); // setting the font
		myArea.setText("");
		add(myArea);
	}
	public void draw(Graphics g) {  // draw method for when the player is not in battle
		if (MasseyMon.frame.inBattle == false){ // if the player is not in battle
			myArea.setVisible(false); // set the text area to not visable
			if (MasseyMon.frame.getPokeBattle() != null){ // if pokeBattle is not null set the text visability to false
				MasseyMon.frame.getPokeBattle().getMyArea().setVisible(false);
			}
			started = false;
			started2 = false;
			alpha = 0; // set color alpha to false
		}
			if (!talking && !starter) { // if there is no one talking and the starter screen is not open
				movable = true; // allow the player to move
				g.setColor(new Color(0, 0, 0)); // set colour
				g.fillRect(0, 0, 956, 795); // draw a black rect over screen
				offsetX = 0; // reset offset X
				offsetY = 0; // reset offset Y
				if (MasseyMon.getMap(picIndex).getMapWidth() > 956) { // if the width of the map is greater than the screen calculate offset
					offsetX = myGuy.getScreenX() - myGuy.getWorldX();
				}
				if (MasseyMon.getMap(picIndex).getMapHeight() > 795) { // if the height of hte map is greater than the screen then calculate offset Y
					offsetY = myGuy.getScreenY() - myGuy.getWorldY();
				}
				if (mini) { // if it is a mini map do not use offsets since all mini maps are smaller than screen size
					g.drawImage(MasseyMon.getMiniMap(picIndex, miniPicIndex).getMap(), MasseyMon.getMiniMap(picIndex, miniPicIndex).getMapX(), MasseyMon.getMiniMap(picIndex, miniPicIndex).getMapY(), this);
				} else { // if it is not a mini map
					// draw the image at either the offset position or map draw location based on if the map is larger than the screen in X and Y or not
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
				myGuy.draw(g); // drawing the player
			}
			if (picIndex == 0 && miniPicIndex == 1) { // if the player is in Oak's lab
				if (!oakTalked) { // only allowing oak to talk once
					if (Textbox.getTextWriting()) { // if text is allowed to be writing
						if (talking) { // if oak is talking
							Textbox.display(g, 1, spacePressed,onePressed,twoPressed,threePressed,
									fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed); // calling the textbox display method with index
							movable = false; // don't allow player to move
							spacePressed = false; // set space pressed false
						}
					}
					else { // if the professor is not talking
						if (!movable) { // if the player is not allowed to move
							talking = false; // setting talking to false
							starter = false; // starter display screen false
							g.setColor(new Color(0, 0, 0)); // setting color
							g.fillRect(0, 0, 956, 795); // filling screen black
							g.drawImage(MasseyMon.getMiniMap(0, 1).getMap(), MasseyMon.getMiniMap(0, 1).getMapX(), MasseyMon.getMiniMap(0, 1).getMapY(), this); // drawing the image over again
							if (!spacePressed) { // if the user does not press space
								starter = true; // starter display screen is active
								g.setColor(new Color(255, 255, 255)); // setting new color
								g.fillRect(350, 250, 300, 300); // drawing starter pick background
								g.drawImage(selectBox, 350, 250, this); // drawing select box image
								g.drawImage(starters[starterIndex], 420, 300, this); // drawing the starter pokemon
								movable = false; // not allowing the player to move
								hasStarter = true; // setting the player having a starter to true
							}
							if (spacePressed) { // if the player presses spcae
								Pokemon select = null; // making a select variable
								if (starterIndex == 0) { // if the index is 0 set select to bulbasaur
									select = MasseyMon.frame.getBulbasaur();
								} else if (starterIndex == 1) { // if the index is 1 set select to charmander
									select = MasseyMon.frame.getCharmander();
								} else if (starterIndex == 2) { // if the index is 2 set select to squirtle
									select = MasseyMon.frame.getSquirtle();
								}
								oakTalked = true; // setting oak talked to true when he's done talking
								MasseyMon.frame.getMyPokes().add(select); // adding the selected pokemon to the trainer array list
							}
						}
					}
				}
			}
			else if (pokeCenter()) { // if the user is in a Pokemon Center
				if (Textbox.getTextWriting()) { // if text is allowed to write
					if (talking) { // if the user talks to the nurse
						Textbox.display(g, 2, spacePressed,onePressed,twoPressed,threePressed,
								fourPressed,fivePressed,sixPressed,sevenPressed,eightPressed,ninePressed); // displaying the textbox at the right index
						movable = false; // not allowing player to move
						spacePressed = false;
						MasseyMon.frame.healPokes(); // healing all pokemon
					}
				} else {
					//re-drawing background
					g.setColor(new Color(0, 0, 0));
					g.fillRect(0, 0, 956, 795);
					g.drawImage(MasseyMon.getMiniMap(2, 0).getMap(), MasseyMon.getMiniMap(2, 0).getMapX(), MasseyMon.getMiniMap(2, 0).getMapY(), this);
					talking = false; // finished talking
					movable = true; // allowing player to move again
				}
			}

			else if (pokeShop()) { // if the user is in a pokeMart
				// same logic as PokeCenter above
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

			else if (trainerText){ // if a trainer is talking
				// similar logic as above
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

			else if (picIndex == 2 && miniPicIndex == 4){ // if the user enters the viridian gym for the first time
				// similar logic as above
					if (Textbox.getTextWriting()) {
						if (talking) {
							Textbox.display(g, 11, spacePressed, onePressed, twoPressed, threePressed,
									fourPressed, fivePressed, sixPressed, sevenPressed, eightPressed, ninePressed);
							movable = false;
							spacePressed = false;
						}
					} else {
						g.setColor(new Color(0, 0, 0));
						g.fillRect(0, 0, 956, 795);
						g.drawImage(MasseyMon.getMiniMap(2, 4).getMap(), MasseyMon.getMiniMap(2, 4).getMapX(), MasseyMon.getMiniMap(2, 4).getMapY(), this);
						myGuy.draw(g);
						tileText = false;
						talking = false;
						movable = true;
					}
			}

			else if (picIndex == 0 && miniPicIndex == 0){ // if the user enters the house
				// similar logic as above
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

			else if (brockTalking){ // if brock is talking
				// similar logic as above
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
			else if (mistyTalking){ // if misty is talking
				// similar logic as above
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
			else if (giovanniTalking){ // if giovanni is talking
				// similar logic as above
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

			else if (championTalking){ // if the champion is talking
				// similar logic as above
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

			if (movable) { // if the player can move
				if (picIndex == 0 && miniPicIndex == 1) { // if the player is in oak's lab
					g.drawImage(MasseyMon.getTrainers(0), 475, 300, this); // draw oak
				}
				if (pokeCenter()) { // if the player is in a Pokemon Center
					g.drawImage(MasseyMon.getTrainers(1), 462, 290, this); // draw nurse joy
					myGuy.draw(g);
				}
				if (pokeShop()) { // if the player is in a pokeShop
					g.drawImage(MasseyMon.getTrainers(2), 358, 350, this); // draw the shop clerk
					myGuy.draw(g);
				}
			}
				if (pokeHouse()) { // if the player is in a NPC house
					// drawing both NPCs
					g.drawImage(MasseyMon.getTrainers(npc1), 407, 385, this);
					g.drawImage(MasseyMon.getTrainers(npc2), 612, 360, this);
					if (npcTalk) { // if the NPC is talking
						// similar logic as above for NPC 1 and NPC 2
						if (Textbox.getTextWriting()) { //
							if (talking) {
								Textbox.display(g, npcText1, spacePressed, onePressed, twoPressed, threePressed,
										fourPressed, fivePressed, sixPressed, sevenPressed, eightPressed, ninePressed);
								movable = false;
								spacePressed = false;
							}
						} else {
							g.setColor(new Color(0, 0, 0));
							g.fillRect(0, 0, 956, 795);
							g.drawImage(MasseyMon.getMiniMap(2, 3).getMap(), MasseyMon.getMiniMap(2, 3).getMapX(), MasseyMon.getMiniMap(2, 3).getMapY(), this);
							g.drawImage(MasseyMon.getTrainers(npc1), 407, 385, this);
							g.drawImage(MasseyMon.getTrainers(npc2), 612, 360, this);
							talking = false;
							movable = true;
						}
					}
					else if (npcTalk2) {
						if (Textbox.getTextWriting()) {
							if (talking) {
								Textbox.display(g, npcText2, spacePressed, onePressed, twoPressed, threePressed,
										fourPressed, fivePressed, sixPressed, sevenPressed, eightPressed, ninePressed);
								movable = false;
								spacePressed = false;
							}
						} else {
							g.setColor(new Color(0, 0, 0));
							g.fillRect(0, 0, 956, 795);
							g.drawImage(MasseyMon.getMiniMap(2, 3).getMap(), MasseyMon.getMiniMap(2, 3).getMapX(), MasseyMon.getMiniMap(2, 3).getMapY(), this);
							g.drawImage(MasseyMon.getTrainers(npc1), 407, 385, this);
							g.drawImage(MasseyMon.getTrainers(npc2), 612, 360, this);
							talking = false;
							movable = true;
						}
					}
					myGuy.draw(g);
			}
			if (menu) { // if menu is true
				Menu.display(g); // draw the menu
				if (bag) { // if bag is true
					Items.display(g); // draw the bag
				}
				if (pokemon) { // if pokemon is true
 					PokemonMenu.display(g); // draw the Pokemon screen
				}
		}
	}
	public JTextArea getTextArea(){ // getter for text area
		return myArea;
	}
	public void paintComponent(Graphics g) { // draw method for if the player is in battle
		if (titleScreen){ // if the user is on the title screen then draw it
			TitleScreen.draw(g);
			if (!titleMusic.isPlaying()) { // if the title screen music ends then replay it
				titleMusic.play();
			}
		}
		else { // if the user is not on the title screen
			titleMusic.stop(); // stop the title music
			if (!MasseyMon.frame.inBattle) { // if the user is not in battle
				if (battleSound.isPlaying()) { // if the battle music is playing then stop it
					battleSound.stop();
				}
				if (!overworldMusic.isPlaying()) { // if the overworld music ends then play it again
					overworldMusic.play();
				}
				if (MasseyMon.frame.getPokeBattle() != null) {
					if (MasseyMon.frame.getPokeBattle().getEvolutions().size() == 0) {
						draw(g);
					} else {
						if (battleSound.isPlaying()) {
							battleSound.stop();
						}
						g.drawImage(evoBack, 0, 0, this);
						Pokemon pokeEvo = MasseyMon.frame.getPokeBattle().getEvolutions().get(0);
						if (!started3) {
							String myString = String.format("What?\n%s is evolving!", pokeEvo.getName());
							myArea2.setVisible(true);
							myArea2.setText(myString);
							progress = 0;
							frameEvo = 1;
							evolutionMusic.play();
							started3 = true;
							doneEvo = false;
						} else {
							if (doneEvo) {
								if (!evolutionMusic.isPlaying()) {
									myArea2.setVisible(false);
									MasseyMon.frame.getPokeBattle().getEvolutions().remove(0);
									started3 = false;
									evolutionMusic.stop();
								}
							}
							if (!(progress == STARTING)) {
								if (frameEvo % (STARTING - progress) == 0) {
									progress += 3;
									pokeEvo.drawEvoNext(g);
									if (progress == STARTING) {
										String curName = pokeEvo.getName();
										try {
											pokeEvo.evolve();
										} catch (IOException e) {
										}
										String myString = String.format("%s evolved into %s!", curName, pokeEvo.getName());
										myArea2.setText(myString);
									}
								} else {
									pokeEvo.drawEvo(g);
								}
								frameEvo++;
							} else {
								pokeEvo.drawEvoNext(g);
							}
						}
					}

				} else {
					draw(g);
				}
			} else { // if the user is in battle
				overworldMusic.stop(); // stop the overworld music
				if (!battleSound.isPlaying()) { // if the battle music stops then replay it
					battleSound.play();
				}
				if (started == false) {
					Color myColor = new Color(0, 0, 0, alpha);
					g.setColor(myColor);
					if (alpha == 100) {
						started = true;
					} else {
						alpha += 1;
						g.fillRect(0, 0, getWidth(), getHeight());
					}
				} else {
					if (started2 == false) {
						try {
							MasseyMon.frame.startBattle(g, myGuy);
							makeNewArea();
							started2 = true;
						} catch (IOException e) {
						}
						started2 = true;
					} else {
						try {
							MasseyMon.frame.getPokeBattle().Start(g);
						} catch (FileNotFoundException e) { }
					}
				}
			}
		}
		spacePressed = false;
	}

	public void drawEndScreen(Graphics g){ // method to draw the ends screen after you beat the champion
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0,0,956,795);
		g.drawImage(hallOfFame,0,130,null);
		g.drawImage(prof,450,0,null);
		g.drawImage(endScreen,180,150,null);


	}
	class clickListener implements MouseListener { // click listener class
		public void mouseEntered(MouseEvent e) {

		}
		public void mouseExited(MouseEvent e) {

		}
		public void mouseReleased(MouseEvent e) {

		}
		public void mouseClicked(MouseEvent e) {

		}
		public void mousePressed(MouseEvent e) {
			if (MasseyMon.frame.inBattle){ // if the user is in battle
				if (!MasseyMon.frame.getPokeBattle().getStopGame()){
					MasseyMon.frame.getPokeBattle().checkCollision();
				}
			}
		}
	}

	class moveListener implements KeyListener { // move listener
		public void keyTyped(KeyEvent e) { // if a key is typed
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
			if (keys[KeyEvent.VK_BACK_SPACE]){
				if (MasseyMon.frame.inBattle){
					MasseyMon.frame.getPokeBattle().dontLearnMove();
				}
			}
		}
		public void keyPressed(KeyEvent e) { // key pressed
			if (titleScreen) { // if the user is on the title screen
				if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){ // if they go down or S then move the pointer arrow
					TitleScreen.setPosY(505);
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W){ // if they go up or W then move the pointer arrow up
					TitleScreen.setPosY(305);
				}
				else if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) && TitleScreen.getInstructionMenu()){ // if the user pressed spcae or enter while instructions is true
					TitleScreen.setInstructionMenu(false); // set instructions to false
				}
				else if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) && TitleScreen.getPosY() == 505){ // if the user pressed space or enter over instructions then set it to true
					TitleScreen.setInstructionMenu(true);
				}
				else if ((e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) && TitleScreen.getPosY() == 305){ // if the user pressed space or enter on start game then set title screen to false and start the game
					titleScreen = false;
				}
			}
			else { // if the user isn't on the title screen
				if (pokeShop()){ // if the user is in the shop
					// getting which number the user pressed to buy an item and setting the corresponding variable to true
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
				if ((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && !movable) { // if the user presses A or left and on the starter screen change the starter inex
					starterIndex -= 1;
					if (starterIndex == -1) {
						starterIndex = 2;
					}
				}
				if ((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && !movable) { // if the user presses right or D on the starter screen then change the starter index
					starterIndex += 1;
					if (starterIndex == 3) {
						starterIndex = 0;
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_M && keys[e.getKeyCode()] == false) { // if the user presses M on the home screen then open the menu
					menu = true;
				}
				if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && keys[e.getKeyCode()] == false && menu && !bag && !pokemon) { // if the user goes down on the menu screen move the pointer arrow down
					Menu.setPosY(40);
				}
				if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && keys[e.getKeyCode()] == false && menu && !bag && !pokemon) { // if the user goes up on the menu screen move the pointer arrow up
					Menu.setPosY(-40);
				}
				if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && keys[e.getKeyCode()] == false && bag) { // if the user goes up while bag is active move the bag pointer arrow down
					Items.setPosY(40);
				}
				if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && keys[e.getKeyCode()] == false && bag) { // if the user goes up while bag is open then move the bag pointer arrow up
					Items.setPosY(-40);
				}
				if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && keys[e.getKeyCode()] == false && pokemon) { // if the user goes down while pokemon is open then set the X and Y of the outline box down
					PokemonMenu.setPosY();
					PokemonMenu.setPosX();
				}
				if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && keys[e.getKeyCode()] == false && pokemon) { // if the uesr goes up while pokemon is active then set the X and Y of the outline box up
					PokemonMenu.setPosYUP();
					PokemonMenu.setPosX();
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE && keys[e.getKeyCode()] == false && Menu.getPosY() == 186 && !pokemon && menu) { // if the user is on the menu screen and pressed space over pokemon then reset the pointer position and open pokemon screen
					PokemonMenu.resetPosXY();
					pokemon = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE && keys[e.getKeyCode()] == false && Menu.getPosY() == 226 && !bag) { // if the user is on the menu screen and pressed space over bag then reset the pointer position and open bag screen
					Items.resetPosY();
					bag = true;
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE && keys[e.getKeyCode()] == false && Menu.getPosY() == 266 && !bag && !pokemon) { // if the user presses space over the exit button on the menu screen then set it to false
					Menu.resetPosY();
					menu = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE && keys[e.getKeyCode()] == false && Items.getPosY() == 567 && bag) { // if the user presses space over exit on the bag screen then set it to false
					Menu.resetPosY();
					bag = false;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE && keys[e.getKeyCode()] == false && PokemonMenu.getDisplayButton() && pokemon) { // if the user presses space over exit on the pokemon screen then set it to false
					Menu.resetPosY();
					pokemon = false;
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE) { // if space is pressed then set spacePressed to true
					spacePressed = true;
				}
				keys[e.getKeyCode()] = true;
			}
		}

		public void keyReleased(KeyEvent e) {
			keys[e.getKeyCode()] = false;
		}
	}


	public void move() { // method to move the player as well as check it against various things in the mask and set position when entering a new area
		if (!titleScreen) { // if user is not in title screen
			if (!menu) { // and menu is not open
				if (movable) { // and the user is allowed to move
					// setting route indexes for trainers based on picIndexs that contain trainers
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
					else if(picIndex == 7){
						routeIndex = 4;
					}
					else if(picIndex == 9){
						routeIndex = 5;
					}
					else if(picIndex == 10){
						routeIndex = 6;
					}
					else if(picIndex == 11){
						routeIndex = 7;
					}
					if (picIndex == 2 && miniPicIndex == 4){
						gymIndex = 2;
						checking = true;
					}
					else if(picIndex == 6 && miniPicIndex == 4){
						gymIndex = 0;
						checking = true;
					}
					else if((picIndex == 12 && miniPicIndex == 3)){
						gymIndex = 1;
						checking = true;
					}
					else{
						checking = false;
						gymIndex = -1;
					}
					inGrass = false;

					// if the user is going up and passes the clear checks and is not going the opposite way of a ledge
					if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W]) && clear(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 19, myGuy.getWorldY() - 1) && (checkLedge(myGuy.getWorldX(), myGuy.getWorldY() - 2, myGuy.getWorldX() + 19, myGuy.getWorldY() - 2)
							&& checkLedgeLeft(myGuy.getWorldX(), myGuy.getWorldY() - 2, myGuy.getWorldX() + 19, myGuy.getWorldY() - 2) && checkLedgeRight(myGuy.getWorldX(), myGuy.getWorldY() - 2, myGuy.getWorldX() + 19, myGuy.getWorldY() - 2))) {
						direction = UP; // setting the diretion to up
						myGuy.move(direction, picIndex, miniPicIndex, mini); // calling the method to move the player
						if (checkBuilding(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) { // if the player is in the entrance to the first building in the map
							mini = true; // set mini to true since they are entering a mini map
							miniPicIndex += 2; // add the correct mini pic index based on the building check
							npc1 = randint(3, 17); // getting a randint for which picture NPC 1 will be
							npc2 = randint(3, 17); // getting a randint for which picture NPC 2 will be
							npcText1 = randint(17,27); // getting a randint for which text NPC 1 will say
							npcText2 = randint(17,27); // getting a randint for which text NPC 2 will say
							// setting the world and screen positions of the player based on the data from the file and the class
							myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							Textbox.setTextWriting(true);
						} else if (checkBuilding2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) { // same logic as checkBuilding1
							mini = true;
							miniPicIndex += 1;
							npc1 = randint(3, 17);
							npc2 = randint(3, 17);
							npcText1 = randint(17,27);
							npcText2 = randint(17,27);
							myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							Textbox.setTextWriting(true);
						} else if (checkBuilding3(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) { // same logic as checkBuilding1
							mini = true;
							miniPicIndex += 3;
							npc1 = randint(3, 17);
							npc2 = randint(3, 17);
							npcText1 = randint(17,27);
							npcText2 = randint(17,27);
							myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						} else if (checkBuilding4(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) { // same logic as checkBuilding1
							mini = true;
							miniPicIndex += 4;
							npc1 = randint(3, 17);
							npc2 = randint(3, 17);
							npcText1 = randint(17,27);
							npcText2 = randint(17,27);
							myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						} else if (checkBuilding5(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) { // same logic as checkBuilding1
							if (tileText && picIndex == 2){ // if the user enters the viridian gym for the first time display text for warp pannels
								talking = true;
							}
							mini = true;
							miniPicIndex += 5;
							npc1 = randint(3, 17);
							npc2 = randint(3, 17);
							npcText1 = randint(17,27);
							npcText2 = randint(17,27);
							myGuy.setWorldX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenY(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosY());
							myGuy.setScreenX(MasseyMon.getMiniMap(picIndex, miniPicIndex).getStartPosX());
						} else if (checkNextRoute(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) { // checking if the user has entered the next route
							if (MasseyMon.frame.getMyPokes().size() != 0) { // if the user has gotten a pokemon
								picIndex += 1; // add to the pixIndex
								// set the starting world positions for spawning on the new route
								myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
								myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());

								// calculating the screen position of the player on the new route
								if (MasseyMon.getMap(picIndex).getMapHeight() > 795) { // if the height of the image is greater than the screen height
									if (myGuy.getWorldY() == 397 || myGuy.getWorldY() == 900 || myGuy.getWorldY() == 860) { // checking for specific world coordinates
										myGuy.setScreenY(398); // put player in middle of the screen
									} else if (myGuy.getWorldY() - 398 > 0 || myGuy.getWorldY() + 398 < MasseyMon.getMap(picIndex).getMapHeight()) { // if the user is not within 398 of the edge of the picture
										myGuy.setScreenY(795 - (MasseyMon.getMap(picIndex).getMapHeight() - MasseyMon.getMap(picIndex).getStartPosY())); // calculate the players screen position based on screen size, map height, and start position

									}
								} else { // if the image height is less than the screen height
									myGuy.setScreenY(MasseyMon.getMap(picIndex).getStartPosY()); // draw the player at the start position
								}
								if (MasseyMon.getMap(picIndex).getMapWidth() > 956) { // similar logic as for height but using 956 since it is the width of the screen and checking widths
									if (myGuy.getWorldX() - 478 > 0 || myGuy.getWorldY() + 478 < MasseyMon.getMap(picIndex).getMapWidth()) {
										myGuy.setScreenX(478);
									} else {
										myGuy.setScreenX(956 - (MasseyMon.getMap(picIndex).getMapWidth() - MasseyMon.getMap(picIndex).getStartPosX()));
									}
								} else {
									myGuy.setScreenX(MasseyMon.getMap(picIndex).getStartPosX());
								}
							}
						} else if (checkPrevRoute(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) { // checking if the user is going back to the previous route
							picIndex -= 1; // subtracting from picIndex
							// setting the world coordinates based on the data file
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX7());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY7());
							// similar checks for screen positions as checkNextRoute
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
						}
						// checking if the user has entered the line of sight of the first trainer in a route and if the user has already defeated the trainer do not display text or start battle. Also same check for gym trainers
						else if ((checkTrainer1(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(0).get(routeIndex) == false && !checking) || (checking && MasseyMon.frame.getGymBattles().get(gymIndex).get(0) == false && checkTrainer1(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1))) {
							if (!oneTimeTalk) { // variable to only allow the trainers to talk on one frame
								// setting all talking variables to true and generate a number of line for the trainer to say
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
								oneTimeTalk = true;
							}
							else{ // on the next frame allow the battle to begin
								talkDone = true;
							}
							if (talkDone) {
								if (checking){ // if it is a gym trainer
									if (MasseyMon.frame.getGymBattles().get(gymIndex).get(0) == false){ // if the trainer is still battable
										MasseyMon.frame.getGymBattles().get(gymIndex).set(0, true); // make the trainer no longer battable
										MasseyMon.frame.inBattle = true; // set inBattle to true
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getGymPokemon().get(gymIndex).get(0)); // get the first enemy Pokemon
										// reset text variables
										oneTimeTalk = false;
										talkDone = false;
									}
								}
								else{ // if it is a regular trainer
									if (MasseyMon.frame.getBattles().get(0).get(routeIndex) == false){ // if the trainer is still battlable
										MasseyMon.frame.getBattles().get(0).set(routeIndex, true); // make the trainer no longer battable
										MasseyMon.frame.inBattle = true; // set inBattle to true
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(0).get(routeIndex)); // get the first enemy pokemon
										// reset text variables
										oneTimeTalk = false;
										talkDone = false;
									}
								}
							}
						}
						// similar logic as for trainer 1
						else if ((checkTrainer2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(1).get(routeIndex) == false && !checking) || (checking && MasseyMon.frame.getGymBattles().get(gymIndex).get(1) == false && (checkTrainer2(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)))) {
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
								if (checking){
									if (MasseyMon.frame.getGymBattles().get(gymIndex).get(1) == false) {
										MasseyMon.frame.getGymBattles().get(gymIndex).set(1, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getGymPokemon().get(gymIndex).get(1));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
								else{
									if (MasseyMon.frame.getBattles().get(1).get(routeIndex) == false){
										MasseyMon.frame.getBattles().get(1).set(routeIndex, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(1).get(routeIndex));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
							}
						}
						// similar logic as for trainer 1
						else if (checkTrainer3(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(2).get(routeIndex) == false) {
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
						}
						// similar logic as for trainer 1 without gym check
						else if (checkTrainer4(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(3).get(routeIndex) == false) {
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
								MasseyMon.frame.getBattles().get(3).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(3).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						}
						// similar logic as for trainer 1 without gym check
						else if (checkTrainer5(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(4).get(routeIndex) == false) {
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
							if (talkDone){
								MasseyMon.frame.getBattles().get(4).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								System.out.println(MasseyMon.frame.getTrainerPokes().get(4));
								if (picIndex == 9){
									MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(4).get(routeIndex-1));
								}
								else{
									MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(4).get(routeIndex));
								}
								oneTimeTalk = false;
								talkDone = false;
							}
						}
						// checking if the user is talking to brock and then checking the same as the trainers
						else if (checkBrock(myGuy.getWorldX()+10, myGuy.getWorldY() - 5, myGuy.getWorldX() + 10, myGuy.getWorldY() - 5) && MasseyMon.frame.getGymBattles().get(0).get(2) == false) {
							if (!oneTimeTalk) {
								brockTalking = true;
								oneTimeTalk = true;
								talking = true;
								Textbox.setTextWriting(true);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								brockTalking = false;
								if (MasseyMon.frame.getGymBattles().get(0).get(2) == false){
									MasseyMon.frame.getGymBattles().get(0).set(2, true);
									MasseyMon.frame.inBattle = true;
									MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getGymPokemon().get(0).get(2));
									oneTimeTalk = false;
									talkDone = false;
								}
							}
						}
						// checking if the user is talking to misty and then checking the same as the trainers
						else if (checkMisty(myGuy.getWorldX()+10, myGuy.getWorldY() - 10, myGuy.getWorldX() + 10, myGuy.getWorldY() - 10) && MasseyMon.frame.getGymBattles().get(1).get(2) == false) {
							if (!oneTimeTalk) {
								mistyTalking = true;
								oneTimeTalk = true;
								talking = true;
								Textbox.setTextWriting(true);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								mistyTalking = false;
								if (MasseyMon.frame.getGymBattles().get(1).get(2) == false){
									MasseyMon.frame.getGymBattles().get(1).set(2, true);
									MasseyMon.frame.inBattle = true;
									MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getGymPokemon().get(1).get(2));
									oneTimeTalk = false;
									talkDone = false;
								}
							}
						}
						// checking if the user is talking to giovanni and then checking the same as the trainers
						else if (checkGiovanni(myGuy.getWorldX()+10, myGuy.getWorldY() - 5, myGuy.getWorldX() + 10, myGuy.getWorldY() - 5)) {
							if (!oneTimeTalk) {
								giovanniTalking = true;
								oneTimeTalk = true;
								talking = true;
								Textbox.setTextWriting(true);
							}
							else{
								talkDone = true;
							}
							if (talkDone){
								giovanniTalking = false;
								if (MasseyMon.frame.getGymBattles().get(2).get(3) == false){
									MasseyMon.frame.getGymBattles().get(2).set(3, true);
									MasseyMon.frame.inBattle = true;
									MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getGymPokemon().get(2).get(3));
									oneTimeTalk = false;
									talkDone = false;
								}
							}
						}
						// checking if the user is talking to the champion and then checking the same as the trainers
						else if (checkChampion(myGuy.getWorldX()+10, myGuy.getWorldY() - 5, myGuy.getWorldX() + 10, myGuy.getWorldY() - 5)) {
							championTalking = true;
							talking = true;
							Textbox.setTextWriting(true);
						}
						// checking if the user is within a spot that can encouter a wild pokemon
						else if (checkWildEncounter(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							inGrass = true; // if they are then set inGrass to true
						}
					}
					// if the user is going down and passes the clear checks and is not going the opposite way of a ledge
					else if ((keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]) && clear(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27) && (checkLedge(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27)
							&& checkLedgeLeft(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27) && checkLedgeRight(myGuy.getWorldX(), myGuy.getWorldY() + 27, myGuy.getWorldX() + 19, myGuy.getWorldY() + 27))) {
						direction = DOWN; // setting the direction to down
						myGuy.move(direction, picIndex, miniPicIndex, mini); // moving the player

						// if the user is within an exit check
						if (checkExit1(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							// don't allow the NPCs to talk
							npcTalk = false;
							npcTalk2 = false;
							mini = false; // set mini to false since they are leaving the mini map
							miniPicIndex -= 2; // reset miniPicIndex to the default position
							// setting world coordinates based on the data file
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX3());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY3());

							// setting the screen coordinates based on a similar check to checkNextRoute in the up section
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
						}
						// similar logic to checkExit1
						else if (checkExit2(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							npcTalk = false;
							npcTalk2 = false;
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
						}
						// similar logic to checkExit1
						else if (checkExit3(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							npcTalk = false;
							npcTalk2 = false;
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
						}
						// similar logic to checkExit1
						else if (checkExit4(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							npcTalk = false;
							npcTalk2 = false;
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
						}
						// similar logic to checkExit1
						else if (checkExit5(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
							npcTalk = false;
							npcTalk2 = false;
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
						}
						// similar logic to checkPrevRoute in up
						else if (checkPrevRoute(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
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
						}
						// similar logic to checkNextRoute in up
						else if (checkNextRoute(myGuy.getWorldX() - 1, myGuy.getWorldY() + 27, myGuy.getWorldX() + 20, myGuy.getWorldY() + 27)) {
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
						// similar logic to checkTrainer1 in up
						else if ((checkTrainer1(myGuy.getWorldX(), myGuy.getWorldY() + 20, myGuy.getWorldX() + 20, myGuy.getWorldY() + 20) && MasseyMon.frame.getBattles().get(1).get(routeIndex) == false && !checking) || (checking && MasseyMon.frame.getGymBattles().get(gymIndex).get(0) == false && checkTrainer1(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1))) {
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
								if (checking){
									if (MasseyMon.frame.getGymBattles().get(gymIndex).get(0) == false){
										MasseyMon.frame.getGymBattles().get(gymIndex).set(0, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getGymPokemon().get(gymIndex).get(0));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
								else{
									if (MasseyMon.frame.getBattles().get(0).get(routeIndex) == false){
										MasseyMon.frame.getBattles().get(0).set(routeIndex, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(0).get(routeIndex));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
							}
						}
						// similar logic to checkTrainer1
						else if (checkTrainer2(myGuy.getWorldX(), myGuy.getWorldY() + 20, myGuy.getWorldX() + 20, myGuy.getWorldY() + 20) && MasseyMon.frame.getBattles().get(1).get(routeIndex) == false && !checking) {
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
							if (talkDone){
								MasseyMon.frame.getBattles().get(1).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(1).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;

							}
						}
						// similar logic to checkTrainer1
						else if (checkTrainer3(myGuy.getWorldX(), myGuy.getWorldY() + 20, myGuy.getWorldX() + 20, myGuy.getWorldY() + 20) && MasseyMon.frame.getBattles().get(2).get(routeIndex) == false) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
								oneTimeTalk = true;
							} else {
								talkDone = true;
							}
							if (talkDone) {
								MasseyMon.frame.getBattles().get(2).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(2).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						}
						// similar logic to checkTrainer1 without the gym trainer check
						else if (checkTrainer4(myGuy.getWorldX(), myGuy.getWorldY() + 20, myGuy.getWorldX() + 20, myGuy.getWorldY() + 20) && MasseyMon.frame.getBattles().get(3).get(routeIndex) == false) {
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
							if (talkDone){
								MasseyMon.frame.getBattles().get(3).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(3).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						}
						// similar logic to checkTrainer1 without the gym trainer check
						else if (checkTrainer5(myGuy.getWorldX(), myGuy.getWorldY() + 20, myGuy.getWorldX() + 20, myGuy.getWorldY() + 20) && MasseyMon.frame.getBattles().get(4).get(routeIndex) == false) {
							if (!oneTimeTalk) {
								talking = true;
								trainerText = true;
								Textbox.setTextWriting(true);
								trainerTextIndex = randint(4, 10);
								oneTimeTalk = true;
							} else {
								talkDone = true;
							}
							if (talkDone) {
								MasseyMon.frame.getBattles().get(4).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								System.out.println(MasseyMon.frame.getTrainerPokes().get(4));
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(4).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						}
						// similar logic to checkTrainer1 without the gym trainer check
							else if (checkTrainer6(myGuy.getWorldX(), myGuy.getWorldY() + 20, myGuy.getWorldX() + 20, myGuy.getWorldY() + 20) && MasseyMon.frame.getBattles().get(5).get(routeIndex) == false) {
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
							if (talkDone){
								MasseyMon.frame.getBattles().get(5).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(5).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						}
							// checking for wild encounters a
							else if (checkWildEncounter(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							inGrass = true; // setting inGrass to true if there is an encounter
						}
					}

					// if the user is going right and passes the clear checks and is not going in the opposite direction of a ledge
					else if ((keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) && clear(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 26) && (checkLedge(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 20)
							&& checkLedgeLeft(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 20) && checkLedgeRight(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 20))) {
						direction = RIGHT; // set direction to right
						myGuy.move(direction, picIndex, miniPicIndex, mini); // move the player

						// similar logic to checkNextRoute in up
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
						// similar logic to checkPrevRoute in up
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
						// special check if the player is going backwards from the final route
						else if (checkChampRouteBack(myGuy.getWorldX() + 20, myGuy.getWorldY(), myGuy.getWorldX() + 20, myGuy.getWorldY() + 26)) {
							picIndex -= 11; // set picIndex to the correct position
							// spawn the player at the exit on the route
							myGuy.setWorldX(20);
							myGuy.setWorldY(555);
							myGuy.setScreenX(20);
							myGuy.setScreenY(398);
						}
						// similar logic to checkTrainer1 in up
						else if (checkTrainer1(myGuy.getWorldX() + 26, myGuy.getWorldY() , myGuy.getWorldX() + 26, myGuy.getWorldY() + 19) && MasseyMon.frame.getBattles().get(0).get(routeIndex) == false) {
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
							if (talkDone){
								MasseyMon.frame.getBattles().get(0).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(0).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						}
						// similar logic to checkTrainer1 in up
						else if ((checkTrainer2(myGuy.getWorldX() + 26, myGuy.getWorldY(), myGuy.getWorldX() + 26, myGuy.getWorldY() + 19) && MasseyMon.frame.getBattles().get(1).get(routeIndex) == false && !checking) || (checking && MasseyMon.frame.getGymBattles().get(gymIndex).get(1) == false && (checkTrainer2(myGuy.getWorldX() + 26, myGuy.getWorldY() , myGuy.getWorldX() + 26, myGuy.getWorldY() + 19)))) {
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
								if (checking){
									if (MasseyMon.frame.getGymBattles().get(gymIndex).get(1) == false) {
										MasseyMon.frame.getGymBattles().get(gymIndex).set(1, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getGymPokemon().get(gymIndex).get(1));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
								else{
									if (MasseyMon.frame.getBattles().get(1).get(routeIndex) == false){
										MasseyMon.frame.getBattles().get(1).set(routeIndex, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(1).get(routeIndex));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
							}
						}
						// similar logic to checkTrainer1 in up
						else if (checkTrainer3(myGuy.getWorldX() + 20, myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1) && MasseyMon.frame.getBattles().get(2).get(routeIndex) == false) {
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
							if (talkDone){
								MasseyMon.frame.getBattles().get(2).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(2).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						} else if (checkWildEncounter(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							inGrass = true;
						}
					}
					// if the user is going left and passes the clear checks and is not going the opposite direction of a ledge
					else if ((keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) && clear(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 26) && (checkLedge(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 20)
							&& checkLedgeLeft(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 20) && checkLedgeRight(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 20))) {
						direction = LEFT; // settind direction toe left
						myGuy.move(direction, picIndex, miniPicIndex, mini); // moving the player
						// similar logic to checkPrevRoute in up
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
						}
						// similar logic to checkNextRoute in up
						else if (checkNextRoute(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 26)) {
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
						// special check for going to the route that leads to the champion
						else if (checkChampRoute(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 26)) {
							picIndex += 11; // setting the picIndex to the correct route
							// getting the world positions from the data file and custom setting the screen positions
							myGuy.setWorldX(MasseyMon.getMap(picIndex).getStartPosX());
							myGuy.setWorldY(MasseyMon.getMap(picIndex).getStartPosY());
							myGuy.setScreenX(800);
							myGuy.setScreenY(275);
						}
						// similar logic to checkTriainer1 in up
						else if ((checkTrainer1(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 19) && MasseyMon.frame.getBattles().get(1).get(routeIndex) == false && !checking) || (checking && MasseyMon.frame.getGymBattles().get(gymIndex).get(0) == false && checkTrainer1(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 19))) {
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
								if (checking){
									if (MasseyMon.frame.getGymBattles().get(gymIndex).get(0) == false){
										MasseyMon.frame.getGymBattles().get(gymIndex).set(0, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getGymPokemon().get(gymIndex).get(0));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
								else{
									if (MasseyMon.frame.getBattles().get(0).get(routeIndex) == false){
										MasseyMon.frame.getBattles().get(0).set(routeIndex, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(0).get(routeIndex));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
							}
						}
						// similar logic to checkTriainer1 in up
						else if ((checkTrainer2(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 19) && MasseyMon.frame.getBattles().get(1).get(routeIndex) == false && !checking) || (checking && MasseyMon.frame.getGymBattles().get(gymIndex).get(1) == false && (checkTrainer2(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 19)))) {
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
								if (checking){
									if (MasseyMon.frame.getGymBattles().get(gymIndex).get(1) == false) {
										MasseyMon.frame.getGymBattles().get(gymIndex).set(1, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getGymPokemon().get(gymIndex).get(1));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
								else{
									if (MasseyMon.frame.getBattles().get(1).get(routeIndex) == false){
										MasseyMon.frame.getBattles().get(1).set(routeIndex, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(1).get(routeIndex));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
							}
						}
						// similar logic to checkTriainer1 in up
						else if ((checkTrainer3(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 19) && MasseyMon.frame.getBattles().get(2).get(routeIndex) == false && !checking) || (checking && MasseyMon.frame.getGymBattles().get(gymIndex).get(2) == false && (checkTrainer3(myGuy.getWorldX() - 1, myGuy.getWorldY(), myGuy.getWorldX() - 1, myGuy.getWorldY() + 19)))) {
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
								if (checking){
									if (MasseyMon.frame.getGymBattles().get(gymIndex).get(2) == false) {
										MasseyMon.frame.getGymBattles().get(gymIndex).set(2, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getGymPokemon().get(gymIndex).get(2));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
								else{
									if (MasseyMon.frame.getBattles().get(2).get(routeIndex) == false){
										MasseyMon.frame.getBattles().get(2).set(routeIndex, true);
										MasseyMon.frame.inBattle = true;
										MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(2).get(routeIndex));
										oneTimeTalk = false;
										talkDone = false;
									}
								}
							}
						}
						// similar logic to checkTriainer1 in up without gym trainer checks
						else if (checkTrainer4(myGuy.getWorldX() -1 , myGuy.getWorldY() , myGuy.getWorldX() - 1, myGuy.getWorldY() + 19) && MasseyMon.frame.getBattles().get(3).get(routeIndex) == false) {
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
							if (talkDone){
								MasseyMon.frame.getBattles().get(3).set(routeIndex, true);
								MasseyMon.frame.inBattle = true;
								MasseyMon.frame.setEnemyPokes(MasseyMon.frame.getTrainerPokes().get(3).get(routeIndex));
								oneTimeTalk = false;
								talkDone = false;
							}
						}
						// checking for wild encounters
						else if (checkWildEncounter(myGuy.getWorldX(), myGuy.getWorldY() - 1, myGuy.getWorldX() + 20, myGuy.getWorldY() - 1)) {
							inGrass = true; // if the user can encounter wild pokemon set inGrass to true
						}
					}
					else { // if the user is not moving
						myGuy.resetExtra(); // reset extra in the player class
						myGuy.idle(direction); // set the direction to idle

						if (direction == UP && (hasStarter || mini)) { // if the direction is up and the player has a starter or is in a mini map
							if (checkTalk(myGuy.getWorldX() + 7, myGuy.getWorldY() - 20, myGuy.getWorldX() + 14, myGuy.getWorldY() - 20)) { // if the user talks to someone
								// setting talking variables to true
								talking = true;
								npcTalk = true;
							}
							// checking if the user talked to an NPC in a house
							else if (checkNpcTalk(myGuy.getWorldX() + 7, myGuy.getWorldY() - 20, myGuy.getWorldX() + 14, myGuy.getWorldY() - 20)) {
								npcTalk2 = true;
								talking = true;
							}
						}
						// if the direction is down and player is idle
						if (direction == DOWN) {
							// if the user is within the talking check of someone
							if (checkTalk(myGuy.getWorldX(), myGuy.getWorldY() + 37, myGuy.getWorldX() + 20, myGuy.getWorldY() + 37)) {
								// setting talking variables to true
								talking = true;
								npcTalk = true;
							}
						}
						// if the direction is right and player is idle
						if (direction == RIGHT) {
							// if the user is within the talking check of someone
							if (checkTalk(myGuy.getWorldX() + 35, myGuy.getWorldY() + 8, myGuy.getWorldX() + 35, myGuy.getWorldY() + 18)) {
								// setting talking variables to true
								talking = true;
								npcTalk = true;
							}
						}
						// if the direction is left and the player is idle
						if (direction == LEFT) {
							// if the user is within the talking check of someone
							if (checkTalk(myGuy.getWorldX() - 10, myGuy.getWorldY(), myGuy.getWorldX() - 10, myGuy.getWorldY() + 26)) {
								// setting talking to true
								talking = true;
							}
						}
					}
				}
			}
		}
	}

	// method to check if the area in the specified direction of the user is clear or if it is a wall
	private boolean clear ( int x, int y, int x2, int y2){ // passing in both sides of the player

		// getting the mask and map drawing coordinates
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY = MasseyMon.getMap(picIndex).getMapY();

		if (mini){ // re-calculating the coordinates and getting the correct mask if it is a mini map
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF0000FF; // getting the color of the wall
		if (x < (0 + posX) || x >=  (maskPic.getWidth(null) + posX) || y < (0 + posY) || y >= (maskPic.getHeight(null) + posY)) {
			return false; // if the player is not on the picture return false
		}
		// checking both positions against the mask and returning if there is a collision or not
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c != WALL && d!=WALL;
	}

	// checking if the player is colliding with a building check
	// similar logic to clear but with a different color
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

	// checking if the player is colliding with a building exit
	// similar logic to clear but with a different color
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

	// similar logic as checkExit1
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

	// similar logic as checkBuilding1
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

	// checking if the user is colliding with a check to go to the next route
	// similar logic to clear but with a different colour
	private boolean checkNextRoute ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFFFF00;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	// checking if the user is colliding with a check to go back to the previous route
	// similar logic to clear but with a different colour
	private boolean checkPrevRoute ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();
		int WALL = 0xFF800000;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	// checking if the player is colliding with a ledge check and only allowing them to go through if they are oging down
	private boolean checkLedge(int x, int y, int x2, int y2){
		// similar logic to clear
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
			// if the direction is any direction other than down return false
			if (keys[KeyEvent.VK_UP]|| keys[KeyEvent.VK_W] || keys[KeyEvent.VK_LEFT]|| keys[KeyEvent.VK_A] || keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				return false;
			}
		}
		return true;
	}

	// similar logic to checkLedge but going left instead
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
			// if the direction is any direction other than left return false
			if (keys[KeyEvent.VK_UP]|| keys[KeyEvent.VK_W] || keys[KeyEvent.VK_DOWN]|| keys[KeyEvent.VK_S] || keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
				return false;
			}
		}
		return true;
	}

	// similar logic to checKLedge but going right instead
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
			// if the direction is any direction other than right then return false
			if (keys[KeyEvent.VK_UP]|| keys[KeyEvent.VK_W] || keys[KeyEvent.VK_LEFT]|| keys[KeyEvent.VK_A] || keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S]){
				return false;
			}
		}
		return true;
	}

	// similar logic to checkBuilding1
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

	// similar logic to checkExit1
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

	// similar logic to checkBuilding1
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

	//similar logic to checkExit1
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

	// similar logic to checkBuilding1
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

	// similar logic to checkExit1
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

	// checking if the user is colliding with a check specially to go to the champions route
	private boolean checkChampRoute ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF01FFFF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	// checking if the user is colldiing with a check to go backwards from the champions route
	private boolean checkChampRouteBack ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF01FF01;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	// checking if the user is talking to someone and only returning true if they alos press space
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


	// checking if the user is talking to an NPC inside on of the houses and only returning true is spcae is pressed too
	private boolean checkNpcTalk ( int x, int y, int x2,int y2){

		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}

		int WALL = 0xFF01FF01;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL && spacePressed;
	}

	// checking if the user is within the line of sight of trainer 1
	private boolean checkTrainer1 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();
		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}
		int WALL = 0xFFFF80FF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	// checking if the user is within the line of sight of trainer 2
	private boolean checkTrainer2 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();
		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}
		int WALL = 0xFFFFFF80;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	// checking if the user is within the line of sight of trainer 3
	private boolean checkTrainer3 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();
		if (mini){
			maskPic = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMask();
			posX = MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapX();
			posY =  MasseyMon.getMiniMap(picIndex,miniPicIndex).getMapY();
		}
		int WALL = 0xFF0080FF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	// checking if the user is within the line of sight of trainer 4
	private boolean checkTrainer4 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFFFF0080;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	// checking if the user is within the line of sight of trainer 5
	private boolean checkTrainer5 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF00FF80;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	// checking if the user is within the line of sight of trainer 6
	private boolean checkTrainer6 ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF8000FF;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}
	//checking if the user is standing in front of brock
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
	//checking if the user is standing in front of misty
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
	// checking if the user is standing in front of giovanni
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
	// checking if the user is standing in front of the champion
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
	// checking if the user is in grass or a cave and can encounter wile pokemon
	private boolean checkWildEncounter ( int x, int y, int x2,int y2){
		BufferedImage maskPic = MasseyMon.getMap(picIndex).getMask();
		int posX = MasseyMon.getMap(picIndex).getMapX();
		int posY =  MasseyMon.getMap(picIndex).getMapY();

		int WALL = 0xFF010000;
		int c = maskPic.getRGB(x - posX, y - posY);
		int d = maskPic.getRGB(x2 - posX, y2 - posY);
		return c == WALL && d==WALL;
	}

	public static int randint(int low, int high){ // randint method
		return (int)(Math.random()*(high-low+1)+low);
	}

	public boolean pokeCenter (){ // method to check if the player is in a Pokemon Center
		if (picIndex == 2 && miniPicIndex == 0 || picIndex == 6 && miniPicIndex == 0 || picIndex == 8 && miniPicIndex == 0 || picIndex == 12 && miniPicIndex == 0){
			return true;
		}
		return false;
	}

	public boolean pokeShop(){ // method to check if the player is in a PokeMart
		if (picIndex == 2 && miniPicIndex == 1 || picIndex == 6 && miniPicIndex == 1 || picIndex == 12 && miniPicIndex == 1){
			return true;
		}
		return false;
	}

	public boolean pokeHouse(){ // method to check if the player is in an NPCs house
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

	public void dieInBattle(){ // method to reset the players position if all of their pokemon faint in battle
		picIndex = 0;
		miniPicIndex = -1;
		myGuy.setWorldX(286);
		myGuy.setWorldY(280);
		myGuy.setScreenX(286);
		myGuy.setScreenY(280);
		MasseyMon.frame.healPokes();
	}

	public static void setStarter(boolean temp){ // setter method for starter
		starter = temp;
	}

}
