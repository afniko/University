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
import ua.com.foxminded.task.dao.TeacherDao;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Teacher;

public class DepartmentDaoImpl implements DepartmentDao {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static GroupDao groupDao = new GroupDaoImpl();
    private static TeacherDao teacherDao = new TeacherDaoImpl();

    @Override
    public boolean create(Department department) {
        int departmentId = department.getId();
        if (departmentId == 0 && findByTitle(department).equals(department)) {
            insertDepartmentRecord(department);
            department = setDepartmentIdFromLastRecordInTable(department);
        }
        if (!department.getGroups().isEmpty()) {
            createGroupRecords(department);
        }
        if (!department.getTeachers().isEmpty()) {
            createTeacherRecords(department);
        }
        return true;
    }

    private void insertDepartmentRecord(Department department) {
        String sql = "insert into departments (title, description) values (?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, department.getTitle());
            preparedStatement.setString(2, department.getDescription());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private Department setDepartmentIdFromLastRecordInTable(Department department) {
        String sql = "select id from departments where id = (select max(id) from departments)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int departmentId = resultSet.getInt("id");
                department.setId(departmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        return department;
    }

    private void createGroupRecords(Department department) {
        List<Group> groups = department.getGroups();
        Iterator<Group> iteratorGroup = groups.iterator();
        while (iteratorGroup.hasNext()) {
            Group group = iteratorGroup.next();
            if (groupDao.findByTitle(group).equals(group)) {
                groupDao.create(group);
                if (group.getId() == 0) {
                    group = groupDao.findByTitle(group);
                }
                updateGroupRecordSetDepartmentId(department, group);
            }
        }
    }

    private void updateGroupRecordSetDepartmentId(Department department, Group group) {
        String sql = "update groups set department_id=? where id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, department.getId());
            preparedStatement.setInt(2, group.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    private void createTeacherRecords(Department department) {
        List<Teacher> teachers = department.getTeachers();
        Iterator<Teacher> iteratorTeacher = teachers.iterator();
        while (iteratorTeacher.hasNext()) {
            Teacher teacher = iteratorTeacher.next();
            if (teacherDao.findByIdFees(teacher).equals(teacher)) {
                teacherDao.create(teacher);
                teacher = teacherDao.findByIdFees(teacher);
                updateTeacherRecordSetDepartmentId(department, teacher);
            }
        }
    }

    private void updateTeacherRecordSetDepartmentId(Department department, Teacher teacher) {
        String sql = "update teachers set department_id=? where person_id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, department.getId());
            preparedStatement.setInt(2, teacher.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
    }

    @Override
    public Department findById(Department department) {
        String sql = "select * from departments where id=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                department.setId(resultSet.getInt("id"));
                department.setTitle(resultSet.getString("title"));
                department.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }

        List<Group> groups = department.getGroups();
        if (groups.isEmpty()) {
            groups.addAll(groupDao.findByDepartmentId(department));
        }
        List<Teacher> teachers = department.getTeachers();
        if (teachers.isEmpty()) {
            teachers.addAll(teacherDao.findByDepartmentId(department));
        }
        return department;
    }

    @Override
    public List<Department> findAll() {
        String sql = "select id from departments";
        List<Integer> departmentsId = new ArrayList<>();
        List<Department> departments = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                departmentsId.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        if (!departmentsId.isEmpty()) {
            departments = getDepartmentsById(departmentsId);
        }
        return departments;
    }

    @Override
    public Department findByTitle(Department department) {
        String sql = "select id from departments where title=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int departmentId = 0;
        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, department.getTitle());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                departmentId = resultSet.getInt("id");
                department.setId(departmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        if (departmentId != 0) {
            department = findById(department);
        }
        return department;
    }

    @Override
    public List<Department> findByFacultyId(int facultyId) {
        String sql = "select id from departments where faculty_id=?";
        List<Integer> departmentsId = new ArrayList<>();
        List<Department> departments = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, facultyId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                departmentsId.add(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            daoFactory.closeResultSet(resultSet);
            daoFactory.closePreparedStatement(preparedStatement);
            daoFactory.closeConnection(connection);
        }
        if (!departmentsId.isEmpty()) {
            departments = getDepartmentsById(departmentsId);
        }
        return departments;
    }

    private List<Department> getDepartmentsById(List<Integer> departmentsId) {
        List<Department> departments = new ArrayList<>();
        departmentsId.forEach(id -> {
            Department department = new Department();
            department.setId(id);
            departments.add(findById(department));
        });
        return departments;
    }
}
