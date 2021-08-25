package exceptions;

import java.sql.SQLException;

public class LowRankException extends SQLException {
    public LowRankException(String msg) {
        super(msg);
    }
}
