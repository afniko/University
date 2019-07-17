package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.AuditoryTypeDao;
import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.AuditoryType;

public class AuditoryTypeDaoImpl implements AuditoryTypeDao {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public boolean create(AuditoryType auditoryType) {

        if (auditoryType.getId() == 0 && findByType(auditoryType).getId() == 0) {
            insertAuditoryTypeRecord(auditoryType);
        }
        return true;
    }

    private void insertAuditoryTypeRecord(AuditoryType auditoryType) {
        String sql = "insert into auditory_types (type) values (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, auditoryType.getType());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    @Override
    public AuditoryType findById(AuditoryType auditoryType) {
        String sql = "select * from auditory_types where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, auditoryType.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                auditoryType.setId(resultSet.getInt("id"));
                auditoryType.setType(resultSet.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return auditoryType;
    }

    @Override
    public List<AuditoryType> findAll() {
        String sql = "select id from auditory_types";
        List<AuditoryType> auditoryTypes = null;
        List<Integer> auditoryTypesId = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                auditoryTypesId.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        if (!auditoryTypesId.isEmpty()) {
            auditoryTypes = getAuditoryTypesById(auditoryTypesId);
        }
        return auditoryTypes;
    }

    @Override
    public AuditoryType findByType(AuditoryType auditoryType) {
        String sql = "select id from auditory_types where type=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, auditoryType.getType());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                auditoryType.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return findById(auditoryType);
    }

    private List<AuditoryType> getAuditoryTypesById(List<Integer> auditoryTypesId) {
        List<AuditoryType> auditoryTypes = new ArrayList<>();
        auditoryTypesId.forEach(id -> {
            AuditoryType auditoryType = new AuditoryType();
            auditoryType.setId(id);
            auditoryTypes.add(findById(auditoryType));
        });
        return auditoryTypes;
    }

}
