package ua.com.foxminded.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.task.domain.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    public Group findByTitle(String title);

}
