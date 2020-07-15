package com.gas.water.monitoring.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GasDataSubmitDto {

    @NotNull
    @Min(value = 0, message = "The gas amount must be positive number")
    private BigDecimal usedGasContent;

    private LocalDateTime submittedTime;
}
