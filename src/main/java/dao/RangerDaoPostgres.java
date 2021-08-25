package dao;

import dao.domain.Ranger;
import dao.enums.RangerRank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RangerDaoPostgres extends AbstractDAO<Ranger> implements RangerDAO {

    protected Connection conn;

    private final String FIND_BY_EMAIL_SQL = "SELECT * FROM ranger " +
            "WHERE email = ? ";
    private final String GET_PASSWORD_BY_ID_SQL = "SELECT password FROM ranger_passwords " +
            "WHERE id = ? ";

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
            firstName = rs.getString("first_name");
            lastName = rs.getString("last_name");
            email = rs.getString("email");
            // TODO map enums properly.
            rank = RangerRank.values()[rs.getShort("rank") - 1];

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

    public Ranger findByEmail(String email) throws SQLException {
        Ranger out = null;

        try(PreparedStatement stmt = conn.prepareStatement(FIND_BY_EMAIL_SQL)){
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            out = mapRow(rs);
        }

        return out;
    }

    public String getPasswordById(long rangerId) throws SQLException {
        String password = null;

        try(PreparedStatement stmt = conn.prepareStatement(GET_PASSWORD_BY_ID_SQL)) {
            stmt.setLong(1, rangerId);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            password = rs.getString("password");
        }

        return password;
    }

}
