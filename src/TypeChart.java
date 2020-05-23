import java.util.ArrayList;
public class TypeChart{
    private double [][] types;
    private ArrayList<String> typeVals;
    public TypeChart(){
        double[][] doubles = {{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,0.5,0.0,1.0},
                {1.0,0.5,0.5,1.0,2.0,2.0,1.0,1.0,1.0,1.0,1.0,2.0,0.5,1.0,0.5},
                {1.0,2.0,0.5,1.0,0.5,1.0,1.0,1.0,2.0,1.0,1.0,1.0,2.0,1.0,0.5},
                {1.0,1.0,2.0,0.5,0.5,1.0,1.0,1.0,0.0,2.0,1.0,1.0,1.0,1.0,0.5},
                {1.0,0.5,2.0,1.0,0.5,1.0,1.0,0.5,2.0,0.5,1.0,0.5,2.0,1.0,0.5},
                {1.0,1.0,0.5,1.0,2.0,0.5,1.0,1.0,2.0,2.0,1.0,1.0,1.0,1.0,2.0},
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
        String [] Ts2 = {"Normal","Fire","Water","Electric","Grass","Ice","Fighting","Poison","Ground","Flying","Psychic","Bug","Rock","Ghost","Dragon"};
        for (String item : Ts2){
            Ts.add(item);
        }
        types = doubles;
        typeVals = Ts;
    }
    public double getEffect(Attack atk, Pokemon def){
        double tot;
        int index1 = typeVals.indexOf(atk.getType());
        int index2 = typeVals.indexOf(def.getType1());
        tot = types[index1][index2];
        if (def.getType2().equals("N/A") == false){
            int index3 = typeVals.indexOf(def.getType2());
            tot *= types[index3][index1];
        }
        return tot;
    }
    public double getPokeEffect(Pokemon atk, Pokemon def){
        double tot;
        int index1,index2;
        int index3 = -1, index4 = -1;
        index1 = typeVals.indexOf(atk.getType1());
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
        if (index4 != -1 && index3 != -1){
            tot*= types[index3][index4];
            tot*= types[index1][index4];
        }
        return tot;
    }
}