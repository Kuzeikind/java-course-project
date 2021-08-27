import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;

public class DatabaseSetup {

    private static Connection connection;
    private static String DBMS;
    private static String HOST_NAME;
    private static String PORT;
    private static String DATABASE;

    public static void setUp() throws IOException, SQLException {
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
        setupDB();
        connection.close();
    }

    private static Properties loadProperties() throws IOException {
        FileInputStream fin = new FileInputStream("src/test/resources/test.properties");
        Properties settings = new Properties();
        settings.load(fin);
        return settings;

    }

    private static String getInitFile() throws IOException {
        return new String(Files.readAllBytes(Path.of("src/test/resources/testDB.sql")));
    }

    private static void setupDB() throws IOException {
        String sql = getInitFile();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
