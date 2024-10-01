package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInvestment {

    private Investment testInvestment;
    
    @BeforeEach
    void runBefore() {
        testInvestment = new Investment("Stock", 
                                        "META Equity", 
                                        500, 
                                        0.15, 
                                        "2024-01-01");
    }

    @Test
    void testConstructor() {
        assertEquals("Stock", testInvestment.getType());
        assertEquals("META Equity", testInvestment.getName());
        assertEquals(500, testInvestment.getAmount());
        assertEquals(0.15, testInvestment.getExpReturn());
        assertEquals("2024-01-01", testInvestment.getDate());
    }
}
