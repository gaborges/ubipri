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
 * @author Renan
 */
public class ClassifierDecisionTree implements Classifier<Object>{

    @Override
    public AccessType classify(UserProfileEnvironment profile, 
                                EnvironmentType typeEnvironment, 
                                Character shift, 
                                Character workday, 
                                Integer weekday) {
        
        // log
        System.out.println("Classificador: DecisionTree");
        System.out.println("UserProfileEnvironment: " + profile.getName());
        System.out.println("EnvironmentType: " + typeEnvironment.getName());
        System.out.println("Shift: " + shift.toString());
        System.out.println("Workday: " + workday.toString());
        System.out.println("Weekday: " + weekday);
        
        AccessType accessType = new AccessType();
        
        switch (typeEnvironment.getName()) {
            case EnvironmentType.PUBLIC: 
                accessType.setName(AccessType.ADMINISTRATIVE);
                accessType.setId(AccessType.ADMINISTRATIVE_ID);
                break;

            case EnvironmentType.BLOCKED:
                if (profile.getName().equals(UserProfileEnvironment.MANAGER)) {
                    accessType.setName(AccessType.ADMINISTRATIVE);
                    accessType.setId(AccessType.ADMINISTRATIVE_ID);
                } else {
                    accessType.setName(AccessType.BLOCKED);
                    accessType.setId(AccessType.BLOCKED_ID);
                }
                break;

            case EnvironmentType.PRIVATE:
                if (weekday == LogEvent.DAY_OF_WEEK)
                    switch (profile.getName()) {
                        case UserProfileEnvironment.MANAGER:
                            accessType.setName(AccessType.ADMINISTRATIVE);
                            accessType.setId(AccessType.ADMINISTRATIVE_ID);
                            break;

                        case UserProfileEnvironment.UNKNOWN:
                        case UserProfileEnvironment.TRANSIENT:
                            accessType.setName(AccessType.BLOCKED);
                            accessType.setId(AccessType.BLOCKED_ID);
                            break;

                        case UserProfileEnvironment.RESPONSIBLE:
                            if (shift == LogEvent.DAY_SHIFT) {
                                accessType.setName(AccessType.ADVANCED);
                                accessType.setId(AccessType.ADVANCED_ID);
                            } else {
                                accessType.setName(AccessType.BASIC);
                                accessType.setId(AccessType.BASIC_ID);
                            }
                            break;

                        case UserProfileEnvironment.STUDENT:
                            if (shift == LogEvent.DAY_SHIFT) {
                                accessType.setName(AccessType.GUEST);
                                accessType.setId(AccessType.GUEST_ID);
                            } else {
                                accessType.setName(AccessType.BLOCKED);
                                accessType.setId(AccessType.BLOCKED_ID);
                            }
                            break;

                        case UserProfileEnvironment.USER:
                            if (workday == LogEvent.NOT_WORKDAY) {
                                if (shift == LogEvent.DAY_SHIFT) {
                                    accessType.setName(AccessType.GUEST);
                                    accessType.setId(AccessType.GUEST_ID);
                                } else {
                                    accessType.setName(AccessType.BLOCKED);
                                    accessType.setId(AccessType.BLOCKED_ID);
                                }
                            } else {
                                accessType.setName(AccessType.GUEST);
                                accessType.setId(AccessType.GUEST_ID);
                            }
                            break;

                        default:
                            accessType.setName(AccessType.BLOCKED);
                            accessType.setId(AccessType.BLOCKED_ID);
                            break;
                    }
                else
                    switch (profile.getName()) {
                        case UserProfileEnvironment.RESPONSIBLE:
                            accessType.setName(AccessType.ADVANCED);
                            accessType.setId(AccessType.ADVANCED_ID);
                            break;

                        case UserProfileEnvironment.MANAGER:
                            accessType.setName(AccessType.ADMINISTRATIVE);
                            accessType.setId(AccessType.ADMINISTRATIVE_ID);
                            break;

                        case UserProfileEnvironment.UNKNOWN:
                            accessType.setName(AccessType.BLOCKED);
                            accessType.setId(AccessType.BLOCKED_ID);
                            break;

                        case UserProfileEnvironment.STUDENT:
                            if (workday == LogEvent.YES_WORKDAY) {
                                if (shift == LogEvent.NIGHT_SHIFT) {
                                    accessType.setName(AccessType.BASIC);
                                    accessType.setId(AccessType.BASIC_ID);
                                } else {
                                    accessType.setName(AccessType.ADVANCED);
                                    accessType.setId(AccessType.ADVANCED_ID);
                                }
                            } else {
                                accessType.setName(AccessType.BASIC);
                                accessType.setId(AccessType.BASIC_ID);
                            }
                            break;

                        case UserProfileEnvironment.USER:
                            if (shift == LogEvent.NIGHT_SHIFT) {
                                if (workday == LogEvent.YES_WORKDAY) {
                                    accessType.setName(AccessType.BASIC);
                                    accessType.setId(AccessType.BASIC_ID);
                                } else {
                                    accessType.setName(AccessType.GUEST);
                                    accessType.setId(AccessType.GUEST_ID);
                                }
                            } else {
                                accessType.setName(AccessType.BASIC);
                                accessType.setId(AccessType.BASIC_ID);
                            }
                            break;

                        case UserProfileEnvironment.TRANSIENT:
                            if (shift == LogEvent.DAY_SHIFT) {
                                if (workday == LogEvent.YES_WORKDAY) {
                                    accessType.setName(AccessType.GUEST);
                                    accessType.setId(AccessType.GUEST_ID);
                                } else {
                                    accessType.setName(AccessType.BLOCKED);
                                    accessType.setId(AccessType.BLOCKED_ID);
                                }
                            } else {
                                accessType.setName(AccessType.BLOCKED);
                                accessType.setId(AccessType.BLOCKED_ID);
                            }
                            break;

                        default:
                            accessType.setName(AccessType.BLOCKED);
                            accessType.setId(AccessType.BLOCKED_ID);
                            break;
                    }
            break;

            default:
                accessType.setName(AccessType.BLOCKED);
                accessType.setId(AccessType.BLOCKED_ID);
                break;
	}
        
        // log
        System.out.println("Tipo de acesso classificado como: " + accessType.getName());
        
        return accessType;
    }

    @Override
    public void setExtraConfigurations(HashMap<String, Object> map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
