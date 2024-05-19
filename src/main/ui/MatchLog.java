package ui;

import model.exceptions.MatchNotFoundException;
import model.Result;
import model.ResultList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.awt.Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

// Application where the user can log the results of their matches
public class MatchLog {
    private Scanner input;
    private ResultList matchResults;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/resultlist.json";
    private Date matchDate1;
    private String stringDate;


    // EFFECTS: runs the match result application
    public MatchLog() {
        matchResults = new ResultList("My Matches");
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        matchDate1 = new Date();
        stringDate = new String();

    }

    // MODIFIES: this
    // EFFECTS: checks if the user wants to cancel, returns to main menu if they do
//    public String cancelInput(String resultField) {
//        System.out.println(resultField + ":");
//        String userInput = input.nextLine();
//        if (userInput.equalsIgnoreCase("cancel")) {
//            try {
//                runMatchLog();
//            } catch (MatchNotFoundException e) {
//                System.out.println("Match ID not found!");
//                input.nextLine();
//            }
//        }
//        return userInput;
//
//    }


    // EFFECTS: sets the format of the date
    public String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        return formatter.format(date);
    }

    // EFFECTS: prompts the user to enter the date of the match
    public void enterDate() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "MM/dd/yyyy");
        spinner.setEditor(editor);
        int result = JOptionPane.showConfirmDialog(null, spinner, "Enter Date:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            matchDate1 = (Date) spinner.getValue();
            stringDate = formatDate(matchDate1);
        }
    }

    // EFFECTS: prompts the user to select the result of the match
    public String askMatchResult() {
        int option = JOptionPane.showOptionDialog(null, "Select Match Result:", "Match Result",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Win", "Loss", "Draw"}, "Win");
        if (option == JOptionPane.CLOSED_OPTION) {
            return null;
        }
        switch (option) {
            case 0:
                return "Win";
            case 1:
                return "Loss";
            default:
                return "Draw";
        }

    }

    // EFFECTS: prompts the user to enter whether they won the die roll or not
    public boolean askRoll() {
        int option1 = JOptionPane.showOptionDialog(null, "Won die roll?", "Die Roll",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Yes", "No"}, "Yes");
        return option1 == JOptionPane.YES_OPTION;
    }

    // EFFECTS: sets the parameters for the chart displayed
    private JFreeChart makeChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createStackedBarChart(
                "", "", "", dataset);
        chart.getLegend().setVisible(false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer render = (BarRenderer) plot.getRenderer();
        render.setSeriesPaint(0, Color.green);
        render.setSeriesPaint(1, Color.red);
        render.setSeriesPaint(2, Color.blue);
        return chart;

    }

    // MODIFIES: this, ResultList
    // EFFECTS: logs details of a match with the user's inputs
    public void logMatch() {
        enterDate();
        String matchResult = askMatchResult();
        boolean roll = askRoll();
//        JSpinner spinner = new JSpinner(new SpinnerDateModel());
//        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "MM/dd/yyyy");
//        spinner.setEditor(editor);
//        int result = JOptionPane.showConfirmDialog(null, spinner, "Enter Date:", JOptionPane.OK_CANCEL_OPTION);
//        Date matchDate1;
//        String stringDate;
//        if (result == JOptionPane.OK_OPTION) {
//            matchDate1 = (Date) spinner.getValue();
//            stringDate = formatDate(matchDate1);
//        } else {
//            return;
//        }

//        int option = JOptionPane.showOptionDialog(null, "Select Match Result:", "Match Result",
//                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
//                new String[]{"Win", "Loss", "Draw"}, "Win");
//        if (option == JOptionPane.CLOSED_OPTION) {
//            return;
//        }
//        String matchResult;
//        switch (option) {
//            case 0:
//                matchResult = "Win";
//                break;
//            case 1:
//                matchResult = "Loss";
//                break;
//            default:
//                matchResult = "Draw";
//                break;
////        }
//        int option1 = JOptionPane.showOptionDialog(null, "Won die roll?", "Die Roll",
//                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
//                new String[]{"Yes", "No"}, "Yes");
//        boolean roll = false;
//        if (option1 == 0) {
//            roll = true;
//        }
        String deck = JOptionPane.showInputDialog(null, "Deck");
        if (deck == null) {
            return;
        }
        String oppDeck = JOptionPane.showInputDialog(null, "Opponent's deck");
        if (oppDeck == null) {
            return;
        }
        String name = JOptionPane.showInputDialog(null, "Opponent");
        if (name == null) {
            return;
        }
        Result newResult = new Result(stringDate, roll, matchResult, deck, oppDeck, name);
        matchResults.addResult(newResult);

        int choice = JOptionPane.showConfirmDialog(
                null, "Match logged successfully! Log another?", "Log Match", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            logMatch();
        }

    }
    // MODIFIES: this, ResultList
    // EFFECTS: removes match from list of matches
    //          throws MatchNotFoundException if user tries to remove a match not in the list of matches
    //          catches InputMatchException if user inputs a non-integer

    public void removeMatchResult() throws MatchNotFoundException {
        System.out.println("Enter the match ID of the match you want to remove:");
        try {
            int inputID = input.nextInt();
            if (inputID < 1 || inputID > matchResults.getListSize()) {
                throw new MatchNotFoundException();
            }
            matchResults.removeResult(inputID - 1);
            System.out.println("Match removed!\nRemove another? (yes/no)");
            input.nextLine();
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("yes")) {
                removeMatchResult();
            } else {
                displayMenu();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Try Again: ");
            input.nextLine();
        } catch (MatchNotFoundException e) {
            System.out.println("Match ID not found!");
            input.nextLine();

        }
    }

    // MODIFIES: this
    // EFFECTS: gives users 6 options and processes user input
    //          catches MatchNotFoundException when user tries to remove a match not in the list in removeMatchResult()
    public void runMatchLog() throws MatchNotFoundException {
        boolean inputting = true;
        while (inputting) {
            displayMenu();
            String choice = input.nextLine();
            if (choice.equals("1")) {
                logMatch();
            } else if (choice.equals("2")) {
                matchResults.viewMatchResults();
                input.nextLine();
            } else if (choice.equals("3")) {
                removeMatchResult();
            } else if (choice.equals("4")) {
                offerToSave();
                inputting = false;
            } else if (choice.equals("5")) {
                saveResultList();
            } else if (choice.equals("6")) {
                loadResultList();
            } else {
                System.out.println("Invalid selection, please try again");
            }
        }
    }

    // EFFECTS: offers user to save results upon exiting program
    public void offerToSave() {
        System.out.println("Would you like to save your results? (yes/no)");
        String option = input.nextLine().toLowerCase();
        if (option.equals("yes")) {
            saveResultList();
            System.out.println("Results saved!");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("1. Log Match");
        System.out.println("2. View Match Results");
        System.out.println("3. Remove Match");
        System.out.println("4. Quit");
        System.out.println("5. Save Match Results");
        System.out.println("6. Load Match Results");

    }

    // EFFECTS: saves the ResultList to file
    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public void saveResultList() {
        try {
            jsonWriter.open();
            jsonWriter.write(matchResults);
            jsonWriter.close();
            System.out.println("Saved " + matchResults.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        matchResults.saveLog();
    }

    // MODIFIES: this
    // EFFECTS: loads ResultList from file
    public void loadResultList() {
        try {
            matchResults = jsonReader.read();
            System.out.println("Loaded " + matchResults.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        matchResults.loadLog();
    }

    // EFFECTS: displays results in a chart format
    public void resultsGraphic() {
        JFrame frame = new JFrame("Match Results");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("src/resources/Yugioh_Card_Back.jpg");
        frame.setIconImage(icon.getImage());

        DefaultTableModel defaultTable = new DefaultTableModel(
                new String[]{"Date", "Result", "Won Die Roll", "Deck", "Opponent's Deck", "Opponent"},
                0);

        for (Result result : matchResults.getResults()) {
            Object[] rowData = {result.getDate(), result.getMatchResult(), result.getRoll() ? "Yes" : "No",
                    result.getDeckType(), result.getOppDeckType(), result.getOppName()};
            defaultTable.addRow(rowData);
        }

        JTable table = new JTable(defaultTable);
        JScrollPane scrollPane = new JScrollPane(table);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(defaultTable);
        table.setRowSorter(sorter);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
        rightClickFunction(table, defaultTable);
        matchResults.viewMatchResults();


    }

    // EFFECTS: gives right click functionality to results table in "View Match Results"
    private void rightClickFunction(JTable table, DefaultTableModel defaultTable) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int[] selectedRows = table.getSelectedRows();
                    ArrayList<Result> selectedResults = new ArrayList<>();
                    for (int row : selectedRows) {
                        selectedResults.add(matchResults.getResults().get(row));
                    }
                    if (selectedRows.length > 0) {
                        showPopupMenu(table, defaultTable, e, selectedRows, selectedResults);
                       // JPopupMenu menu = new JPopupMenu();

                    //    JMenuItem removeMatch = new JMenuItem("Delete");
                    //    removeMatch.addActionListener((action) -> {
                    //       removeRows(defaultTable, selectedRows);
                    //        for (Result result: selectedResults) {
                     //           matchResults.removeChartResult(result);
                            }
                    //    });
                    //    JMenuItem stats = new JMenuItem("Statistics");
                    //    stats.addActionListener((action) -> {
                    //        showStats(defaultTable,selectedRows);
                    //    });
                        // menu.add(removeMatch);
                      //  menu.add(stats);
                      //  menu.show(table, e.getX(), e.getY());
                    }
                }
         //   }
        });

    }

    // EFFECTS: creates popup menu to be shown in "View Results"
    private void showPopupMenu(JTable table, DefaultTableModel defaultTable, MouseEvent e,
                               int[] selectedRows, ArrayList<Result> selectedResults) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem removeMatch = new JMenuItem("Delete");
        removeMatch.addActionListener((action) -> {
            removeRows(defaultTable, selectedRows);
            for (Result result: selectedResults) {
                matchResults.removeChartResult(result);
            }
        });
        JMenuItem stats = new JMenuItem("Statistics");
        stats.addActionListener((action) -> {
            showStats(defaultTable,selectedRows);
        });
        menu.add(removeMatch);
        menu.add(stats);
        menu.show(table, e.getX(), e.getY());

    }

    // EFFECTS: creates frame for statistics
    private JFrame setFrame() {
        JFrame statsFrame = new JFrame("Statistics");
        statsFrame.setSize(400, 300);
        statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        statsFrame.setLocationRelativeTo(null);
        statsFrame.setVisible(true);
        ImageIcon icon = new ImageIcon("src/resources/Yugioh_Card_Back.jpg");
        statsFrame.setIconImage(icon.getImage());
        return statsFrame;
    }

    // EFFECTS: calculates and updates number of wins, losses, and draws for selected matches
    private int[] calculateStats(DefaultTableModel defaultTable, int[] selectedRows) {
        int[] stats = new int[4];
        for (int row : selectedRows) {
            String result = (String) defaultTable.getValueAt(row, 1);
            if (result.equals("Win")) {
                stats[0]++;
            } else {
                if (result.equals("Loss")) {
                    stats[1]++;
                } else {
                    stats[2]++;
                }
            }


        }

        for (int row : selectedRows) {
            String diceResult = (String) defaultTable.getValueAt(row, 2);
            if (diceResult.equals("Yes")) {
                stats[3]++;
            }
        }

        return stats;
    }

    // EFFECTS: creates constraints for grid
    private GridBagConstraints createConstraints() {
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.weightx = 1;
        g.weighty = 1;
        g.gridy = 0;
        return g;
    }

    // MODIFIES: panel
    // EFFECTS: positions and adds labels to panel
    private void addLabelsToPanel(JPanel panel, JLabel[] labels) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.fill = GridBagConstraints.HORIZONTAL;
        labelConstraints.weightx = 1;
        labelConstraints.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < labels.length; i++) {
            labelConstraints.gridy = i + 1;
            panel.add(labels[i], labelConstraints);
        }
    }

    // EFFECTS: creates new JPanel with set dimensions
    private JPanel createJPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(300, 500));
        return panel;

    }

    // EFFECTS: creates a JLabel with given font
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 16));
        return label;
    }

    // EFFECTS: creates JLabel array specifically for use in showStats
    private JLabel[] createLabels(int wins, int losses, int draws, double winRate, double dieRollWinRate) {
        JLabel label = createLabel("Win Percentage: " + winRate + "%");
        JLabel diceLabel = createLabel("Dice Roll Win Percentage: " + dieRollWinRate + "%");
        JLabel winsLabel = createLabel("Wins: " + wins);
        JLabel lossesLabel = createLabel("Losses: " + losses);
        JLabel drawsLabel = createLabel("Draws: " + draws);
        return new JLabel[]{label, diceLabel, winsLabel, lossesLabel, drawsLabel};
    }

    // EFFECTS: creates new dataset with 3 parameters
    private DefaultCategoryDataset createDataset(int wins, int losses, int draws) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(wins, "Wins", "Wins");
        dataset.addValue(losses, "Losses", "Losses");
        dataset.addValue(draws, "Draws", "Draws");
        return dataset;
    }


    // EFFECTS: creates information/bar graph for "Statistics" option when right-clicking result(s)
    private void showStats(DefaultTableModel defaultTable, int[] selectedRows) {
        JFrame statsFrame = setFrame();
        int[] stats = calculateStats(defaultTable,selectedRows);
        int matches = selectedRows.length;
        int wins = stats[0];
        int losses = stats[1];
        int draws = stats[2];
        int diceRollsWon = stats[3];

        double winRate = ((double) wins / matches) * 100;
        winRate = Double.parseDouble(String.format("%.2f", winRate));
        double dieRollWinRate = ((double) diceRollsWon / matches) * 100;
        dieRollWinRate = Double.parseDouble(String.format("%.2f", dieRollWinRate));
        JLabel[] labels = createLabels(wins, losses, draws, winRate, dieRollWinRate);
        DefaultCategoryDataset dataset = createDataset(wins, losses, draws);
        JFreeChart chart = makeChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        JPanel contentPanel = createJPanel();
        GridBagConstraints g = createConstraints();
        contentPanel.add(chartPanel, g);
        addLabelsToPanel(contentPanel, labels);

        statsFrame.setContentPane(contentPanel);
        statsFrame.pack();
        statsFrame.setVisible(true);
    }

    // EFFECTS: gives the option to remove rows (results) from results table
    private void removeRows(DefaultTableModel defaultTable, int[] selectedRows) {
        int response = JOptionPane.showConfirmDialog(
                null, "Do you want to delete the selected matches?",
                "Delete Rows", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                defaultTable.removeRow(selectedRows[i]);
            }
        }

    }


}

