package ua.com.foxminded.task.domain;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.DepartmentModelRepository;
import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.TeacherModelRepository;

@RunWith(JUnitPlatform.class)
class DepartmentTest {

    @Test
    void whenAddGroupToDepartment_thenDepartmentContainsGroup() {
        Department department = DepartmentModelRepository.getEmptyModel();
        Group group = GroupModelRepository.getModel1();
        department.addGroup(group);
        assertTrue(department.getGroups().contains(group));
    }

    @Test
    void whenAddGroupToDepartment_thenGroupContainsDepartment() {
        Department department = DepartmentModelRepository.getEmptyModel();
        Group group = GroupModelRepository.getModel1();
        department.addGroup(group);
        assertTrue(group.getDepartment().equals(department));
    }
    
    

    @Test
    void whenAddTeacherToDepartment_thenDepartmentContainsTeacher() {
        Department department = DepartmentModelRepository.getEmptyModel();
        Teacher teacher = TeacherModelRepository.getModel();
        department.addTeacher(teacher);
        assertTrue(department.getTeachers().contains(teacher));
    }

    @Test
    void whenAddTeacherToDepartment_thenTeacherContainsDepartment() {
        Department department = DepartmentModelRepository.getEmptyModel();
        Teacher teacher = TeacherModelRepository.getModel();
        department.addTeacher(teacher);
        assertTrue(teacher.getDepartment().equals(department));
    }
}
