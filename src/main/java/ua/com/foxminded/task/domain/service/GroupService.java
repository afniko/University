package ua.com.foxminded.task.domain.service;

import java.util.List;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;

public class GroupService {
    private GroupDao groupDao = new GroupDaoImpl();

    public Group findById(int id) {
        return groupDao.findById(id);
    }

    public List<Group> findAll() {
        return groupDao.findAll();
    }

}
