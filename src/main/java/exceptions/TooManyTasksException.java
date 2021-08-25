package exceptions;

import java.sql.SQLException;

public class TooManyTasksException extends SQLException {
    public TooManyTasksException(String msg) {
        super(msg);
    }
}
