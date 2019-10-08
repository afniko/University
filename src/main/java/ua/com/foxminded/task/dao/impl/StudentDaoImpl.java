package ua.com.foxminded.task.dao.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.ConnectionFactory;
import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;

public class StudentDaoImpl implements StudentDao {
    private ConnectionFactory connectionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public StudentDaoImpl() {
        connectionFactory = ConnectionFactory.getInstance();
    }

    @Override
    public Student create(Student student) {
        LOGGER.debug("create() [student:{}]", student);
        insertPersonRecord(student);
        int id = getTheLastRecordId();
        student.setId(id);
        insertStudentRecord(student);
        return findById(id);
    }

    private void insertPersonRecord(Student student) {
        LOGGER.debug("insertPersonRecord() [student:{}]", student);
        String sql = "insert into persons (first_name, last_name, middle_name, birthday, idfees) values (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getMiddleName());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(student.getBirthday().atStartOfDay()));
            preparedStatement.setInt(5, student.getIdFees());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("insertPersonRecord() [student:{}] was not inserted. Sql query:{}. {}", student, preparedStatement, e);
            throw new NoExecuteQueryException("insertPersonRecord() Student entity was not created", e);
        } finally {
            connectionFactory.closePreparedStatement(preparedStatement);
            connectionFactory.closeConnection(connection);
        }
    }

    private int getTheLastRecordId() {
        LOGGER.debug("getTheLastRecordId()");
        String sql = "select id from persons where id = (select max(id) from persons)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int personId = 0;
        try {
            connection = connectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personId = resultSet.getInt("id");
            } else {
                LOGGER.warn("getTheLastRecordId() Don`t find the last record id in table persons. Sql query:{}.", preparedStatement);
            }
        } catch (SQLException e) {
            LOGGER.error("getTheLastRecordId() Crached request for finding  the last record id in table persons. Sql query:{}. {}", preparedStatement, e);
            throw new NoExecuteQueryException("getTheLastRecordId() Crached request for finding  the last record id in table persons. Sql query:" + preparedStatement, e);
        } finally {
            connectionFactory.closeResultSet(resultSet);
            connectionFactory.closePreparedStatement(preparedStatement);
            connectionFactory.closeConnection(connection);
        }
        return personId;
    }

    private void insertStudentRecord(Student student) {
        LOGGER.debug("insertStudentRecord() [student:{}]", student);
        String sql = "insert into students (person_id, group_id) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer groupId = null;
        if (Objects.nonNull(student.getGroup())) {
            groupId = student.getGroup().getId() == 0 ? null : student.getGroup().getId();
        }
        try {
            connection = connectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student.getId());
            if (Objects.isNull(groupId)) {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(2, groupId);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("insertStudentRecord() [student:{}] was not inserted. Sql query:{}. {}.", student, preparedStatement, e);
            throw new NoExecuteQueryException("insertStudentRecord() [student:" + student + "] was not inserted. Sql query:" + preparedStatement, e);
        } finally {
            connectionFactory.closePreparedStatement(preparedStatement);
            connectionFactory.closeConnection(connection);
        }
    }

    @Override
    public Student findById(int id) {
        LOGGER.debug("findById() [student id:{}]", id);
        String sql = "select * from persons p inner join students s on p.id = s.person_id " 
        + "left join groups g on s.group_id=g.id where p.id=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Student student = null;

        try {
            connection = connectionFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                student = getStudentFromResultSet(resultSet);

            } else {
                LOGGER.warn("findById() Student with id#{} not finded", id);
                throw new NoEntityFoundException("Student id#" + id + "not found");
            }
        } catch (SQLException e) {
            LOGGER.error("findById() Select Student with id#{} was crashed. Sql query:{}, {}", id, preparedStatement, e);
            throw new NoExecuteQueryException("findById() Select Student with id#" + id + " was crashed. Sql query:" + preparedStatement, e);
        } finally {
            connectionFactory.closeResultSet(resultSet);
            connectionFactory.closePreparedStatement(preparedStatement);
            connectionFactory.closeConnection(connection);
        }
        return student;
    }

    private Student getStudentFromResultSet(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        student.setMiddleName(resultSet.getString("middle_name"));
        Timestamp birthday = resultSet.getTimestamp("birthday");
        student.setBirthday(birthday.toLocalDateTime().toLocalDate());
        student.setIdFees(resultSet.getInt("idfees"));
        if (Objects.nonNull(resultSet.getObject("title"))) {
            Group group = new Group();
            int idGroup = resultSet.getInt("group_id");
            String titleGroup = resultSet.getString("title");
            int yearEntry = resultSet.getInt("yearentry");
            group.setId(idGroup);
            group.setTitle(titleGroup);
            group.setYearEntry(yearEntry);
            student.setGroup(group);
        }
        return student;
    }

    @Override
    public List<Student> findAll() {
        LOGGER.debug("findAll()");
        String sql = "select * from persons p inner join students s on p.id = s.person_id " 
        + "left join groups g on s.group_id=g.id";
        List<Student> students = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(getStudentFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("findAll() Select all Students query was crashed. Sql query:{}, {}", preparedStatement, e);
            throw new NoExecuteQueryException("findAll() Select all Students query was crashed. Sql query:" + preparedStatement, e);
        } finally {
            connectionFactory.closeResultSet(resultSet);
            connectionFactory.closePreparedStatement(preparedStatement);
            connectionFactory.closeConnection(connection);
        }
        return students;
    }

    @Override
    public List<Student> findByGroupId(int id) {
        LOGGER.debug("findByGroupId() [id:{}]", id);
        String sql = "select * from persons p inner join students s on p.id = s.person_id " 
        + "left join groups g on s.group_id=g.id where group_id=?";
        List<Student> students = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(getStudentFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("findByGroupId() Select Students query by group id#{} was crashed. Sql query:{}, {}", id, preparedStatement, e);
            throw new NoExecuteQueryException("findAll() Select all Students query was crashed. Sql query:" + preparedStatement, e);
        } finally {
            connectionFactory.closeResultSet(resultSet);
            connectionFactory.closePreparedStatement(preparedStatement);
            connectionFactory.closeConnection(connection);
        }
        return students;
    }

    @Override
    public Student update(Student student) {
        LOGGER.debug("update() [student:{}]", student);
        updatePersonRecord(student);
        updateStudentRecord(student);
        return findById(student.getId());
    }

    private void updatePersonRecord(Student student) {
        LOGGER.debug("updatePersonRecord() [student:{}]", student);
        String sql = "update persons set first_name=?, last_name=?, middle_name=?, birthday=?, idfees=? where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getMiddleName());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(student.getBirthday().atStartOfDay()));
            preparedStatement.setInt(5, student.getIdFees());
            preparedStatement.setInt(6, student.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("updatePersonRecord() [student:{}] was not updated. Sql query:{}. {}", student, preparedStatement, e);
            throw new NoExecuteQueryException("updatePersonRecord() Student entity was not updated", e);
        } finally {
            connectionFactory.closePreparedStatement(preparedStatement);
            connectionFactory.closeConnection(connection);
        }
    }

    private void updateStudentRecord(Student student) {
        LOGGER.debug("updateStudentRecord() [student:{}]", student);
        String sql = "update students set group_id=? where person_id=? ";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer groupId = Objects.isNull(student.getGroup()) ? null : student.getGroup().getId();
        try {
            connection = connectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if (Objects.isNull(groupId)) {
                preparedStatement.setNull(1, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(1, groupId);
            }
            preparedStatement.setInt(2, student.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("updateStudentRecord() [student:{}] was not updated. Sql query:{}. {}.", student, preparedStatement, e);
            throw new NoExecuteQueryException("updateStudentRecord() [student:" + student + "] was not updated. Sql query:" + preparedStatement, e);
        } finally {
            connectionFactory.closePreparedStatement(preparedStatement);
            connectionFactory.closeConnection(connection);
        }
    }

}
