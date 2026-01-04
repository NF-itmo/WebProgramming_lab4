package org.historyService.controllers.rest.DTO.history;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetHistoryRequestQueryParams {
    @QueryParam(value = "groupId")
    @NotNull(message = "groupId must be specified")
    @Min(value = 0, message = "groupId must be >= 0")
    private int groupId;

    @QueryParam(value = "start")
    @NotNull(message = "start must be specified")
    @Min(value = 0, message = "start must be >= 0")
    private int start = 0;

    @QueryParam(value = "length")
    @NotNull(message = "length must be specified")
    @Min(value = 1, message = "length must be >= 1")
    @Max(value = 100, message = "length must be <= 100")
    private int length = 10;
}