package org.web3.services.checker.DTOs;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.OffsetDateTime;

@XmlRootElement
@AllArgsConstructor @NoArgsConstructor
public class CheckResultResponse {
    boolean result;
    OffsetDateTime timestamp;

    @XmlElement
    public boolean getResult() {
        return result;
    }

    @XmlElement
    public long getTimestamp() {
        return timestamp.toEpochSecond();
    }
}
