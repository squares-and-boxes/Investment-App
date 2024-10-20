package persistence;

import model.Investment;
import model.ListOfInvestment;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestJsonReader extends TestJson {

    @Test
    void testReaderNonExistent() {
        JsonReader reader = new JsonReader("./data/::invalid.json");
        try {
            ListOfInvestment loi = reader.read();
            fail("IOException expected...");
        } catch (IOException e) {
            // pog
        }
    }

    @Test
    void testReaderEmpty() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            ListOfInvestment loi = reader.read();
            assertEquals("My Investments", loi.getName());
            assertEquals(0, loi.getNumInvestment());
        } catch (IOException e) {
            fail("Couldn't read from file...");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            ListOfInvestment loi = reader.read();
            assertEquals("My Investments", loi.getName());
            List<Investment> investments = loi.getInvestments();
            assertEquals(2, investments.size());
            checkInvestment("Stock","META",900,0.5,"2024-01-01",investments.get(0));
            checkInvestment("Bond", "CAD GOVN", 1000, 0.2, "2024-01-02",investments.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file...");
        }
    }

}