package ua.com.foxminded.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ua.com.foxminded.task.dao.AuditoryTypeDao;
import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.impl.AuditoryTypeDaoImpl;
import ua.com.foxminded.task.domain.AuditoryType;

public class Main {

    public static void main(String[] args) {

        String sql = "CREATE TABLE IF NOT EXISTS auditory_types (id serial PRIMARY KEY, type VARCHAR(45));";
        DaoFactory daoFactory = DaoFactory.getInstance();
        try {
            Connection connection = daoFactory.getConnection();
            System.out.println(connection);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println();

            AuditoryTypeDao auditoryTypeDao = new AuditoryTypeDaoImpl();
            AuditoryType auditoryType = new AuditoryType();
            String testAuditoryType = "test auditory type";
            auditoryType.setType(testAuditoryType+1);
            auditoryTypeDao.create(auditoryType);
            auditoryType.setType(testAuditoryType+2);
            auditoryTypeDao.create(auditoryType);
            System.out.println(auditoryTypeDao.findAll());
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
