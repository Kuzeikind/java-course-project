package exceptions;

import dao.domain.Task;

import java.sql.SQLException;

public class TaskNotAssignedException extends SQLException {
    public TaskNotAssignedException(String msg) {
        super(msg);
    }
}
