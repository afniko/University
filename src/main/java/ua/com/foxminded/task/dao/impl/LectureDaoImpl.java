package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.LectureDao;
import ua.com.foxminded.task.domain.Lecture;

public class LectureDaoImpl implements LectureDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public boolean create(Lecture lecture) {
        String sql = "insert into lecturies (number, start_time, end_time) values (?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean isCreate = false;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lecture.getNumber());
            preparedStatement.setTime(2, lecture.getStartTime());
            preparedStatement.setTime(3, lecture.getEndTime());
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
    public Lecture findById(int id) {
        String sql = "select * from lecturies where id=?";
        Lecture lecture = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lecture = new Lecture();
                lecture.setId(resultSet.getInt("id"));
                lecture.setNumber(resultSet.getString("number"));
                lecture.setStartTime(resultSet.getTime("start_time"));
                lecture.setEndTime(resultSet.getTime("end_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        return lecture;
    }

    @Override
    public List<Lecture> findAll() {
        String sql = "select * from lecturies";
        List<Lecture> lectures = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Lecture lecture = new Lecture();
                lecture.setId(resultSet.getInt("id"));
                lecture.setNumber(resultSet.getString("number"));
                lecture.setStartTime(resultSet.getTime("start_time"));
                lecture.setEndTime(resultSet.getTime("end_time"));
                lectures.add(lecture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        return lectures;
    }

    @Override
    public Lecture findByNumber(String number) {
        String sql = "select * from lecturies where number=?";
        Lecture lecture = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, number);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                lecture = new Lecture();
                lecture.setId(resultSet.getInt("id"));
                lecture.setNumber(resultSet.getString("number"));
                lecture.setStartTime(resultSet.getTime("start_time"));
                lecture.setEndTime(resultSet.getTime("end_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        return lecture;
    }

}
