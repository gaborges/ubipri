/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.model.classify;

import java.util.HashMap;
import server.model.AccessType;
import server.model.EnvironmentType;
import server.model.LogEvent;
import server.model.UserProfileEnvironment;

/**
 *
 * @author Guilherme
 * 
 * 
 * 
 * 
 * Algoritmo RandomTree - Gerado pelo Weka
Attribute 2 - Tipo de ambiente = Private
|   Attribute 4 - Dia da semana = Week
|   |   Attribute 1 - Perfil do usuario no ambiente = Unknown : Blocked (4/0)
|   |   Attribute 1 - Perfil do usuario no ambiente = Transient
|   |   |   Attribute 5 - Turno = Diurnal
|   |   |   |   Attribute 6 - dia util = Yes : Guest (1/0)
|   |   |   |   Attribute 6 - dia util = No : Blocked (1/0)
|   |   |   Attribute 5 - Turno = Nocturnal : Blocked (2/0)
|   |   Attribute 1 - Perfil do usuario no ambiente = User
|   |   |   Attribute 5 - Turno = Diurnal : Basic (2/0)
|   |   |   Attribute 5 - Turno = Nocturnal
|   |   |   |   Attribute 6 - dia util = Yes : Basic (1/0)
|   |   |   |   Attribute 6 - dia util = No : Guest (1/0)
|   |   Attribute 1 - Perfil do usuario no ambiente = Responsible : Advanced (4/0)
|   |   Attribute 1 - Perfil do usuario no ambiente = Student
|   |   |   Attribute 6 - dia util = Yes
|   |   |   |   Attribute 5 - Turno = Diurnal : Advanced (1/0)
|   |   |   |   Attribute 5 - Turno = Nocturnal : Basic (1/0)
|   |   |   Attribute 6 - dia util = No : Basic (2/0)
|   |   Attribute 1 - Perfil do usuario no ambiente = Manager : Administrative (4/0)
|   Attribute 4 - Dia da semana = Weekend
|   |   Attribute 1 - Perfil do usuario no ambiente = Unknown : Blocked (4/0)
|   |   Attribute 1 - Perfil do usuario no ambiente = Transient : Blocked (4/0)
|   |   Attribute 1 - Perfil do usuario no ambiente = User
|   |   |   Attribute 6 - dia util = Yes : Guest (2/0)
|   |   |   Attribute 6 - dia util = No
|   |   |   |   Attribute 5 - Turno = Diurnal : Guest (1/0)
|   |   |   |   Attribute 5 - Turno = Nocturnal : Blocked (1/0)
|   |   Attribute 1 - Perfil do usuario no ambiente = Responsible
|   |   |   Attribute 5 - Turno = Diurnal : Advanced (2/0)
|   |   |   Attribute 5 - Turno = Nocturnal : Basic (2/0)
|   |   Attribute 1 - Perfil do usuario no ambiente = Student
|   |   |   Attribute 5 - Turno = Diurnal : Guest (2/0)
|   |   |   Attribute 5 - Turno = Nocturnal : Blocked (2/0)
|   |   Attribute 1 - Perfil do usuario no ambiente = Manager : Administrative (4/0)
Attribute 2 - Tipo de ambiente = Public : Administrative (48/0)
Attribute 2 - Tipo de ambiente = Blocked
|   Attribute 1 - Perfil do usuario no ambiente = Unknown : Blocked (8/0)
|   Attribute 1 - Perfil do usuario no ambiente = Transient : Blocked (8/0)
|   Attribute 1 - Perfil do usuario no ambiente = User : Blocked (8/0)
|   Attribute 1 - Perfil do usuario no ambiente = Responsible : Blocked (8/0)
|   Attribute 1 - Perfil do usuario no ambiente = Student : Blocked (8/0)
|   Attribute 1 - Perfil do usuario no ambiente = Manager : Administrative (8/0)
 */
public class ClassifierRandomTreeBorges implements Classifier {

    @Override
    public AccessType classify(UserProfileEnvironment profile, EnvironmentType typeEnvironment, Character shift, Character workday, Integer weekday) {
        AccessType accessType = new AccessType(1, "Blocked"); // if nothing then blocked
        /* Public Environment */
        if(typeEnvironment.getId() == 3) //Public environment
            accessType = new AccessType(5, "Administrative"); 
        /* Blocked Environment */
        else if(typeEnvironment.getId() == 1){ // Blocked environment
            if (profile.getId() == 6) // if manager in blocked environment ADM else blocked
                accessType = new AccessType(5, "Administrative");
            else
                accessType = new AccessType(1, "Blocked");
        }  
        /* Private Environment */
        else if(typeEnvironment.getId() == 2) {
            /* weekday == Week */
            if(weekday == LogEvent.DAY_OF_WEEK){
                /* Profile == Unknown */
                if (profile.getId() == 1) accessType = new AccessType(1, "Blocked");
                /* Profile == Transient */
                else if (profile.getId() == 2){
                    /* Shift/Turno == DAY */
                    if(shift == LogEvent.DAY_SHIFT){
                        /* workday == YES_WORKDAY */
                        if(workday == LogEvent.YES_WORKDAY){
                            accessType = new AccessType(1, "Guest");
                        } 
                        /* workday == NOT_WORKDAY */
                        else accessType = new AccessType(1, "Blocked");
                    } 
                    /* Shift/Turno == NIGHT */
                    else accessType = new AccessType(1, "Blocked");
                }
                /* Profile == User */
                else if (profile.getId() == 3) {
                    /* Shift/Turno == DAY */
                    if(shift == LogEvent.DAY_SHIFT)
                        /* workday == YES_WORKDAY */
                        accessType = new AccessType(3, "Basic"); 
                    /* Shift/Turno == NIGHT */
                    else 
                        /* workday == YES_WORKDAY */
                        if(workday == LogEvent.YES_WORKDAY){
                            accessType = new AccessType(3, "Basic");
                        } 
                        /* workday == NOT_WORKDAY */
                        else accessType = new AccessType(2, "Guest");
                }
                /* Profile == Student  */
                else if (profile.getId() == 5) {
                    /* workday == YES_WORKDAY */
                    if(workday == LogEvent.YES_WORKDAY){
                        /* Shift/Turno == DAY */
                        if(shift == LogEvent.DAY_SHIFT){
                            accessType = new AccessType(4, "Advanced");
                        /* Shift/Turno == NIGHT */
                        } else {
                            accessType = new AccessType(3, "Basic");
                        }
                    } 
                    /* workday == NOT_WORKDAY */
                    else accessType = new AccessType(3, "Basic");
                }
                /* Profile == Responsible */
                else if (profile.getId() == 4) accessType = new AccessType(4, "Advanced");
                /* Profile == Administrator */
                else if (profile.getId() == 6) accessType = new AccessType(5, "Administrative");
            }
            /* weekday == Weekend */
            if(weekday == LogEvent.DAY_OF_WEEKEND){
                /* Profile == Unknown */
                if (profile.getId() == 1) accessType = new AccessType(1, "Blocked");
                /* Profile == Transient */
                if (profile.getId() == 2) accessType = new AccessType(1, "Blocked");
                /* Profile == User */
                if (profile.getId() == 3) {
                    /* workday == YES_WORKDAY */
                    if(workday == LogEvent.YES_WORKDAY){
                        accessType = new AccessType(2, "Guest");
                    } 
                    /* workday == NOT_WORKDAY */
                    else {
                        /* Shift/Turno == DAY */
                        if(shift == LogEvent.DAY_SHIFT){
                            accessType = new AccessType(2, "Guest");
                        /* Shift/Turno == NIGHT */
                        } else {
                            accessType = new AccessType(1, "Blocked");
                        }
                    }
                }
                /* Profile == Responsible */
                if (profile.getId() == 4) {
                    /* Shift/Turno == DAY */
                    if(shift == LogEvent.DAY_SHIFT){
                        accessType = new AccessType(4, "Advanced");
                    /* Shift/Turno == NIGHT */
                    } else {
                        accessType = new AccessType(3, "Basic");
                    }
                }
                /* Profile == Student */
                if (profile.getId() == 5) {
                    /* Shift/Turno == DAY */
                    if(shift == LogEvent.DAY_SHIFT){
                        accessType = new AccessType(2, "Guest");
                    /* Shift/Turno == NIGHT */
                    } else {
                        accessType = new AccessType(1, "Blocked");
                    }
                }
                /* Profile == Administrator */
                if (profile.getId() == 6) accessType = new AccessType(5, "Administrative");
            }
        }
        return accessType;
    }

    @Override
    public void setExtraConfigurations(HashMap map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
