package ir.maktab.jdbc.config;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceConfig {
    private static final MariaDbDataSource dataSource = new MariaDbDataSource();
    private static Connection connection ;
    static {
        try {
            dataSource.setUrl(DbConfig.URL);
            dataSource.setUser(DbConfig.USERNAME);
            dataSource.setPassword(DbConfig.PASSWORD);
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DataSourceConfig() {}

    public static Connection getInstance() throws SQLException {
        return connection;
    }

    public DataSource createDataSource() {
        return dataSource;
    }
}
