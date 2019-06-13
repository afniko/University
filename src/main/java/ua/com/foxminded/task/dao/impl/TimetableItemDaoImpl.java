package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.TimetableItemDao;
import ua.com.foxminded.task.domain.TimetableItem;

public class TimetableItemDaoImpl implements TimetableItemDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public boolean create(TimetableItem timetableItem) {
        String sql = "insert into timetable_items (subject_id, auditory_id, lecture_id, date, teacher_id) values (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isCreate = false;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, timetableItem.getSubject().getId());
            preparedStatement.setInt(2, timetableItem.getAuditory().getId());
            preparedStatement.setInt(3, timetableItem.getLecture().getId());
            preparedStatement.setDate(4, timetableItem.getDate());
            preparedStatement.setInt(5, timetableItem.getTeacher().getId());
            isCreate = preparedStatement.execute();
// TODO need to add other parameters
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return isCreate;
    }

    @Override
    public TimetableItem findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TimetableItem> findAll() {
        String sql = "select id from timetable_items";
        List<Integer> timetableItemsId = new ArrayList<>();
        List<TimetableItem> timetableItems = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                timetableItemsId.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        timetableItemsId.forEach(id -> timetableItems.add(findById(id)));

        return timetableItems;
    }

}
