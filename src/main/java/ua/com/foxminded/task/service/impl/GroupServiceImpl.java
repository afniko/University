package ua.com.foxminded.task.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.foxminded.task.dao.GroupRepo;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.converter.ConverterToDtoService;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepo groupRepo;
    private Logger logger;

    @Autowired
    public GroupServiceImpl(Logger logger, GroupRepo groupRepo) {
        this.logger = logger;
        this.groupRepo = groupRepo;
    }

    @Override
    public Group findById(int id) {
        logger.debug("findById() [id:{}]", id);
        return groupRepo.getOne(id);
    }

    @Override
    public GroupDto findByIdDto(int id) {
        logger.debug("findById() [id:{}]", id);
        Group group = findById(id);
        return ConverterToDtoService.convert(group);
    }

    @Override
    public List<GroupDto> findAllDto() {
        logger.debug("findAllDto()");
        return groupRepo.findAll().stream().map(ConverterToDtoService::convert).collect(Collectors.toList());
    }

    @Override
    public GroupDto create(GroupDto groupDto) {
        logger.debug("create() [groupDto:{}]", groupDto);
        Group group = retriveGroupFromDto(groupDto);
        Group groupResult = groupRepo.save(group);
        return ConverterToDtoService.convert(groupResult);
    }

    @Override
    public GroupDto update(GroupDto groupDto) {
        logger.debug("update() [groupDto:{}]", groupDto);
        Group group = retriveGroupFromDto(groupDto);
        Group groupUpdated = groupRepo.save(group);
        return ConverterToDtoService.convert(groupUpdated);
    }

    private Group retriveGroupFromDto(GroupDto groupDto) {
        Group group = (groupDto.getId() != 0) ? groupRepo.getOne(groupDto.getId()) : new Group();
        group.setTitle(groupDto.getTitle());
        group.setYearEntry(groupDto.getYearEntry());
        return group;
    }
}
