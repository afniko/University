package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Teacher;

public interface TeacherDao {

    public boolean create(Teacher teacher);

    public Teacher findById(Teacher teacher);

    public List<Teacher> findAll();

    public Teacher findByIdFees(Teacher teacher);

    public List<Teacher> findByDepartmentId(Department department);
}
