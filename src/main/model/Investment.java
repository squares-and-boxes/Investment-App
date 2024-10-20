package model;

import persistence.Writable;
import org.json.JSONObject;

// Represents an investment having an investment type, investment name, amount invested, 
// expected return, date of purchase
public class Investment implements Writable {
    private String type; // investment type
    private String name; // investment name
    private double amount; // amount invested (in CAD $)
    private double expRet; // expected return
    private String date; // date of purchase (in YYYY-MM-DD)

    // REQUIRES: investedAmount>0, 0<expectedReturn<=1, investmentName isn't used
    // already.
    // EFFECTS: Creates investment object and assigns investmentType to type,
    // investmentName to name,
    // investedAmount to amount, expectedReturn to expRet, dateOfPurchase to date.
    public Investment(String investmentType, String investmentName, double investedAmount,
            double expectedReturn, String dateOfPurchase) {
        this.type = investmentType;
        this.name = investmentName;
        this.amount = investedAmount;
        this.expRet = expectedReturn;
        this.date = dateOfPurchase;
    }

    // EFFECTS: gets type
    public String getType() {
        return type;
    }

    // EFFECTS: gets name
    public String getName() {
        return name;
    }

    // EFFECTS: gets amount
    public double getAmount() {
        return amount;
    }

    // EFFECTS: gets expected return
    public double getExpReturn() {
        return expRet;
    }

    // EFFECTS: gets date
    public String getDate() {
        return date;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type",type);
        json.put("name",name);
        json.put("amount",amount);
        json.put("expRet",expRet);
        json.put("date",date);
        return json;
    }

}
