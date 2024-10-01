package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestListOfInvestment {

    private ListOfInvestment testListOfInvestment;
    
    @BeforeEach
    void runBefore() {
        testListOfInvestment = new ListOfInvestment();
    }

    @Test
    void testConstructor() {
        assertEquals(0,testListOfInvestment.getNumInvestment());
    }

    @Test
    void testAdd() {
        Investment investmentNull = new Investment("Stock", "META STOCK",
                                                  400.5, 0.4, "2024-01-01");
        testListOfInvestment.add(investmentNull);
        assertEquals(1,testListOfInvestment.getNumInvestment());

    }

    @Test
    void testFilter() {
        testListOfInvestment.filter("Stock");
        assertEquals(0,testListOfInvestment.getNumInvestment());

        Investment investmentOne = new Investment("Stock", "META STOCK",
                                                  400.5, 0.4, "2024-01-01");
        Investment investmentTwo = new Investment("Stock", "FB STOCK",
                                                  900.8, 0.35, "2024-01-02");
        Investment investmentThree = new Investment("Bond", "GOOGL STOCK",
                                                  800.3, 0.14, "2024-01-03");
        testListOfInvestment.add(investmentOne);
        testListOfInvestment.add(investmentTwo);
        testListOfInvestment.add(investmentThree);

        testListOfInvestment.filter("Jack");
        assertEquals(3,testListOfInvestment.getNumInvestment());

        testListOfInvestment.filter("Stock");
        assertEquals(2,testListOfInvestment.getNumInvestment());

        testListOfInvestment.filter("Bond");
        assertEquals(0,testListOfInvestment.getNumInvestment());
    }

    @Test
    void testDelete() {
        testListOfInvestment.delete("GOVN BOND ONE");
        assertEquals(0,testListOfInvestment.getNumInvestment());

        Investment investmentFour = new Investment("Bond", "GOVN BOND TWO",
                                                  400.5, 0.4, "2024-01-01");
        testListOfInvestment.add(investmentFour);
        testListOfInvestment.delete("GOVN BOND");
        assertEquals(1,testListOfInvestment.getNumInvestment());
        testListOfInvestment.delete("GOVN BOND TWO");
        assertEquals(0,testListOfInvestment.getNumInvestment());


        Investment investmentFive = new Investment("Real Estate", "9341 BROWNING",
                                                  900, 0.50, "2024-01-02");
        Investment investmentSix = new Investment("Stock", "GOOGL STOCK",
                                                  800, 0.20, "2024-01-03");
        testListOfInvestment.add(investmentFive);
        testListOfInvestment.add(investmentSix);
        testListOfInvestment.delete("GOOGL STOCK");
        assertEquals(1,testListOfInvestment.getNumInvestment());
    }

    @Test // [mean,std,max,min,return]
    void testSummarize() {
        assertEquals(0,testListOfInvestment.summarize().size());

        Investment investmentSix = new Investment("Real Estate", "9341 BROWNING",
                                                  1, 0.50, "2024-01-02");
        Investment investmentSeven = new Investment("Stock", "GOOGL STOCK",
                                                  1, 0.20, "2024-01-03");

        testListOfInvestment.add(investmentSix);
        List<Double> resultOne = testListOfInvestment.summarize();
        assertEquals(resultOne.get(0),1);
        assertEquals(resultOne.get(1),0);
        assertEquals(resultOne.get(2),1);
        assertEquals(resultOne.get(3),1);
        assertEquals(resultOne.get(4),0.5);

        testListOfInvestment.add(investmentSeven);
        List<Double> resultTwo = testListOfInvestment.summarize();
        assertEquals(resultTwo.get(0),1);
        assertEquals(resultTwo.get(1),0);
        assertEquals(resultTwo.get(2),1);
        assertEquals(resultTwo.get(3),1);
        assertEquals(resultTwo.get(4),0.7);

        testListOfInvestment = new ListOfInvestment();

        Investment investmentEight = new Investment("Real Estate", "9341 BROWNING",
                                                  1, 0.17, "2024-01-02");
        Investment investmentNine = new Investment("JACK", "912 RAILWAY",
                                                  2, 0.2, "2024-01-04");
        Investment investmentTen = new Investment("Stock", "META EQUITY",
                                                  3, 0.5, "2024-01-05");
        
        testListOfInvestment.add(investmentEight);
        testListOfInvestment.add(investmentNine);
        testListOfInvestment.add(investmentTen);

        List<Double> resultThree = testListOfInvestment.summarize();
        assertEquals(resultThree.get(0),2);
        assertEquals(resultThree.get(1),1);
        assertEquals(resultThree.get(2),3);
        assertEquals(resultThree.get(3),1);
        assertEquals(resultThree.get(4),0.72);
    }
}
