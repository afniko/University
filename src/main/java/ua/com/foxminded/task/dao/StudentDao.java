package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Student;

public interface StudentDao extends CRUDDao<Student> {

    public Student findByIdFees(Student student);

    public Student findPersonIdByIdfees(Student student);

    public List<Student> findByGroupId(int id);
}
