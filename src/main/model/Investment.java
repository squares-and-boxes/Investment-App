package model;

// Represents an investment having an investment type, investment name, amount invested, expected return, date of purchase
public class Investment {
    private String type;    // investment type
    private String name;    // investment name
    private double amount;  // amount invested (in CAD $)
    private double expRet;  // expected return
    private String date;    // date of purchase (in YYYY-MM-DD)


    // REQUIRES: investedAmount>0, 0<expectedReturn<=1.
    // EFFECTS: Creates investment object and assigns investmentType to type, investmentName to name,
    //          investedAmount to amount, expectedReturn to expRet, dateOfPurchase to date.
    public Investment(String investmentType, String investmentName, double investedAmount, 
                      double expectedReturn, String dateOfPurchase) {
        this.type = investmentType;
        this.name = investmentName;
        this.amount = investedAmount;
        this.expRet = expectedReturn;
        this.date = dateOfPurchase;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public double getExpReturn() {
        return expRet;
    }

    public String getDate() {
        return date;
    }

}
