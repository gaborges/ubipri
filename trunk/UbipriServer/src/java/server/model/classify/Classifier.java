/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.model.classify;

import java.util.HashMap;
import server.model.AccessType;
import server.model.EnvironmentType;
import server.model.UserProfileEnvironment;

/**
 *
 * @author Borges
 * @param <V>
 */
public interface Classifier<V> {
    /* 
    Valores possíveis:
        shift: LogEvent.DAY_SHIFT ou NIGHT_SHIFT
        workday: LogEvent.YES_WORKDAY ou NOT_WORKDAY
        weekday: LogEvent.DAY_OF_WEEK; ou LogEvent.DAY_OF_WEEKEND;
        profile (id;nome): 
            1;"Unknown"
            2;"Transient"
            3;"User"
            4;"Responsible"
            5;"Student"
            6;"Manager"
        EnvironmentType (id;nome):
            1;"Blocked"
            2;"Private"
            3;"Public"
    Saídas
        1 = "Blocked"
        2 = "Guest"
        3 = "Basic"
        4 = "Advanced"
        5 = "Administrative"
    
    */
    
    
    public AccessType classify(UserProfileEnvironment profile, EnvironmentType typeEnvironment, Character shift, Character workday, Integer weekday);
    public void setExtraConfigurations(HashMap<String,V> map);
}
