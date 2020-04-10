import java.util.*;
import java.io.*;

public class PokemonBattle {

    private static Pokemon poke;
    public static class HashTable <K,V> extends Dictionary <K,V> implements Map <K,V>, Cloneable, Serializable {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object o) {
            return false;
        }

        @Override
        public boolean containsValue(Object o) {
            return false;
        }

        @Override
        public Enumeration<K> keys() {
            return null;
        }

        @Override
        public Enumeration<V> elements() {
            return null;
        }

        @Override
        public V get(Object o) {
            return null;
        }

        @Override
        public V put(K k, V v) {
            return null;
        }

        @Override
        public V remove(Object o) {
            return null;
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<K> keySet() {
            return null;
        }

        @Override
        public Collection<V> values() {
            return null;
        }

        @Override
        public Set<Entry<K, V>> entrySet() {
            return null;
        }
    }
    private static  HashTable <Integer,Pokemon> pokemon = new HashTable<Integer,Pokemon>();

    public static void main(String[] arguments) throws IOException {
        load();
    }

    public static void load() throws FileNotFoundException {
        Scanner inFile = new Scanner (new BufferedReader( new FileReader("Data/Pokemon")));
        inFile.nextLine();
        while (inFile.hasNextLine()){
            poke = new Pokemon (inFile.nextLine());
            pokemon.put(poke.hashCode(),poke);
        }
        inFile.close();
        System.out.println(pokemon.toString());
    }
}

