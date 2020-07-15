package com.gas.water.monitoring.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SubscriberDto {

    @NotBlank
    @Pattern(regexp = "[A-Za-z]*", message = "FirstName must contain only letters")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "[A-Za-z]*", message = "LastName must contain only letters")
    private String lastName;
}
