package org.historyService.controllers.rest.DTO.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointHistoryResponse {
    private float x;
    private float y;
    private float r;
    private boolean isHitted;
    private long timestamp;

    public PointHistoryResponse(float x, float y, float r, boolean isHitted, OffsetDateTime dateTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHitted = isHitted;
        this.timestamp = dateTime.toEpochSecond();
    }
}
