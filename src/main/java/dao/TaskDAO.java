package dao;

import dao.domain.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDAO {

    void create(Task task) throws SQLException;

    Task findById(long id) throws SQLException;

    List<Task> findRecent(long rangerId) throws SQLException;

    List<Task> findUrgent(long rangerId) throws SQLException;

    List<Task> findFinished(long rangerId, long limit) throws SQLException;

    List<Task> findUnassigned(long limit) throws SQLException;

    void updateById(long taskId, long rangerId) throws SQLException;

    int deleteById(long taskId) throws SQLException;

    int moveToHistoryById(long taskId) throws SQLException;

}
