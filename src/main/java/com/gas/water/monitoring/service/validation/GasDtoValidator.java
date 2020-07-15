package com.gas.water.monitoring.service.validation;

import com.gas.water.monitoring.model.dto.GasDataSubmitDto;
import com.gas.water.monitoring.model.dto.SubscriberDto;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

/**
 * Class intended to validate new gas usage request data
 */
@Service
public class GasDtoValidator extends DataValidator<GasDataSubmitDto> {

    private final Validator validator;

    public GasDtoValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public String getExceptionMessage() {
        return "Water data update request contains invalid fields";
    }
}
