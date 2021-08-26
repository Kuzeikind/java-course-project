package service;

import dao.TaskDAO;
import dao.domain.Ranger;
import dao.domain.Task;
import dao.enums.RangerRank;
import exceptions.LowRankException;
import exceptions.TaskAlreadyTakenException;
import exceptions.TooManyTasksException;

import java.sql.SQLException;
import java.util.List;

public class TaskManager {

    private TaskDAO taskDAO;
    private static final int MAX_ACTIVE_TASKS = 5;

    TaskManager(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }


    /**
     * Prints ranger's tasks, showing most recently created tasks first.
     */
    public List<Task> seeMostRecent(Ranger ranger) throws SQLException {
        return taskDAO.findRecent(ranger.getId());
    }

    /**
     * Prints ranger's tasks, showing those with the highest priority first.
     */
    public List<Task> seeUrgent(Ranger ranger) throws SQLException {
        return taskDAO.findUrgent(ranger.getId());
    }

    /**
     * Prints ranger's finished tasks.
     */
    public List<Task> seeFinished(Ranger ranger, long limit) throws SQLException {
        return taskDAO.findFinished(ranger.getId(), limit);
    }

    /**
     * Prints unassigned tasks, so that ranger can pick one.
     * By default shows tasks with higher priority first.
     */
    public List<Task> seeUnassigned(long limit) throws SQLException {
        return taskDAO.findUnassigned(limit);
    }

    public Task seeTaskDetails(long taskId) throws SQLException {
        return taskDAO.findById(taskId);
    }

    /**
     * Assigns selected tasks to the ranger. If the ranger has 5 unfinished tasks, prints
     * error message and cancels the assignment.
     */
    public void takeTask(Ranger ranger, long taskId) throws SQLException {
        if (taskDAO.findById(taskId).getAssignedTo() != 0) {
            throw new TaskAlreadyTakenException("Task is already assigned to a ranger");
        }

        if (seeMostRecent(ranger).size() == MAX_ACTIVE_TASKS) {
            throw new TooManyTasksException("Ranger already has too many tasks");
        }

        taskDAO.updateById(ranger.getId(), taskId);
    }

    /**
     * Marks selected tasks as finished and removes it for ranger's list of tasks.
     * Only available for rangers of rank `LEAD`. For rangers of lower ranks prints error message and
     * cancels the assignment.
     */
    public void approveTask(Ranger ranger, long taskId) throws SQLException {
        if (ranger.getRangerRank() != RangerRank.LEAD) {
            throw new LowRankException("Only rangers with rank LEAD can approve tasks");
        }
        taskDAO.moveToHistoryById(taskId);
    }

}
