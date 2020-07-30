package app.validations.anotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE,ANNOTATION_TYPE })
@Retention(RUNTIME)
/*@Constraint(validatedBy = DateComparatorValidator.class)*/
@Documented
public @interface DateComparator {
}
