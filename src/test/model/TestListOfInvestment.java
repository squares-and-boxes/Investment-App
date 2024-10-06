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

        testListOfInvestment = new ListOfInvestment();
        Investment investmentOne = new Investment("Stock", "META STOCK",
                                                  400.5, 0.4, "2024-01-01");
        Investment investmentTwo = new Investment("Stock", "FB STOCK",
                                                  900.8, 0.35, "2024-01-02");
        Investment investmentThree = new Investment("Bond", "GOOGL STOCK",
                                                   800.3, 0.14, "2024-01-03");
        testListOfInvestment.add(investmentOne);
        testListOfInvestment.add(investmentTwo);
        testListOfInvestment.add(investmentThree);
        testListOfInvestment.filter("Stock");
        assertEquals(2,testListOfInvestment.getNumFilteredInvestment());
        assertEquals(3,testListOfInvestment.getNumInvestment());

        testListOfInvestment = new ListOfInvestment();
        testListOfInvestment.add(investmentOne);
        testListOfInvestment.add(investmentTwo);
        testListOfInvestment.filter("Stock");
        assertEquals(2,testListOfInvestment.getNumFilteredInvestment());
        assertEquals(2,testListOfInvestment.getNumInvestment());

        testListOfInvestment = new ListOfInvestment();
        testListOfInvestment.add(investmentThree);
        testListOfInvestment.filter("Bond");
        assertEquals(1,testListOfInvestment.getNumFilteredInvestment());
        assertEquals(1,testListOfInvestment.getNumInvestment());

        testListOfInvestment = new ListOfInvestment();
        testListOfInvestment.add(investmentOne);
        testListOfInvestment.add(investmentTwo);
        testListOfInvestment.add(investmentThree);
        testListOfInvestment.filter("Bond");
        assertEquals(1,testListOfInvestment.getNumFilteredInvestment());
        assertEquals(3,testListOfInvestment.getNumInvestment());
    }

    @Test
    void testDeleteInvestment() {
        testListOfInvestment.deleteInvestment("GOVN BOND ONE");
        assertEquals(0,testListOfInvestment.getNumInvestment());

        Investment investmentFour = new Investment("Bond", "GOVN BOND TWO",
                                                  400.5, 0.4, "2024-01-01");
        testListOfInvestment.add(investmentFour);
        testListOfInvestment.deleteInvestment("GOVN BOND");
        assertEquals(1,testListOfInvestment.getNumInvestment());
        testListOfInvestment.deleteInvestment("GOVN BOND TWO");
        assertEquals(0,testListOfInvestment.getNumInvestment());


        Investment investmentFive = new Investment("Real Estate", "9341 BROWNING",
                                                  900, 0.50, "2024-01-02");
        Investment investmentSix = new Investment("Stock", "GOOGL STOCK",
                                                  800, 0.20, "2024-01-03");
        testListOfInvestment.add(investmentFive);
        testListOfInvestment.add(investmentSix);
        testListOfInvestment.deleteInvestment("GOOGL STOCK");
        assertEquals(1,testListOfInvestment.getNumInvestment());
    }

    @Test
    void testSummarize() {
        List<Double> resultNull = testListOfInvestment.summarize();
        assertEquals(0,resultNull.get(0));
        assertEquals(0,resultNull.get(1));
        assertEquals(0,resultNull.get(2));
        assertEquals(0,resultNull.get(3));
        assertEquals(0,resultNull.get(4));
        assertEquals(5,testListOfInvestment.summarize().size());

        Investment investmentSix = new Investment("Real Estate", "9341 BROWNING",
                                                  1, 0.50, "2024-01-02");
        Investment investmentSeven = new Investment("Stock", "GOOGL STOCK",
                                                  1, 0.20, "2024-01-03");

        testListOfInvestment.add(investmentSix);
        List<Double> resultOne = testListOfInvestment.summarize();
        assertEquals(1,resultOne.get(0));
        assertEquals(0,resultOne.get(1));
        assertEquals(1,resultOne.get(2));
        assertEquals(1,resultOne.get(3));
        assertEquals(0.5,resultOne.get(4));
        assertEquals(5,testListOfInvestment.summarize().size());

        testListOfInvestment.add(investmentSeven);
        List<Double> resultTwo = testListOfInvestment.summarize();
        assertEquals(1,resultTwo.get(0));
        assertEquals(0,resultTwo.get(1));
        assertEquals(1,resultTwo.get(2));
        assertEquals(1,resultTwo.get(3));
        assertEquals(0.7,resultTwo.get(4));
        assertEquals(5,testListOfInvestment.summarize().size());
    }
}
