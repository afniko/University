package ua.com.foxminded.task.service;

import java.util.List;

import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;

public interface StudentService {

    public StudentDto findByIdDto(int id);

    public List<StudentDto> findAllDto();

    public Student create(Student student);

    public StudentDto update(Student student);

}
