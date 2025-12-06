package org.web3.services.history.DTOs;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@XmlRootElement
@AllArgsConstructor @NoArgsConstructor
public class PointHistoryResponseArray {
    PointHistoryResponse[] pointsArray;

    @XmlElement(name = "point")
    public PointHistoryResponse[] getPointsArray() {
        return pointsArray;
    }
}
