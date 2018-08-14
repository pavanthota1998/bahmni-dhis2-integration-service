package com.thoughtworks.bahmnidhis2integrationservice.security;

import com.thoughtworks.bahmnidhis2integrationservice.config.AppProperties;
import com.thoughtworks.bahmnidhis2integrationservice.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.thoughtworks.bahmnidhis2integrationservice.util.PrivilegeUtil.APP_DHIS2SYNC;

@Component
public class OpenMRSAuthenticator {

    private static final String WHOAMI_URL = "/bahmnicore/whoami";
    private static final String OPENMRS_SESSION_ID_COOKIE_NAME = "JSESSIONID";

    @Autowired
    private AppProperties appProperties;

    public AuthenticationResponse authenticate(String sessionId) {
        ResponseEntity<PrivilegeUtil.Privileges> response = callOpenMRS(sessionId);
        HttpStatus status = response.getStatusCode();

        if (status.series() == HttpStatus.Series.SUCCESSFUL) {
            PrivilegeUtil.savePrivileges(response.getBody());
            return PrivilegeUtil.hasPrivilege(APP_DHIS2SYNC)?
                    AuthenticationResponse.AUTHORIZED:
                    AuthenticationResponse.UNAUTHORIZED;
        }

        return AuthenticationResponse.NOT_AUTHENTICATED;
    }

    private ResponseEntity<PrivilegeUtil.Privileges> callOpenMRS(String sessionId) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", OPENMRS_SESSION_ID_COOKIE_NAME + "=" + sessionId);
        try {
            return new RestTemplate()
                    .exchange(appProperties.getOpenmrsRootUrl() + WHOAMI_URL,
                            HttpMethod.GET,
                            new HttpEntity<>(null, requestHeaders),
                            PrivilegeUtil.Privileges.class
                    );
        } catch (HttpClientErrorException exception) {
            return new ResponseEntity<>(exception.getStatusCode());
        }
    }
}
