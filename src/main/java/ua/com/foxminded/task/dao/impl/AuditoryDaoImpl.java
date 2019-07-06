package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.AuditoryDao;
import ua.com.foxminded.task.dao.AuditoryTypeDao;
import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.AuditoryType;

public class AuditoryDaoImpl implements AuditoryDao {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public boolean create(Auditory auditory) {
        String sql = "insert into auditories (number, auditory_type_id, capacity, description) values (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        AuditoryTypeDao auditoryTypeDao = new AuditoryTypeDaoImpl();
        boolean isCreate = false;

        AuditoryType auditoryType;
        auditoryType = auditoryTypeDao.findByType(auditory.getType().getType());
        if (auditoryType == null) {
            auditoryTypeDao.create(auditory.getType());
            auditoryType = auditoryTypeDao.findByType(auditory.getType().getType());
        }

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, auditory.getAuditoryNumber());
            preparedStatement.setInt(2, auditoryType.getId());
            preparedStatement.setInt(3, auditory.getMaxCapacity());
            preparedStatement.setString(4, auditory.getDescription());
            isCreate = preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return isCreate;
    }

    @Override
    public Auditory findById(int id) {
        String sql = "select a.id, number, capacity, description, at.id as at_id, type from auditories a inner join auditory_types at on a.auditory_type_id = at.id where a.id=?";
        Auditory auditory = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                auditory = new Auditory();
                auditory.setId(resultSet.getInt("id"));
                auditory.setAuditoryNumber(resultSet.getString("number"));
                auditory.setMaxCapacity(resultSet.getInt("capacity"));
                auditory.setDescription(resultSet.getString("description"));
                AuditoryType auditoryType = new AuditoryType();
                auditoryType.setId(resultSet.getInt("at_id"));
                auditoryType.setType(resultSet.getString("type"));
                auditory.setType(auditoryType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return auditory;
    }

    @Override
    public List<Auditory> findAll() {
        String sql = "select a.id, number, capacity, description, at.id as at_id, type from auditories a inner join auditory_types at on a.auditory_type_id = at.id";
        Auditory auditory = null;
        List<Auditory> auditories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                auditory = new Auditory();
                auditory.setId(resultSet.getInt("id"));
                auditory.setAuditoryNumber(resultSet.getString("number"));
                auditory.setMaxCapacity(resultSet.getInt("capacity"));
                auditory.setDescription(resultSet.getString("description"));
                AuditoryType auditoryType = new AuditoryType();
                auditoryType.setId(resultSet.getInt("at_id"));
                auditoryType.setType(resultSet.getString("type"));
                auditory.setType(auditoryType);
                auditories.add(auditory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return auditories;
    }

    @Override
    public Auditory findByNumber(String number) {
        String sql = "select a.id, number, capacity, description, at.id as at_id, type from auditories a inner join auditory_types at on a.auditory_type_id = at.id where number=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Auditory auditory = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, number);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                auditory = new Auditory();
                auditory.setId(resultSet.getInt("id"));
                auditory.setAuditoryNumber(resultSet.getString("number"));
                auditory.setMaxCapacity(resultSet.getInt("capacity"));
                auditory.setDescription(resultSet.getString("description"));
                AuditoryType auditoryType = new AuditoryType();
                auditoryType.setId(resultSet.getInt("at_id"));
                auditoryType.setType(resultSet.getString("type"));
                auditory.setType(auditoryType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        return auditory;
    }

}
