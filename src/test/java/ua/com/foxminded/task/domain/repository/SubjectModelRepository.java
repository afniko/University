package ua.com.foxminded.task.domain.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ua.com.foxminded.task.domain.Subject;

public class SubjectModelRepository {
    public static Subject getModel() {
        Subject subject = new Subject();
        subject.setId(6);
        subject.setTitle("Programming");
        return subject;
    }

    public static List<Subject> getModels() {
        List<Subject> subjects = Arrays.asList(createModel1(), createModel2(), createModel3());
        return new ArrayList<>(subjects);
    }

    private static Subject createModel1() {
        Subject subject = new Subject();
        subject.setId(1);
        subject.setTitle("Phisics");
        return subject;
    }

    private static Subject createModel2() {
        Subject subject = new Subject();
        subject.setId(2);
        subject.setTitle("Mathmatics");
        return subject;
    }

    private static Subject createModel3() {
        Subject subject = new Subject();
        subject.setId(3);
        subject.setTitle("Biologic");
        return subject;
    }

}
