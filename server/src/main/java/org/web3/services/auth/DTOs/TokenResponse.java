package org.web3.services.auth.DTOs;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@XmlRootElement
@AllArgsConstructor @NoArgsConstructor
public class TokenResponse {
    private String token;

    @XmlElement
    public String getToken() {
        return token;
    }
}
