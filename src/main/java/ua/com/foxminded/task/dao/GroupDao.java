package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Group;

public interface GroupDao extends CRUDDao<Group> {

    public Group findByTitle(Group group);

    public List<Group> findByDepartmentId(Department department);

    public List<Group> getGroupsById(List<Integer> groupsId);
}
