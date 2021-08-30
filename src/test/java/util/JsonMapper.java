package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.domain.Ranger;
import dao.domain.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    private static String TASKS_SQL = "SELECT * FROM task";
    private static String RANGERS_SQL = "SELECT * FROM ranger";

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
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static String tasksPath = "src/test/resources/tasks/";
    private static String rangersPath = "src/test/resources/rangers/";
    private static ObjectMapper mapper = new ObjectMapper();

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
