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
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.LectureDao;
import ua.com.foxminded.task.dao.SubjectDao;
import ua.com.foxminded.task.dao.TeacherDao;
import ua.com.foxminded.task.dao.TimetableItemDao;
import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Lecture;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.Subject;
import ua.com.foxminded.task.domain.Teacher;
import ua.com.foxminded.task.domain.TimetableItem;

public class TimetableItemDaoImpl implements TimetableItemDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static SubjectDao subjectDao = new SubjectDaoImpl();
    private static AuditoryDao auditoryDao = new AuditoryDaoImpl();
    private static LectureDao lectureDao = new LectureDaoImpl();
    private static TeacherDao teacherDao = new TeacherDaoImpl();
    private static GroupDao groupDao = new GroupDaoImpl();

    @Override
    public boolean create(TimetableItem timetableItem) {
        int timetableItemId = timetableItem.getId();
        if (timetableItemId == 0 && !findAll().contains(timetableItem)) {
            timetableItem = getIdComponentsTimetableItems(timetableItem);
            insertTimetableItemRecord(timetableItem);
            timetableItem = setTimetableItemIdFromLastRecordInTable(timetableItem);
        }
        if (!timetableItem.getGroups().isEmpty()) {
            createGroupRecords(timetableItem);
        }
        insertGropsTimetableItemsRecodrs(timetableItem);
        return true;
    }

    private TimetableItem getIdComponentsTimetableItems(TimetableItem timetableItem) {
        if (timetableItem.getSubject().getId() == 0) {
            Subject subject = subjectDao.findByTitle(timetableItem.getSubject());
            timetableItem.setSubject(subject);
        }
        if (timetableItem.getAuditory().getId() == 0) {
            Auditory auditory = auditoryDao.findByNumber(timetableItem.getAuditory());
            timetableItem.setAuditory(auditory);
        }
        if (timetableItem.getLecture().getId() == 0) {
            Lecture lecture = lectureDao.findByNumber(timetableItem.getLecture());
            timetableItem.setLecture(lecture);
        }
        if (timetableItem.getTeacher().getId() == 0) {
            Teacher teacher = teacherDao.findByIdFees(timetableItem.getTeacher());
            timetableItem.setTeacher(teacher);
        }
        return timetableItem;
    }

    private void insertTimetableItemRecord(TimetableItem timetableItem) {
        String sql = "insert into timetable_items (subject_id, auditory_id, lecture_id, date, teacher_id) values (?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, timetableItem.getSubject().getId());
            preparedStatement.setInt(2, timetableItem.getAuditory().getId());
            preparedStatement.setInt(3, timetableItem.getLecture().getId());
            preparedStatement.setDate(4, timetableItem.getDate());
            preparedStatement.setInt(5, timetableItem.getTeacher().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private TimetableItem setTimetableItemIdFromLastRecordInTable(TimetableItem timetableItem) {
        String sql = "select id from timetable_items where id = (select max(id) from timetable_items)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                timetableItem.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return timetableItem;
    }

    private void createGroupRecords(TimetableItem timetableItem) {
        List<Group> groups = timetableItem.getGroups();
        Iterator<Group> iteratorGroup = groups.iterator();
        while (iteratorGroup.hasNext()) {
            Group group = iteratorGroup.next();
            if (group.getId() == 0 && groupDao.findByTitle(group).equals(group)) {
                groupDao.create(group);
                if (group.getId() == 0) {
                    group = groupDao.findByTitle(group);
                }
            }

        }
    }

    private void insertGropsTimetableItemsRecodrs(TimetableItem timetableItem) {
        String sql = "insert into groups_timetable_items (group_id, timetable_item_id) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            if (!timetableItem.getGroups().isEmpty()) {
                List<Group> groups = timetableItem.getGroups();
                Iterator<Group> iteratorGroup = groups.iterator();
                while (iteratorGroup.hasNext()) {
                    Group group = iteratorGroup.next();
                    connection = daoFactory.getConnection();
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, group.getId());
                    preparedStatement.setInt(2, timetableItem.getId());
                    preparedStatement.execute();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

//    public boolean create2(TimetableItem timetableItem) {
//        String sqlInsertTimetablesItem = "insert into timetable_items (subject_id, auditory_id, lecture_id, date, teacher_id) values (?, ?, ?, ?, ?)";
//        String sqlRequestId = "select id timetable_items where subject_id=? and auditory_id=? and lecture_id=? and date=? and teacher_id=?";
//        String sqlInsertGroups = "insert into groups_timetable_items (group_id, timetable_item_id) values (?, ?)";
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//        boolean isCreate = false;
//        int timetableItemId = 0;
//
//        try {
//            connection = daoFactory.getConnection();
//
//            preparedStatement = connection.prepareStatement(sqlInsertTimetablesItem);
//            preparedStatement.setInt(1, timetableItem.getSubject().getId());
//            preparedStatement.setInt(2, timetableItem.getAuditory().getId());
//            preparedStatement.setInt(3, timetableItem.getLecture().getId());
//            preparedStatement.setDate(4, timetableItem.getDate());
//            preparedStatement.setInt(5, timetableItem.getTeacher().getId());
//            isCreate = preparedStatement.execute();
//
//            daoFactory.closePreparedStatement(preparedStatement);
//
//            if (isCreate) {
//                preparedStatement = connection.prepareStatement(sqlRequestId);
//                preparedStatement.setInt(1, timetableItem.getSubject().getId());
//                preparedStatement.setInt(2, timetableItem.getAuditory().getId());
//                preparedStatement.setInt(3, timetableItem.getLecture().getId());
//                preparedStatement.setDate(4, timetableItem.getDate());
//                preparedStatement.setInt(5, timetableItem.getTeacher().getId());
//                resultSet = preparedStatement.executeQuery();
//                if (resultSet.next()) {
//                    timetableItemId = resultSet.getInt("id");
//                }
//                daoFactory.closeResultSet(resultSet);
//                daoFactory.closePreparedStatement(preparedStatement);
//
//                if (!timetableItem.getGroups().isEmpty()) {
//                    List<Group> groups = timetableItem.getGroups();
//                    Iterator<Group> iteratorGroup = groups.iterator();
//                    while (iteratorGroup.hasNext()) {
//                        int groupId = iteratorGroup.next().getId();
//                        preparedStatement = connection.prepareStatement(sqlInsertGroups);
//                        preparedStatement.setInt(1, groupId);
//                        preparedStatement.setInt(2, timetableItemId);
//                        isCreate = preparedStatement.execute();
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            daoFactory.closePreparedStatement(preparedStatement);
//            daoFactory.closeConnection(connection);
//        }
//        return isCreate;
//    }

    @Override
    public TimetableItem findById(int id) {
        String sql = "select * from timetable_items where id=?";
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
            Subject subject = new Subject();
            subject.setId(subjectId);
            timetableItem.setSubject(subjectDao.findById(subject));
        }
        if (auditoryId != 0) {
            Auditory auditory = new Auditory();
            auditory.setId(auditoryId);
            timetableItem.setAuditory(auditoryDao.findById(auditory));
        }
        if (lectureId != 0) {
            Lecture lecture = new Lecture();
            lecture.setId(teacherId);
            timetableItem.setLecture(lectureDao.findById(lecture));
        }
        if (teacherId != 0) {
            Teacher teacher = new Teacher();
            teacher.setId(teacherId);
            timetableItem.setTeacher(teacherDao.findById(teacher));
        }

        List<Group> groups = getGroupsFromTimetableItemId(timetableItem);
        timetableItem.setGroups(groups);

        return timetableItem;
    }

    private List<Group> getGroupsFromTimetableItemId(TimetableItem timetableItem) {
        List<Integer> groupsId = getIdGroupsFromTimetableGroupTable(timetableItem.getId());
        List<Group> groups = groupDao.getGroupsById(groupsId);
        return groups;
    }

    private List<Integer> getIdGroupsFromTimetableGroupTable(int id) {
        String sql = "select group_id from groups_timetable_items where timetable_item_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Integer> groupsId = new ArrayList<>();
        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int groupId = resultSet.getInt("group_id");
                groupsId.add(groupId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return groupsId;
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
