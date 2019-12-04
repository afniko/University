package ua.com.foxminded.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.task.domain.Group;

public interface GroupRepo extends JpaRepository<Group, Integer> {

}
