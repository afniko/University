package ua.com.foxminded.task.domain.repository;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.Teacher;

public class TeacherModelRepository {

    public static Teacher getModel() {
        Teacher teacher = new Teacher();
        teacher.setId(6);
        teacher.setFirstName("firstName6");
        teacher.setMiddleName("middleName6");
        teacher.setLastName("lastName6");
        teacher.setBirthday(Date.valueOf("1980-06-25"));
        teacher.setIdFees(1111111161);
        teacher.setSubjects(SubjectModelRepository.getModels());
        return teacher;
    }

    public static List<Teacher> getModels() {
        return Arrays.asList(createModel1(), createModel2(), createModel3());
    }

    private static Teacher createModel1() {
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setFirstName("firstName1");
        teacher.setMiddleName("middleName1");
        teacher.setLastName("lastName1");
        teacher.setBirthday(Date.valueOf("1966-06-25"));
        teacher.setIdFees(1111111111);
        teacher.setSubjects(SubjectModelRepository.getModels());
        return teacher;
    }

    private static Teacher createModel2() {
        Teacher teacher = new Teacher();
        teacher.setId(2);
        teacher.setFirstName("firstName2");
        teacher.setMiddleName("middleName2");
        teacher.setLastName("lastName2");
        teacher.setBirthday(Date.valueOf("1950-06-25"));
        teacher.setIdFees(1331111111);
        teacher.setSubjects(SubjectModelRepository.getModels());
        return teacher;
    }

    private static Teacher createModel3() {
        Teacher teacher = new Teacher();
        teacher.setId(3);
        teacher.setFirstName("firstName3");
        teacher.setMiddleName("middleName3");
        teacher.setLastName("lastName3");
        teacher.setBirthday(Date.valueOf("1971-06-25"));
        teacher.setIdFees(1111111331);
        teacher.setSubjects(SubjectModelRepository.getModels());
        return teacher;
    }

}
