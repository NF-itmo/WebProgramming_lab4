package org.web3.services.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.xml.soap.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class JwtAuthFilter implements SOAPHandler<SOAPMessageContext> {
    private static final String AUTH_HEADER_NAMESPACE = "http://bearer.services.web3.org/";
    private static final String AUTH_HEADER_NAME = "Authorization";

    private final JwtVerifier jwtVerifier = new JwtVerifier("AuthService");

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (!outbound) {
            try {
                SOAPMessage message = context.getMessage();
                SOAPPart soapPart = message.getSOAPPart();
                SOAPEnvelope envelope = soapPart.getEnvelope();
                SOAPHeader header = envelope.getHeader();

                if (header == null)  throw new RuntimeException("Missing SOAP header");

                String jwtToken = extractJwtToken(header);

                if (jwtToken == null || jwtToken.isEmpty())   throw new RuntimeException("Missing JWT token");

                DecodedJWT decodedToken = jwtVerifier.verify(jwtToken);

                context.put("token", decodedToken);
                context.setScope("token", MessageContext.Scope.APPLICATION);

            } catch (SOAPException e) {
                throw new RuntimeException("SOAP processing error: " + e.getMessage(), e);
            } catch (JWTVerificationException e) {
                throw new RuntimeException("Invalid JWT token");
            } catch (Exception e) {
                throw new RuntimeException("Authentication error: " + e.getMessage(), e);
            }
        }

        return true;
    }

    private String extractJwtToken(SOAPHeader header) throws SOAPException {
        QName authQName = new QName(AUTH_HEADER_NAMESPACE, AUTH_HEADER_NAME);
        Iterator<?> headerElements = header.getChildElements(authQName);

        if (headerElements.hasNext()) {
            SOAPHeaderElement authElement = (SOAPHeaderElement) headerElements.next();
            String authValue = authElement.getValue();

            if (authValue != null && authValue.startsWith("Bearer ")) {
                return authValue.substring(7);
            }
        }

        return null;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logger.error("JWT Auth Handler encountered a fault");
        System.err.println("JWT Auth Handler encountered a fault");
        return true;
    }

    @Override
    public void close(MessageContext context) {}

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }
}