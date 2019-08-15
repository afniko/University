package ua.com.foxminded.task.service;

import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.dto.GroupDto;

public class GroupService {
    private GroupDao groupDao = new GroupDaoImpl();

    public GroupDto findById(int id) {
        return groupDao.findById(id).convertToDto();
    }

    public List<GroupDto> findAll() {
        return groupDao.findAll().stream().map(g -> g.convertToDto()).collect(Collectors.toList());
    }

}
