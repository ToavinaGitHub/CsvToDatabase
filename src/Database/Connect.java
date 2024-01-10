package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static Connection con;
    static String jdbcUrl = "jdbc:postgresql://localhost:5432/csv_to_bdd";
    static String username = "postgres";
    static String password = "mdpProm15";

    public Connect() {
    }
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        return connection;
    }
}