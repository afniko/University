package ua.com.foxminded.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.task.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
