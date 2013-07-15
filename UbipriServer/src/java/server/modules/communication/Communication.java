/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.communication;

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
    
    public boolean sendActionsToDevice(String ip,String port,String jsonActions){
        System.out.println("Ip: "+ip+" port: " + port + " actions: " +jsonActions );
        return true;
    }
    
    public boolean sendActionsToDevice(DeviceCommunication devComm,String jsonActions){
        System.out.println("Ip: "+devComm.getAddress()+" port: " + devComm.getPort() + " actions: " +jsonActions );
        return true;
    }
    
    
}
