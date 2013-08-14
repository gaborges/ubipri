/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.communication;

import com.google.android.gcm.server.Message;
import java.util.ArrayList;
import server.model.Action;
import server.model.DeviceCommunication;
import server.model.Environment;
import server.modules.communication.cloudmessage.GoogleCloudMessageCommunication;
import server.modules.privacy.PrivacyControlUbiquitous;

/**
 *
 * @author Estudo
 */
public class Communication {
    
    public static final String OK = "1";
    public static final String ERROR = "2";
    public static final String UPDATED = "3";
    public static final String DENNY = "4";
    
    private PrivacyControlUbiquitous ubiPri;

    public Communication() {        
    }
    
    public Communication(PrivacyControlUbiquitous ubiPri) {
        this.ubiPri = ubiPri;
    }
    
    public String onChangeCurrentUserLocalization(int environmentId, String userName,String userPassword,String deviceCode,Boolean exiting){
        ubiPri.setCommunication(this);
        return ubiPri.onChangeCurrentUserLocalizationWithReturnAsynchronousActions(environmentId, userName,userPassword,deviceCode,exiting);
    }
    
    public String onChangeCurrentUserLocalizationWithResponse(int environmentId, String userName,String userPassword,String deviceCode,Boolean exiting){
        ubiPri.setCommunication(this);
        return ubiPri.onChangeCurrentUserLocalizationReturnActions(environmentId, userName,userPassword,deviceCode,exiting);
    }
    
    public boolean sendActionsToDevice(DeviceCommunication devComm,ArrayList<Action> actions, Environment environment, Boolean exitingEnvironment){
        System.out.println("id:"+devComm.getId()+",Communication Type = {id:"+devComm.getCommunicationType().getId()
                +",name:"+devComm.getCommunicationType().getName()+"},Ip: "
                +devComm.getAddress()+" port: " + devComm.getPort() );
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
                Message.Builder build = makeGoogleCloudMessage(actions,environment, exitingEnvironment);
                //GoogleCloudMessageCommunication ms = new GoogleCloudMessageCommunication();
                //ms.sendToBrunoDevice(actions); // Para testes
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
        System.out.println("Communication Type = {id:"+devComm.getCommunicationType().getId()
                +",name:"+devComm.getCommunicationType().getName()+"},Ip: "
                +devComm.getAddress()+" port: " + devComm.getPort() + " actions: " +message );
        return true;
    }
    
    private Message.Builder makeGoogleCloudMessage(ArrayList<Action> actions, Environment environment,Boolean exitingEnvironment) {
        String json ;
        Message.Builder builder = new Message.Builder();
        // Adiciona o id do ambiente alvo
        builder.addData("environment", String.valueOf(environment.getId()));
        builder.addData("exiting", exitingEnvironment.toString());
        for (int i = 0; i < actions.size();i++) {
            json = "{\"state\":\"";
            if (actions.get(i).getFunctionality().getId()!=9)
                json += actions.get(i).getAction() +"\"";
            else{
                if(!actions.get(i).getArgs().get(0).getValue().isEmpty()) {
                         json += "\"" + actions.get(i).getArgs().get(0).getValue() + "\"";
                } else {
                    json += "50\"";
                }
            }
            if(actions.get(i).getDuration() > 0)
                json += ",\"duration\":\"" + String.valueOf(actions.get(i).getDuration()) + "\"";
            if(actions.get(i).getArgs().size() > 0){
                //json += ",\"args\":[";
                for(int j = 0; j < actions.get(i).getArgs().size();j++){
                     if(!actions.get(i).getArgs().get(j).getLabel().isEmpty()) {
                         json += "\"" + actions.get(i).getArgs().get(j).getLabel() + "\":";
                     }
                     json += "\"" + actions.get(i).getArgs().get(j).getValue() + "\"";
                     if(j != (actions.get(i).getArgs().size()-1)) json += ",";
                }
                //json += "]";
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
                case 6: // MOBILE_NETWORK_DATA_ACCESS
                    builder.addData("MNDA_STATE", json);
                        break;
                case 7: // SYSTEM_VOLUME_VALUE
                    builder.addData("SYSTEM_VOLUME_VALUE", json);
                        break;
                case 8: // MEDIA_VOLUME_VALUE
                    builder.addData("MEDIA_VOLUME_VALUE", json);
                        break;
                case 9: // RINGER_VOLUME_VALUE
                    builder.addData("RINGER_VOLUME_VALUE", json);
                        break;
                case 10: // SCREEN_TIMEOUT
                    builder.addData("SCREEN_TIMEOUT", json);
                        break;
                case 11: // SCREEN_BRIGHTNESS
                    builder.addData("SCREEN_BRIGHTNESS", json);
                        break;
                case 12: // SMS_STATE
                    builder.addData("SMS_STATE", json);
                        break;
                case 13: // LAUNCH_APP
                    builder.addData("LAUNCH_APP", json);
                        break;
                case 14: // CAMERA_ACCESS
                    builder.addData("CAMERA_ACCESS", json);
                        break;
                case 15: // GPS_STATUS
                    builder.addData("GPS_STATUS", json);
                        break;
                } 
        
        }
        return builder;
    }
    
    public String validateRemoteLoginUser(String userName, String userPassword, String deviceCode){
        ubiPri.setCommunication(this);
        return this.ubiPri.validateRemoteLoginUser(userName, userPassword, deviceCode);
    }

    public String onInsertNewCommunicationCode(String userName, String userPassword, String deviceCode, String communicationCode, int communicationType, int communicationId, String deviceName, int[] functionalities) {
        ubiPri.setCommunication(this);
        return this.ubiPri.onInsertNewCommunicationCode(
                userName,
                userPassword, 
                deviceCode,
                communicationCode,
                communicationType,      // Google Cloud Message
                communicationId,    // O primeiro que encontrar
                deviceName,functionalities);
    }
    
    public String getEnvironmentJSONMap(String userName,String userPassword, String deviceCode,int version){
        ubiPri.setCommunication(this);
        return this.ubiPri.getJsonEnvironments(userName, userPassword, deviceCode, version);
    }
    
}
