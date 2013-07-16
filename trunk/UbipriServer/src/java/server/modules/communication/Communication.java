/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.communication;

import java.util.ArrayList;
import server.model.Action;
import server.model.DeviceCommunication;
import server.modules.privacy.PrivacyControlUbiquitous;

/**
 *
 * @author Estudo
 */
public class Communication {
    
    private PrivacyControlUbiquitous ubiPri;
    
    public String onChangeCurrentUserLocalization(int environmentId, String userName,String userPassword,String deviceCode){
        ubiPri = new PrivacyControlUbiquitous();
        ubiPri.setCommunication(this);
        return ubiPri.onChangeCurrentUserLocalizationWithReturnAsynchronousActions(environmentId, userName,userPassword,deviceCode);
    }
    
    public String onChangeCurrentUserLocalizationWithResponse(int environmentId, String userName,String userPassword,String deviceCode){
        ubiPri = new PrivacyControlUbiquitous();
        ubiPri.setCommunication(this);
        return ubiPri.onChangeCurrentUserLocalizationReturnActions(environmentId, userName,userPassword,deviceCode);
    }
    
    public boolean sendActionsToDevice(DeviceCommunication devComm,ArrayList<Action> actions){
        System.out.println("Ip: "+devComm.getAddress()+" port: " + devComm.getPort() + " num. actions: " +actions.size() );
        return true;
    }
    
    public boolean sendActionsToDevice(DeviceCommunication devComm,String message){
        System.out.println("Ip: "+devComm.getAddress()+" port: " + devComm.getPort() + " actions: " +message );
        return true;
    }
    
    
}
