/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.util.Date;

/**
 *
 * @author Estudo
 */
public class LogEvent {
    
    /* Turno: Diurno ou Noturno
        d - day shift
        n - night shift
    */
    public static final char DAY_SHIFT = 'd';
    public static final char NIGHT_SHIFT = 'n';
    
    /* Dia da Semana: Sim ou não
        0 - não: Final de semana
        1 - sim: Dia de semana
    */
    public static final int DAY_OF_WEEKEND = 0;
    public static final int DAY_OF_WEEK = 1;
    
    /* Dia útil ou não - Workday / 
        n - not - holliday ou weekend
        y - yes - workday
    */
    public static final char YES_WORKDAY = 'y';
    public static final char NOT_WORKDAY = 'n';
    
    private int id;
    private Date time;
    private Character shift;
    private Integer weekday;
    private Character workday;
    private String currentData;
    private String event;
    private Environment environment;
    private User user;
    private Device device;
    
    public LogEvent() {
        this.time = new Date();
        this.currentData = "";
    }

    public LogEvent(String currentData) {
        this();
        this.currentData = currentData;
    }
    
    public String getCurrentData() {
        return currentData;
    }

    public void setCurrentData(String currentData) {
        this.currentData = currentData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Character getShift() {
        return shift;
    }

    public void setShift(Character shift) {
        this.shift = shift;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Character getWorkday() {
        return workday;
    }

    public void setWorkday(Character workday) {
        this.workday = workday;
    }
    
    public void setTimeVariables(Date now){
        this.setTime(now);
        this.setShift(calculateShift(now));
        this.setWeekday(calculateWeekday(now));
        this.setWorkday(calculateWorkday(now));
    }
    
    private char calculateShift(Date now){
        // Função para culcular
        
        // retornar LogEvent.DAY_SHIFT ou LogEvent.NIGHT_SHIFT
        return LogEvent.DAY_SHIFT;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private char calculateWorkday(Date now){
        // Função para culcular
        
        // retornar LogEvent.YES_WORKDAY ou LogEvent.NOT_WORKDAY
        return LogEvent.YES_WORKDAY;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Integer calculateWeekday(Date now) {
        // Função para culcular
        
        // retornar LogEvent.DAY_OF_WEEK ou LogEvent.DAY_OF_WEEKEND
        return LogEvent.DAY_OF_WEEK;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
