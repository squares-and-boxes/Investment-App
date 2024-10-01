package model;

import java.util.List;
import java.util.ArrayList;

// Represents a list of investments, with the core functionalities of filtering, deleting, 
// and providing summary statistics
public class ListOfInvestment {

    private List<Investment> listOfInvestment;    // list of investments

    // EFFECTS: Initializes an empty list of investments
    public ListOfInvestment() {
        listOfInvestment = new ArrayList<Investment>();
    }

    // MODIFIES: this
    // EFFECTS: adds investment to the list of investments
    public void add(Investment investment) {
        listOfInvestment.add(investment);
    }

    // REQUIRES: key is a type of investment that is already present.]
    // MODIFIES: this
    // EFFECTS: keeps only the investments for which the type of investment and key match
    public void filter(String key) {
        for (Investment i : listOfInvestment) {
            if (!(i.getName().equals(key))) {
                listOfInvestment.removeInvestment(i);
            }
        }
    }

    private List<Investment> removeInvestment() {
        return ;
    }

    // REQUIRES: key is an investment name that is already present.
    // MODIFIES: this
    // EFFECTS: deletes investments for which the investment name and key match
    public void delete(String key) {
        int size = listOfInvestment.size();

        for (int i = 0; i < size; i++) {
            if (listOfInvestment.get(i).getName().equals(key)) {
                listOfInvestment.remove(i);
            }
        }
    }

    // EFFECTS: produces a list of statistics in the order [mean,std,max,min,return]
    public List<Double> summarize() {
    }

    public int getNumInvestment() {
        return listOfInvestment.size();
    }


}