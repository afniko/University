package ua.com.foxminded.task.service.converter;

import static java.util.Objects.nonNull;

import ua.com.foxminded.task.domain.Auditory;
import ua.com.foxminded.task.domain.AuditoryType;
import ua.com.foxminded.task.domain.Department;
import ua.com.foxminded.task.domain.Faculty;
import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Lecture;
import ua.com.foxminded.task.domain.Student;
import ua.com.foxminded.task.domain.Subject;
import ua.com.foxminded.task.domain.Teacher;
import ua.com.foxminded.task.domain.dto.AuditoryDto;
import ua.com.foxminded.task.domain.dto.AuditoryTypeDto;
import ua.com.foxminded.task.domain.dto.DepartmentDto;
import ua.com.foxminded.task.domain.dto.FacultyDto;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.LectureDto;
import ua.com.foxminded.task.domain.dto.StudentDto;
import ua.com.foxminded.task.domain.dto.SubjectDto;
import ua.com.foxminded.task.domain.dto.TeacherDto;

public final class ConverterToDtoService {

    private ConverterToDtoService() {
    }

    public static AuditoryDto convert(Auditory auditory) {
        AuditoryDto auditoryDto = new AuditoryDto();
        auditoryDto.setId(auditory.getId());
        auditoryDto.setAuditoryNumber(auditory.getAuditoryNumber());
        if (nonNull(auditory.getAuditoryType())) {
            auditoryDto.setAuditoryTypeTitle(auditory.getAuditoryType().getType());
            auditoryDto.setIdAuditoryType(auditory.getAuditoryType().getId());
        }
        auditoryDto.setMaxCapacity(auditory.getMaxCapacity());
        auditoryDto.setDescription(auditory.getDescription());
        return auditoryDto;
    }
    
    public static AuditoryTypeDto convert(AuditoryType auditoryType) {
        AuditoryTypeDto auditoryTypeDto = new AuditoryTypeDto();
        auditoryTypeDto.setId(auditoryType.getId());
        auditoryTypeDto.setType(auditoryType.getType());
        return auditoryTypeDto;
    }
    
    public static DepartmentDto convert(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setTitle(department.getTitle());
        departmentDto.setDescription(department.getDescription());
        if (nonNull(department.getFaculty())) {
            departmentDto.setFacultyTitle(department.getFaculty().getTitle());
            departmentDto.setIdFaculty(department.getFaculty().getId());
        }
        return departmentDto;
    }
    
    public static FacultyDto convert(Faculty faculty) {
        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setId(faculty.getId());
        facultyDto.setTitle(faculty.getTitle());
        return facultyDto;
    }

    public static GroupDto convert(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setTitle(group.getTitle());
        groupDto.setYearEntry(group.getYearEntry());
        return groupDto;
    }
    
    public static LectureDto convert(Lecture lecture) {
        LectureDto lectureDto = new LectureDto();
        lectureDto.setId(lecture.getId());
        lectureDto.setNumber(lecture.getNumber());
        lectureDto.setStartTime(lecture.getStartTime());
        lectureDto.setEndTime(lecture.getEndTime());
        return lectureDto;
    }
    
    public static StudentDto convert(Student student) {
        StudentDto studentDto = new StudentDto();
        if (nonNull(student.getId())) {
            studentDto.setId(student.getId());
        }
        studentDto.setFirstName(student.getFirstName());
        studentDto.setMiddleName(student.getMiddleName());
        studentDto.setLastName(student.getLastName());
        studentDto.setBirthday(student.getBirthday());
        studentDto.setIdFees(student.getIdFees());
        if (nonNull(student.getGroup())) {
            studentDto.setIdGroup(student.getGroup().getId());
            studentDto.setGroupTitle(student.getGroup().getTitle());
        }
        return studentDto;
    }
    
    public static SubjectDto convert(Subject subject) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subject.getId());
        subjectDto.setTitle(subject.getTitle());
        return subjectDto;
    }
    
    public static TeacherDto convert(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setMiddleName(teacher.getMiddleName());
        teacherDto.setBirthday(teacher.getBirthday());
        teacherDto.setIdFees(teacher.getIdFees());
        if (nonNull(teacher.getDepartment())) {
            teacherDto.setDepartmentTitle(teacher.getDepartment().getTitle());
            teacherDto.setIdDepartment(teacher.getDepartment().getId());
        }
        return teacherDto;
    }

}
