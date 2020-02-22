package ua.com.foxminded.task.dao.filter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import ua.com.foxminded.task.domain.Group;
import ua.com.foxminded.task.domain.Group_;
import ua.com.foxminded.task.domain.Teacher_;
import ua.com.foxminded.task.domain.TimetableItem;
import ua.com.foxminded.task.domain.TimetableItem_;
import ua.com.foxminded.task.domain.dto.FiltersDto;

public class TimetableItemSpecification implements Specification<TimetableItem>{

    private static final long serialVersionUID = 8089427019528112580L;
    
    private FiltersDto filters; 

    public TimetableItemSpecification(FiltersDto filters) {
        this.filters = filters;
    }

    @Override
    public Predicate toPredicate(Root<TimetableItem> timetableRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Predicate result = null;
        Predicate predicateBetween = builder.between(timetableRoot.get(TimetableItem_.DATE), filters.getStartDate(), filters.getEndDate());
        Predicate predicateTeacher = builder.equal(timetableRoot.get(TimetableItem_.TEACHER).get(Teacher_.ID), filters.getTeacherId());
        

        if (filters.getStudentId() == 0 && filters.getTeacherId() == 0) {
            result = predicateBetween;
        } else if (filters.getStudentId() == 0 && filters.getTeacherId() != 0) {
            result = builder.and(predicateBetween, predicateTeacher);
        } else if (filters.getStudentId() != 0 && filters.getTeacherId() == 0) {

            Join<TimetableItem, Group> joinTimGr = timetableRoot.join(TimetableItem_.GROUPS, JoinType.INNER);
//            Root<Student> studentRoot = query.from(Student.class);
//            Root<Group> rootGroup = query.from(Group.class);
//            Join<Student, Group> joinGrSt = studentRoot.join(Student_.GROUP);
//            Predicate predicateGrFrSt = builder.equal(joinGrSt.get(Student_.ID), filters.getStudentId());
//            result = builder.and(predicateGrFrSt.in(joinTimGr));
            result = builder.and(builder.equal(joinTimGr.get(Group_.ID), 2));
            
        } else if (filters.getStudentId() != 0 && filters.getTeacherId() != 0) {
        }

        return result;
    }

}
