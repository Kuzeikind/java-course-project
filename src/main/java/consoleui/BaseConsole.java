package consoleui;

import dao.Ranger;
import service.Controller;
import service.RangerManager;
import service.TaskManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class BaseConsole {

    private Controller ctrl;
    private RangerManager rangerManager;
    private TaskManager taskManager;
    private Ranger activeUser = null;

    protected Scanner scanner;

    protected BaseConsole() throws IOException, SQLException {
        scanner = new Scanner(System.in);

        ctrl = new Controller();
        rangerManager = ctrl.getRangerManager();
        taskManager = ctrl.getTaskManager();
    }

    protected boolean logIn() {
        return false;
    }

    protected void seeInfo() {}

    protected void seeMostRecent() {}

    protected void seeUrgent() {}

    protected void seeFinished() {}

    protected void seeUnassigned() {}

    protected void seeTaskDetails() {}

    protected void takeTask() {}

    protected void markAsFinished() {}


}
