package ua.com.foxminded.task.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import ua.com.foxminded.task.dao.GroupRepository;
import ua.com.foxminded.task.dao.exception.EntityAlreadyExistsException;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.converter.ConverterToDtoService;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private Logger logger;

    @Autowired
    public GroupServiceImpl(Logger logger, GroupRepository groupRepository) {
        this.logger = logger;
        this.groupRepository = groupRepository;
    }

    @Override
    public Group findById(int id) {
        logger.debug("findById() [id:{}]", id);
        return groupRepository.getOne(id);
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
        return groupRepository.findAll().stream().map(ConverterToDtoService::convert).collect(Collectors.toList());
    }

    @Override
    public GroupDto create(GroupDto groupDto) {
        logger.debug("create() [groupDto:{}]", groupDto);
        Group group = retriveGroupFromDto(groupDto);
        Group groupResult = null;
        try {
            groupResult = groupRepository.save(group);
        } catch (DataIntegrityViolationException e) {
            logger.warn("create() [group:{}], exception:{}", group, e);
            throw new EntityAlreadyExistsException("create() group: " + group, e);
        }
        return ConverterToDtoService.convert(groupResult);
    }

    @Override
    public GroupDto update(GroupDto groupDto) {
        logger.debug("update() [groupDto:{}]", groupDto);
        Group group = retriveGroupFromDto(groupDto);
        Group groupUpdated = null;
        try {
            groupUpdated = groupRepository.save(group);
        } catch (DataIntegrityViolationException e) {
            logger.warn("update() [group:{}], exception:{}", group, e);
            throw new EntityAlreadyExistsException("update() group: " + group, e);
        }
        return ConverterToDtoService.convert(groupUpdated);
    }

    private Group retriveGroupFromDto(GroupDto groupDto) {
        Group group = (groupDto.getId() != 0) ? groupRepository.getOne(groupDto.getId()) : new Group();
        group.setTitle(groupDto.getTitle());
        group.setYearEntry(groupDto.getYearEntry());
        return group;
    }
}
