package ua.com.foxminded.task.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ua.com.foxminded.task.validator.constraint.GroupTitleUniqueValidator;

@Documented
@Constraint(validatedBy = GroupTitleUniqueValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupTitleUnique {

    String message() default "Title not unigue!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
