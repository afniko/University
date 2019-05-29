package ua.com.foxminded.task.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ua.com.foxminded.task.domain.repository.SubjectModelRepository;
import ua.com.foxminded.task.domain.repository.TeacherModelRepository;

@RunWith(JUnitPlatform.class)
public class TeacherTest {

    @Test
    public void whenAddSubjectToTeacher_thenTeacherContainsSubject() {
        Teacher teacher  = TeacherModelRepository.getEmptyModel();
        Subject subject = SubjectModelRepository.getModel();
        teacher.addSubject(subject);
        assertTrue(teacher.getSubjects().contains(subject));
    }
    
    @Test
    public void whenRemoveSubjectToTeacher_thenTeacherNonContainsSubject() {
        Teacher teacher  = TeacherModelRepository.getModel1();
        Subject subject = SubjectModelRepository.getModel();
        teacher.removeSubject(subject);
        assertFalse(teacher.getSubjects().contains(subject));
    }
    
}
