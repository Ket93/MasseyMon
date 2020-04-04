import java.util.*;
import java.io.*;
import java.awt.*;

public class PokemonBattle {

    private static int numLoad;

    private static  HashTable <Pokemon> pokemon = new HashTable<>();

    public static void main(String[] arguments) throws IOException {
        load();
    }

    public static void load() throws FileNotFoundException {
        Scanner inFile = new Scanner (new BufferedReader( new FileReader("Data/Pokemon")));
        inFile.nextLine();
        while (inFile.hasNextLine()){
            pokemon.add(new Pokemon(inFile.nextLine()));
        }
        inFile.close();
        System.out.println(pokemon.toString());
    }
}

