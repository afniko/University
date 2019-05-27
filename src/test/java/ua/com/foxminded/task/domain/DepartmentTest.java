package ua.com.foxminded.task.domain;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.DepartmentModelRepository;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;

@RunWith(JUnitPlatform.class)
class DepartmentTest {

    @Test
    void test() {
        Department department = DepartmentModelRepository.getEmptyModel();
        Group group = GroupModelRepository.getModel();
        department.addGroup(group);
        assertTrue(department.getGroups().contains(group));
        assertTrue(group.getDepartment().equals(department));
    }

    @Test
    void test2() {
        Department department = DepartmentModelRepository.getEmptyModel();
        Group group = GroupModelRepository.getModel();
        department.addGroup(group);
        assertTrue(group.getDepartment().equals(department));
    }

}
