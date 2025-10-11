package com.sportyfi.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
public class GoogleAuthService {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    public GoogleIdToken.Payload verifyCode(String code) throws Exception {
        System.out.println("Received code: " + code);
        
        GoogleAuthorizationCodeTokenRequest tokenRequest = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                "https://oauth2.googleapis.com/token",
                clientId,
                clientSecret,
                code,
                redirectUri
        );

        GoogleTokenResponse tokenResponse = tokenRequest.execute();

        System.out.println("Token response: " + tokenResponse.toPrettyString());

        GoogleIdToken idToken = tokenResponse.parseIdToken();
        if (idToken == null) {
            throw new IllegalArgumentException("ID Token is null in Google response");
        }
        return idToken.getPayload();
    }

}

