package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.AuditoryTypeDao;
import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.domain.AuditoryType;

public class AuditoryTypeDaoImpl implements AuditoryTypeDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public boolean create(AuditoryType auditoryType) {
        String sql = "insert into auditory_types (type) values (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rowsChange = 0;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, auditoryType.getType());
            rowsChange = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block to connection
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return rowsChange != 0 ? true : false;
    }

    @Override
    public AuditoryType findById(long id) {
        String sql = "select * from auditory_types where id=?";
        AuditoryType auditoryType = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            auditoryType = new AuditoryType();
            auditoryType.setId(resultSet.getInt("id"));
            auditoryType.setType(resultSet.getString("type"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block to connection
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
        String sql = "select * from auditory_types";
        List<AuditoryType> auditoryTypes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AuditoryType auditoryType = new AuditoryType();
                auditoryType.setId(resultSet.getInt("id"));
                auditoryType.setType(resultSet.getString("type"));
                auditoryTypes.add(auditoryType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return auditoryTypes;
    }

}
