package dao;

import dao.domain.Task;
import dao.enums.TaskPriority;
import dao.enums.TaskType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TaskDaoPostgres extends AbstractDAO<Task> implements TaskDAO {

    protected Connection conn;

    public TaskDaoPostgres(Connection connection) {
        conn = connection;
    }

    private final String FIND_BY_ID_SQL = "SELECT * FROM task " +
            "WHERE id = ? ";
    private final String FIND_MANY_SQL = "SELECT * FROM task ";

    @Override
    protected Task mapRow(ResultSet rs) {
        long id = 0;
        long assignedTo = 0;
        TaskType type = null;
        TaskPriority priority = null;
        String description = null;
        double latitude = 0;
        double longitude = 0;
        LocalDateTime createdAt = null;

        try {
            id = rs.getLong("id");
            assignedTo = rs.getLong("assigned_to");

            type = TaskType.values()[rs.getShort("task_type")];
            priority = TaskPriority.values()[rs.getShort("task_priority")];

            description = rs.getString("description");

            latitude = rs.getDouble("latitude");
            longitude = rs.getDouble("longitude");

            createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Task task = new Task(
                id,
                assignedTo,
                type,
                priority,
                description,
                latitude,
                longitude,
                createdAt
        );

        return task;
    }
}
