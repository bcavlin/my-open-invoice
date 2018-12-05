package com.bgh.myopeninvoice.common.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ReportAsSingleViolation
public @interface EnumValidator {

    Class<? extends Enum<?>> enumClass();

    String message() default "Value is not valid";

    Class<? extends Payload>[] payload() default {};

}
