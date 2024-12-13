package WaterIntakeTrackerApp;

public class Settings extends javax.swing.JFrame {

    public Settings() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Settings");
        setSize(400, 300);
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new java.awt.Color(173, 216, 230));

        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Settings");
        titleLabel.setFont(new java.awt.Font("Century Schoolbook", java.awt.Font.BOLD, 24));
        titleLabel.setBounds(150, 20, 115, 30);
        add(titleLabel);

        javax.swing.JLabel dailyGoalLabel = new javax.swing.JLabel("Daily Goal (ml):");
        dailyGoalLabel.setBounds(50, 80, 100, 25);
        add(dailyGoalLabel);

        javax.swing.JTextField dailyGoalField = new javax.swing.JTextField();
        dailyGoalField.setBounds(160, 80, 150, 25);
        add(dailyGoalField);

        javax.swing.JButton resetButton = new javax.swing.JButton("Reset Daily Goal");
        resetButton.setBounds(50, 150, 140, 30);
        resetButton.addActionListener(e -> dailyGoalField.setText("0"));
        add(resetButton);

        javax.swing.JButton saveButton = new javax.swing.JButton("Save Settings");
        saveButton.setBounds(210, 150, 140, 30);
        saveButton.addActionListener(e -> {
            String dailyGoalText = dailyGoalField.getText();
            try {
                int dailyGoal = Integer.parseInt(dailyGoalText);
                DailyGoal.setDailyGoal(dailyGoal); // Update the daily goal in DailyGoal class
                dispose();
                new WaterIntake().setVisible(true);
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });
        add(saveButton);

        javax.swing.JButton logoutButton = new javax.swing.JButton("Logout");
        logoutButton.setBounds(130, 210, 140, 30);
        logoutButton.addActionListener(e -> {
            java.awt.Window[] windows = java.awt.Window.getWindows();
            for (java.awt.Window window : windows) {
                if (window instanceof WaterIntake || window instanceof Settings) {
                    window.dispose();
                }
            }
            new MainActivity().setVisible(true);
        });
        add(logoutButton);
    }
}
