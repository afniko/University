package ua.com.foxminded.task.domain.service;

import java.util.List;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;

public class GroupController {
    private static GroupDao groupDao = new GroupDaoImpl();

    private static GroupController instance;

    private GroupController() {
    }

    public Group findById(int id) {
        return groupDao.findById(id);
    }

    public List<Group> findAll() {
        return groupDao.findAll();
    }

    public synchronized static GroupController getInstance() {
        if (instance == null) {
            instance = new GroupController();
        }
        return instance;
    }

}
