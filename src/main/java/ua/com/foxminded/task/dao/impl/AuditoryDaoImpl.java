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
import ua.com.foxminded.task.domain.Group;

public class AuditoryDaoImpl implements AuditoryDao {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static AuditoryTypeDao auditoryTypeDao = new AuditoryTypeDaoImpl();

    @Override
    public boolean create(Auditory auditory) {
        String sql = "insert into auditories (number, auditory_type_id, capacity, description) values (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        AuditoryType auditoryType = auditoryTypeDao.findByType(auditory.getAuditoryType());
        if (auditoryType.getId() == 0) {
            auditoryTypeDao.create(auditory.getAuditoryType());
        }
        auditoryType = auditoryTypeDao.findByType(auditory.getAuditoryType());
        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, auditory.getAuditoryNumber());
            preparedStatement.setInt(2, auditoryType.getId());
            preparedStatement.setInt(3, auditory.getMaxCapacity());
            preparedStatement.setString(4, auditory.getDescription());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return true;
    }

    @Override
    public Auditory findById(Auditory auditory) {
        String sql = "select a.id, number, capacity, description, at.id as at_id, type from auditories a inner join auditory_types at on a.auditory_type_id = at.id where a.id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, auditory.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
        String sql = "select id from auditories";

        List<Auditory> auditories = null;
        List<Integer> auditoriesId = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                auditoriesId.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        if (!auditoriesId.isEmpty()) {
            auditories = getAuditoriesById(auditoriesId);
        }
        return auditories;
    }

    @Override
    public Auditory findByNumber(Auditory auditory) {
        String sql = "select id from auditories where number=?";
        int id = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, auditory.getAuditoryNumber());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id");
                auditory.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return findById(auditory);
    }

    private List<Auditory> getAuditoriesById(List<Integer> auditoriesId) {
        List<Auditory> auditories = new ArrayList<>();
        auditoriesId.forEach(id -> {
            Auditory auditory = new Auditory();
            auditory.setId(id);
            auditories.add(findById(auditory));
        });
        return auditories;
    }

}
