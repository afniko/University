package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Group;

public interface GroupDao extends CRUDDao<Group> {

    public List<Group> findByDepartmentId(int id);
}
