package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.Investment;
import model.ListOfInvestment;
import persistence.JsonReader;
import persistence.JsonWriter;

// GUI for investment application
// Credits to example code in project specification for a basic starting code structure
public class InvestmentUI extends JFrame implements ActionListener {
    private JLabel label; // labels for display
    private JTextField fieldDelete;
    private JTextField fieldFilter;

    private JTextField fieldName;
    private JTextField fieldType;
    private JTextField fieldAmount;
    private JTextField fieldReturn;
    private JTextField fieldDate;

    private JFrame frame; // frame for display
    private JFrame frameFilt;

    private JPanel panel; // panel for dosplay
    private JPanel panel1;
    private JPanel panelFilt;

    private ListOfInvestment loi; // list of investment
    private Investment inv;

    private static final String JSON_STORE = "./data/investments.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    public InvestmentUI() throws FileNotFoundException {

        JWindow windowWelcome = new JWindow();
        windowWelcome.getContentPane().add(new JLabel("Welcome! :)", SwingConstants.CENTER));
        windowWelcome.setBounds(575, 300, 300, 200);
        windowWelcome.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        windowWelcome.setVisible(false);
        windowWelcome.dispose();

        JWindow windowIcon = new JWindow();
        try {
            BufferedImage img = ImageIO.read(new File("/Users/jackwu/Desktop/download.jpg"));
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            windowIcon.getContentPane().add(imageLabel);
            windowIcon.setBounds(575, 300, 225, 200);
            windowIcon.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        windowIcon.setVisible(false);
        windowIcon.dispose();

        loi = new ListOfInvestment("Investments");
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 225, 225));

        JPanel panel = new JPanel();
        JButton btn1 = new JButton("Stats");
        btn1.setActionCommand("stat");
        btn1.addActionListener(this);
        JButton btn2 = new JButton("View");
        btn2.setActionCommand("view");
        btn2.addActionListener(this);
        JButton btn3 = new JButton("Save");
        btn3.setActionCommand("save");
        btn3.addActionListener(this);
        JButton btn4 = new JButton("Load");
        btn4.setActionCommand("load");
        btn4.addActionListener(this);
        JButton btn5 = new JButton("Quit");
        btn5.setActionCommand("quit");
        btn5.addActionListener(this);

        label = new JLabel("View statistics");
        panel.add(btn1);
        panel.add(label);
        label = new JLabel("View investments");
        panel.add(btn2);
        panel.add(label);
        label = new JLabel("Save investments");
        panel.add(btn3);
        panel.add(label);
        label = new JLabel("Load investments");
        panel.add(btn4);
        panel.add(label);

        panel.add(btn5);

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setPreferredSize(new Dimension(425, 130));
        panel.setBorder(BorderFactory.createTitledBorder("My Investments"));

        frame.pack();
        frame.getContentPane().add(panel);
        frame.setSize(600, 620);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    // EFFECTS: produces stats of current investments
    private void runStats() {
        frame = new JFrame("Statistics");
        panel = new JPanel();

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 225, 225));

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setPreferredSize(new Dimension(425, 130));
        panel.setBorder(BorderFactory.createTitledBorder("Statistics"));

        frame.pack();
        frame.getContentPane().add(panel);
        frame.setSize(600, 620);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        label = new JLabel("Mean: " + loi.summarize().get(0) + ", ");
        panel.add(label);
        label = new JLabel("Std: " + loi.summarize().get(1) + ", ");
        panel.add(label);
        label = new JLabel("Max: " + loi.summarize().get(2) + ", ");
        panel.add(label);
        label = new JLabel("Min: " + loi.summarize().get(3) + ", ");
        panel.add(label);
        label = new JLabel("Expected ret: " + loi.summarize().get(4));
        panel.add(label);

        JButton backBtn = new JButton("Back");
        panel.add(backBtn);
        backBtn.setActionCommand("back");
        backBtn.addActionListener(this);
    }

    // EFFECTS: runs view window
    private void runView() {
        frame = new JFrame("View");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0, 1, 4, 5));
        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel1.setPreferredSize(new Dimension(425, 130));
        panel1.setBorder(BorderFactory.createTitledBorder("Investments"));

        printInvs();

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(0, 1, 4, 5));
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setPreferredSize(new Dimension(425, 130));
        panel2.setBorder(BorderFactory.createTitledBorder("Add, Delete, Filter"));

        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);

        fieldType = new JTextField();
        JLabel lab1 = new JLabel("Type: ");
        fieldType = new JTextField(15);
        panel2.add(lab1);
        panel2.add(fieldType);

        fieldName = new JTextField();
        JLabel lab2 = new JLabel("Name: ");
        fieldName = new JTextField(15);
        panel2.add(lab2);
        panel2.add(fieldName);

        fieldAmount = new JTextField();
        JLabel lab3 = new JLabel("Invested Amount: ");
        fieldAmount = new JTextField(15);
        panel2.add(lab3);
        panel2.add(fieldAmount);

        fieldReturn = new JTextField();
        JLabel lab4 = new JLabel("Expected Return (as a decimal): ");
        fieldReturn = new JTextField(15);
        panel2.add(lab4);
        panel2.add(fieldReturn);

        fieldDate = new JTextField();
        JLabel lab5 = new JLabel("Date: ");
        fieldDate = new JTextField(15);
        panel2.add(lab5);
        panel2.add(fieldDate);

        JButton btn = new JButton("Add");
        panel2.add(btn);
        btn.setActionCommand("add");
        btn.addActionListener(this);

        fieldDelete = new JTextField();
        JLabel labDelete = new JLabel("Delete (Enter name key) :");
        fieldDelete = new JTextField(15);
        panel2.add(labDelete);
        panel2.add(fieldDelete);
        JButton btnDelete = new JButton("Delete");
        panel2.add(btnDelete);
        btnDelete.setActionCommand("del");
        btnDelete.addActionListener(this);

        fieldFilter = new JTextField();
        JLabel labFilter = new JLabel("Filter (Enter type key) :");
        fieldFilter = new JTextField(15);
        panel2.add(labFilter);
        panel2.add(fieldFilter);
        JButton btnFilter = new JButton("Filter");
        panel2.add(btnFilter);
        btnFilter.setActionCommand("filt");
        btnFilter.addActionListener(this);

        JButton backBtn = new JButton("Back");
        panel2.add(backBtn);
        backBtn.setActionCommand("back");
        backBtn.addActionListener(this);

        frame.pack();
        frame.setSize(600, 620);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: saves loi to file
    private void runSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(loi);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file:" + JSON_STORE);
        }
    }

    // EFFECTS: prints investments in loi
    private void printInvs() {
        for (Investment i : loi.getInvestments()) {
            String text = "Type: " + i.getType() + ", Name: " + i.getName() + ", Amount: "
                    + Double.toString(i.getAmount())
                    + ", Expected Return: " + Double.toString(i.getExpReturn()) + ", Date: " + i.getDate();
            JLabel investmentLabel = new JLabel(text);
            panel1.add(investmentLabel);
        }
    }

    // EFFECTS: prints investments in filtered loi
    private void printFilteredInvs() {
        loi.filter(fieldFilter.getText());

        for (Investment i : loi.getFilteredInvestments()) {
            String text = "Type: " + i.getType() + ", Name: " + i.getName() + ", Amount: "
                    + Double.toString(i.getAmount())
                    + ", Expected Return: " + Double.toString(i.getExpReturn()) + ", Date: " + i.getDate();
            JLabel investmentLabel = new JLabel(text);
            panelFilt.add(investmentLabel);

            loi.setFilteredInvestments(new ArrayList<Investment>());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads loi from file
    private void runLoad() {
        try {
            loi = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read investments from " + JSON_STORE);
        }
    }

    // EFFECTS: returns true if the field is a double
    private Boolean isDouble(JTextField f) {
        try {
            Double.parseDouble(f.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // This is the method that is called when the the JButton btn is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("quit")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("stat")) {
            runStats();
        } else if (e.getActionCommand().equals("bbb")) {
            frameFilt.dispose();
        } else if (e.getActionCommand().equals("back")) {
            frame.dispose();
        } else if (e.getActionCommand().equals("view")) {
            runView();
        } else if (e.getActionCommand().equals("save")) {
            runSave();
            popWindow("Saved!");
        } else if (e.getActionCommand().equals("load")) {
            runLoad();
            popWindow("Loaded!");
        } else if (e.getActionCommand().equals("del")) {

            if (fieldDelete.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "Must specify name of investment to be deleted.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!loi.getInvestmentNames().contains(fieldDelete.getText())) {
                JOptionPane.showMessageDialog(frame, "Name not present.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            loi.deleteInvestment(fieldDelete.getText());

            fieldDelete.setText("");

            panel1.removeAll();
            printInvs();
            panel1.revalidate();
            panel1.repaint();

        } else if (e.getActionCommand().equals("filt")) {
            if (fieldFilter.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "Must specify type of investment to be filtered for.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!loi.getInvestmentTypes().contains(fieldFilter.getText())) {
                JOptionPane.showMessageDialog(frame, "Type not present.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            frameFilt = new JFrame("Filtered");
            frameFilt.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frameFilt.setLayout(new BoxLayout(frameFilt.getContentPane(), BoxLayout.X_AXIS));

            panelFilt = new JPanel();
            panelFilt.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelFilt.setPreferredSize(new Dimension(425, 130));
            panelFilt.setBorder(BorderFactory.createTitledBorder("Investments"));
            frameFilt.getContentPane().add(panelFilt);

            printFilteredInvs();
            fieldFilter.setText("");

            JButton backBtn = new JButton("Back");
            panelFilt.add(backBtn);
            backBtn.setActionCommand("bbb");
            backBtn.addActionListener(this);

            frameFilt.pack();
            frameFilt.setSize(600, 620);
            frameFilt.setLocationRelativeTo(null);
            frameFilt.setVisible(true);

        } else if (e.getActionCommand().equals("add")) {

            if (fieldType.getText().equals("") || fieldName.getText().equals("") || fieldAmount.getText().equals("")
                    || fieldReturn.getText().equals("") || fieldDate.getText().equals("")) {
                JOptionPane.showMessageDialog(frame, "Must have an entry for all fields.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isDouble(fieldAmount)) {
                JOptionPane.showMessageDialog(frame, "Amount must be a double.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Double.parseDouble(fieldAmount.getText()) < 0) {
                JOptionPane.showMessageDialog(frame, "Amount must be a positive double.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isDouble(fieldReturn)) {
                JOptionPane.showMessageDialog(frame, "Return must be a double.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Double.parseDouble(fieldReturn.getText()) < 0) {
                JOptionPane.showMessageDialog(frame, "Return must be a positive double.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (loi.getInvestmentNames().contains(fieldName.getText())) {
                JOptionPane.showMessageDialog(frame, "Name already used.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            inv = new Investment(fieldType.getText(), fieldName.getText(), Double.parseDouble(fieldAmount.getText()),
                    Double.parseDouble(fieldReturn.getText()), fieldDate.getText());
            loi.add(inv);

            fieldType.setText("");
            fieldName.setText("");
            fieldAmount.setText("");
            fieldReturn.setText("");
            fieldDate.setText("");

            panel1.removeAll();
            printInvs();
            panel1.revalidate();
            panel1.repaint();
        }
    }

    // EFFECTS: pops window with given string message
    private void popWindow(String str) {
        JOptionPane.showMessageDialog(this, str, "Message", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            new InvestmentUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found...");
        }
    }

}
