/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.communication;

import com.google.android.gcm.server.Message;
import java.util.ArrayList;
import server.model.Action;
import server.model.DeviceCommunication;
import server.modules.communication.cloudmessage.GoogleCloudMessageCommunication;
import server.modules.privacy.PrivacyControlUbiquitous;

/**
 *
 * @author Estudo
 */
public class Communication {
    
    private PrivacyControlUbiquitous ubiPri;

    public Communication() {
        ubiPri = new PrivacyControlUbiquitous();
    }

    public Communication(PrivacyControlUbiquitous ubiPri) {
        this.ubiPri = ubiPri;
    }
    
    public String onChangeCurrentUserLocalization(int environmentId, String userName,String userPassword,String deviceCode){
        ubiPri.setCommunication(this);
        return ubiPri.onChangeCurrentUserLocalizationWithReturnAsynchronousActions(environmentId, userName,userPassword,deviceCode);
    }
    
    public String onChangeCurrentUserLocalizationWithResponse(int environmentId, String userName,String userPassword,String deviceCode){
        ubiPri.setCommunication(this);
        return ubiPri.onChangeCurrentUserLocalizationReturnActions(environmentId, userName,userPassword,deviceCode);
    }
    
    public boolean sendActionsToDevice(DeviceCommunication devComm,ArrayList<Action> actions){
        System.out.println("Ip: "+devComm.getAddress()+" port: " + devComm.getPort() + " num. actions: " +actions.size() );
        switch(devComm.getCommunicationType().getId()){
            case 1:// SOCKET
                System.out.println("Envio de mensagens por socket não implementada");  
                break;
            case 2:// WEB SERVICE SOAP
                System.out.println("Envio de mensagens por Web Service SOAP não implementada");
                break;
            case 3:// WEB SERVICE REST
                System.out.println("Envio de mensagens por Web Service Rest não implementada");
                break;
            case 4:// GOOGLE CLOUD MESSAGE
                System.out.println("Envio de mensagens por Google Cloud Message não implementada");
                Message.Builder build = makeGoogleCloudMessage(actions);
                GoogleCloudMessageCommunication gcmComm = new GoogleCloudMessageCommunication(devComm.getAddress());
                gcmComm.send(devComm.getParameters(), build); 
                break;
            case 5:// USB
                System.out.println("Envio de mensagens por USB não implementada");
                break;
            case 6:// RS-232
                System.out.println("Envio de mensagens por RS-232 não implementada");
                break;
            case 7:// HTTP
                System.out.println("Envio de mensagens por HTTP não implementada");
                break;
            default:
                System.out.println("Comunicação não Suportada!");
                break;
        };
        return true;
    }
    
    public boolean sendActionsToDevice(DeviceCommunication devComm,String message){
        System.out.println("Ip: "+devComm.getAddress()+" port: " + devComm.getPort() + " actions: " +message );
        return true;
    }
    
    private Message.Builder makeGoogleCloudMessage(ArrayList<Action> actions) {
        String json ;
        Message.Builder builder = new Message.Builder();
        for (int i = 0; i < actions.size();i++) {
            json = "{\"state\":\""+ actions.get(i).getAction() +"\"";
            if(actions.get(i).getDuration() > 0)
                json += ",\"duration\":\"" + String.valueOf(actions.get(i).getDuration()) + "\"";
            if(actions.get(i).getArgs().length > 0){
                json += ",\"args\":[";
                for(int j = 0; j < actions.get(i).getArgs().length;j++){
                     json += "\"" + actions.get(i).getArgs()[i] + "\"";
                     if(j != (actions.get(i).getArgs().length-1)) json += ",";
                }
            }
            json += "}";
        
            switch (actions.get(i).getFunctionality().getId()) {
                case 1: // BLUETOOTH_STATE
                    builder.addData("BLUETOOTH_STATE", json);
                        break;
                case 2: // SILENT_MODE
                    builder.addData("SILENT_MODE", json);
                        break;
                case 3: // VIBRATION_STATE
                    builder.addData("VIBRATION_STATE", json);
                        break;
                case 4: // AIRPLANE_MODE_STATE
                    builder.addData("AIRPLANE_MODE_STATE", json);
                        break;
                case 5: // WIFI_STATE
                    builder.addData("WIFI_STATE", json);
                        break;
                case 9: // RINGER_VOLUME_VALUE
                    builder.addData("RINGER_VOLUME_VALUE", json);
                        break;
                case 15: // GPS_STATUS
                    builder.addData("GPS_STATUS", json);
                        break;
                } 
        
        }
        return builder;
    }
    
    public String validateRemoteLoginUser(String userName, String userPassword, String deviceCode){
        return this.ubiPri.validateRemoteLoginUser(userName, userPassword, deviceCode);
    }

    public String onInsertNewCommunicationCode(String userName, String userPassword, String deviceCode, String communicationCode, int communicationType, int communicationId) {
        return this.ubiPri.onInsertNewCommunicationCode(
                userName,
                userPassword, 
                deviceCode,
                communicationCode,
                communicationType,      // Google Cloud Message
                communicationId);    // O primeiro que encontrar
    }
    
}
