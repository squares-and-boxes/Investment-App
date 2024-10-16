package persistence;

import model.Investment;
import model.ListOfInvestment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Reads list of investment from stored JSON file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
    }

    // EFFECTS: reads list of investments from file and returns it
    // throws IOException if error occurs during reading
    public ListOfInvestment read() throws IOException {
        return null;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return null;
    }

    // EFFECTS: parses list of investment from JSON object and returns it
    private ListOfInvestment parseListOfInvestment(JSONObject jsonObject) {
        return null;
    }

    // MODIFIES: loi
    // EFFECTS: parses investments from JSON object and adds them to loi
    private void addInvestments(ListOfInvestment loi, JSONObject jsonObject) {
    }

    // MODIFIES: loi
    // EFFECTS: parses investment from JSON object and adds to loi
    private void addInvestment(ListOfInvestment loi, JSONObject jsonObject) {
    }
}
