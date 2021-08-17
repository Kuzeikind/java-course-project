package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
    private Connection connection;
    private final String DBMS;
    private final String HOST_NAME;
    private final String PORT;
    private final String DATABASE;

    private Properties loadProperties() throws IOException {
        FileInputStream fin = new FileInputStream("ranger.properties");
        Properties settings = new Properties();
        settings.load(fin);
        return settings;

    }

    public DAOFactory() throws IOException, SQLException {
        Properties settings = loadProperties();

        DBMS = settings.getProperty("dbms");
        HOST_NAME = settings.getProperty("hostname");
        PORT = settings.getProperty("port");
        DATABASE = settings.getProperty("database");

        connection = DriverManager.getConnection(
                "jdbc:" + DBMS + "://"
                        + HOST_NAME + ":" + PORT + "/"
                        + DATABASE
        );
    }

    public RangerDAO getRangerDAO() {
        RangerDAO rangerDAO = new RangerDAO(connection);
        return rangerDAO;
    }

    public TaskDAO getTaskDAO() {
        TaskDAO taskDAO = new TaskDAO(connection);
        return taskDAO;
    }
}
