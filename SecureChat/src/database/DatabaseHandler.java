package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private Connection connection;

    public void connectToDB(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMessage(String sender, String message, boolean isSent) {
        String query = "INSERT INTO Messages (sender, message, is_sent) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, sender);
            stmt.setString(2, message);
            stmt.setBoolean(3, isSent);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getChatHistory(String user) {
        List<String> history = new ArrayList<>();
        String query = "SELECT sender, message, is_sent FROM Messages WHERE sender = ? ORDER BY timestamp";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String sender = rs.getString("sender");
                String message = rs.getString("message");
                boolean isSent = rs.getBoolean("is_sent");
                history.add((isSent ? "Sent: " : "Received: ") + sender + ": " + message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}