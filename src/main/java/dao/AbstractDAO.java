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
    private String UPDATE_BY_ID_SQL = null;
    private String DELETE_BY_ID_SQL = null;

    protected T mapRow(ResultSet rs) {
        throw new RuntimeException("mapRow method not implemented");
    }

    protected List<T> mapAll(ResultSet rs) {
        List<T> out = new ArrayList<>();

        try {
            while (rs.next()) {
                out.add(mapRow(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return out;
    }

    public void create(T t){
        try (PreparedStatement stmt = conn.prepareStatement(CREATE_SQL)) {
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public T findById(long id) {
        T out = null;

        try (PreparedStatement stmt = conn.prepareStatement(FIND_BY_ID_SQL)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

             out = mapRow(rs);

        } catch (SQLException sqe) {
            sqe.printStackTrace();
        }

        return out;
    }

    public void updateById(T t) {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_BY_ID_SQL)) {
            stmt.setLong(1, t.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteById(T t) {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID_SQL)) {
            stmt.setLong(1, t.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



}
