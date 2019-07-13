package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Student;

public interface StudentDao {

    public boolean create(Student student);

    public Student findById(Student student);

    public List<Student> findAll();

    public Student findByIdFees(Student student);
    
    public Student findPersonIdByIdfees(Student student);
    
    public List<Student> findByGroupId(int id);
}
