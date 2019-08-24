package ua.com.foxminded.task.service;

import java.util.List;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;

public interface GroupService {

    public Group findById(int id);

    public GroupDto findByIdDto(int id);

    public List<GroupDto> findAllDto();

    public Group create(Group group);

    public GroupDto update(Group group);

}
