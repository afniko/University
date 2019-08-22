package ua.com.foxminded.task.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.converter.ConverterToDtoService;

public class GroupServiceImpl implements GroupService {

    private GroupDao groupDao = new GroupDaoImpl();

    public GroupServiceImpl() {
    }

    public GroupServiceImpl(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public GroupDto findById(int id) {
        Group group = groupDao.findById(id);
        return ConverterToDtoService.convert(group);
    }

    @Override
    public List<GroupDto> findAll() {
        return groupDao.findAll().stream().map(ConverterToDtoService::convert).collect(Collectors.toList());
    }

    @Override
    public Group create(Group group) {
        return groupDao.create(group);
    }

    @Override
    public Group update(Group group) {
        return groupDao.update(group);
    }

}
