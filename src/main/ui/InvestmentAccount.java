package ui;

import model.Investment;
import model.ListOfInvestment;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

// Investment manager application
public class InvestmentAccount {

    private Scanner input; // stores user input
    private ListOfInvestment listOfInvestment; // stores investments
    private List<String> checkRepeatName; // checks name repetition when adding investment
    private String name; // stores investment name

    // EFFECTS: initializes the app interface
    public InvestmentAccount() {
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
        listOfInvestment = new ListOfInvestment();
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
        } else {
            System.out.println("Invalid selection");
        }
    }

    // EFFECTS: displays options
    private void displayMenuOptions() {
        System.out.println("\nSelect from:");
        System.out.println("\ts -> stats");
        System.out.println("\tv -> view");
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

        System.out.println("Enter date of investment (in YYYY-MM-DD):");
        String date = input.next();

        Investment newInvestment = new Investment(type, name, amount, ret, date);
        listOfInvestment.add(newInvestment);
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

    // MODIFIES: thiS
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
        doView();
    }
}
