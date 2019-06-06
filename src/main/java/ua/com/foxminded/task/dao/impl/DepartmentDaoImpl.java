package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.DepartmentDao;
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.TeacherDao;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Teacher;

public class DepartmentDaoImpl implements DepartmentDao {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    GroupDao groupDao = new GroupDaoImpl();
    TeacherDao teacherDao = new TeacherDaoImpl();

    @Override
    public boolean create(Department department) {
        String sql = "insert into departments (title, description) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isCreate = false;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, department.getTitle());
            preparedStatement.setString(2, department.getDescription());
            // TODO add list Groups to table groups, add list teachers to table teachers
            isCreate = preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return isCreate;
    }

    @Override
    public Department findById(int id) {
        String sql = "select * from departments where id=?";
        Department department = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setTitle(resultSet.getString("title"));
                department.setDescription(resultSet.getString("description"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        int departmentId = department.getId();
        List<Group> groups = department.getGroups();
        groups.addAll(groupDao.findGroupsByDepartmentId(departmentId));
        List<Teacher> teachers = department.getTeachers();
        teachers.addAll(teacherDao.findTeachersByDepartmentId(departmentId));

        return department;
    }

    @Override
    public List<Department> findAll() {
        String sql = "select id from departments";
        List<Integer> departmentsId = new ArrayList<>();
        List<Department> departments = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                departmentsId.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        departmentsId.forEach(id -> departments.add(findById(id)));

        return departments;
    }

    @Override
    public Department findByTitle(String title) {
        String sql = "select * from departments where title=?";
        Department department = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setTitle(resultSet.getString("title"));
                department.setDescription(resultSet.getString("description"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        int departmentId = department.getId();
        List<Group> groups = department.getGroups();
        groups.addAll(groupDao.findGroupsByDepartmentId(departmentId));
        List<Teacher> teachers = department.getTeachers();
        teachers.addAll(teacherDao.findTeachersByDepartmentId(departmentId));

        return department;
    }

}
