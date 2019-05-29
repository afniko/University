package ua.com.foxminded.task.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.GroupModelRepository;
import ua.com.foxminded.task.domain.repository.StudentModelRepository;

@RunWith(JUnitPlatform.class)
public class GroupTest {

    @Test
    public void whenAddStudentToGroup_thenGroupContainsStudent() {
        Group group = GroupModelRepository.getEmptyModel();
        Student student = StudentModelRepository.getModel1();
        group.addStudent(student);
        assertTrue(group.getStudents().contains(student));
    }

    @Test
    public void whenAddStudentToGroup_thenStudentContainsGroup() {
        Group group = GroupModelRepository.getEmptyModel();
        Student student = StudentModelRepository.getModel1();
        group.addStudent(student);
        assertTrue(student.getGroup().equals(group));
    }

    @Test
    public void whenRemoveStudentFromGroup_thenGroupNonContainsStudent() {
        Group group = GroupModelRepository.getModel1();
        Student student = StudentModelRepository.getModel1();
        group.removeStudent(student);
        assertFalse(group.getStudents().contains(student));
    }

    @Test
    public void whenRemoveStudentFromGroup_thenStudentNonContainsGroup() {
        Group group = GroupModelRepository.getModel1();
        Student student = StudentModelRepository.getModel1();
        student.setGroup(group);
        group.removeStudent(student);
        assertNull(student.getGroup());
    }

}
