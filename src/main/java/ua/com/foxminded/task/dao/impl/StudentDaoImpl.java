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
    private static GroupDao groupDao = new GroupDaoImpl();

    @Override
    public boolean create(Student student) {
        int studentId = student.getId();
        if (studentId == 0 && findByIdFees(student.getIdFees()).getId() == 0) {
            insertPersonRecord(student);
            student = setPersonIdFromLastRecordInTable(student);
            insertStudentRecord(student);
        }
        return true;
    }

    private void insertPersonRecord(Student student) {
        String sql = "insert into persons (first_name, last_name, middle_name, birthday, idfees) values (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getMiddleName());
            preparedStatement.setDate(4, student.getBirthday());
            preparedStatement.setInt(5, student.getIdFees());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private Student setPersonIdFromLastRecordInTable(Student student) {
        String sql = "select id from persons where id = (select max(id) from persons)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int personId = resultSet.getInt("id");
                student.setId(personId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return student;
    }

    private void insertStudentRecord(Student student) {
        String sqlWithGroup = "insert into students (person_id, group_id) values (?, ?)";
        String sqlWithoutGroup = "insert into students (person_id) values (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            if (student.getGroup() != null) {
                preparedStatement = connection.prepareStatement(sqlWithGroup);
                preparedStatement.setInt(1, student.getId());
                preparedStatement.setInt(2, student.getGroup().getId());
            } else {
                preparedStatement = connection.prepareStatement(sqlWithoutGroup);
                preparedStatement.setInt(1, student.getId());
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    @Override
    public Student findById(int id) {
        String sql = "select * from persons p inner join students s on p.id = s.person_id where p.id=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int groupId = 0;
        Student student = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            student = new Student();
            if (resultSet.next()) {
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
        if (groupId != 0 && student.getGroup() == null) {
            student.setGroup(groupDao.findById(groupId));
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        String sql = "select person_id from students";
        List<Integer> studentsId = new ArrayList<>();
        List<Student> students = null;

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
        students = getStudentsById(studentsId);
        return students;
    }

    @Override
    public Student findByIdFees(int idFees) {
        int id = findPersonIdByIdfees(idFees);
        return findById(id);
    }

    @Override
    public int findPersonIdByIdfees(int idFees) {
        String sql = "select id from persons where idfees=?";
        int personId = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idFees);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return personId;
    }

    @Override
    public List<Student> findByGroupId(int id) {
        String sql = "select person_id from students where group_id=?";
        List<Integer> studentsId = new ArrayList<>();
        List<Student> students;

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
        students = getStudentsById(studentsId);
        return students;
    }

    private List<Student> getStudentsById(List<Integer> studentsId) {
        List<Student> students = new ArrayList<Student>();
        studentsId.forEach(id -> students.add(findById(id)));
        return students;
    }
}
