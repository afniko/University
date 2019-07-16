package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.TimetableItem;

public interface TimetableItemDao {

    public boolean create(TimetableItem timetableItem);

    public TimetableItem findById(TimetableItem timetableItem);

    public List<TimetableItem> findAll();
}
