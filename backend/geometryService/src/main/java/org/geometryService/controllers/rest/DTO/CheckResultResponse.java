package org.geometryService.controllers.rest.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckResultResponse {
    private boolean result;
    private long timestamp;

    public CheckResultResponse(boolean result, OffsetDateTime dateTime) {
        this.result = result;
        this.timestamp = dateTime.toEpochSecond();
    }
}
