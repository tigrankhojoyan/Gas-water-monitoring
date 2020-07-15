package com.gas.water.monitoring.service.validation;

import com.gas.water.monitoring.exception.DataValidationException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Class contains generic functionality to validate data of specified type
 *
 * @param <T>
 */
@Slf4j
public abstract class DataValidator<T> {

    public void validateData(T data) {
        log.info("Validating request data - {}, type -({})", data, data.getClass().getName());
        Set<ConstraintViolation<T>> constraintViolations = getValidator().validate(data);
        if (!constraintViolations.isEmpty()) {
            StringBuilder fieldErrors = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                fieldErrors.append(constraintViolation.getPropertyPath().toString()).append(" property - ").
                        append(constraintViolation.getMessage()).append("(").append(constraintViolation.getInvalidValue()).append(")\n");
            }
            log.error("The following fields are incorrect in {} request \n {}", data.getClass().getName(), fieldErrors.toString());
            throw new DataValidationException(getExceptionMessage());
        }
    }

    public abstract Validator getValidator();

    public abstract String getExceptionMessage();
}
