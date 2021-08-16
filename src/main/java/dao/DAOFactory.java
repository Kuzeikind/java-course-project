package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DAOFactory {
    private Connection connection;
    private final String dbms;
    private final String hostName;
    private final String port;
    private final String database;

    private Properties loadProperties() throws IOException {
        FileInputStream fin = new FileInputStream("ranger.properties");
        Properties settings = new Properties();
        settings.load(fin);
        return settings;

    }

    public DAOFactory() throws IOException, SQLException {
        Properties settings = loadProperties();

        dbms = settings.getProperty("dbms");
        hostName = settings.getProperty("hostname");
        port = settings.getProperty("port");
        database = settings.getProperty("database");

        connection = DriverManager.getConnection(
                "jdbc:" + dbms + "://"
                        + hostName + ":" + port + "/"
                        + database
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
