package consoleui;

import dao.domain.Ranger;
import dao.domain.Task;
import exceptions.PasswordMismatchException;
import exceptions.TooManyTasksException;
import service.Controller;
import service.RangerManager;
import service.TaskManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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

    private void displayTask(Task task) {
        String formatted = String.format(
                ""
        );
    }

    private void displayTasks(List<Task> tasks) {
        tasks.stream().forEach(this::displayTask);
    }

    private void showBadInputMsg() {
        System.out.println("Bad input. Aborting command.");
    }

    protected boolean logIn() {
        boolean success = false;

        try {
            System.out.println("Enter email");
            String email = scanner.next();
            System.out.println("Enter password");
            String pwd = scanner.next();

            activeUser = rangerManager.logIn(email, pwd);
            success = true;
        } catch (RuntimeException rte) {
            showBadInputMsg();
        } catch (PasswordMismatchException pme) {
            System.out.println("Wrong password");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return success;
    }

    protected void seeInfo() {
        System.out.println("Not implemented");
    }

    protected void seeMostRecent() {
        List<Task> tasks = null;

        try {
            tasks = taskManager.seeMostRecent(activeUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        displayTasks(tasks);
    }

    protected void seeUrgent() {
        List<Task> tasks = null;

        try {
            tasks = taskManager.seeUrgent(activeUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        displayTasks(tasks);
    }

    protected void seeFinished() {}

    protected void seeUnassigned() {}

    protected void seeTaskDetails() {}

    protected void takeTask() {
        try {
            System.out.println("Enter task ID");
            long taskId = scanner.nextLong();
            taskManager.takeTask(activeUser, taskId);
        } catch(RuntimeException rte) {
            System.out.println("Bad input. Try again.");
        } catch(TooManyTasksException tmte) {
            showBadInputMsg();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }

    protected void approve() {
        try {
            System.out.println("Enter task ID");
            long taskId = scanner.nextLong();
            taskManager.approveTask(activeUser, taskId);
        } catch(RuntimeException rte) {
            System.out.println("Bad input. Try again.");
        } catch(TooManyTasksException tmte) {
            showBadInputMsg();
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }


}
