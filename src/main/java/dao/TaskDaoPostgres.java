package dao;

import dao.domain.Task;
import dao.domain.enums.TaskPriority;
import dao.domain.enums.TaskType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TaskDaoPostgres extends AbstractDAO<Task> implements TaskDAO {

    TaskDaoPostgres(Connection connection) {
        super(connection);
    }

    private final String FIND_BY_ID_SQL = "SELECT * FROM task " +
            "WHERE id = ? ";
    private final String FIND_MANY_SQL = "SELECT * FROM task ";
    private final String FIND_MANY_BY_RANGER_SQL = FIND_MANY_SQL + "WHERE assigned_to = ? ";
    private final String FIND_FINISHED_BY_RANGER_SQL = "SELECT * FROM history " +
            "WHERE assigned_to = ? " +
            "ORDER BY created_at DESC " +
            "LIMIT ? ";
    private final String UPDATE_BY_ID_ASSIGN_SQL = "UPDATE task " +
            "SET assigned_to = ? " +
            "WHERE id = ? ";
    private final String MOVE_TO_HISTORY_BY_ID_SQL = "INSERT INTO history " +
            "SELECT * FROM task WHERE id = ? ";
    private final String DELETE_BY_ID_SQL = "DELETE FROM task " +
            "WHERE id = ? ";

    @Override
    public String getDeleteByIdSql() {
        return DELETE_BY_ID_SQL;
    }

    @Override
    public String getFindByIdSql() {
        return FIND_BY_ID_SQL;
    }

    @Override
    protected Task mapRow(ResultSet rs) throws SQLException {
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
            // TODO map enums properly.
            type = TaskType.values()[rs.getShort("type") - 1];
            priority = TaskPriority.values()[rs.getShort("priority") - 1];

            description = rs.getString("description");

            latitude = rs.getDouble("latitude");
            longitude = rs.getDouble("longitude");

            createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        } catch(SQLException sqe) {
            throw new SQLException("Unable to match row", sqe);
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

    public List<Task> findRecent(long rangerId) throws SQLException {
        List<Task> out = null;

        String SQL = FIND_MANY_BY_RANGER_SQL +
                "ORDER BY created_at DESC ";

        try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setLong(1, rangerId);

            out = findMany(stmt);
        }
        return out;
    }

    public List<Task> findUrgent(long rangerId) throws SQLException {
        List<Task> out = null;

        String SQL = FIND_MANY_BY_RANGER_SQL +
                "ORDER BY priority DESC";

        try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setLong(1, rangerId);

            out = findMany(stmt);
        }
        return out;
    }

    public List<Task> findFinished(long rangerId, long limit) throws SQLException {
        List<Task> out = null;

        try (PreparedStatement stmt = conn.prepareStatement(FIND_FINISHED_BY_RANGER_SQL)) {
            stmt.setLong(1, rangerId);
            stmt.setLong(2, limit);

            out = findMany(stmt);
        }
        return out;
    }

    public List<Task> findUnassigned(long limit) throws SQLException {
        List<Task> out = null;

        String SQL = FIND_MANY_SQL +
                "WHERE assigned_to IS NULL " +
                "LIMIT ? ";

        try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setLong(1, limit);

            out = findMany(stmt);
        }
        return out;
    }

    public void updateById(long taskId, long rangerId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_BY_ID_ASSIGN_SQL)) {
            stmt.setLong(1, taskId);
            stmt.setLong(2, rangerId);

            stmt.executeUpdate();
        }
    }

    public int moveToHistoryById(long taskId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(MOVE_TO_HISTORY_BY_ID_SQL)) {
            stmt.setLong(1, taskId);
            // TODO execute int one transaction.
            stmt.executeUpdate();
        }
        int out = deleteById(taskId);
        return out;
    }

}
