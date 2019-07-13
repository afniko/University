package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Department;

public interface DepartmentDao {

    public boolean create(Department department);

    public Department findById(Department department);

    public List<Department> findAll();

    public Department findByTitle(Department department);

    public List<Department> findByFacultyId(int id);
}
