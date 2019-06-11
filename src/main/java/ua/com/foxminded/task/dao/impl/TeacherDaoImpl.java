package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.DepartmentDao;
import ua.com.foxminded.task.dao.TeacherDao;
import ua.com.foxminded.task.domain.Teacher;

public class TeacherDaoImpl implements TeacherDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private DepartmentDao departmentDao = new DepartmentDaoImpl();

    @Override
    public boolean create(Teacher teacher) {
        String sqlInsertPerson = "insert into persons (first_name, last_name, middle_name, birthday, idfees) values (?, ?, ?, ?, ?)";
        String sqlRequestId = "select id persons where idfees=?";
        String sqlInsertTeacher = "insert into teachers (person_id, department_id) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isCreate = false;
        int idTeacher = 0;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sqlInsertPerson);
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setString(3, teacher.getMiddleName());
            preparedStatement.setDate(4, teacher.getBirthday());
            preparedStatement.setInt(5, teacher.getIdFees());
            isCreate = preparedStatement.execute();

            daoFactory.closePreparedStatement(preparedStatement);

            if (isCreate) {
                preparedStatement = connection.prepareStatement(sqlRequestId);
                preparedStatement.setInt(1, teacher.getIdFees());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    idTeacher = resultSet.getInt("id");
                }
                daoFactory.closeResultSet(resultSet);
                daoFactory.closePreparedStatement(preparedStatement);

                preparedStatement = connection.prepareStatement(sqlInsertTeacher);
                preparedStatement.setInt(1, idTeacher);
                preparedStatement.setInt(2, teacher.getDepartment().getId());
                isCreate = preparedStatement.execute();
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
    public Teacher findById(int id) {
        String sql = "select * from persons p inner join teachers t on p.id = t.person_id where p.id=?";
        Teacher teacher = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int departmentId = 0;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("first_name"));
                teacher.setLastName(resultSet.getString("last_name"));
                teacher.setMiddleName(resultSet.getString("middle_name"));
                teacher.setBirthday(resultSet.getDate("birthday"));
                teacher.setIdFees(resultSet.getInt("idfees"));
                departmentId = resultSet.getInt("department_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        if (departmentId != 0) {
            teacher.setDepartment(departmentDao.findById(departmentId));
//TODO attention! can be a problem - bidirectional
        }
        return teacher;
    }

    @Override
    public List<Teacher> findAll() {
        String sql = "select person_id from teachers";
        List<Integer> teachersId = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teachersId.add(resultSet.getInt("person_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        teachersId.forEach(id -> teachers.add(findById(id)));

        return teachers;
    }

    @Override
    public Teacher findByIdFees(int idFees) {
        String sql = "select id from persons where idfees=?";
        int teacherId = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idFees);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                teacherId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        return findById(teacherId);
    }

    @Override
    public List<Teacher> findByDepartmentId(int id) {
        String sql = "select person_id from teachers where department_id=?";
        List<Integer> teachersId = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teachersId.add(resultSet.getInt("person_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        teachersId.forEach(i -> teachers.add(findById(i)));

        return teachers;
    }

}
