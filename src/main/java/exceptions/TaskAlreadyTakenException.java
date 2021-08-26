package exceptions;

import java.sql.SQLException;

public class TaskAlreadyTakenException extends SQLException {
    public TaskAlreadyTakenException(String msg) {
        super(msg);
    }
}
