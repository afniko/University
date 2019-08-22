package ua.com.foxminded.task.service;

import java.util.List;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.dto.GroupDto;

public interface GroupService {

    public GroupDto findById(int id);

    public List<GroupDto> findAll();

    public Group create(Group group);

    public Group update(Group group);

}
