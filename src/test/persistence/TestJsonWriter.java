package persistence;

import model.Investment;
import model.ListOfInvestment;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonWriter extends TestJson {

    @Test
    void testWriterNonExistent() {
        try {
            ListOfInvestment loi = new ListOfInvestment("My Investments");
            JsonWriter writer = new JsonWriter("./data/:`k:inv9220\0a:8lid.json");
            writer.open();
            fail("IOException was expected...");
        } catch (IOException e) {
            // pog
        }
    }

    @Test
    void testWriterEmpty() {
        try {
            ListOfInvestment loi = new ListOfInvestment("My Investments");
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(loi);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            loi = reader.read();
            assertEquals("My Investments", loi.getName());
            assertEquals(0, loi.getNumInvestment());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterGeneral() {
        try {
            ListOfInvestment loi = new ListOfInvestment("My Investments");
            loi.add(new Investment("Bond", "CAD GOVN", 1000, 0.2, "2024-01-02"));
            loi.add(new Investment("Stock","META",900,0.5,"2024-01-01"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(loi);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            loi = reader.read();
            assertEquals("My Investments",loi.getName());
            List<Investment> investments = loi.getInvestments();
            assertEquals(2, investments.size());
            checkInvestment("Bond", "CAD GOVN", 1000, 0.2, "2024-01-02",investments.get(0));
            checkInvestment("Stock","META",900,0.5,"2024-01-01",investments.get(1));
        } catch (IOException e) {
            fail("IOException should not have been thrown...");
        }
    }

}
