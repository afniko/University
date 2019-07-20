package ua.com.foxminded.task.dao;

import java.util.List;

public interface CRUDDao<T> {

    public boolean create(T t);

    public T findById(int id);

    public List<T> findAll();
}
