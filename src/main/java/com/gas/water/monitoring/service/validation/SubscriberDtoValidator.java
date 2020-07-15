package com.gas.water.monitoring.service.validation;

import com.gas.water.monitoring.model.dto.SubscriberDto;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

/**
 * Class intended to validate new subscriber data
 */
@Service
public class SubscriberDtoValidator extends DataValidator<SubscriberDto> {

    private final Validator validator;

    public SubscriberDtoValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public String getExceptionMessage() {
        return "Subscriber request contains invalid fields";
    }
}
