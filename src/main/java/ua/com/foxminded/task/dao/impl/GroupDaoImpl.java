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
    private DepartmentDao departmentDao = new DepartmentDaoImpl();
    private StudentDao studentDao = new StudentDaoImpl();

    @Override
    public boolean create(Group group) {
        String sqlInsertGroup = "insert into groups (title, department_id, yearEntry) values (?, ?, ?)";
        String sqlRequestId = "select id from groups where title=?";
        String sqlUpdateDepartment = "update students set group_id=? where person_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isCreate = false;
        int groupId = 0;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sqlInsertGroup);
            preparedStatement.setString(1, group.getTitle());
            if (group.getDepartment() != null) {
                preparedStatement.setInt(2, group.getDepartment().getId());
            } else {
                preparedStatement.setInt(2, 0);
            }
            preparedStatement.setDate(3, group.getYearEntry());
            isCreate = preparedStatement.execute();

            daoFactory.closePreparedStatement(preparedStatement);

            preparedStatement = connection.prepareStatement(sqlRequestId);
            preparedStatement.setString(1, group.getTitle());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                groupId = resultSet.getInt("id");
            }

            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);

            if (!group.getStudents().isEmpty()) {
                List<Student> students = group.getStudents();
                Iterator<Student> iteratorStudent = students.iterator();
                while (iteratorStudent.hasNext()) {
                    int studentId = iteratorStudent.next().getId();
                    preparedStatement = connection.prepareStatement(sqlUpdateDepartment);
                    preparedStatement.setInt(1, groupId);
                    preparedStatement.setInt(2, studentId);
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
                group.setYearEntry(resultSet.getDate("yearsEntry"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        group.setDepartment(departmentDao.findById(departmentId));
//       TODO attention! can be bidirectional stack overflow
        List<Student> students = group.getStudents();
        students.addAll(studentDao.findByGroupId(group.getId()));

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
        List<Integer> groupsId = new ArrayList<>();
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
                groupsId.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        groupsId.forEach(i -> groups.add(findById(i)));

        return groups;
    }

}
