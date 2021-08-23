package service;

import dao.TaskDAO;
import dao.domain.Task;

public class TaskManager {

    private TaskDAO taskDAO;

    TaskManager(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }


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
