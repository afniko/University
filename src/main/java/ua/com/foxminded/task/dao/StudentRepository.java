package ua.com.foxminded.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.task.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
