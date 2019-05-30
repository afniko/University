package ua.com.foxminded.task.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.DepartmentModelRepository;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.TeacherModelRepository;

@RunWith(JUnitPlatform.class)
class DepartmentTest {

    @Test
    void whenAddGroupToDepartment_thenDepartmentContainsGroupAndGroupContainsDepartment() {
        Department department = DepartmentModelRepository.getEmptyModel();
        Group group = GroupModelRepository.getModel1();
        department.addGroup(group);
        assertTrue(department.getGroups().contains(group));
        assertTrue(group.getDepartment().equals(department));
    }

    @Test
    void whenRemoveGroupFromDepartment_thenDepartmentNonContainsGroupAndGroupNonContainsDepartment() {
        Department department = DepartmentModelRepository.getModel1();
        Group group = GroupModelRepository.getModel1();
        group.setDepartment(department);
        department.removeGroup(group);
        assertFalse(department.getGroups().contains(group));
        assertNull(group.getDepartment());
    }

    @Test
    void whenAddTeacherToDepartment_thenDepartmentContainsTeacherAndTeacherContainsDepartment() {
        Department department = DepartmentModelRepository.getEmptyModel();
        Teacher teacher = TeacherModelRepository.getModel1();
        department.addTeacher(teacher);
        assertTrue(department.getTeachers().contains(teacher));
        assertTrue(teacher.getDepartment().equals(department));
    }

    @Test
    void whenRemoveTeacherFromDepartment_thenDepartmentNonContainsTeacherAndTeacherNonContainsDepartment() {
        Department department = DepartmentModelRepository.getModel1();
        Teacher teacher = TeacherModelRepository.getModel1();
        teacher.setDepartment(department);
        department.removeTeacher(teacher);
        assertFalse(department.getTeachers().contains(teacher));
        assertNull(teacher.getDepartment());
    }

}
