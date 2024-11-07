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

    private JTextField fieldDelete; // deleting key
    private JTextField fieldFilter; // filtering key

    private JTextField fieldName; // name key
    private JTextField fieldType; // type key
    private JTextField fieldAmount; // amount key
    private JTextField fieldReturn; // return key
    private JTextField fieldDate; // date key

    private JFrame frame; // frame
    private JFrame frameFilt; // filter frame 

    private JPanel panel; // display panel
    private JPanel panel1; // left view page
    private JPanel panel2; // right view page
    private JPanel panelFilt; // filtered investments panel

    private JWindow windowWelcome; // welcome window popup
    private JWindow windowIcon; // icon window popup

    private JButton btn1; // button on main menu
    private JButton btn2; // button on main menu
    private JButton btn3; // button on main menu
    private JButton btn4; // button on main menu
    private JButton btn5; // button on main menu

    private ListOfInvestment loi; // list of investment
    private Investment inv; // new investment

    private static final String JSON_STORE = "./data/investments.json"; // file save destination
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE); // writer object
    private JsonReader jsonReader = new JsonReader(JSON_STORE); // reader object

    // EFFECTS: start application
    public InvestmentUI() throws FileNotFoundException {
        welcomePopUpHelper();
        logoPopUpHelper();

        loi = new ListOfInvestment("Investments");
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 225, 225));

        initializeButtonsHelper();

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setPreferredSize(new Dimension(425, 130));
        panel.setBorder(BorderFactory.createTitledBorder("My Investments"));

        frame.pack();
        frame.getContentPane().add(panel);
        frame.setSize(600, 620);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: produce welcome popup
    private void welcomePopUpHelper() {
        windowWelcome = new JWindow();
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
    }

    // EFFECTS: produce logo popup
    private void logoPopUpHelper() {
        windowIcon = new JWindow();
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
    }

    // EFFECTS: initialize buttons for main menu
    private void initializeButtonsHelper() {
        panel = new JPanel();

        makeButtons();

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
    }

    // EFFECTS: make buttons for main menu
    private void makeButtons() {
        btn1 = new JButton("Stats");
        btn1.setActionCommand("stat");
        btn1.addActionListener(this);

        btn2 = new JButton("View");
        btn2.setActionCommand("view");
        btn2.addActionListener(this);

        btn3 = new JButton("Save");
        btn3.setActionCommand("save");
        btn3.addActionListener(this);

        btn4 = new JButton("Load");
        btn4.setActionCommand("load");
        btn4.addActionListener(this);

        btn5 = new JButton("Quit");
        btn5.setActionCommand("quit");
        btn5.addActionListener(this);
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

        provideStats();

        JButton backBtn = new JButton("Back");
        panel.add(backBtn);
        backBtn.setActionCommand("back");
        backBtn.addActionListener(this);
    }

    // EFFECTS: print summary statistics for loi
    private void provideStats() {
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
        initializeFrameAndTwoPanels();
        initializeFields();

        makeAddButton();

        makeDeleteOption();
        makeFilterOption();

        JButton backBtn = new JButton("Back");
        panel2.add(backBtn);
        backBtn.setActionCommand("back");
        backBtn.addActionListener(this);

        frame.pack();
        frame.setSize(600, 620);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // EFFECTS: construct an add button
    private void makeAddButton() {
        JButton btn = new JButton("Add");
        panel2.add(btn);
        btn.setActionCommand("add");
        btn.addActionListener(this);
    }

    // EFFECTS: initialize fields for view window
    private void initializeFields() {
        fieldType = new JTextField();
        fieldType = new JTextField(15);
        panel2.add(new JLabel("Type: "));
        panel2.add(fieldType);

        fieldName = new JTextField();
        fieldName = new JTextField(15);
        panel2.add(new JLabel("Name: "));
        panel2.add(fieldName);

        fieldAmount = new JTextField();
        fieldAmount = new JTextField(15);
        panel2.add(new JLabel("Invested Amount: "));
        panel2.add(fieldAmount);

        fieldReturn = new JTextField();
        fieldReturn = new JTextField(15);
        panel2.add(new JLabel("Expected Return (as a decimal): "));
        panel2.add(fieldReturn);

        fieldDate = new JTextField();
        fieldDate = new JTextField(15);
        panel2.add(new JLabel("Date: "));
        panel2.add(fieldDate);
    }

    // EFFECTS: make delete button and delete label
    private void makeDeleteOption() {
        JLabel labDelete = new JLabel("Delete (Enter name key) :");
        fieldDelete = new JTextField(15);
        panel2.add(labDelete);
        panel2.add(fieldDelete);
        JButton btnDelete = new JButton("Delete");
        panel2.add(btnDelete);
        btnDelete.setActionCommand("del");
        btnDelete.addActionListener(this);
    }

    // EFFECTS: make filter button and filter label
    private void makeFilterOption() {
        JLabel labFilter = new JLabel("Filter (Enter type key) :");
        fieldFilter = new JTextField(15);
        panel2.add(labFilter);
        panel2.add(fieldFilter);
        JButton btnFilter = new JButton("Filter");
        panel2.add(btnFilter);
        btnFilter.setActionCommand("filt");
        btnFilter.addActionListener(this);
    }

    // EFFECTS: initialize frame and two panels
    private void initializeFrameAndTwoPanels() {
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(0, 1, 4, 5));
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setPreferredSize(new Dimension(425, 130));
        panel2.setBorder(BorderFactory.createTitledBorder("Add, Delete, Filter"));

        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
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
        } else if (e.getActionCommand().equals("load")) {
            runLoad();
        } else if (e.getActionCommand().equals("del")) {
            runDelete();
        } else if (e.getActionCommand().equals("filt")) {
            runFilter();
        } else if (e.getActionCommand().equals("add")) {
            runAdd();
        }
    }

    // EFFECTS: run filter operations
    private void runFilter() {
        if (checkValidFilter() == null) {
            return;
        }
        initializeFilterDisplay();
    }

    // EFFECTS: run delete operations
    private void runDelete() {
        if (checkValidDelete() == null) {
            return;
        }
        loi.deleteInvestment(fieldDelete.getText());
        fieldDelete.setText("");
        refreshPanelOne();
    }

    // EFFECTS: runs add operations
    private void runAdd() {
        if (checkValidityOfInvestmentFields() == null) {
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

        resetFields();
        refreshPanelOne();
    }

    // EFFECTS: saves loi to file
    private void runSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(loi);
            jsonWriter.close();
            popWindow("Saved!");
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

    // MODIFIES: this
    // EFFECTS: loads loi from file
    private void runLoad() {
        try {
            loi = jsonReader.read();
            popWindow("Loaded!");
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

    // EFFECTS: refresh panel1
    private void refreshPanelOne() {
        panel1.removeAll();
        printInvs();
        panel1.revalidate();
        panel1.repaint();
    }

    // EFFECTS: check that fieldDelete has an entry
    private String checkValidDelete() {
        if (fieldDelete.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Must specify name of investment to be deleted.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!loi.getInvestmentNames().contains(fieldDelete.getText())) {
            JOptionPane.showMessageDialog(frame, "Name not present.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return "filler";
    }

    // EFFECTS: check that fieldFilter has an entry
    private String checkValidFilter() {
        if (fieldFilter.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Must specify type of investment to be filtered for.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!loi.getInvestmentTypes().contains(fieldFilter.getText())) {
            JOptionPane.showMessageDialog(frame, "Type not present.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return "filler";
    }

    // EFFECTS: check that entered amount and return fields are doubles and positive
    private String checkValidityOfInvestmentFields() {
        if (nonEmpty() == null) {
            return null;
        }

        if (!isDouble(fieldAmount)) {
            JOptionPane.showMessageDialog(frame, "Invested Amount must be a double.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (Double.parseDouble(fieldAmount.getText()) < 0) {
            JOptionPane.showMessageDialog(frame, "Invested Amount must be a positive double.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!isDouble(fieldReturn)) {
            JOptionPane.showMessageDialog(frame, "Expected Return must be a double.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (Double.parseDouble(fieldReturn.getText()) < 0) {
            JOptionPane.showMessageDialog(frame, "Expected Return must be a positive double.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return "filler";
    }

    // EFFECTS: check that fields are non-empty
    private String nonEmpty() {
        if (fieldType.getText().equals("") || fieldName.getText().equals("") || fieldAmount.getText().equals("")
                || fieldReturn.getText().equals("") || fieldDate.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Must have an entry for all fields.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return "filler";
    }

    // EFFECTS: reset fields after valid entry has been made
    private void resetFields() {
        fieldType.setText("");
        fieldName.setText("");
        fieldAmount.setText("");
        fieldReturn.setText("");
        fieldDate.setText("");
    }

    // EFFECTS: initialize filter frame and panel and print filtered investments
    private void initializeFilterDisplay() {
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

    // EFFECTS: pops window with given string message
    private void popWindow(String str) {
        JOptionPane.showMessageDialog(this, str, "Message", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: start application
    public static void main(String[] args) {
        try {
            new InvestmentUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found...");
        }
    }

}
