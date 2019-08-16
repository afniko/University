package ua.com.foxminded.task.service;

import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.impl.GroupDaoImpl;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.converter.ConverterToDtoService;

public class GroupService {
    private GroupDao groupDao = new GroupDaoImpl();

    public GroupDto findById(int id) {
        Group group = groupDao.findById(id);
        return ConverterToDtoService.convert(group);
    }

    public List<GroupDto> findAll() {
        return groupDao.findAll().stream().map(ConverterToDtoService::convert).collect(Collectors.toList());
    }

}
