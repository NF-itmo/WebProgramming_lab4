package org.historyService.controllers.rest.DTO.groups;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetGroupsRequestQueryParams {
    @NotNull(message = "start must be specified")
    @Min(value = 0, message = "start must be >= 0")
    private int start = 0;

    @NotNull(message = "length must be specified")
    @Min(value = 1, message = "length must be >= 1")
    @Max(value = 10, message = "length must be <= 10")
    private int length = 10;
}