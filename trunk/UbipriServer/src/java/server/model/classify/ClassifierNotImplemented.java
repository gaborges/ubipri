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
 * @author Borges
 */
public class ClassifierNotImplemented implements Classifier<Object>{

    @Override
    public AccessType classify(UserProfileEnvironment profile, EnvironmentType typeEnvironment, Character shift, Character workday, Integer weekday) {
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setExtraConfigurations(HashMap<String, Object> map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
