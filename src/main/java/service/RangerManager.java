package service;

import dao.DAOFactory;
import dao.Ranger;
import dao.RangerDAO;

public class RangerManager {

    private RangerDAO rangerDAO;

    RangerManager(RangerDAO rangerDAO) {
        this.rangerDAO = rangerDAO;
    }

    /**
     * Returns information about ranger's tasks: [unfinished, finished].
     */
    public int[] seeStatistics(Ranger ranger) {
        return null;
    };

    /**
     * Logs in a ranger with an email and a password.
     */
    public Ranger logIn(String email, String password) {
        return null;
    };

}
