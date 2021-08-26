package dao;

import dao.domain.AbstractEntity;
import exceptions.NotImplementedException;
import jdk.jshell.spi.ExecutionControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDAO<T extends AbstractEntity> {

    protected Connection conn = null;

    public AbstractDAO(Connection connection) {
        this.conn = connection;
    }

//    private String CREATE_SQL = null;
//    private String FIND_BY_ID_SQL = null;
//    private String DELETE_BY_ID_SQL = null;

    public String getCREATE_SQL() {
        throw new NotImplementedException("Method must be overridden");
    }

    public String getFIND_BY_ID_SQL() {
        throw new NotImplementedException("Method must be overridden");
    }

    public String getDELETE_BY_ID_SQL() {
        throw new NotImplementedException("Method must be overridden");
    }

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
        try (PreparedStatement stmt = conn.prepareStatement(getCREATE_SQL())) {
            stmt.executeUpdate();
        }
    }

    public T findById(long id) throws SQLException {
        T out = null;

        try (PreparedStatement stmt = conn.prepareStatement(getFIND_BY_ID_SQL())) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();

            out = mapRow(rs);

        }

        return out;
    }

    protected List<T> findMany(PreparedStatement stmt) throws SQLException {
        List<T> out;

        ResultSet rs = stmt.executeQuery();
        out = mapAll(rs);

        return out;
    }

//    public void updateById(long id, Object ... params) throws SQLException {
//        throw new RuntimeException("Update method not implemented");
//    }

    public void deleteById(long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(getDELETE_BY_ID_SQL())) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

}
