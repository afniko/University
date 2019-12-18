package ua.com.foxminded.task.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.task.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    public List<Student> findAllByGroupId(Integer id);

    public Student findByIdFees(Integer idFees);
    
    public long countByGroupId(Integer id);
    
    public boolean existsStudentByIdAndGroupId(Integer studentId, Integer groupId);

}
