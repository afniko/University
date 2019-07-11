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
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;

public class GroupDaoImpl implements GroupDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static DepartmentDao departmentDao = new DepartmentDaoImpl();;
    private static StudentDao studentDao = new StudentDaoImpl();

    @Override
    public boolean create(Group group) {
        int groupId = group.getId();
        if (groupId == 0 && findByTitle(group.getTitle()) == null) {
            insertGroupRecord(group);
            group = setGroupIdFromLastRecordInTable(group);
        }
        if (!group.getStudents().isEmpty()) {
            createStudentRecords(group);
        }
        return true;
    }

    private void insertGroupRecord(Group group) {
        String sqlWithDepartmentId = "insert into groups (title, department_id, yearEntry) values (?, ?, ?)";
        String sqlWithoutDepartmentId = "insert into groups (title, yearEntry) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            if (group.getDepartment() != null) {
                preparedStatement = connection.prepareStatement(sqlWithDepartmentId);
                preparedStatement.setString(1, group.getTitle());
                preparedStatement.setInt(2, group.getDepartment().getId());
                preparedStatement.setDate(3, group.getYearEntry());
            } else {
                preparedStatement = connection.prepareStatement(sqlWithoutDepartmentId);
                preparedStatement.setString(1, group.getTitle());
                preparedStatement.setDate(2, group.getYearEntry());
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private Group setGroupIdFromLastRecordInTable(Group group) {
        String sql = "select id from groups where id = (select max(id) from groups)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int groupId = resultSet.getInt("id");
                group.setId(groupId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return group;
    }

    private void createStudentRecords(Group group) {
//        studentDao = new StudentDaoImpl();
        List<Student> students = group.getStudents();
        Iterator<Student> iteratorStudent = students.iterator();
        while (iteratorStudent.hasNext()) {
            Student student = iteratorStudent.next();
            if (studentDao.findByIdFees(student.getIdFees()) == null) {
                studentDao.create(student);
                student = studentDao.findByIdFees(student.getIdFees());
                updateStudentRecordSetStudentId(group, student);
            }
        }
    }

    private void updateStudentRecordSetStudentId(Group group, Student student) {
        String sql = "update students set group_id=? where person_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, group.getId());
            preparedStatement.setInt(2, student.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    @Override
    public Group findById(int id) {
        String sql = "select * from groups where id=?";
        Group group = null;
        int departmentId = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setTitle(resultSet.getString("title"));
                departmentId = resultSet.getInt("department_id");
                group.setYearEntry(resultSet.getDate("yearEntry"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        if (departmentId != 0 && group.getDepartment() == null) {
//            group.setDepartment(departmentDao.findById(departmentId));
        }
//        TODO stackOverFlow Error ((
        if (group != null) {
            group.setStudents(studentDao.findByGroupId(group.getId()));
        }
        return group;
    }

    @Override
    public List<Group> findAll() {
        String sql = "select id from groups";
        List<Integer> groupsId = new ArrayList<>();
        List<Group> groups = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupsId.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        groupsId.forEach(id -> groups.add(findById(id)));

        return groups;
    }

    @Override
    public Group findByTitle(String title) {
        String sql = "select id from groups where title=?";
        int groupId = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                groupId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        Group group = findById(groupId);

        return group;
    }

    @Override
    public List<Group> findByDepartmentId(int id) {
        String sql = "select id from groups where department_id=?";
        List<Integer> groupIds = new ArrayList<>();
        List<Group> groups = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupIds.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        groupIds.forEach(i -> groups.add(findById(i)));
        return groups;
    }

}
