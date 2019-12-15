package ua.com.foxminded.task.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ua.com.foxminded.task.validator.constraint.StudentIdFeesUniqueValidator;

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
