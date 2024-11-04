package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.ListOfInvestment;

// GUI for investment application
// Credits to example code in project specification for a basic starting code structure
public class InvestmentUI extends JFrame implements ActionListener {
    private JLabel label;  // labels for display
    private JTextField field;  // fields for display
    private JFrame frame;  // frame for display 
    private JPanel panel; // panel for dosplay
    private ListOfInvestment loi; // list of investment

    public InvestmentUI() {
        super("My investment account");

        loi = new ListOfInvestment("Investments");

        JWindow window = new JWindow();
        window.getContentPane().add(new JLabel("Welcome! :)", SwingConstants.CENTER));
        window.setBounds(575,300,300,200);
        window.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();


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

        JButton btn = new JButton("Back");
        panel.add(btn);
        btn.setActionCommand("back");
        btn.addActionListener(this);
    }

    // EFFECTS: runs view window
    private void runView() {
    }

    // EFFECTS: runs save
    private void runSave() {
    }

    // EFFECTS: runs load
    private void runLoad() {
    }


    // This is the method that is called when the the JButton btn is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("quit")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("stat")) {
            runStats();
        } else if (e.getActionCommand().equals("back")) {
            frame.dispose();
        } else if (e.getActionCommand().equals("view")) {
            runView();
        } else if (e.getActionCommand().equals("save")) {
            runSave();
        } else if (e.getActionCommand().equals("load")) {
            runLoad();
        }
    }

    

    public static void main(String[] args) {
        new InvestmentUI();
    }

}
