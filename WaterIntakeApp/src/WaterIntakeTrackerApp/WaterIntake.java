package WaterIntakeTrackerApp;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Stack;

public class WaterIntake extends JFrame implements PropertyChangeListener {

    private JLabel titleLabel, dailyGoalLabel, waterIntakeLabel;
    private JProgressBar intakeProgressBar;
    private JButton button250ml, button500ml, button1000ml, addCustomIntakeButton, resetHistoryButton, undoButton, settingsButton;
    private JTextField customIntakeField;
    private JTable historyTable;
    private LinkedList<WaterIntakeEntry> history;
    private int totalIntake;
    private JTextField searchDateField;
    private JTextField searchAmountField;
    private JButton searchButton;
    private JLabel searchDateLabel;
    private JLabel searchAmountLabel;


    public WaterIntake() {
        initComponents();
        updateLabels(); 
        setupListeners();
        history = new LinkedList<>();
        totalIntake = 0;
    }

    private void updateLabels() {
        int dailyGoal = DailyGoal.getDailyGoal();
        dailyGoalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dailyGoalLabel.setText("You're Daily Goal: " + dailyGoal + "ml");
        waterIntakeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        waterIntakeLabel.setText("You're daily water intake: " + totalIntake + "ml/" + dailyGoal + "ml");
        intakeProgressBar.setMaximum(dailyGoal);
        intakeProgressBar.setValue(totalIntake);
    }
    private LinkedList<WaterIntakeEntry> searchHistory(Date date, Integer amount) {
        LinkedList<WaterIntakeEntry> filteredList = new LinkedList<>();

        for (WaterIntakeEntry entry : history) {
            boolean matchesDate = (date == null) || isSameDay(entry.getTimestamp(), date);
            boolean matchesAmount = (amount == null) || (entry.getAmount() == amount);

            if (matchesDate && matchesAmount) {
                filteredList.add(entry);
            }
        }

        return filteredList;
    }
    private boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date1).equals(sdf.format(date2));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("dailyGoal".equals(evt.getPropertyName()) || "dailyIntake".equals(evt.getPropertyName())) {
            updateLabels();
        }
    }

    private void setupListeners() {
        button250ml.addActionListener(e -> logWaterIntake(250));
        button500ml.addActionListener(e -> logWaterIntake(500));
        button1000ml.addActionListener(e -> logWaterIntake(1000));

        addCustomIntakeButton.addActionListener(e -> {
            try {
                int customAmount = Integer.parseInt(customIntakeField.getText());
                if (customAmount > 0) {
                    logWaterIntake(customAmount);
                    customIntakeField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a valid amount greater than 0.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a numeric value.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        resetHistoryButton.addActionListener(e -> {
            history.clear();
            totalIntake = 0;
            updateHistoryTable();
            updateLabels();
        });

        Stack<WaterIntakeEntry> undoStack = new Stack<>();

        undoButton.addActionListener(e -> {
            if (!history.isEmpty()) {
                WaterIntakeEntry lastEntry = history.removeLast();
                undoStack.push(lastEntry);
                totalIntake -= lastEntry.getAmount();
                updateHistoryTable();
                updateLabels();
            }
        });
    }

    private void logWaterIntake(int amount) {
        totalIntake += amount;
        history.add(new WaterIntakeEntry(new Date(), amount, totalIntake));
        updateHistoryTable();
        updateLabels();

        int dailyGoal = DailyGoal.getDailyGoal();
        if (totalIntake >= dailyGoal) {
            JOptionPane.showMessageDialog(this, "Congratulations! You have completed your daily goal!",
                    "Daily Goal Achieved", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateHistoryTable() {
        String[][] tableData = new String[history.size()][3];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < history.size(); i++) {
            WaterIntakeEntry entry = history.get(i);
            tableData[i][0] = sdf.format(entry.getTimestamp());
            tableData[i][1] = entry.getAmount() + "ml";
            tableData[i][2] = entry.getCumulativeTotal() + "ml";
        }

        historyTable.setModel(new javax.swing.table.DefaultTableModel(
                tableData,
                new String[]{"Date & Time", "Amount (ml)", "Total Amount (ml)"}
        ));
    }

    private static class WaterIntakeEntry {
        private final Date timestamp;
        private final int amount;
        private final int cumulativeTotal;

        public WaterIntakeEntry(Date timestamp, int amount, int cumulativeTotal) {
            this.timestamp = timestamp;
            this.amount = amount;
            this.cumulativeTotal = cumulativeTotal;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public int getAmount() {
            return amount;
        }

        public int getCumulativeTotal() {
            return cumulativeTotal;
        }
    }

    private void updateSearchResultsTable(LinkedList<WaterIntakeEntry> results) {
        String[][] tableData = new String[results.size()][3];
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        for (int i = 0; i < results.size(); i++) {
            WaterIntakeEntry entry = results.get(i);
            tableData[i][0] = sdf.format(entry.getTimestamp());
            tableData[i][1] = entry.getAmount() + "ml";
            tableData[i][2] = entry.getCumulativeTotal() + "ml";
        }

        historyTable.setModel(new javax.swing.table.DefaultTableModel(
                tableData,
                new String[]{"Time", "Amount (ml)", "Total Amount (ml)"}
        ));
    }

    private void initComponents() {
        setTitle("Water Intake Tracker");
        setSize(850, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new java.awt.Color(173, 216, 230));

        // Title Label
        titleLabel = new JLabel("Water Intake Tracker");
        titleLabel.setFont(new java.awt.Font("Century Schoolbook", java.awt.Font.BOLD, 28));
        titleLabel.setBounds(265, 20, 330, 50);
        add(titleLabel);

        // Daily Goal Label
        dailyGoalLabel = new JLabel("You're Daily Goal: 0ml");
        dailyGoalLabel.setBounds(310, 80, 200, 25);
        add(dailyGoalLabel);

        // Progress Bar
        intakeProgressBar = new JProgressBar();
        intakeProgressBar.setBounds(40, 120, 740, 25);
        intakeProgressBar.setValue(0); // Initial value
        add(intakeProgressBar);

        // Daily Water Intake Label
        waterIntakeLabel = new JLabel("You're daily water intake: 0ml/0ml");
        waterIntakeLabel.setBounds(290, 160, 250, 25);
        add(waterIntakeLabel);

        // Buttons for predefined amounts
        button250ml = new JButton("250ml");
        button250ml.setBounds(120, 200, 100, 30);
        add(button250ml);

        button500ml = new JButton("500ml");
        button500ml.setBounds(230, 200, 100, 30);
        add(button500ml);

        button1000ml = new JButton("1000ml");
        button1000ml.setBounds(340, 200, 100, 30);
        add(button1000ml);

        // Custom Intake Field and Add Button
        customIntakeField = new JTextField();
        customIntakeField.setBounds(500, 200, 100, 30);
        add(customIntakeField);

        addCustomIntakeButton = new JButton("Add");
        addCustomIntakeButton.setBounds(610, 200, 80, 30);
        add(addCustomIntakeButton);

        // History Table
        String[] columnNames = {"Time", "Amount (ml)", "Total Amount (ml)"};
        String[][] data = {};
        historyTable = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(historyTable);
        tableScrollPane.setBounds(40, 250, 740, 200);
        add(tableScrollPane);

        // Reset History Button
        resetHistoryButton = new JButton("Reset History");
        resetHistoryButton.setBounds(170, 550, 130, 30);
        add(resetHistoryButton);

        // Undo Button
        undoButton = new JButton("Undo");
        undoButton.setBounds(360, 550, 100, 30);
        add(undoButton);

        // Settings Button
        settingsButton = new JButton("Settings");
        settingsButton.setBounds(520, 550, 100, 30);
        settingsButton.addActionListener(e -> new Settings().setVisible(true));
        add(settingsButton);

        // Search Date Label
        searchDateLabel = new JLabel("Search Date:");
        searchDateLabel.setBounds(120, 480, 100, 25);
        add(searchDateLabel);

        // Search Date Field
        searchDateField = new JTextField();
        searchDateField.setBounds(200, 480, 100, 30);
        add(searchDateField);

        // Search Amount Label
        searchAmountLabel = new JLabel("Search Amount:");
        searchAmountLabel.setBounds(350, 480, 100, 25);
        add(searchAmountLabel);

        // Search Amount Field
        searchAmountField = new JTextField();
        searchAmountField.setBounds(460, 480, 100, 30);
        add(searchAmountField);

        // Search Button
        searchButton = new JButton("Search");
        searchButton.setBounds(580, 480, 100, 30);
        searchButton.addActionListener(e -> {
            try {
                Date date = null;
                if (!searchDateField.getText().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date = sdf.parse(searchDateField.getText());
                }

                Integer amount = null;
                if (!searchAmountField.getText().isEmpty()) {
                    amount = Integer.parseInt(searchAmountField.getText());
                }

                LinkedList<WaterIntakeEntry> results = searchHistory(date, amount);
                updateSearchResultsTable(results);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid search input. Please try again.",
                        "Search Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(searchButton);

    }
}
