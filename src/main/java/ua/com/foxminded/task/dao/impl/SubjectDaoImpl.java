package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.SubjectDao;
import ua.com.foxminded.task.domain.Subject;

public class SubjectDaoImpl implements SubjectDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public boolean create(Subject subject) {
        if (subject.getId() == 0 && findByTitle(subject).getId() == 0) {
            insertSubjectRecord(subject);
        }
        return true;
    }

    private void insertSubjectRecord(Subject subject) {
        String sql = "insert into subjects (title) values (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, subject.getTitle());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    @Override
    public Subject findById(Subject subject) {
        String sql = "select * from subjects where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, subject.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                subject.setId(resultSet.getInt("id"));
                subject.setTitle(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return subject;
    }

    @Override
    public List<Subject> findAll() {
        String sql = "select id from subjects";
        List<Subject> subjects = null;
        List<Integer> subjectsId = new ArrayList<Integer>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                subjectsId.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        subjects = getSubjectsById(subjectsId);
        return subjects;
    }

    @Override
    public Subject findByTitle(Subject subject) {
        String sql = "select id from subjects where title=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, subject.getTitle());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                subject.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        subject = findById(subject);
        return subject;
    }

    private List<Subject> getSubjectsById(List<Integer> subjectsId) {
        List<Subject> subjects = new ArrayList<>();
        subjectsId.forEach(id -> {
            Subject subject = new Subject();
            subject.setId(id);
            subjects.add(findById(subject));
        });
        return subjects;
    }
}
