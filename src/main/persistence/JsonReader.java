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
        this.source = source;
    }

    // EFFECTS: reads list of investments from file and returns it
    // throws IOException if error occurs during reading
    public ListOfInvestment read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfInvestment(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses list of investment from JSON object and returns it
    private ListOfInvestment parseListOfInvestment(JSONObject jsonObject) {
        ListOfInvestment loi = new ListOfInvestment();
        addInvestments(loi, jsonObject);
        return loi;
    }

    // MODIFIES: loi
    // EFFECTS: parses investments from JSON object and adds them to loi
    private void addInvestments(ListOfInvestment loi, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("investments");
        for (Object json : jsonArray) {
            JSONObject nextObject = (JSONObject) json;
            addInvestment(loi, nextObject);
        }
    }
    

    // MODIFIES: loi
    // EFFECTS: parses investment from JSON object and adds to loi
    private void addInvestment(ListOfInvestment loi, JSONObject jsonObject) {
        String type = jsonObject.getString("type");
        String name = jsonObject.getString("name");
        int amount = jsonObject.getInt("amount");
        int expRet = jsonObject.getInt("expRet");
        String date = jsonObject.getString("date");
        
        Investment i = new Investment(type,name,amount,expRet,date);
        loi.add(i);
    }
}
