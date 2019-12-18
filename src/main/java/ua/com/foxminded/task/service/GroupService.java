package ua.com.foxminded.task.service;

import java.util.List;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;

public interface GroupService {

    public Group findById(int id);

    public GroupDto findByIdDto(int id);

    public List<GroupDto> findAllDto();

    public GroupDto create(GroupDto groupDto);

    public GroupDto update(GroupDto groupDto);

    public Group findByTitle(String title);

}
