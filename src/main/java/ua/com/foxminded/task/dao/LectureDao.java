package ua.com.foxminded.task.dao;

import java.util.List;

import ua.com.foxminded.task.domain.Lecture;

public interface LectureDao {

    public boolean create(Lecture lecture);

    public Lecture findById(Lecture lecture);

    public List<Lecture> findAll();

    public Lecture findByNumber(Lecture lectures);
}
