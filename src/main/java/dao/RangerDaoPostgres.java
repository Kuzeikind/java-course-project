package dao;

import dao.domain.Ranger;
import dao.enums.RangerRank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RangerDaoPostgres extends AbstractDAO<Ranger> implements RangerDAO {

    protected Connection conn;

    RangerDaoPostgres(Connection connection){
        conn = connection;
    }

    @Override
    protected Ranger mapRow(ResultSet rs) {
        long id = 0;
        String firstName = null;
        String lastName = null;
        String email = null;
        RangerRank rank = null;

        try {
            id = rs.getLong("id");
            firstName = rs.getString(firstName);
            lastName = rs.getString(lastName);
            email = rs.getString(email);
            rank = RangerRank.values()[rs.getShort("rank")];

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
