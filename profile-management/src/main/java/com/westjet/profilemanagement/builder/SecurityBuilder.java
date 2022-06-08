package com.westjet.profilemanagement.builder;

import com.westjet.profilemanagement.model.wsdl.ObjectFactory;
import com.westjet.profilemanagement.model.wsdl.Security;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityBuilder {

    private final ObjectFactory objectFactory;

    public Security buildSecurity() {
        final Security security = objectFactory.createSecurity();
        security.setUsernameToken(buildUsernameToken());

        return security;
    }

    private Security.UsernameToken buildUsernameToken() {
        final Security.UsernameToken securityUsernameToken = objectFactory.createSecurityUsernameToken();
        securityUsernameToken.setUsername("800075");
        securityUsernameToken.setPassword("IDMGMT02");
        securityUsernameToken.setOrganization("WS");
        securityUsernameToken.setDomain("WS");

        return securityUsernameToken;
    }
}
