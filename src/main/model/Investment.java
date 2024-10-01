package model;

// Represents an investment having an investment type, investment name, amount invested, expected return, date of purchase
public class Investment {
    String type;    // investment type
    String name;    // investment name
    double amount;  // amount invested (in CAD $)
    double expRet;  // expected return
    String date;    // date of purchase (in YYYY-MM-DD)


    // REQUIRES: amount>0, 0<expRet<=1.
    // EFFECTS: Creates investment object and assigns investmentType to type, investmentName to name,
    //          investedAmount to amount, expectedReturn to expRet, dateOfPurchase to date.
    public Investment(String investmentType, String investmentName, Double investedAmount, double expectedReturn, String dateOfPurchase) {
    }

    public String getType() {
    }

    public String getName() {
    }

    public int getAmount() {
    }

    public int getExpReturn() {
    }

    public String getDate() {
    }

}
