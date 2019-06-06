package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Teacher;

public interface TeacherDao {

    public boolean create(Teacher teacher);

    public Teacher findById(int id);

    public List<Teacher> findAll();

    public Teacher findByIdFees(int idFees);

    public List<Teacher> findTeachersByDepartmentId(int id);
}
