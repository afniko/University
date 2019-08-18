package ua.com.foxminded.task.service;

import java.util.List;

import ua.com.foxminded.task.domain.dto.StudentDto;

public interface StudentService {

    public StudentDto findById(int id);

    public List<StudentDto> findAll();

}
