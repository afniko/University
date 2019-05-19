package ua.com.foxminded.task.domain.repository;

import ua.com.foxminded.task.domain.Auditory;

public class AuditoryModelRepository {

    private static Auditory auditory;

    public static Auditory getModel(TestModel testModel) {
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
        case MODEL_6:
            createModel6();
            break;
        }
        return auditory;
    }

    private static void createModel1() {

    }

    private static void createModel2() {

    }

    private static void createModel3() {

    }

    private static void createModel4() {

    }

    private static void createModel5() {

    }

    private static void createModel6() {

    }

}
