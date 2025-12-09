package org.historyService.controllers.rest.DTO.history;

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
public class GetCountRequestQueryParams {
    @NotNull
    @NotEmpty(message = "groupId must be specified")
    private int groupId;
}