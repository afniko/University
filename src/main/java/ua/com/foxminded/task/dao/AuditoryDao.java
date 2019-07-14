package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Auditory;

public interface AuditoryDao {

    public boolean create(Auditory auditory);

    public Auditory findById(Auditory auditory);

    public List<Auditory> findAll();

    public Auditory findByNumber(Auditory auditory);

}
