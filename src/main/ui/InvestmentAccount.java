package ui;

import model.Investment;
import model.ListOfInvestment;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.Scanner;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Investment manager application
// Credits to sample TellerApp UI
// Credits to example JsonSerialization demo
public class InvestmentAccount {

    private Scanner input; // stores user input
    private ListOfInvestment listOfInvestment; // stores investments
    private List<String> checkRepeatName; // checks name repetition when adding investment
    private String name; // stores investment name
    private String date; // stores investment date

    private static final String JSON_STORE = "./data/investments.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: initializes the app interface
    public InvestmentAccount() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runAccount();
    }

    // MODIFIES: this
    // EFFECTS: runs the interface, dealing with human input
    private void runAccount() {
        Boolean continueRunning = true;
        String commandOne = null;

        init();

        while (continueRunning) {
            displayMenuOptions();
            commandOne = input.next().toLowerCase();

            if (commandOne.equals("q")) {
                continueRunning = false;
            } else {
                handleCommand(commandOne);
            }
        }

        System.out.println("\nSee you soon!");
    }

    // MODIFIES: this
    // EFFECTS: initializes fields
    private void init() {
        listOfInvestment = new ListOfInvestment("Jack's Investments");
        input = new Scanner(System.in);
        checkRepeatName = new ArrayList<String>();
        input.useDelimiter("\r?\n|\r");
    }

    // MODIFIES: this
    // EFFECTS: processes human commands
    private void handleCommand(String c) {
        if (c.equals("s")) {
            doStats();
        } else if (c.equals("v")) {
            doView();
            handleTwo();
        } else if (c.equals("save")) {
            saveInvestments();
        } else if (c.equals("load")) {
            loadInvestments();
        } else {
            System.out.println("Invalid selection.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes human inputs for secondary menu
    private void handleTwo() {
        while (true) {
            displayViewOptions();
            String commandTwo = input.next().toLowerCase();

            if (commandTwo.equals("a")) {
                doAddInvestment();
            } else if (commandTwo.equals("d")) {
                doDelete();
            } else if (commandTwo.equals("f")) {
                doFilter();
            } else if (commandTwo.equals("q")) {
                break;
            }
        }
    }

    // EFFECTS: displays options
    private void displayMenuOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\ts -> stats");
        System.out.println("\tv -> view");
        System.out.println("\tsave -> save investments to file");
        System.out.println("\tload -> load investments from file");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: display add,delete,filter options in view mode
    private void displayViewOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add");
        System.out.println("\td -> delete");
        System.out.println("\tf -> filter");
        System.out.println("\tq -> quit");

    }

    // MODIFIES: this
    // EFFECTS: display statistics
    private void doStats() {
        List<Double> stats = listOfInvestment.summarize();

        System.out.println("Mean:");
        System.out.println(stats.get(0));

        System.out.println("Standard deviation:");
        System.out.println(stats.get(1));

        System.out.println("Max:");
        System.out.println(stats.get(2));

        System.out.println("Min:");
        System.out.println(stats.get(3));

        System.out.println("Return:");
        System.out.println(stats.get(4));
    }

    // MODIFIES: this
    // EFFECTS: view all investments
    private void doView() {
        if (listOfInvestment.getNumInvestment() == 0) {
            System.out.println("\nNothing to see!");
        } else {
            listOfInvestment.getPrintedInvestments();
        }
    }

    // MODIFIES: this
    // EFFECTS: add investment
    private void doAddInvestment() {
        System.out.println("Enter type of investment:");
        String type = input.next();

        nameHelper();

        System.out.println("Enter amount invested:");
        double amount = input.nextDouble();
        while (amount < 0) {
            System.out.println("Can not investment negative dollars.");
            System.out.println("Enter amount invested:");
            amount = input.nextDouble();
        }
        System.out.println("Enter expected return (proportion between 0 and 1):");
        double ret = input.nextDouble();
        while (ret > 1 || ret < 0) {
            System.out.println("Must be between 0 and 1.");
            System.out.println("Enter expected return:");
            ret = input.nextDouble();
        }
        dateHelper();
        
        Investment newInvestment = new Investment(type, name, amount, ret, date);
        listOfInvestment.add(newInvestment);
    }

    // EFFECTS: get date in correct format from user
    private void dateHelper() {
        System.out.println("Enter date of investment (in YYYY-MM-DD):");
        date = input.next();
        while (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid form. Try again.");
            date = input.next();
        }
    }

    // EFFECTS: gets distinct name input from user
    private void nameHelper() {
        System.out.println("Enter name of investment:");
        name = input.next();

        while (checkRepeatName.contains(name)) {
            System.out.println("Name already used.");
            System.out.println("Enter name of investment:");
            name = input.next();
        }
        checkRepeatName.add(name);
    }

    // MODIFIES: this
    // EFFECTS: delete an investment, with confirmation
    private void doDelete() {
        System.out.println("Enter name of investment you would like to delete:");
        String commandThree = input.next();
        while (!(listOfInvestment.getInvestmentNames().contains(commandThree))) {
            System.out.println("Name not present. Try again.");
            commandThree = input.next();
        }

        System.out.println("Are you sure you would like to delete this investment? (y/n)");
        String commandFour = input.next().toLowerCase();

        while (!((commandFour.equals("y")) || (commandFour.equals("n")))) {
            System.out.println("Invalid selection. Try again.");
            commandFour = input.next().toLowerCase();
        }

        if (commandFour.equals("y")) {
            listOfInvestment.deleteInvestment(commandThree);
            doView();
        }
    }

    // MODIFIES: this
    // EFFECTS: filter investments
    private void doFilter() {
        String commandFour = null;
        System.out.println("Enter type of investment you would like to filter by:");
        commandFour = input.next();

        while (!(listOfInvestment.getInvestmentTypes().contains(commandFour))) {
            System.out.println("Type not present. Try again.");
            commandFour = input.next();
        }
        listOfInvestment.filter(commandFour);
        listOfInvestment.getPrintedFilteredInvestments();
    }

    // EFFECTS: saves loi to file
    private void saveInvestments() {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfInvestment);
            jsonWriter.close();
            System.out.println("Saved " + listOfInvestment.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file:" + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads loi from file
    private void loadInvestments() {
        try {
            listOfInvestment = jsonReader.read();
            System.out.println("Loaded investments from " + listOfInvestment.getName() + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read investments from " + JSON_STORE);
        }
    }
}