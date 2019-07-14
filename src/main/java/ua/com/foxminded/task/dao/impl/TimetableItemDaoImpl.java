package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ua.com.foxminded.task.dao.AuditoryDao;
import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.LectureDao;
import ua.com.foxminded.task.dao.SubjectDao;
import ua.com.foxminded.task.dao.TeacherDao;
import ua.com.foxminded.task.dao.TimetableItemDao;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Teacher;
import ua.com.foxminded.task.domain.TimetableItem;

public class TimetableItemDaoImpl implements TimetableItemDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private SubjectDao subjectDao = new SubjectDaoImpl();
    private AuditoryDao auditoryDao = new AuditoryDaoImpl();
    private LectureDao lectureDao = new LectureDaoImpl();
    private TeacherDao teacherDao = new TeacherDaoImpl();

    @Override
    public boolean create(TimetableItem timetableItem) {
        String sqlInsertTimetablesItem = "insert into timetable_items (subject_id, auditory_id, lecture_id, date, teacher_id) values (?, ?, ?, ?, ?)";
        String sqlRequestId = "select id timetable_items where subject_id=? and auditory_id=? and lecture_id=? and date=? and teacher_id=?";
        String sqlInsertGroups = "insert into groups_timetable_items (group_id, timetable_item_id) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isCreate = false;
        int timetableItemId = 0;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sqlInsertTimetablesItem);
            preparedStatement.setInt(1, timetableItem.getSubject().getId());
            preparedStatement.setInt(2, timetableItem.getAuditory().getId());
            preparedStatement.setInt(3, timetableItem.getLecture().getId());
            preparedStatement.setDate(4, timetableItem.getDate());
            preparedStatement.setInt(5, timetableItem.getTeacher().getId());
            isCreate = preparedStatement.execute();

            daoFactory.closePreparedStatement(preparedStatement);

            if (isCreate) {
                preparedStatement = connection.prepareStatement(sqlRequestId);
                preparedStatement.setInt(1, timetableItem.getSubject().getId());
                preparedStatement.setInt(2, timetableItem.getAuditory().getId());
                preparedStatement.setInt(3, timetableItem.getLecture().getId());
                preparedStatement.setDate(4, timetableItem.getDate());
                preparedStatement.setInt(5, timetableItem.getTeacher().getId());
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    timetableItemId = resultSet.getInt("id");
                }
                daoFactory.closeResultSet(resultSet);
                daoFactory.closePreparedStatement(preparedStatement);

                if (!timetableItem.getGroups().isEmpty()) {
                    List<Group> groups = timetableItem.getGroups();
                    Iterator<Group> iteratorGroup = groups.iterator();
                    while (iteratorGroup.hasNext()) {
                        int groupId = iteratorGroup.next().getId();
                        preparedStatement = connection.prepareStatement(sqlInsertGroups);
                        preparedStatement.setInt(1, groupId);
                        preparedStatement.setInt(2, timetableItemId);
                        isCreate = preparedStatement.execute();
                    }
                }
            }

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
        String sql = "select * from timetable_items where p.id=?";
        TimetableItem timetableItem = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int subjectId = 0;
        int auditoryId = 0;
        int lectureId = 0;
        int teacherId = 0;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                timetableItem = new TimetableItem();
                timetableItem.setId(resultSet.getInt("id"));
                subjectId = resultSet.getInt("subject_id");
                auditoryId = resultSet.getInt("auditory_id");
                lectureId = resultSet.getInt("lecture_id");
                timetableItem.setDate(resultSet.getDate("date"));
                teacherId = resultSet.getInt("teacher_id");
                // TODO many to many to groups table
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        if (subjectId != 0) {
            timetableItem.setSubject(subjectDao.findById(subjectId));
        }
        if (auditoryId != 0) {
            timetableItem.setAuditory(auditoryDao.findById(auditoryId));
        }
        if (lectureId != 0) {
            timetableItem.setLecture(lectureDao.findById(lectureId));
        }
        if (teacherId != 0) {
            Teacher teacher = new Teacher();
            teacher.setId(teacherId);
            timetableItem.setTeacher(teacherDao.findById(teacher));
        }
        return timetableItem;
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
