package org.historyService.services.history.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetHistoryRequestQueryParams {
    @Min(value = 0, message = "start must be >= 0")
    private int start = 0;

    @Min(value = 1, message = "length must be >= 1")
    @Max(value = 100, message = "length must be <= 100")
    private int length = 10;
}
