package ua.com.foxminded.task.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ua.com.foxminded.task.validator.constraint.MaxStudentsInGroupLimitValidator;

@Documented
@Constraint(validatedBy = MaxStudentsInGroupLimitValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxStudentsInGroupLimit {

    int value();

    String message() default "Max participant in group!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
