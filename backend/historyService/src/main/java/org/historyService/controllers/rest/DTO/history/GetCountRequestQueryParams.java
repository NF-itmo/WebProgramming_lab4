package org.historyService.controllers.rest.DTO.history;

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
public class GetCountRequestQueryParams {
    @QueryParam(value = "groupId")
    @NotNull(message = "groupId must be specified")
    private int groupId;
}