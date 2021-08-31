package util;

import dao.domain.Ranger;
import dao.domain.Task;
import dao.domain.enums.RangerRank;
import dao.domain.enums.TaskPriority;
import dao.domain.enums.TaskType;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

public class RowMapper {

    public static Task mapTask(ResultSet rs) throws SQLException {
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

    public static Ranger mapRanger(ResultSet rs) throws SQLException {
        long id = 0;
        String firstName = null;
        String lastName = null;
        String email = null;
        RangerRank rank = null;

        try {
            id = rs.getLong("id");
            firstName = rs.getString("first_name");
            lastName = rs.getString("last_name");
            email = rs.getString("email");
            // TODO map enums properly.
            rank = RangerRank.values()[rs.getShort("rank") - 1];

        } catch (SQLException sqe) {
            throw new SQLException("Unable to map row", sqe);
        }

        Ranger ranger = new Ranger(
                id,
                firstName,
                lastName,
                email,
                rank
        );

        return ranger;
    }


}
