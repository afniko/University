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
    public Group findById(int id) {
        return groupDao.findById(id);
    }

    @Override
    public GroupDto findByIdDto(int id) {
        Group group = findById(id);
        return ConverterToDtoService.convert(group);
    }

    @Override
    public List<GroupDto> findAllDto() {
        return groupDao.findAll().stream().map(ConverterToDtoService::convert).collect(Collectors.toList());
    }

    @Override
    public Group create(Group group) {
        return groupDao.create(group);
    }

    @Override
    public GroupDto update(Group group) {
        Group groupUpdated = groupDao.update(group);
        return ConverterToDtoService.convert(groupUpdated);
    }

}
