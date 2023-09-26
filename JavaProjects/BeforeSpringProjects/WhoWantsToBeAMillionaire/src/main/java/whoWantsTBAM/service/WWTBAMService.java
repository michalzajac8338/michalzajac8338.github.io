package whoWantsTBAM.service;

import whoWantsTBAM.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WWTBAMService {
    private static final String url = "jdbc:sqlite:src\\main\\resources\\TOPTEN.db";
    public static void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("The driver was successfully loaded.");
        } catch (ClassNotFoundException e) {
            System.out.println("The driver class was not found in the program files at runtime.");
            System.out.println(e);
            System.exit(1);
        }
    }
    public static void testDatabaseConnection() {
        try (Connection connection = DriverManager.getConnection(url)) {
            System.out.println("The connection to the SQLite database was successful!");
        } catch (SQLException e) {
            System.out.println("The connection to the database was unsuccessful!");
            System.out.println(e);
        }
    }
    public static void resetDatabase() {
        try (Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("DROP TABLE BESTPLAYERS");
            System.out.println("The database has been reset!!");
            Thread.sleep(1000);
        } catch (SQLException | InterruptedException e) {
            System.out.println("The database was not reset.");
            System.out.println(e);
        }
    }
    public static void createTable() {
        String createTableStatement = "CREATE TABLE BESTPLAYERS ("
                + "NAME TEXT PRIMARY KEY,"
                + "RESULT INTEGER NOT NULL"
                + ");";

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement())
        {
            statement.executeUpdate(createTableStatement);
            System.out.println("The BESTPLAYERS table has been created.");
        } catch (SQLException  e) {
            System.out.println("There was an error with your request.");
            System.out.println(e);
        }
    }
    public static void saveResult(Player player) {
        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement())
        {
                String insertIntoBests = "INSERT INTO BESTPLAYERS VALUES (" +
                        "\"" + player.getName() + "\","
                        + player.getResult() + ");";
                statement.executeUpdate(insertIntoBests);
        } catch (SQLException e) {
            System.out.println("There was an error with your request.");
            System.out.println(e);
        }
    }

    public static List<Player> getTopTen() {
        List<Player> playerList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery("SELECT * FROM BESTPLAYERS ORDER BY RESULT DESC LIMIT 10;")
        ) {
            int place = 1;
            while (results.next()) {
                String name = results.getString(1);
                int result = results.getInt(2);
                playerList.add(new Player(place, name, result));
                place++;
            }
        } catch (SQLException e) {
            System.out.println("There was an error with your request.");
            System.out.println(e);
        }

        return playerList;
    }
}