package ui;

import java.io.FileNotFoundException;

// Credits to example JsonSerialization demo
public class Main {
    public static void main(String[] args) {
        try {
            new InvestmentAccount();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found...");
        }
    }
}
