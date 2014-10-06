/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.application.gcalendar;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.calendar.CalendarScopes;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;


/**
 *
 * @author Renan
 */
public class Configuration {
    
    public Configuration() {
        
    }
    
    Event event = new Event();
    
    public void setUp() throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();

        // The clientId and clientSecret can be found in Google Developers Console
        String clientId = "1060354502110-arvo6rfop11bqh3ak59t61u5d7o6kc19.apps.googleusercontent.com";
        String clientSecret = "notasecret";

        // Or your redirect URL for web based applications.
        String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
        String scope = "https://www.googleapis.com/auth/calendar";

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, clientId, clientSecret,
                Arrays.asList(CalendarScopes.CALENDAR)).setAccessType("online")
                    .setApprovalPrompt("auto").build();

        String url = flow.newAuthorizationUrl().setRedirectUri(redirectUrl).build();
        System.out.println("Please open the following URL in your browser then type the authorization code:");

        System.out.println("  " + url);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String code = br.readLine();

        GoogleTokenResponse response = flow.newTokenRequest(code)
                                            .setRedirectUri(redirectUrl)
                                            .execute();
        GoogleCredential credential = new GoogleCredential()
                        .setFromTokenResponse(response);

        // Create a new authorized API client
        Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credential)
                                        .build();

        //service.setApplicationName("YOUR_APPLICATION_NAME");
    }
    
    public void setup2() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        
        // Build service account credential.
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
            .setJsonFactory(jsonFactory)
            .setServiceAccountId("1060354502110-arvo6rfop11bqh3ak59t61u5d7o6kc19@developer.gserviceaccount.com ")
            .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
            .setServiceAccountPrivateKeyFromP12File(new File("API Project-bd918790204d.p12"))
            .build();
        
        Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credential)
                                        .setApplicationName("UFRGS-TGUbipriCalNotifMan/1.0")
                                        .build();
    }

}
