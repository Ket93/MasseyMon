import java.util.*;
import java.io.*;
import java.awt.*;

public class PokemonBattle {

    int numLoad;

    private HashTable <Pokemon> pokemon = new HashTable<>();

    public void load() throws FileNotFoundException {
        Scanner inFile = new Scanner (new BufferedReader( new FileReader("Pokemon.txt")));
        numLoad = inFile.nextInt();
        inFile.nextLine();
        for (int i =0; i<numLoad;i++){
            pokemon.add(new Pokemon(inFile.nextLine()));
        }
        inFile.close();
    }
}

