package ui;

import model.EventLog;
import model.Event;
import model.ResultList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class MatchGUI extends JFrame implements ActionListener, WindowListener {
    private MatchLog matchLog;


    public MatchGUI() {
        super("Match Log");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        setButtons(panel);
        add(panel, BorderLayout.CENTER);
        this.matchLog = new MatchLog();
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/resources/Yugioh_Card_Back.jpg");
        setIconImage(icon.getImage());
        addWindowListener(this);

    }

    // EFFECTS: adds specified buttons to panel
    public void setButtons(JPanel panel) {
        addButton(panel, "Log Match");
        addButton(panel, "View Match Results");
        addButton(panel, "Save Match Results");
        addButton(panel, "Load Match Results");
        addButton(panel, "Exit");
    }

    // EFFECTS: creates new buttons
    public void addButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        panel.add(button);
    }


    // EFFECTS: makes the 5 main buttons on the home screen work as intended
    @Override
    public void actionPerformed(ActionEvent e) {
        String button = e.getActionCommand();
        switch (button) {
            case "Log Match":
                matchLog.logMatch();
                break;
            case "View Match Results":
                matchLog.resultsGraphic();
                break;
            case "Save Match Results":
                matchLog.saveResultList();
                break;
            case "Load Match Results":
                matchLog.loadResultList();
                break;
            default:
                dispose();
                break;

        }

    }

    public void printLog(EventLog el) {
        for (Event event : el) {
            System.out.println(event.getDescription());


        }
    }

    public static void main(String[] args) {
        new MatchGUI();

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {


        printLog(EventLog.getInstance());



    }

    @Override
    public void windowClosed(WindowEvent e) {


        printLog(EventLog.getInstance());





    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
