package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Group;

public interface GroupDao {

    public boolean create(Group group);

    public Group findById(Group group);

    public List<Group> findAll();

    public Group findByTitle(Group group);
    
    public List<Group> findByDepartmentId(int id);
}
