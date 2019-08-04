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
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;

public class GroupDaoImpl implements GroupDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    @Override
    public Group create(Group group) {
        LOGGER.debug("create() [group:{}]", group);
        insertGroupRecord(group);
        int id = getTheLastRecordId();
        group.setId(id);
        return group;
    }

    private void insertGroupRecord(Group group) {
        LOGGER.debug("insertGroupRecord() [group:{}]", group);
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
            LOGGER.error("insertGroupRecord() [group:{}] was not inserted. Sql query:{}. {}", group, preparedStatement, e);
            throw new NoExecuteQueryException("insertGroupRecord() [group:" + group + "] was not inserted. Sql query:" + preparedStatement, e);
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private int getTheLastRecordId() {
        LOGGER.debug("getTheLastRecordId()");
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
            LOGGER.error("getTheLastRecordId() Crached request for finding the last record id in table groups. Sql query:{}. {}", preparedStatement, e);
            throw new NoExecuteQueryException("getTheLastRecordId() Group entity was not created", e);
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return groupId;
    }

    @Override
    public Group findById(int id) {
        LOGGER.debug("findById() [id:{}]", id);
        Group group = findByIdNoBidirectional(id);
        List<Student> students = new StudentDaoImpl().findByGroupIdNoBidirectional(group.getId());
        students.forEach(s -> s.setGroup(group));
        group.setStudents(students);
        return group;
    }

    Group findByIdNoBidirectional(int id) {
        LOGGER.debug("findByIdNoBidirectional() [id:{}]", id);
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
            } else {
                LOGGER.warn("findByIdNoBidirectional() Group with id#{} not finded", id);
                throw new NoEntityFoundException("Group by id#" + id + " not finded");
            }
        } catch (SQLException e) {
            LOGGER.error("findByIdNoBidirectional() Select Group with id#{} was crashed. Sql query:{}, {}", id, preparedStatement, e);
            throw new NoExecuteQueryException("findByIdNoBidirectional() Select Group with id#" + id + " was crashed. Sql query:" + preparedStatement, e);
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return group;
    }

    @Override
    public List<Group> findAll() {
        LOGGER.debug("findAll()");
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
            LOGGER.error("findAll() Select all groups query was crashed. Sql query:{}, {}", preparedStatement, e);
            throw new NoExecuteQueryException("findAll() Select all groups query was crashed. Sql query:" + preparedStatement, e);
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        groups = getGroupsById(groupsId);
        return groups;
    }

    @Override
    public List<Group> findByDepartmentId(int id) {
        LOGGER.debug("findByDepartmentId() [id:{}]", id);
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
            LOGGER.error("findByDepartmentId() Select Groups query by department id#{} was crashed. Sql query:{}, {}", id, preparedStatement, e);
            throw new NoExecuteQueryException("findByDepartmentId() Select Groups query by department id#" + id + " was crashed. Sql query:" + preparedStatement, e);
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
        LOGGER.debug("getGroupsById() [groupsId:{}]", groupsId);
        List<Group> groups = new ArrayList<>();
        groupsId.forEach(id -> groups.add(findById(id)));
        return groups;
    }

}
