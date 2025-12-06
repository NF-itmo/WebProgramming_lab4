package org.web3.services.checker.DTOs;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.web3.services.checker.validators.Step;

@Setter @Getter @AllArgsConstructor
public class CheckRequest {
    @NotNull
    @DecimalMin(value = "-2.0", message = "x must be >= -2")
    @DecimalMax(value = "2.0", message = "x must be <= 2")
    @Step(value = 0.5f, message = "x must have a step of 0.5")
    private Float x;

    @NotNull
    @DecimalMin(value = "-3.0", message = "y must be >= -3")
    @DecimalMax(value = "3.0", message = "y must be <= 3")
    private Float y;

    @NotNull
    @DecimalMin(value = "0.5", message = "r must be >= 0.5")
    @DecimalMax(value = "2.0", message = "r must be <= 2")
    @Step(value = 0.5f, message = "r must have a step of 0.5")
    private Float r;
}
