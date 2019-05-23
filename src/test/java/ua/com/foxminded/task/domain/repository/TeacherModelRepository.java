package ua.com.foxminded.task.domain.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.domain.Teacher;

public class TeacherModelRepository {

    private static Teacher teacher;
    private static List<Teacher> teachers;

    public static Teacher getModel(TestModel testModel) {
        switch (testModel) {
        case MODEL_1:
            createModel1(testModel);
            break;
        case MODEL_2:
            createModel2(testModel);
            break;
        case MODEL_3:
            createModel3(testModel);
            break;
        case MODEL_4:
            createModel4(testModel);
            break;
        case MODEL_5:
            createModel5(testModel);
            break;
        case MODEL_6:
            createModel6(testModel);
            break;
        }
        return teacher;
    }

    public static List<Teacher> getList(TestModel testModel) {
        teachers = new ArrayList<>();
        switch (testModel) {
        case MODEL_1:
            createModel1(testModel);
            teachers.add(teacher);
            createModel2(testModel);
            teachers.add(teacher);
            break;
        case MODEL_2:
            createModel2(testModel);
            teachers.add(teacher);
            createModel3(testModel);
            teachers.add(teacher);
            break;
        case MODEL_3:
            createModel1(testModel);
            teachers.add(teacher);
            createModel6(testModel);
            teachers.add(teacher);
            break;
        case MODEL_4:
            createModel4(testModel);
            teachers.add(teacher);
            createModel5(testModel);
            teachers.add(teacher);
            break;
        case MODEL_5:
            createModel3(testModel);
            teachers.add(teacher);
            createModel6(testModel);
            teachers.add(teacher);
            break;
        case MODEL_6:
            createModel3(testModel);
            teachers.add(teacher);
            createModel5(testModel);
            teachers.add(teacher);
            break;
        }
        return teachers;
    }

    private static void createModel1(TestModel testModel) {
        teacher = new Teacher();
        teacher.setId(1);
        teacher.setFirstName("firstName1");
        teacher.setMiddleName("middleName1");
        teacher.setLastName("lastName1");
        teacher.setBirthday(Date.valueOf("1966-06-25"));
        teacher.setIdFees(1111111111);
        teacher.setSubjects(SubjectModelRepository.getList(testModel));
//        teacher.setDepartment(DepartmentModelRepository.getModel(testModel));
    }

    private static void createModel2(TestModel testModel) {
        teacher = new Teacher();
        teacher.setId(2);
        teacher.setFirstName("firstName2");
        teacher.setMiddleName("middleName2");
        teacher.setLastName("lastName2");
        teacher.setBirthday(Date.valueOf("1950-06-25"));
        teacher.setIdFees(1331111111);
        teacher.setSubjects(SubjectModelRepository.getList(testModel));
//        teacher.setDepartment(DepartmentModelRepository.getModel(testModel));
    }

    private static void createModel3(TestModel testModel) {
        teacher = new Teacher();
        teacher.setId(3);
        teacher.setFirstName("firstName3");
        teacher.setMiddleName("middleName3");
        teacher.setLastName("lastName3");
        teacher.setBirthday(Date.valueOf("1971-06-25"));
        teacher.setIdFees(1111111331);
        teacher.setSubjects(SubjectModelRepository.getList(testModel));
//        teacher.setDepartment(DepartmentModelRepository.getModel(testModel));
    }

    private static void createModel4(TestModel testModel) {
        teacher = new Teacher();
        teacher.setId(4);
        teacher.setFirstName("firstName4");
        teacher.setMiddleName("middleName4");
        teacher.setLastName("lastName4");
        teacher.setBirthday(Date.valueOf("1979-06-25"));
        teacher.setIdFees(1111111441);
        teacher.setSubjects(SubjectModelRepository.getList(testModel));
//        teacher.setDepartment(DepartmentModelRepository.getModel(testModel));
    }

    private static void createModel5(TestModel testModel) {
        teacher = new Teacher();
        teacher.setId(5);
        teacher.setFirstName("firstName5");
        teacher.setMiddleName("middleName5");
        teacher.setLastName("lastName5");
        teacher.setBirthday(Date.valueOf("1963-06-25"));
        teacher.setIdFees(1111111551);
        teacher.setSubjects(SubjectModelRepository.getList(testModel));
//        teacher.setDepartment(DepartmentModelRepository.getModel(testModel));
    }

    private static void createModel6(TestModel testModel) {
        teacher = new Teacher();
        teacher.setId(6);
        teacher.setFirstName("firstName6");
        teacher.setMiddleName("middleName6");
        teacher.setLastName("lastName6");
        teacher.setBirthday(Date.valueOf("1980-06-25"));
        teacher.setIdFees(1111111161);
        teacher.setSubjects(SubjectModelRepository.getList(testModel));
//        teacher.setDepartment(DepartmentModelRepository.getModel(testModel));
    }

}
