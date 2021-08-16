package dao;

import java.sql.Connection;

public class RangerDAO extends  AbstractDAO<Ranger> {

    private Connection conn;

    RangerDAO(Connection connection){
        conn = connection;
    }

    @Override
    public void create(Ranger ranger) {

    }

    @Override
    public Ranger find(long id) {
        return null;
    }

    @Override
    public void update(Ranger ranger) {

    }

    @Override
    public void delete(Ranger ranger) {

    }


}
