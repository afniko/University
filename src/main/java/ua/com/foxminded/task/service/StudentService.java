package ua.com.foxminded.task.service;

import java.util.List;

import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.dto.StudentDto;

public interface StudentService {

    public StudentDto findByIdDto(int id);

    public List<StudentDto> findAllDto();

    public StudentDto create(StudentDto studentDto);

    public StudentDto update(StudentDto studentDto);

    public Student findByIdFees(Integer idFees);

    public long countByGroupId(Integer id);
    
    public boolean existsStudentByIdAndGroupId(Integer studentId, Integer groupId);

}
