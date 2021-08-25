package dao;

import dao.domain.AbstractEntity;
import dao.domain.Ranger;
import dao.domain.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T extends AbstractEntity> {

    protected Connection conn = null;

    private String CREATE_SQL = null;
    private String FIND_BY_ID_SQL = null;
    private String FIND_MANY_SQL = null;
    private String DELETE_BY_ID_SQL = null;

    protected T mapRow(ResultSet rs) throws SQLException {
        throw new RuntimeException("mapRow method not implemented");
    }

    protected List<T> mapAll(ResultSet rs) throws SQLException {
        List<T> out = new ArrayList<>();

        try {
            while (rs.next()) {
                out.add(mapRow(rs));
            }
        } catch (SQLException sqe) {
            throw new SQLException("Unable to map rows", sqe);
        }

        return out;
    }

    public void create(T t) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(CREATE_SQL)) {
            stmt.executeUpdate();
        }
    }

    public T findById(long id) throws SQLException {
        T out = null;

        try (PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_SQL)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            out = mapRow(rs);

        }

        return out;
    }

    protected List<T> findMany(PreparedStatement stmt) throws SQLException {
        List<T> out = new ArrayList<>();

        ResultSet rs = stmt.executeQuery();
        out = mapAll(rs);

        return out;
    }

//    public void updateById(long id, Object ... params) throws SQLException {
//        throw new RuntimeException("Update method not implemented");
//    }

    public void deleteById(long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID_SQL)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

}
