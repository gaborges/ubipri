/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.application.gcalendar;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.calendar.CalendarScopes;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.AclRule.Scope;
import com.google.api.services.calendar.model.Event.Reminders;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TimeZone;


/**
 *
 * @author Renan
 */
public class CalendarManager {
    
    public static final String TIME_ZONE_SAO_PAULO = "America/Sao_Paulo";
    public static final String EVENT_RESPONSE_STATUS_DECLINED = "declined";
    public static final String EVENT_REMINDER_MODE_EMAIL = "email";
    public static final String EVENT_REMINDER_MODE_SMS = "sms";
    public static final String EVENT_REMINDER_MODE_POPUP = "popup";
    
    private static com.google.api.services.calendar.Calendar calService = null;
    
    public CalendarManager() {
        try {
            //setup();
            authorize();
        } catch (GeneralSecurityException|IOException e) {
            System.out.println(e);
        }
    }
    
    private void setup() throws GeneralSecurityException, IOException {
        
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
        }
    }
    
    private void authorize() throws IOException, GeneralSecurityException {
        
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        
        String client_id = "1060354502110-ho0eica6il5t0hgvcdd80iiu9ee2ve2u.apps.googleusercontent.com";
        String client_secret = "cEHqpFFyxIDIVCP6a9QaFTXs";
        
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            httpTransport, jsonFactory, client_id, client_secret, 
                Arrays.asList(CalendarScopes.CALENDAR))
            .setAccessType("offline")
            .setApprovalPrompt("auto")
            .build();
        
        String firstAccessToken = "4/gP83ZfVK9boLfYsTRSn9jg.cs4EPwOx-gMVJvIeHux6iLYFYrsMkgI";
        String refreshToken = "1/tSn8DzB6KfhaT8ExeNNSJpA7EpvYjDaTyQF1f3WI_J0";
        
        GoogleTokenResponse response;
        
        if (!refreshToken.isEmpty()) {
            response = new GoogleRefreshTokenRequest(
                httpTransport, jsonFactory, refreshToken, client_id, client_secret)
                .execute();
        } else {
            response = flow.newTokenRequest(firstAccessToken)
                .setRedirectUri("http://localhost:8084/UbipriServer/HandleAuthorize")
                .setGrantType("authorization_code")
                .execute();
        }
        
        System.out.println(response.toString());
        
        // http://stackoverflow.com/questions/15064636/googlecredential-wont-build-without-googlecredential-builder
        // Para "online" mode
        // GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
        // Para "offline" mode
        GoogleCredential credential = new GoogleCredential.Builder()
            .setTransport(httpTransport)
            .setJsonFactory(jsonFactory)
            .setClientSecrets("1060354502110-ho0eica6il5t0hgvcdd80iiu9ee2ve2u.apps.googleusercontent.com", 
                    "cEHqpFFyxIDIVCP6a9QaFTXs")
            .build()
            .setFromTokenResponse(response);

        // Create a new authorized API client
        calService = new com.google.api.services.calendar
            .Calendar.Builder(httpTransport, jsonFactory,credential)
            .build();
    }
    
    public boolean isCalendarServiceConfigured() {
        return calService != null;
    }
    
    public String createCalendar(String name, String timeZone) throws IOException {
        Calendar calendar = new Calendar();

        calendar.setSummary(name);
        calendar.setTimeZone(timeZone);

        Calendar createdCalendar = calService.calendars().insert(calendar).execute();

        String calendarId = createdCalendar.getId();
        System.out.println(calendarId);
        
        return calendarId;
    }
    
    public String shareCalendar(String calendarId, String scopeType, String scopeValue, String role) throws IOException {
        AclRule rule = new AclRule();
        Scope scope = new Scope();

        scope.setType(scopeType);
        scope.setValue(scopeValue);
        rule.setScope(scope);
        rule.setRole(role);
        
        AclRule createdRule = calService.acl().insert(calendarId, rule).execute();
        
        String ruleId = createdRule.getId();
        System.out.println(ruleId);
        
        return ruleId;
    }
    
    public String createEvent(String calendarId, String name, String location, List<String> attendeesEmails, String timeZone) throws IOException {
        
        Event event = new Event();

        event.setSummary(name);
        event.setLocation(location);

        ArrayList<EventAttendee> eventAttendees = new ArrayList<>();
        
        for (String attendeeMail : attendeesEmails) {
            eventAttendees.add(new EventAttendee().setEmail(attendeeMail));
        }
        
        if (!eventAttendees.isEmpty()) {
            event.setAttendees(eventAttendees);
        }

        // Começa em uma (duas?) hora.
        Date startDate = new Date(new Date().getTime() + 3600000);
        // Uma hora de duração.
        Date endDate = new Date(startDate.getTime() + 3600000);
        
        DateTime start = new DateTime(startDate, TimeZone.getTimeZone(timeZone));
        event.setStart(new EventDateTime().setDateTime(start));
        
        DateTime end = new DateTime(endDate, TimeZone.getTimeZone(timeZone));
        event.setEnd(new EventDateTime().setDateTime(end));

        Event createdEvent = calService.events().insert(calendarId, event).execute();

        String eventId = createdEvent.getId();
        System.out.println(eventId);
        
        return eventId;
    }
    
    public String updateNotificationMethod(String calendarId, String userCalendarMail, String notificationMode) throws IOException {
        Events events = calService.events().list(calendarId).execute();
        
        List<Entry<String, String>> updatedEvents = new ArrayList<>();
        
        if (events != null) {
            
            List<Event> eventsList = events.getItems();
            for (Event event : eventsList) {

                List<EventAttendee> eventAttendeeList = event.getAttendees();
                if (eventAttendeeList != null) {
                
                    for (EventAttendee attendee : event.getAttendees()) {
                        if (attendee.getEmail().equalsIgnoreCase(userCalendarMail)
                            && !attendee.getResponseStatus().equalsIgnoreCase(EVENT_RESPONSE_STATUS_DECLINED)) {

                            EventReminder reminder = new EventReminder();
                            reminder.setMethod(notificationMode);
                            reminder.setMinutes(10);

                            ArrayList<EventReminder> remindersList = new ArrayList<>();
                            remindersList.add(reminder);

                            Reminders reminders = new Reminders();
                            reminders.setOverrides(remindersList);
                            reminders.setUseDefault(false);

                            event.setReminders(reminders);

                            Event updatedEvent = calService.events().update(calendarId, event.getId(), event).execute();

                            Entry<String, String> eventIdNotifMethodPair = new SimpleEntry<>(updatedEvent.getId(), updatedEvent.getReminders().getOverrides().get(0).getMethod());
                            System.out.println(eventIdNotifMethodPair);

                            updatedEvents.add(eventIdNotifMethodPair);
                        }
                    }
                }
            }
        }
        
        return Integer.toString(updatedEvents.size());
    }

}
