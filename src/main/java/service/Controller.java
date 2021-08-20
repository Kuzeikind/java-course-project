package service;

import dao.DAOFactory;

import java.io.IOException;
import java.sql.SQLException;

public class Controller {

    private DAOFactory daoFactory;


    public Controller() throws IOException, SQLException {
        daoFactory = new DAOFactory();
    }

    public RangerManager getRangerManager() {
        return new RangerManager(daoFactory.getRangerDAO());
    }

    public TaskManager getTaskManager() {
        return new TaskManager(daoFactory.getTaskDAO());
    }

}
