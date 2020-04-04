// HashTable.java
// Kevin Cui
// Creating a hash table data structure from scratch with methods to assist the use of the table

import java.util.ArrayList;
import java.util.LinkedList;

class HashTable<T> {
    private double maxLoad; // max load of the table
    private int numElements; // number of elements in the table
    private ArrayList<LinkedList<T>> table;

    public HashTable() {
        maxLoad = 0.75; // default max load
        numElements = 0; // empty table
        emptyTable(10); // making the table
    }

    public void emptyTable(int n) { // an empty table
        table = new ArrayList<LinkedList<T>>();
        for (int i = 0; i < n; i++) { // adding nulls linked lists to the empty table
            table.add(null);
        }
    }

    public void add(T val) { // method to add elements to the table
        int spot = Math.abs(val.hashCode()) % table.size(); // the correct linked list in the table
        if (table.get(spot) == null) { // if the spot is empty
            table.set(spot, new LinkedList()); // make a linked list at the spot
        }
        table.get(spot).add(val); // add the element to the spot
        numElements++;
        if ((double) numElements / table.size() >= maxLoad) { // if the load is over the maxload then resize the table
            resize();
        }
    }

    public void resize() { // method to resize the table
        ArrayList<LinkedList<T>> tmp = table; // temp table
        numElements = 0;
        emptyTable(table.size() * 10); // make the table larger
        for (LinkedList<T> list : tmp) { // re-add add values to the table correctly
            if (list != null) {
                for (T val : list) {
                    add(val);
                }
            }
        }
    }

    public void resize(double v) { // alternate resize method used for setLoad, takes double as parameter
        ArrayList<LinkedList<T>> temp = table;
        numElements = 0;
        emptyTable((int) Math.round(v));
        for (LinkedList<T> list : temp) {
            if (list != null) {
                for (T val : list) {
                    add(val);
                }
            }
        }
    }

    public void remove(T val) { // removes a element from the table
        int spot = Math.abs(val.hashCode()) % table.size(); // spot of the element
        if (table.get(spot) != null) { // if the spot is not empty
            table.get(spot).remove(val); // remove the element from the linked list
            numElements -= 1;
        }
    }

    public boolean contains(T val) { // checks if the table contains a certain element
        int spot = Math.abs(val.hashCode() % table.size()); // getting the spot of that element
        if (table.get(spot) != null && table.get(spot).contains(val)) { // if the spot is empty and the linked list contains the element then return true
            return true;
        } else { // else return false
            return false;
        }
    }

    public T get(int val) { // gets a value from the table if it's there
        int spot = Math.abs(val) % table.size(); // getting the spot of the element
        if (table.get(spot) != null) { // if the spot isn't null
            for (T v : table.get(spot)) { // going through the linked list
                if (v.hashCode() == val) { // if the value is there then return it
                    return v;
                }
            }
        }
        return null; // if it's not there then return null
    }


    public double getLoad() { // gets the load of the table
        return (double) numElements / table.size(); // find the number of filled spots and return it as a decimal
    }

    public void maxLoad(double load) { // allows max load of the table to be set
        if (0.1 < load && load < 0.8) { // if the load is in the acceptable range
            maxLoad = load; // setting the max load to the current load
            if ((double) numElements / table.size() > load) { // if the filled spots is greater than the load then resize the table
                resize();
            }
        }
    }

    public void setLoad(double percent) { // allows the load of the table to be set
        if (percent > maxLoad) { // if the load is greater than the max load then do nothing
        } else { // if the load is less than the max load
            if (0.1 > percent && 0.8 < percent) { // if the load entered is within the acceptable range
                resize(numElements / percent); // resize the table using the load amount entered
            }
        }
    }

    public ArrayList<T> toArray() { // returns the table as an array
        ArrayList<T> objects = new ArrayList(); // making an array list to put the elements of the table into
        for (LinkedList<T> list : table) { // adding all the elements in the table to the array list
            if (list != null) {
                for (T val : list) {
                    list.add(val);
                }
            }
        }
        return objects; // returning the array list
    }


    @Override
    public String toString() { // returns the table as a string, Mr. McKenzie's method
        String ans = ""; // blank string
        for (LinkedList<T> list : table) { // adding all elements in the table to the string
            if (list != null) {
                for (T val : list) {
                    ans += val + ", ";
                }
            }
        }
        if (numElements > 0) { // getting rid of the comma space at the end
            ans = ans.substring(0, ans.length() - 2);
        }
        return "<" + ans + ">"; // returning the string
    }
}

