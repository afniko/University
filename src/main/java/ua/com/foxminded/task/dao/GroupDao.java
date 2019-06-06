package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Group;

public interface GroupDao {

    public boolean create(Group group);

    public Group findById(int id);

    public List<Group> findAll();

    public Group findByTitle(String title);
    
    public List<Group> findGroupsByDepartmentId(int id);
}
