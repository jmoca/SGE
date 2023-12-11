package main.sge;

import java.sql.Connection;
import java.sql.SQLException;
import org.mariadb.jdbc.MariaDbDataSource;

public class ConexionPool {

    private static final String URL = "jdbc:mariadb://localhost:3306/sge";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static MariaDbDataSource dataSource;

    static {
        dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            dataSource.setUser(USER);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            dataSource.setPassword(PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection obtenerConexion() throws SQLException {
        return dataSource.getConnection();
    }
}
