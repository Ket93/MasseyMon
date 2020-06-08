//Dimitrios Christopoulos
//TypeChart.java
//This class is meant to be a tool to use during pokemon battles. It is a 2d grid which uses the types of the attack and the defending pokemon to determine how effective an attack will or would be. It is also used in
//the AI to determine whether or not they should switch to another pokemon to give them a better type advantage
import java.util.ArrayList;
public class TypeChart{
    private double [][] types;//these 2 fields are going to determine indexes and values associated with those indexes
    private ArrayList<String> typeVals;
    public TypeChart(){
        double[][] doubles = {{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.5,0.0,1.0},//2d grid of type matchups, y axis is attack and x axis is defender
                {1.0,0.5,0.5,1.0,2.0,2.0,1.0,1.0,1.0,1.0,1.0,2.0,0.5,1.0,0.5},
                {1.0,2.0,0.5,1.0,0.5,1.0,1.0,1.0,2.0,1.0,1.0,1.0,2.0,1.0,0.5},
                {1.0,1.0,2.0,0.5,0.5,1.0,1.0,1.0,0.0,2.0,1.0,1.0,1.0,1.0,0.5},
                {1.0,0.5,2.0,1.0,0.5,1.0,1.0,0.5,2.0,0.5,1.0,0.5,2.0,1.0,0.5},
                {1.0,1.0,0.5,1.0,2.0,0.5,1.0,1.0,2.0,2.0,1.0,2.0,1.0,1.0,2.0},
                {2.0,1.0,1.0,1.0,1.0,2.0,1.0,0.5,1.0,0.5,0.5,0.5,2.0,0.0,1.0},
                {1.0,1.0,1.0,1.0,2.0,1.0,1.0,0.5,0.5,1.0,1.0,2.0,0.5,0.5,1.0},
                {1.0,2.0,1.0,2.0,0.5,1.0,1.0,2.0,1.0,0.0,1.0,0.5,2.0,1.0,1.0},
                {1.0,1.0,1.0,0.5,2.0,1.0,2.0,1.0,1.0,1.0,1.0,2.0,0.5,1.0,1.0},
                {1.0,1.0,1.0,1.0,1.0,1.0,2.0,2.0,1.0,1.0,0.5,1.0,1.0,1.0,1.0},
                {1.0,0.5,1.0,1.0,2.0,1.0,0.5,2.0,1.0,0.5,2.0,1.0,1.0,0.5,1.0},
                {1.0,2.0,1.0,1.0,1.0,2.0,0.5,1.0,0.5,2.0,1.0,2.0,1.0,1.0,1.0},
                {0.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,2.0,1.0},
                {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,2.0}};
        ArrayList<String> Ts = new ArrayList<String>();
        String [] Ts2 = {"Normal","Fire","Water","Electric","Grass","Ice","Fighting","Poison","Ground","Flying","Psychic","Bug","Rock","Ghost","Dragon"};//Indexes of a certain type in the 2d array
        for (String item : Ts2){
            Ts.add(item);
        }
        types = doubles;
        typeVals = Ts;
    }
    public double getEffect(Attack atk, Pokemon def){//this method is used to determine the type matchup
        double tot;//this would be the multiplier
        int index1 = typeVals.indexOf(atk.getType());//the index of the type of the attakc
        int index2 = typeVals.indexOf(def.getType1());//the index of the type of the defender
        tot = types[index1][index2];//the multiplier
        if (def.getType2().equals("N/A") == false){//if the defender has a secondary type
            int index3 = typeVals.indexOf(def.getType2());//get the index
            tot *= types[index3][index1];//adjusting the mulitplier accordingly
        }
        return tot;//return the multiplier
    }
    public double getPokeEffect(Pokemon atk, Pokemon def){//this is used by the ai to determine if the mathcup is favorable or if they should swtich
        double tot;
        int index1,index2;
        int index3 = -1, index4 = -1;
        index1 = typeVals.indexOf(atk.getType1());//same concept, the program takes the types of the the pokemons and determines if the matchup is favorable or not
        index2 = typeVals.indexOf(def.getType1());
        if (atk.getType2().equals("N/A") == false){
            index3 = typeVals.indexOf(atk.getType2());
        }
        if (def.getType2().equals("N/A") == false){
            index4 = typeVals.indexOf(def.getType2());
        }
        tot = types[index1][index2];
        if (index3 != -1){
            tot*= types[index3][index2];
        }
        if (index4 != -1 && index3 != -1){//since there are 2 pokemon used in this methodm there could be a total of 4 indexes
            tot*= types[index3][index4];
            tot*= types[index1][index4];
        }
        return tot;//return the damage multiplier
    }
}