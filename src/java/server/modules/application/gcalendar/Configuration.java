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
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.AclRule.Scope;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;


/**
 *
 * @author Renan
 */
public class Configuration {
    
    public static com.google.api.services.calendar.Calendar calService = null;
    
    public Configuration() {
        
    }
    
    private void setUp1() throws IOException {
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
        com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(httpTransport, jsonFactory, credential)
                                        .build();

        //service.setApplicationName("YOUR_APPLICATION_NAME");
    }
    
    public boolean setup() throws GeneralSecurityException, IOException {
        
        System.out.println("SETUP");
        
        if (calService == null) {
        
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            // Build service account credential.
            GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setServiceAccountId("1060354502110-arvo6rfop11bqh3ak59t61u5d7o6kc19@developer.gserviceaccount.com")
                .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
                .setServiceAccountPrivateKeyFromP12File(new File("E:\\API Project-bd918790204d.p12"))
                .build();

            calService = new com.google.api.services.calendar.Calendar.Builder(httpTransport, jsonFactory, credential)
                                            .setApplicationName("UFRGS-TGUbipriCalNotifMan/1.0")
                                            .build();
            return true;
        } else {
            return false;
        }
    }
    
    public String createCalendar() throws IOException {
        Calendar calendar = new Calendar();

        calendar.setSummary("ServiceCalendar10");
        calendar.setTimeZone("America/Sao_Paulo");

        Calendar createdCalendar = calService.calendars().insert(calendar).execute();

        String calendarId = createdCalendar.getId();
        System.out.println(calendarId);
        
        return calendarId;
    }
    
    public String shareCalendar(String calendarId) throws IOException {
        AclRule rule = new AclRule();
        Scope scope = new Scope();

        scope.setType("user");
        scope.setValue("rmdrabach@gmail.com");
        rule.setScope(scope);
        rule.setRole("writer");
        
        AclRule createdRule = calService.acl().insert(calendarId, rule).execute();
        
        String ruleId = createdRule.getId();
        System.out.println(ruleId);
        
        return ruleId;
    }
    
    public String createEvent(String calendarId) throws IOException {
        
        Event event = new Event();

        event.setSummary("teste service");
        event.setLocation("ufrgs porto alegre");

        //ArrayList<EventAttendee> attendees = new ArrayList<>();
        //attendees.add(new EventAttendee().setEmail("attendeeEmail"));
        //event.setAttendees(attendees);

        // Começa em uma hora.
        Date startDate = new Date(new Date().getTime() + 3600000);
        // Uma hora de duração.
        Date endDate = new Date(startDate.getTime() + 3600000);
        
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone("America/Sao_Paulo"));
        event.setStart(new EventDateTime().setDateTime(start));
        
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone("America/Sao_Paulo"));
        event.setEnd(new EventDateTime().setDateTime(end));

        Event createdEvent = calService.events().insert(calendarId, event).execute();

        String eventId = createdEvent.getId();
        System.out.println(eventId);
        
        return eventId;
    }

}
