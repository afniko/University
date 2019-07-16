package ua.com.foxminded.task.dao;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.dao.impl.DepartmentDaoImpl;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.repository.DepartmentModelRepository;

@RunWith(JUnitPlatform.class)
public class DepartmentDaoTest {

    private static DepartmentDao departmentDao;
    private static final Department DEPARTMENT1 = DepartmentModelRepository.getModel1();
    private static final Department DEPARTMENT2 = DepartmentModelRepository.getModel2();
    private static final Department DEPARTMENT3 = DepartmentModelRepository.getModel3();

    @BeforeAll
    public static void createRecords() {
        DaoFactory.getInstance().createTables();
        departmentDao = new DepartmentDaoImpl();
        departmentDao.create(DEPARTMENT1);
        departmentDao.create(DEPARTMENT2);
        departmentDao.create(DEPARTMENT3);
    }

    @Test
    public void WhenPutAtTableDbDepartmentObjects_thenGetThisObjectsFindById() {
        Department department = new Department();
        department.setId(3);
        assertTrue(departmentDao.findById(department).equals(DEPARTMENT3));
    }

    @Test
    public void WhenPutAtTableDbDepartmentObjects_thenGetThisObjects() {
        assertTrue(departmentDao.findAll().containsAll(Arrays.asList(DEPARTMENT1, DEPARTMENT2, DEPARTMENT3)));
    }

    @Test
    public void WhenPutAtTableDbDepartmentObjects_thenGetThisObjectsFindByTitle() {
        Department department = new Department();
        department.setTitle(DEPARTMENT2.getTitle());
        assertTrue(departmentDao.findByTitle(department).equals(DEPARTMENT2));
    }

    @AfterAll
    public static void removeCreatedTables() {
        DaoFactory.getInstance().removeTables();
    }

}
