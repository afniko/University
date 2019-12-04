package ua.com.foxminded.task.dao;

import org.springframework.data.repository.Repository;

import ua.com.foxminded.task.domain.Group;

@org.springframework.stereotype.Repository
public interface GroupRepo extends Repository<Group, Integer> {

    Group findById(Integer id);
}
