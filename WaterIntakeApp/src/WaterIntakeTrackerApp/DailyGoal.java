package WaterIntakeTrackerApp;

public class DailyGoal {
    private static int dailyGoal = 0;  // Default goal (in ml)

    // Getters and setters
    public static int getDailyGoal() {
        return dailyGoal;
    }

    public static void setDailyGoal(int goal) {
        dailyGoal = goal;
    }
}

