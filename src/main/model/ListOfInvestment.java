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
        int i = 0;
        while (!(listOfInvestment.isEmpty()) && i < listOfInvestment.size()) {
            if (!(listOfInvestment.get(i).getType().equals(key))) {
                listOfInvestment.remove(i);
            } else {
                i++;
            }
        }
    }

    // REQUIRES: key is an investment name that is already present.
    // MODIFIES: this
    // EFFECTS: deletes investments for which the investment name and key match
    public void deleteInvestment(String key) {
        int size = listOfInvestment.size();

        for (int i = 0; i < size; i++) {
            if (listOfInvestment.get(i).getName().equals(key)) {
                listOfInvestment.remove(i);
                break;
            }
        }
    }

    // REQUIRES: listOfInvestment is non-empty
    // EFFECTS: produces a list of statistics in the order [mean,std,max,min,return]
    public List<Double> summarize() {
        if (listOfInvestment.isEmpty()) {
            List<Double> nullList = new ArrayList<Double>();
            nullList.add(0.0);
            nullList.add(0.0);
            nullList.add(0.0);
            nullList.add(0.0);
            nullList.add(0.0);

            return nullList;
        }

        double cumsum = 0;
        double maxMarker = 0;
        double minMarker = 1000000000;
        double retMarker = 0;
        for (Investment i : listOfInvestment) {
            cumsum += i.getAmount();
            retMarker += i.getExpReturn() * i.getAmount();
            if (i.getAmount() > maxMarker) {
                maxMarker = i.getAmount();
            }
            if (i.getAmount() < minMarker) {
                minMarker = i.getAmount();
            }
        }
        double mean = cumsum / listOfInvestment.size();
        double max = maxMarker;
        double min = minMarker;
        double ret = retMarker;

        double std = 0;
        for (Investment i : listOfInvestment) {
            std += (i.getAmount() - mean) * (i.getAmount() - mean);
        }

        List<Double> list = new ArrayList<Double>();
        list.add(mean);
        list.add(std);
        list.add(max);
        list.add(min);
        list.add(ret);

        return list;
    }

    public int getNumInvestment() {
        return listOfInvestment.size();
    }


}