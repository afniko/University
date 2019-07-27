package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;

public class GroupDaoImpl implements GroupDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static StudentDao studentDao = new StudentDaoImpl();

    @Override
    public Group create(Group group) {
        insertGroupRecord(group);
        int id = getTheLastRecordId();
        group.setId(id);
        return group;
    }

    private void insertGroupRecord(Group group) {
        String sql = "insert into groups (title, department_id, yearEntry) values (?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Integer departmentId = Objects.nonNull(group.getDepartment()) ? group.getDepartment().getId() : null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, group.getTitle());
            if (Objects.isNull(departmentId)) {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(2, departmentId);
            }
            preparedStatement.setDate(3, group.getYearEntry());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private int getTheLastRecordId() {
        String sql = "select id from groups where id = (select max(id) from groups)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int groupId = 0;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
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
        return groupId;
    }

    @Override
    public Group findById(int id) {
        Group group = findByIdNoBidirectional(id);
        List<Student> students = studentDao.findByGroupIdNoBidirectional(group.getId());
        students.forEach(s -> s.setGroup(group));
        group.setStudents(students);
        return group;
    }

    @Override
    public List<Group> findAll() {
        String sql = "select id from groups";
        List<Integer> groupsId = new ArrayList<>();
        List<Group> groups = null;

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
        groups = getGroupsById(groupsId);
        return groups;
    }

    @Override
    public Group findByIdNoBidirectional(int id) {
        String sql = "select * from groups where id=?";
        Integer departmentId = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Group group = new Group();

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                group.setId(resultSet.getInt("id"));
                group.setTitle(resultSet.getString("title"));
                if (Objects.nonNull(resultSet.getObject("department_id"))) {
                    departmentId = resultSet.getInt("department_id");
                }
                group.setYearEntry(resultSet.getDate("yearEntry"));
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

    @Override
    public List<Group> findByDepartmentId(int id) {
        String sql = "select id from groups where department_id=?";
        List<Integer> groupsId = new ArrayList<>();
        List<Group> groups = new ArrayList<Group>();

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
        if (!groupsId.isEmpty()) {
            groups.addAll(getGroupsById(groupsId));
        }
        return groups;
    }

    private List<Group> getGroupsById(List<Integer> groupsId) {
        List<Group> groups = new ArrayList<>();
        groupsId.forEach(id -> groups.add(findById(id)));
        return groups;
    }

}
