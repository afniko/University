package ua.com.foxminded.task.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ua.com.foxminded.task.dao.DaoFactory;
import ua.com.foxminded.task.dao.DepartmentDao;
import ua.com.foxminded.task.dao.GroupDao;
import ua.com.foxminded.task.dao.StudentDao;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Student;

public class GroupDaoImpl implements GroupDao {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static DepartmentDao departmentDao = new DepartmentDaoImpl();;
    private static StudentDao studentDao = new StudentDaoImpl();

    @Override
    public boolean create(Group group) {
        int groupId = group.getId();
        if (groupId == 0 && findByTitle(group).equals(group)) {
            insertGroupRecord(group);
            group = setGroupIdFromLastRecordInTable(group);
        }
        if (!group.getStudents().isEmpty()) {
            createStudentRecords(group);
        }
        return true;
    }

    private void insertGroupRecord(Group group) {
        String sqlWithDepartmentId = "insert into groups (title, department_id, yearEntry) values (?, ?, ?)";
        String sqlWithoutDepartmentId = "insert into groups (title, yearEntry) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            if (group.getDepartment() != null) {
                preparedStatement = connection.prepareStatement(sqlWithDepartmentId);
                preparedStatement.setString(1, group.getTitle());
                preparedStatement.setInt(2, group.getDepartment().getId());
                preparedStatement.setDate(3, group.getYearEntry());
            } else {
                preparedStatement = connection.prepareStatement(sqlWithoutDepartmentId);
                preparedStatement.setString(1, group.getTitle());
                preparedStatement.setDate(2, group.getYearEntry());
            }
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private Group setGroupIdFromLastRecordInTable(Group group) {
        String sql = "select id from groups where id = (select max(id) from groups)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int groupId = resultSet.getInt("id");
                group.setId(groupId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return group;
    }

    private void createStudentRecords(Group group) {
        List<Student> students = group.getStudents();
        Iterator<Student> iteratorStudent = students.iterator();
        while (iteratorStudent.hasNext()) {
            Student student = iteratorStudent.next();
            if (studentDao.findByIdFees(student).equals(student)) {
                studentDao.create(student);
                student = studentDao.findPersonIdByIdfees(student);
                updateStudentRecordSetStudentId(group, student.getId());
            }
        }
    }

    private void updateStudentRecordSetStudentId(Group group, int studentId) {
        String sql = "update students set group_id=? where person_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, group.getId());
            preparedStatement.setInt(2, studentId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    @Override
    public Group findById(Group group) {
        String sql = "select * from groups where id=?";
        int departmentId = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, group.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                group.setId(resultSet.getInt("id"));
                group.setTitle(resultSet.getString("title"));
                departmentId = resultSet.getInt("department_id");
                group.setYearEntry(resultSet.getDate("yearEntry"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        if (departmentId != 0 && group.getDepartment() == null) {
            Department department = new Department();
            department.setId(departmentId);
            department.addGroup(group);
            group.setDepartment(departmentDao.findById(department));
        }
        if (group.getStudents().isEmpty()) {
            group.setStudents(studentDao.findByGroupId(group.getId()));
        }
        return group;
    }

    @Override
    public List<Group> findAll() {
        String sql = "select id from groups";
        List<Integer> groupsId = new ArrayList<>();
        List<Group> groups = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupsId.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        groups = getGroupsById(groupsId);
        return groups;
    }

    @Override
    public Group findByTitle(Group group) {
        String sql = "select id from groups where title=?";
        int groupId = 0;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, group.getTitle());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                groupId = resultSet.getInt("id");
                group.setId(groupId);
                group = findById(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return group;
    }

    @Override
    public List<Group> findByDepartmentId(Department department) {
        String sql = "select id from groups where department_id=?";
        List<Integer> groupsId = new ArrayList<>();
        List<Group> groups = department.getGroups();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupsId.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        if (!groupsId.isEmpty()) {
            groups.addAll(getGroupsById(groupsId));
        }
        return groups;
    }

    @Override
    public List<Group> getGroupsById(List<Integer> groupsId) {
        List<Group> groups = new ArrayList<>();
        groupsId.forEach(id -> {
            Group group = new Group();
            group.setId(id);
            groups.add(findById(group));
        });
        return groups;
    }

}
