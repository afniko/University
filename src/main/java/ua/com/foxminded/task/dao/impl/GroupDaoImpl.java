package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.AuditoryType;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Group;

public class GroupDaoImpl implements GroupDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    @Override
    public boolean create(Group group) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Group findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Group> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group findByTitle(String title) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Group> findGroupsByDepartmentId(int id) {
        
        String sqlGroups = "select * from groups where department_id=?";
        List<Group> groups = new ArrayList<>();
        Group group = null;
        
        Department department = null;
        Auditory auditory = null;
        AuditoryType auditoryType = null;
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sqlGroups);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setTitle(resultSet.getString("title"));
//                group.setDepartment(department);
//                TODO set department
                group.setYearEntry(resultSet.getDate("yearEntry"));
//               TODO 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
      
        return null;
    }

}
