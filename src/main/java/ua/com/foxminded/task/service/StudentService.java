package ua.com.foxminded.task.service;

import java.util.List;

import ua.com.foxminded.task.domain.dto.StudentDto;

public interface StudentService {

    public StudentDto findByIdDto(int id);

    public List<StudentDto> findAllDto();

    public StudentDto create(StudentDto studentDto);

    public StudentDto update(StudentDto studentDto);

}
