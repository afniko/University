package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Subject;

public interface SubjectDao {

    public boolean create(Subject subject);

    public Subject findById(int id);

    public List<Subject> findAll();

    public Subject findByTitle(String title);
}
