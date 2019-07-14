package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.LectureDao;
import ua.com.foxminded.task.domain.Auditory;
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
    public Lecture findById(Lecture lecture) {
        String sql = "select * from lecturies where id=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, lecture.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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
        String sql = "select id from lecturies";
        List<Lecture> lectures = null;
        List<Integer> lecturiesId = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                lecturiesId.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        lectures = getLecturesById(lecturiesId);
        return lectures;
    }

    @Override
    public Lecture findByNumber(Lecture lecture) {
        String sql = "select * from lecturies where number=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lecture.getNumber());
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

    private List<Lecture> getLecturesById(List<Integer> lecturesId) {
        List<Lecture> lectures = new ArrayList<>();
        lecturesId.forEach(id -> {
            Lecture lecture = new Lecture();
            lecture.setId(id);
            lectures.add(findById(lecture));
        });
        return lectures;
    }

}
