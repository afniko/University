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
    public GroupDto create(GroupDto groupDto) {
        Group group = retriveGroupFromDto(groupDto);
        Group groupResult = groupDao.create(group);
        return ConverterToDtoService.convert(groupResult);
    }

    @Override
    public GroupDto update(GroupDto groupDto) {
        Group group = retriveGroupFromDto(groupDto);
        Group groupUpdated = groupDao.update(group);
        return ConverterToDtoService.convert(groupUpdated);
    }

    private Group retriveGroupFromDto(GroupDto groupDto) {

        Group group = (groupDto.getId() != 0) ? groupDao.findById(groupDto.getId()) : new Group();
        group.setTitle(groupDto.getTitle());
        group.setYearEntry(groupDto.getYearEntry());

        return group;
    }
}
