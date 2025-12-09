package org.historyService.controllers.rest.DTO.groups;

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
public class CreateGroupsRequest {
    @NotNull
    @NotEmpty(message = "groupName must be specified")
    private String groupName;
}
