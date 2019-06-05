package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Faculty;

public interface FacultyDao {

    public boolean create(Faculty faculty);

    public Faculty findById(int id);

    public List<Faculty> findAll();

    public Faculty findByTitle(String title);
}
