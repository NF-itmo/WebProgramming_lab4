package org.geometryService.repository.history.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPointRequest {
    @JsonProperty("x")
    private Float x;
    
    @JsonProperty("y")
    private Float y;
    
    @JsonProperty("r")
    private Float r;
    
    @JsonProperty("isHitted")
    private Boolean isHitted;
}

