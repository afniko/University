package ua.com.foxminded.task.dao.impl.jdbc;

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

import ua.com.foxminded.task.dao.ConnectionFactory;
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.exception.NoEntityFoundException;
import ua.com.foxminded.task.dao.exception.NoExecuteQueryException;
import ua.com.foxminded.task.domain.Group;

public class GroupDaoImpl implements GroupDao {

    private ConnectionFactory connectionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public GroupDaoImpl() {
        connectionFactory = ConnectionFactoryImpl.getInstance();
    }

    @Override
    public Group create(Group group) {
        LOGGER.debug("create() [group:{}]", group);
        String sql = 
                "insert into groups (title, department_id, yearEntry) " 
              + "values (?, ?, ?) " 
              + "returning id";

        Integer departmentId = Objects.nonNull(group.getDepartment()) ? group.getDepartment().getId() : null;
        try (Connection connection = connectionFactory.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, group.getTitle());
            if (Objects.isNull(departmentId)) {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(2, departmentId);
            }
            preparedStatement.setInt(3, group.getYearEntry());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    group.setId(id);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("create() [group:{}] was not inserted. Sql query:{}. {}", group, sql, e);
            throw new NoExecuteQueryException("create() [group:" + group + "] was not inserted. Sql query:" + sql, e);
        }
        return group;
    }

    @Override
    public Group findById(int id) {
        LOGGER.debug("findById() [id:{}]", id);
        String sql = "select * from groups where id=?";

        Group group = null;
        try (Connection connection = connectionFactory.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    group = getGroupFromResultSet(resultSet);
                } else {
                    LOGGER.warn("findById() Group with id#{} not found", id);
                    throw new NoEntityFoundException("findById() Group by id#" + id + " not found");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("findById() Select Group with id#{} was crashed. Sql query:{}, {}", id, sql, e);
            throw new NoExecuteQueryException("findById() Select Group with id#" + id + " was crashed. Sql query:" + sql, e);
        }
        return group;
    }

    @Override
    public List<Group> findAll() {
        LOGGER.debug("findAll()");
        String sql = "select * from groups";
        List<Group> groups = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Group group = getGroupFromResultSet(resultSet);
                groups.add(group);
            }
        } catch (SQLException e) {
            LOGGER.error("findAll() Select all groups query was crashed. Sql query:{}, {}", sql, e);
            throw new NoExecuteQueryException("findAll() Select all groups query was crashed. Sql query:" + sql, e);
        }
        return groups;
    }

    @Override
    public List<Group> findByDepartmentId(int id) {
        LOGGER.debug("findByDepartmentId() [id:{}]", id);
        String sql = "select id from groups where department_id=?";
        List<Integer> groupsId = new ArrayList<>();
        List<Group> groups = new ArrayList<>();

        try (Connection connection = connectionFactory.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    groupsId.add(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("findByDepartmentId() Select Groups query by department id#{} was crashed. Sql query:{}, {}", id, sql, e);
            throw new NoExecuteQueryException("findByDepartmentId() Select Groups query by department id#" + id + " was crashed. Sql query:" + sql, e);
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

    @Override
    public Group update(Group group) {
        LOGGER.debug("update() [group:{}]", group);
        updateGroupRecord(group);
        return findById(group.getId());
    }

    private Group getGroupFromResultSet(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getInt("id"));
        group.setTitle(resultSet.getString("title"));
        group.setYearEntry(resultSet.getInt("yearEntry"));
        return group;
    }

    private void updateGroupRecord(Group group) {
        LOGGER.debug("updateGroupRecord() [group:{}]", group);
        String sql = "update groups set title=?, department_id=?, yearEntry=? where id=? ";
        Integer departmentId = Objects.nonNull(group.getDepartment()) ? group.getDepartment().getId() : null;

        try (Connection connection = connectionFactory.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, group.getTitle());
            if (Objects.isNull(departmentId)) {
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
            } else {
                preparedStatement.setInt(2, departmentId);
            }
            preparedStatement.setInt(3, group.getYearEntry());
            preparedStatement.setInt(4, group.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("updateGroupRecord() [group:{}] was not updated. Sql query:{}. {}", group, sql, e);
            throw new NoExecuteQueryException("updateGroupRecord() [group:" + group + "] was not updated. Sql query:" + sql, e);
        }
    }
}
