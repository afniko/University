package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Group;

public interface GroupDao extends CRUDDao<Group> {

    public Group findByIdNoBidirectional(int id);

    public List<Group> findByDepartmentId(int id);
}
