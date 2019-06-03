package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Auditory;

public interface AuditoryDao {

    public Auditory create();

    public Auditory findById(long id);

    public List<Auditory> findAll();

}
