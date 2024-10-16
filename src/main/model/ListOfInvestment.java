package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;

// Represents a list of investments, with the core functionalities of filtering, deleting, 
// and providing summary statistics
public class ListOfInvestment implements Writable {

    private List<Investment> listOfInvestment;  // list of investments
    private List<Investment> filteredList;  // list of filtered investments

    private List<Double> nullList;  // holds list of 0.0's, used by summarize method
    private double max;  // max value of invested amounts
    private double min = Double.POSITIVE_INFINITY;  // min value of invested amounts
    private double mean;  // mean value of invested amounts
    private double std;  // std of invested amounts

    // EFFECTS: Initializes an empty list of investments
    public ListOfInvestment() {
        listOfInvestment = new ArrayList<Investment>();
        filteredList = new ArrayList<Investment>();
        
        nullList = new ArrayList<Double>();
        nullList.add(0.0);
        nullList.add(0.0);
        nullList.add(0.0);
        nullList.add(0.0);   
        nullList.add(0.0);
    }

    // MODIFIES: this
    // EFFECTS: adds investment to the list of investments
    public void add(Investment investment) {
        listOfInvestment.add(investment);
    }

    // REQUIRES: key is a type of investment that is already present.]
    // MODIFIES: this
    // EFFECTS: keeps only the investments for which the type of investment and key
    // match
    public void filter(String key) {
        for (Investment i : listOfInvestment) {
            if (i.getType().equals(key)) {
                filteredList.add(i);
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
            return nullList;
        }

        double cumsum = 0;
        double ret = 0;
        for (Investment i : listOfInvestment) {
            cumsum += i.getAmount();
            ret += i.getExpReturn() * i.getAmount();
            maxHelper(i);
            minHelper(i);
        }
        mean = cumsum / listOfInvestment.size();

        stdHelper();
        
        List<Double> list = new ArrayList<Double>();
        list.add(mean);
        list.add(std);
        list.add(max);
        list.add(min);
        list.add(ret);

        return list;
    }

    // EFFECTS: calculates standard deviation
    private void stdHelper() {
        for (Investment i : listOfInvestment) {
            std += Math.sqrt(((i.getAmount() - mean) * (i.getAmount() - mean)) / getNumInvestment());
        }
    }

    // EFFECTS: calculates maximum value in list
    private void maxHelper(Investment inv) {
        if (inv.getAmount() > max) {
            max = inv.getAmount();
        }
    }

    // EFFECTS: calculates minimum value in list
    private void minHelper(Investment inv) {
        if (inv.getAmount() < min) {
            min = inv.getAmount();
        }
    }

    // EFFECTS: get size of listOfInvestment
    public int getNumInvestment() {
        return listOfInvestment.size();
    }

    // EFFECTS: get size of filteredList
    public int getNumFilteredInvestment() {
        return filteredList.size();
    }

    // EFFECTS: gets the names of all investments
    public List<String> getInvestmentNames() {
        List<String> accumulator = new ArrayList<String>();

        for (Investment i : listOfInvestment) {
            String name = i.getName();
            accumulator.add(name);
        }

        return accumulator;
    }

    // EFFECTS: gets the types of all investments
    public List<String> getInvestmentTypes() {
        List<String> accumulator = new ArrayList<String>();

        for (Investment i : listOfInvestment) {
            String type = i.getType();
            accumulator.add(type);
        }

        return accumulator;
    }

    // EFFECTS: prints listOfInvestment in the order of the list elements
    public void getPrintedInvestments() {
        for (Investment i : listOfInvestment) {
            String output = "Name: " + i.getName() + ", " + "\t" + "Type: " + i.getType() + ", " + "\t" 
                    + "Amount: " + Double.toString(i.getAmount()) + ", " + "\t" 
                    + "Expected return: " + Double.toString(i.getExpReturn()) + ", " + "\t" 
                    + "Date of purchase: " + i.getDate() + ", " + "\t" 
                    + "\n";
            System.out.println(output);
        }
    }

    // EFFECTS: prints filteredList in the order of the list elements
    public void getPrintedFilteredInvestments() {
        for (Investment i : filteredList) {
            String output = "Name: " + i.getName() + ", " + "\t" + "Type: " + i.getType() + ", " + "\t" 
                    + "Amount: " + Double.toString(i.getAmount()) + ", " + "\t" 
                    + "Expected return: " + Double.toString(i.getExpReturn()) + ", " + "\t" 
                    + "Date of purchase: " + i.getDate() + ", " + "\t" 
                    + "\n";
            System.out.println(output);
        }
    }

    @Override
    public JSONObject toJson() {
        return null;
    }

    // EFFECTS: returns investments in this list as a JSON array
    private JSONArray investmentsToJson() {
        return null;
    }
}