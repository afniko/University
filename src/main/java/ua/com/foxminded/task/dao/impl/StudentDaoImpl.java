package ua.com.foxminded.task.dao.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.dao.exception.NoCreatedEntity;
import ua.com.foxminded.task.dao.exception.NoEntityFound;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;

public class StudentDaoImpl implements StudentDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    @Override
    public Student create(Student student) {
        insertPersonRecord(student);
        int id = getTheLastRecordId();
        student.setId(id);
        insertStudentRecord(student);
        logger.debug("Insert Student record {}", student);
        return student;
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
            logger.warn("Student (Person) {} was not inserted. Sql query = {}.  {}", student, preparedStatement, e);
            throw new NoCreatedEntity("Student entity was not created", e);
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private int getTheLastRecordId() {
        String sql = "select id from persons where id = (select max(id) from persons)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int personId = 0;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personId = resultSet.getInt("id");
            } else {
                logger.warn("Don`t find the last record id in table persons. Sql query = {}.", preparedStatement);
            }
        } catch (SQLException e) {
            logger.warn("Crached request for finding  the last record id in table persons. Sql query = {}. {}", preparedStatement, e);
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return personId;
    }

    private void insertStudentRecord(Student student) {
        String sql = "insert into students (person_id, group_id) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Integer groupId = student.getGroup().getId() == 0 ? null : student.getGroup().getId();
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, student.getId());
            if (Objects.isNull(groupId)) {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(2, groupId);
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.warn("Student {} was not inserted. Sql query = {}. {}.", student, preparedStatement, e);
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    @Override
    public Student findById(int id) {
        Student student = findByIdWithoutGroup(id);
        int groupId = student.getGroup().getId();
        if (groupId != 0) {
            student.setGroup(new GroupDaoImpl().findByIdNoBidirectional(groupId));
        }
        logger.debug("FindById Student {}", student);
        return student;
    }

    private Student findByIdWithoutGroup(int id) {
        String sql = "select * from persons p inner join students s on p.id = s.person_id where p.id=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer groupId = null;
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
                if (Objects.nonNull(resultSet.getObject("group_id"))) {
                    groupId = resultSet.getInt("group_id");
                }
            } else {
                logger.warn("Student with id {} not finded", id);
                throw new NoEntityFound("Student id #" + id + "not found");
            }
        } catch (SQLException e) {
            logger.error("Select Student with id {} was crashed. Sql query = {}, {}", id, preparedStatement, e);
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        Group group = new Group();
        group.setId(groupId);
        student.setGroup(group);
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
            if (studentsId.isEmpty()) {
                logger.warn("Students by findAll not finded. Sql query = {}", preparedStatement);
                throw new NoEntityFound("Students not finded");
            }
        } catch (SQLException e) {
            logger.error("Select all Students query was crashed. Sql query = {}, {}", preparedStatement, e);
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        students = getStudentsById(studentsId);
        return students;
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
            if (studentsId.isEmpty()) {
                logger.warn("Students by group id {} do nobody find. Sql query = {}", id, preparedStatement);
                throw new NoEntityFound("Students by group id #" + id + " not finded");
            }
        } catch (SQLException e) {
            logger.error("Select Students query by group id {} was crashed. Sql query = {}, {}", id, preparedStatement, e);
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        students = getStudentsById(studentsId);
        return students;
    }

    List<Student> findByGroupIdNoBidirectional(int id) {
        String sql = "select person_id from students where group_id=?";
        List<Integer> studentsId = new ArrayList<>();
        List<Student> students = new ArrayList<Student>();
        ;

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
            if (studentsId.isEmpty()) {
                logger.warn("Students by goupId don`t nobody finded. Sql query = {}", preparedStatement);
                throw new NoEntityFound("Students by group id #" + id + " not finded");
            }
        } catch (SQLException e) {
            logger.error("Select Students query by group id {} was crashed. Sql query = {}, {}", id, preparedStatement, e);
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        studentsId.forEach(i -> students.add(findByIdWithoutGroup(i)));

        return students;
    }

    private List<Student> getStudentsById(List<Integer> studentsId) {
        List<Student> students = new ArrayList<Student>();
        studentsId.forEach(id -> students.add(findById(id)));
        return students;
    }
}
