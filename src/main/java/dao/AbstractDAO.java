package dao;

public abstract class AbstractDAO<T> {

    public abstract void create(T t);

    public abstract T find(long id);

    public abstract void update(T t);

    public abstract void delete(T t);



}
