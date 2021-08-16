package service;

import dao.*;

public class Controller {

    private Ranger activeRanger;
    private DAOFactory factory;
    private RangerDAO rangerDAO;
    private TaskDAO taskDAO;

    public Controller() {
        factory = new DAOFactory();
        rangerDAO = factory.getRangerDAO();
        taskDAO = factory.getTaskDAO();
    }

    /**
     * Logs in a ranger with an email and a password.
     */
    public void logIn(String email, String password) {};

    /**
     * Prints ranger's personal information.
     */
    public void seeInfo() {};

    /**
     * Prints ranger's tasks, showing most recently created tasks first.
     */
    public void seeMostRecent(long limit) {};

    /**
     * Prints ranger's tasks, showing those with the highest priority first.
     */
    public void seeUrgent(long limit) {};

    /**
     * Prints ranger's finished tasks.
     */
    public void seeFinished(long limit) {};

    /**
     * Prints unassigned tasks, so that ranger can pick one.
     * By default shows tasks with higher priority first.
     */
    public void seeUnassigned(long limit) {};

    /**
     * Assigns selected tasks to the ranger. If the ranger has 5 unfinished tasks, prints
     * error message and cancels the assignment.
     */
    public void takeTask(Task task) {};

    /**
     * Marks selected tasks as finished and removes it for ranger's list of tasks.
     * Only available for rangers of rank `LEAD`. For rangers of lower ranks prints error message and
     * cancels the assignment.
     */
    public void markAsFinished(Task task) {};


}
