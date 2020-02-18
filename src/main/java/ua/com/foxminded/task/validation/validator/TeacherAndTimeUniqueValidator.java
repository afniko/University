package ua.com.foxminded.task.validation.validator;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.foxminded.task.service.TimetableItemService;
import ua.com.foxminded.task.validation.annotation.TeacherAndTimeUnique;

public class TeacherAndTimeUniqueValidator implements ConstraintValidator<TeacherAndTimeUnique, Object> {

    private String message;
    private String fieldError;

    @Autowired
    private TimetableItemService timetableItemService;
    @Autowired
    private Logger logger;

    @Override
    public void initialize(TeacherAndTimeUnique annotation) {
        this.message = annotation.message();
        this.fieldError = annotation.fieldError();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean result = true;
        String fieldTeacherId;
        String fieldLectureId;
        String fieldDate;

        try {
            fieldTeacherId = BeanUtils.getProperty(value, "teacherId");
            fieldLectureId = BeanUtils.getProperty(value, "lectureId");
            fieldDate = BeanUtils.getProperty(value, "date");
            
            Integer teacherId = Integer.valueOf(fieldTeacherId);
            Integer lectureId = Integer.valueOf(fieldLectureId);
            LocalDate date =  LocalDate.parse(fieldDate);
            
                result = !timetableItemService.existsByTeacherIdAndLectureIdAndDate(teacherId, lectureId, date);

            if (!result) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldError).addConstraintViolation();
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.warn("isValid() [Object:{}], {}", value, e);
        }
        return result;
    }

}
