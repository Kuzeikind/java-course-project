package dao;

import java.sql.Connection;

public class DAOFactory {
    private Connection connection;

    public RangerDAO getRangerDAO() {
        RangerDAO rangerDAO = new RangerDAO(connection);
        return rangerDAO;
    }

    public TaskDAO getTaskDAO() {
        TaskDAO taskDAO = new TaskDAO(connection);
        return taskDAO;
    }
}
