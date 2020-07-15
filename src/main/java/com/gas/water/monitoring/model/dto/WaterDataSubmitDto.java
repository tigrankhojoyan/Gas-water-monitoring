package com.gas.water.monitoring.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gas.water.monitoring.model.WaterType;
import com.gas.water.monitoring.service.validation.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WaterDataSubmitDto {

    @NotNull
    @Min(value = 0, message = "The water amount must be positive number")
    private BigDecimal waterAmount;

    private LocalDateTime submittedTime;

    @ValueOfEnum(enumClass = WaterType.class)
    private String waterType;
}
