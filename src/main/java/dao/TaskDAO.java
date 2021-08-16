package dao;

import java.sql.Connection;

public class TaskDAO extends AbstractDAO<Task> {

    private Connection conn;

    public TaskDAO(Connection connection) {
        conn = connection;
    }

    @Override
    public void create(Task task) {

    }

    @Override
    public Task find(long id) {
        return null;
    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void delete(Task task) {

    }


}
