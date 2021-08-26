package consoleui;

import dao.domain.Ranger;
import dao.domain.Task;
import exceptions.LowRankException;
import exceptions.PasswordMismatchException;
import exceptions.TaskAlreadyTakenException;
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

    private String taskHeader = String.format(
            "|%-5s|%-10s|%-10s|%-21s|",
            "ID",
            "PRIORITY",
            "TYPE",
            "CREATED AT"
    );
    private String taskMultipleFormat = "|%-5d|%-10s|%-10s|%4$tH:%4$tM:%4$tS %4$tb %4$td, %4$tY|";
    private String taskSingleFormat = null;

    protected Scanner scanner;

    protected BaseConsole() throws IOException, SQLException {
        scanner = new Scanner(System.in);

        ctrl = new Controller();
        rangerManager = ctrl.getRangerManager();
        taskManager = ctrl.getTaskManager();
    }

    private void displayTaskHeader() {
        System.out.println(taskHeader);
        System.out.println("-".repeat(taskHeader.length()));

    }

    private void displayTask(Task task) {
        String formatted = String.format(
                taskMultipleFormat,
                task.getId(),
                task.getPriority(),
                task.getType(),
                task.getCreatedAt()
        );
        System.out.println(formatted);
    }

    private void displayTasks(List<Task> tasks) {
        displayTaskHeader();
        tasks.stream().forEach(this::displayTask);
    }

    private void showBadInputMsg() {
        System.out.println("Bad input. Aborting command.");
    }

    protected boolean logIn() {
        boolean success = false;

        try {
            System.out.println("Enter email:");
            String email = scanner.next();
            System.out.println("Enter password:");
            String pwd = scanner.next();

            activeUser = rangerManager.logIn(email, pwd);
            success = true;

            System.out.println(String.format(
                    "Successfully logged in as %s %s",
                    activeUser.getFirstName(),
                    activeUser.getLastName()
            ));
        } catch (RuntimeException rte) {
            showBadInputMsg();
            rte.printStackTrace();
        } catch (PasswordMismatchException pme) {
            System.out.println("Wrong password");
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage());
        }

        return success;
    }

    protected void seeInfo() {
        System.out.println("Not implemented");
    }

    protected void seeMostRecent() {

        try {
            List<Task> tasks = null;
            tasks = taskManager.seeMostRecent(activeUser);
            displayTasks(tasks);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void seeUrgent() {

        try {
            List<Task> tasks = null;
            tasks = taskManager.seeUrgent(activeUser);
            displayTasks(tasks);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void seeFinished() {

        try {
            List<Task> tasks = null;
            System.out.println("Enter a number of tasks to see:");
            long limit = Long.parseLong(scanner.next());

            tasks = taskManager.seeFinished(activeUser, limit);
            displayTasks(tasks);
        } catch (RuntimeException rte) {
            showBadInputMsg();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void seeUnassigned() {

        try {
            List<Task> tasks = null;
            System.out.println("Enter a number of tasks to see:");
            long limit = Long.parseLong(scanner.next());

            tasks = taskManager.seeUnassigned(limit);
            displayTasks(tasks);
        } catch (RuntimeException rte) {
            showBadInputMsg();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void seeTaskDetails() {
        //TODO implement proper formatting.
        try {
            System.out.println("Enter task ID:");
            long taskId = Long.parseLong(scanner.next());
            Task task = taskManager.seeTaskDetails(taskId);
            System.out.println(task);
        } catch(SQLException sqe) {
            sqe.printStackTrace();
        }
    }

    protected void takeTask() {
        try {
            System.out.println("Enter task ID:");
            long taskId = Long.parseLong(scanner.next());
            taskManager.takeTask(activeUser, taskId);
        } catch(RuntimeException rte) {
            showBadInputMsg();
        } catch(TaskAlreadyTakenException | TooManyTasksException ble) {
            System.out.println(ble.getMessage());
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }

    protected void approve() {
        // TODO TestCase: non-existent task
        try {
            System.out.println("Enter task ID:");
            long taskId = Long.parseLong(scanner.next());
            taskManager.approveTask(activeUser, taskId);
        } catch(RuntimeException rte) {
            showBadInputMsg();
        } catch(LowRankException lre) {
            System.out.println(lre.getMessage());;
        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }
    }


}
