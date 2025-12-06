package org.web3.services.history.DTOs;

import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.OffsetDateTime;

@AllArgsConstructor @NoArgsConstructor
public class PointHistoryResponse {
    float X;
    float Y;
    float R;
    boolean isHitted;
    OffsetDateTime timestamp;

    @XmlElement
    public float getX() {
        return X;
    }

    @XmlElement
    public float getY() {
        return Y;
    }

    @XmlElement
    public float getR() {
        return R;
    }

    @XmlElement
    public boolean isHitted() {
        return isHitted;
    }

    @XmlElement
    public long getTimestamp() {
        return timestamp.toEpochSecond();
    }
}
