/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model.calendar;

/**
 *
 * @author Renan
 */
public class CalendarAccess {

    private Integer userId;
    private String authCode;
    private String refreshToken;
    private String calendarMail;
    
    public CalendarAccess(Integer userId, String code, String refreshToken, String mail) {
        this.userId = userId;
        this.authCode = code;
        this.refreshToken = refreshToken;
        this.calendarMail = mail;
    }
    
    public Integer getUserId() {
        return userId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getCalendarMail() {
        return calendarMail;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setCalendarMail(String calendarMail) {
        this.calendarMail = calendarMail;
    }
    
}
