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
    private DepartmentDao departmentDao = new DepartmentDaoImpl();

    @Override
    public boolean create(Faculty faculty) {
        String sqlInsertFaculty = "insert into faculties (title) values (?)";
        String sqlRequestId = "select id from faculties where title=?";
        String sqlUpdateDepartment = "update departments set faculty_id=? where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isCreate = false;
        int facultyId = 0;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sqlInsertFaculty);
            preparedStatement.setString(1, faculty.getTitle());
            isCreate = preparedStatement.execute();

            daoFactory.closePreparedStatement(preparedStatement);

            preparedStatement = connection.prepareStatement(sqlRequestId);
            preparedStatement.setString(1, faculty.getTitle());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                facultyId = resultSet.getInt("id");
            }

            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);

            if (!faculty.getDepartments().isEmpty()) {
                List<Department> departments = faculty.getDepartments();
                Iterator<Department> iteratorDepartment = departments.iterator();
                while (iteratorDepartment.hasNext()) {
                    int departmentId = iteratorDepartment.next().getId();
                    preparedStatement = connection.prepareStatement(sqlUpdateDepartment);
                    preparedStatement.setInt(1, facultyId);
                    preparedStatement.setInt(2, departmentId);
                    isCreate = preparedStatement.execute();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return isCreate;
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

        Faculty faculty = findById(id);
        return faculty;
    }

}
