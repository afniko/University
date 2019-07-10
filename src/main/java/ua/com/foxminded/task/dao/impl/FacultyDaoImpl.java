package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.DepartmentDao;
import ua.com.foxminded.task.dao.FacultyDao;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Faculty;

public class FacultyDaoImpl implements FacultyDao {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private DepartmentDao departmentDao;

    @Override
    public boolean create(Faculty faculty) {
        int facultyId = faculty.getId();
        if (facultyId == 0 && findByTitle(faculty.getTitle()) == null) {
            insertFacultyRecord(faculty);
            faculty = setFacultyIdFromLastRecordInTable(faculty);
        }
        if (!faculty.getDepartments().isEmpty()) {
            createDepartmentRecords(faculty);
        }
        return true;
    }

    private void insertFacultyRecord(Faculty faculty) {
        String sql = "insert into faculties (title) values (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, faculty.getTitle());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private Faculty setFacultyIdFromLastRecordInTable(Faculty faculty) {
        String sql = "select id from faculties where id = (select max(id) from faculties)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int facultyId = resultSet.getInt("id");
                faculty.setId(facultyId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return faculty;
    }

    private void createDepartmentRecords(Faculty faculty) {
        departmentDao = new DepartmentDaoImpl();
        List<Department> departments = faculty.getDepartments();
        Iterator<Department> iteratorDepartment = departments.iterator();
        while (iteratorDepartment.hasNext()) {
            Department department = iteratorDepartment.next();
            if (departmentDao.findByTitle(department.getTitle()) == null) {
                departmentDao.create(department);
                department = departmentDao.findByTitle(department.getTitle());
                updateDepartmentRecordSetFacultyId(faculty, department);
            }
        }
    }

    private void updateDepartmentRecordSetFacultyId(Faculty faculty, Department department) {
        String sql = "update departments set faculty_id=? where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, faculty.getId());
            preparedStatement.setInt(2, department.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    @Override
    public Faculty findById(int id) {
        String sql = "select * from faculties where id=?";
        Faculty faculty = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                faculty = new Faculty();
                faculty.setId(resultSet.getInt("id"));
                faculty.setTitle(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        departmentDao = new DepartmentDaoImpl();
        List<Department> departments = faculty.getDepartments();
        departments.addAll(departmentDao.findByFacultyId(faculty.getId()));
        return faculty;
    }

    @Override
    public List<Faculty> findAll() {
        String sql = "select id from faculties";
        List<Integer> facultiesId = new ArrayList<>();
        List<Faculty> faculties = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                facultiesId.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        facultiesId.forEach(id -> faculties.add(findById(id)));
        return faculties;

    }

    @Override
    public Faculty findByTitle(String title) {
        String sql = "select id from faculties where title=?";
        int id = 0;
        Faculty faculty = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        if (id != 0) {
            faculty = findById(id);
        }
        return faculty;
    }

}
