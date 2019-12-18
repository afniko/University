package ua.com.foxminded.task.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ua.com.foxminded.task.validation.validator.StudentIdFeesUniqueValidator;

@Documented
@Constraint(validatedBy = StudentIdFeesUniqueValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StudentIdFeesUnique {

    String message() default "Id fees not unigue!";

    String fieldName() default "idFees";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
