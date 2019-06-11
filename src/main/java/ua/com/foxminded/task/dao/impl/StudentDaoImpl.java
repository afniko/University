package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.domain.Student;

public class StudentDaoImpl implements StudentDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private GroupDao groupDao = new GroupDaoImpl();

    @Override
    public boolean create(Student student) {
        String sqlInsertPerson = "insert into persons (first_name, last_name, middle_name, birthday, idfees) values (?, ?, ?, ?, ?)";
        String sqlRequestId = "select id persons where idfees=?";
        String sqlInsertStudent = "insert into students (person_id, group_id) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isCreate = false;
        int idStudent = 0;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sqlInsertPerson);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getMiddleName());
            preparedStatement.setDate(4, student.getBirthday());
            preparedStatement.setInt(5, student.getIdFees());
            isCreate = preparedStatement.execute();

            daoFactory.closePreparedStatement(preparedStatement);

            if (isCreate) {
                preparedStatement = connection.prepareStatement(sqlRequestId);
                preparedStatement.setInt(1, student.getIdFees());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    idStudent = resultSet.getInt("id");
                }
                daoFactory.closeResultSet(resultSet);
                daoFactory.closePreparedStatement(preparedStatement);

                preparedStatement = connection.prepareStatement(sqlInsertStudent);
                preparedStatement.setInt(1, idStudent);
                preparedStatement.setInt(2, student.getGroup().getId());
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
    public Student findById(int id) {
        String sql = "select * from persons p inner join students s on p.id = s.person_id where p.id=?";
        Student student = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int groupId = 0;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setMiddleName(resultSet.getString("middle_name"));
                student.setBirthday(resultSet.getDate("birthday"));
                student.setIdFees(resultSet.getInt("idfees"));
                groupId = resultSet.getInt("group_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        if (groupId != 0) {
            student.setGroup(groupDao.findById(groupId));
//TODO attention! can be a problem - bidirectional
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        String sql = "select person_id from students";
        List<Integer> studentsId = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentsId.add(resultSet.getInt("person_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        studentsId.forEach(id -> students.add(findById(id)));

        return students;
    }

    @Override
    public Student findByIdFees(int idFees) {
        String sql = "select id from persons where idfees=?";
        int studentId = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idFees);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                studentId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        return findById(studentId);
    }

    @Override
    public List<Student> findByGroupId(int id) {
        String sql = "select person_id from students where group_id=?";
        List<Integer> studentsId = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentsId.add(resultSet.getInt("person_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        studentsId.forEach(i -> students.add(findById(i)));

        return students;
    }

}
