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
import ua.com.foxminded.task.dao.TeacherDao;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.Subject;
import ua.com.foxminded.task.domain.Teacher;

public class TeacherDaoImpl implements TeacherDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private DepartmentDao departmentDao = new DepartmentDaoImpl();

    @Override
    public boolean create(Teacher teacher) {
        int tescherId = teacher.getId();
        if (tescherId == 0 && findByIdFees(teacher).equals(teacher)) {
            insertPersonRecord(teacher);
            teacher = setPersonIdFromLastRecordInTable(teacher);
            insertTeacherRecord(teacher);
        }
        return true;
    }

    private void insertPersonRecord(Teacher teacher) {
        String sql = "insert into persons (first_name, last_name, middle_name, birthday, idfees) values (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setString(3, teacher.getMiddleName());
            preparedStatement.setDate(4, teacher.getBirthday());
            preparedStatement.setInt(5, teacher.getIdFees());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private Teacher setPersonIdFromLastRecordInTable(Teacher teacher) {
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
                teacher.setId(personId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return teacher;
    }

    private void insertTeacherRecord(Teacher teacher) {
        String sqlWithDepartment = "insert into teachers (person_id, department_id) values (?, ?)";
        String sqlWithoutDepartment = "insert into teachers (person_id) values (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            if (teacher.getDepartment() != null) {
                preparedStatement = connection.prepareStatement(sqlWithDepartment);
                preparedStatement.setInt(1, teacher.getId());
                preparedStatement.setInt(2, teacher.getDepartment().getId());
            } else {
                preparedStatement = connection.prepareStatement(sqlWithoutDepartment);
                preparedStatement.setInt(1, teacher.getId());
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
    public Teacher findById(Teacher teacher) {
        String sql = "select * from persons p inner join teachers t on p.id = t.person_id where p.id=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int departmentId = 0;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, teacher.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
        if (departmentId != 0 && teacher.getDepartment() == null) {
            Department department = new Department();
            department.setId(departmentId);
            department.addTeacher(teacher);
            teacher.setDepartment(department);
//            TODO ????
            teacher.setDepartment(departmentDao.findById(department));
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
        teachers = getTeachersById(teachersId, null);
        return teachers;
    }

    @Override
    public Teacher findByIdFees(Teacher teacher) {
        String sql = "select id from persons where idfees=?";
        int teacherId = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, teacher.getIdFees());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                teacherId = resultSet.getInt("id");
                teacher.setId(teacherId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return findById(teacher);
    }

    @Override
    public List<Teacher> findByDepartmentId(Department department) {
        String sql = "select person_id from teachers where department_id=?";
        List<Integer> teachersId = new ArrayList<>();
        List<Teacher> teachers = department.getTeachers();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, department.getId());
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
        if (!teachersId.isEmpty()) {
            teachers.addAll(getTeachersById(teachersId, department));
        }
        return teachers;
    }

    private List<Teacher> getTeachersById(List<Integer> teachersId, Department department) {
        List<Teacher> teachers = new ArrayList<>();
        teachersId.forEach(id -> {
            Teacher teacher = new Teacher();
            teacher.setId(id);
            teacher.setDepartment(department);
            teachers.add(findById(teacher));
        });
        return teachers;
    }
}
