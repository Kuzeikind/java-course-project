package dao;

import dao.domain.Ranger;

import java.sql.SQLException;

public interface RangerDAO {

    String getPasswordById(long rangerId) throws SQLException;

    Ranger findById(long id) throws SQLException;

    Ranger findByEmail(String email) throws SQLException;

}
