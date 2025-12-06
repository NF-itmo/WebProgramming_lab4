package org.web3.services.history.DTOs;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
public class PointsCountResponse {
    private long count;

    @XmlElement
    public long getCount() {
        return count;
    }
}