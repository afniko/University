package ua.com.foxminded.task.domain.repository;

import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task.domain.Subject;

public class SubjectModelRepository {

    private static Subject subject;
    private static List<Subject> subjects;

    public static Subject getModel(TestModel testModel) {
        switch (testModel) {
        case MODEL_1:
            createModel1();
            break;
        case MODEL_2:
            createModel2();
            break;
        case MODEL_3:
            createModel3();
            break;
        case MODEL_4:
            createModel4();
            break;
        case MODEL_5:
            createModel5();
            break;
        case MODEL_EMPTY:
            createModel6();
            break;
        }
        return subject;
    }

    public static List<Subject> getList(TestModel testModel) {
        subjects = new ArrayList<>();
        switch (testModel) {
        case MODEL_1:
            createModel1();
            subjects.add(subject);
            createModel2();
            subjects.add(subject);
            break;
        case MODEL_2:
            createModel2();
            subjects.add(subject);
            createModel3();
            subjects.add(subject);
            break;
        case MODEL_3:
            createModel1();
            subjects.add(subject);
            createModel6();
            subjects.add(subject);
            break;
        case MODEL_4:
            createModel4();
            subjects.add(subject);
            createModel5();
            subjects.add(subject);
            break;
        case MODEL_5:
            createModel3();
            subjects.add(subject);
            createModel6();
            subjects.add(subject);
            break;
        case MODEL_EMPTY:
            createModel3();
            subjects.add(subject);
            createModel5();
            subjects.add(subject);
            break;
        }
        return subjects;
    }

    private static void createModel1() {
        subject = new Subject();
        subject.setId(1);
        subject.setTitle("Phisics");
    }

    private static void createModel2() {
        subject = new Subject();
        subject.setId(2);
        subject.setTitle("Mathmatics");
    }

    private static void createModel3() {
        subject = new Subject();
        subject.setId(3);
        subject.setTitle("Biologic");
    }

    private static void createModel4() {
        subject = new Subject();
        subject.setId(4);
        subject.setTitle("History");
    }

    private static void createModel5() {
        subject = new Subject();
        subject.setId(5);
        subject.setTitle("Filosophi");
    }

    private static void createModel6() {
        subject = new Subject();
        subject.setId(6);
        subject.setTitle("Programming");
    }

}
