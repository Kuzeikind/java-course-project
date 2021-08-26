package service;

import dao.RangerDAO;
import dao.domain.Ranger;
import exceptions.PasswordMismatchException;

import java.sql.SQLException;

public class RangerManager {

    private RangerDAO rangerDAO;

    RangerManager(RangerDAO rangerDAO) {
        this.rangerDAO = rangerDAO;
    }

//    /**
//     * Returns information about ranger's tasks: [unfinished, finished].
//     */
//    public int[] seeInfo(Ranger ranger) {
//        return null;
//    };

    /**
     * Logs in a ranger with an email and a password.
     */
    public Ranger logIn(String email, String password) throws SQLException {
        Ranger user;
        try {
            user = rangerDAO.findByEmail(email);
        } catch (SQLException sqe) {
            throw new SQLException("User does not exit", sqe);
        }

        String actualPwd = rangerDAO.getPasswordById(user.getId());

        if (!password.equals(actualPwd)) {
            throw new PasswordMismatchException("Wrong password");
        }

        return user;
    };

}
