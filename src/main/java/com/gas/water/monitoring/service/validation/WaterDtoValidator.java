package com.gas.water.monitoring.service.validation;

import com.gas.water.monitoring.model.dto.WaterDataSubmitDto;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

/**
 * Class intended to validate new water usage request data
 */
@Service
public class WaterDtoValidator extends DataValidator<WaterDataSubmitDto> {

    private final Validator validator;

    public WaterDtoValidator(Validator validator) {
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
