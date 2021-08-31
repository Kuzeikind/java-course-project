package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dao.domain.Ranger;
import dao.domain.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JsonMapper {

    private static Properties loadProperties() throws IOException {
        FileInputStream fin = new FileInputStream("src/test/resources/test.properties");
        Properties settings = new Properties();
        settings.load(fin);
        return settings;
    }

    private static Connection connection;
    private static String DBMS;
    private static String HOST_NAME;
    private static String PORT;
    private static String DATABASE;

    private static String TASKS_SQL = "SELECT * FROM task " +
            "UNION " +
            "SELECT * FROM history";
    private static String RANGERS_SQL = "SELECT * FROM ranger";

    private static String tasksPath = "src/test/resources/tasks/";
    private static String rangersPath = "src/test/resources/rangers/";
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        try {
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

            // Config the mapper.
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeAll() {
        try(Statement stmt = connection.createStatement()){

            ResultSet rs;
            Task t;
            Ranger r;

//            Tasks
            rs = stmt.executeQuery(TASKS_SQL);
            while(rs.next()) {
                t = RowMapper.mapTask(rs);
                writeTask(t);
            }

//            Rangers
            rs = stmt.executeQuery(RANGERS_SQL);
            while(rs.next()) {
                r = RowMapper.mapRanger(rs);
                writeRanger(r);
            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void writeRanger(Ranger r) throws IOException {
        mapper.writeValue(new File(rangersPath + "ranger" + r.getId() + ".json"), r);
    }

    private static void writeTask(Task t) throws IOException {
        mapper.writeValue(new File(tasksPath + "task" + t.getId() + ".json"), t);
    }

    public static void main(String[] args) {
        writeAll();
    }

}
