package exceptions;

import java.sql.SQLException;

public class PasswordMismatchException extends SQLException {
    public PasswordMismatchException(String msg) {
        super(msg);
    }
}
