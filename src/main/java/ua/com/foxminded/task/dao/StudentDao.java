package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Student;

public interface StudentDao {

    public boolean create(Student student);

    public Student findById(int id);

    public List<Student> findAll();

    public Student findByIdFees(int idFees);
}
