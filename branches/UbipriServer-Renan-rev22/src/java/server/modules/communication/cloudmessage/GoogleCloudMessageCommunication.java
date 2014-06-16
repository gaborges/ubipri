/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.modules.communication.cloudmessage;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.io.IOException;
import java.util.List;
import server.model.Action;

/**
 *
 * @author guilherme
 */
public class GoogleCloudMessageCommunication {
    
      // é preciso criar um projeto no Google APIs e gerar uma key pra usar o Cloud Messaging. Essa é a key do projeto que criei
    private static String apiKey = "AIzaSyDAVL0xUjF4i0k0FhpzZA4owWsUdBNPySY";
    private String cloudApiKey = "";
    private Sender sender;
    private Result result = null;
    // Essa é a key do device. Ele é registrado no projeto que criei, recebe essa key e vai mandar pro servidor na hora de registrar, junto com usuário, senha, ID do dispositivo e o que mais precisar
    String device = "APA91bFj1Di-hnTprql9BfB5ipJxJxOdpYzbch2VlO4be81RjzUg7blak5rEtd_LVEzg5wr2WQ4dIcXSxHVyhv1H_yXUj7m8SklE8qUxSeqsjJCTzOsUrgk0MJKzZ6TXpOFdIoaDTqc2thzWnMhsqIbZ_7TEPrtLIQ";


    /*
     *
     *
    Param: state : String, [duration : String],[args : String]
    OBS: Entre aspas são opcionais. O args refere-se a um vetor de argumentos.
    Formato da mensagem:
    {
    "state":"on", 		// Os estados podem ser: on, off ou back.
    "duration":"600", 	// Intervalo de tempo que a regra é válida, após retorna-se para
    // o estado anterior
    "args": ["arg1", "arg2"] // Argumentos genéricos que podem ser utilizados para
    // casos particulares
    }
    Retorno: Resposta gerada pela API do GCM: Null se insucesso.
    Listagem das funcionalidades a serem utilizadas:
    messege.addData("WIFI_STATE","{\"state\":\"back\"}");
    messege.addData("BLUETOOTH_STATE","{\"state\":\"off\"}");
    messege.addData("VIBRATION_STATE","{\"state\":\"on\"}");
    messege.addData("RINGER_VOLUME_VALUE","{\"state\":\"on\",\"args\":[\"50\"]}");
    messege.addData("AIRPLANE_MODE_STATE","{\"state\":\"on\",\"duration\":\"360\"}");
     */
    
    public GoogleCloudMessageCommunication() {
    }

    public GoogleCloudMessageCommunication(String cloudApiKey) {
        this.cloudApiKey = cloudApiKey;
        sender = new Sender(apiKey);
    }
    
    public boolean send(String deviceKey,Message.Builder build){
        if(sender == null) sender = (!this.cloudApiKey.equals("")) ? new Sender(cloudApiKey) : new Sender(apiKey);
        
        build.delayWhileIdle(true);
        
        Message message = build.build();
        System.out.println("Message GCM: "+message.toString());
        try {
            // envia a mensagem, 5 retries
            result = sender.send(message, device, 5);

        } catch (IOException e) {
            System.out.println("Class: "+GoogleCloudMessageCommunication.class+" Exception: " + e);
            return false;
        }

        if (result.getMessageId() != null) {
            String canonicalRegId = result.getCanonicalRegistrationId();
            if (canonicalRegId != null) {
                System.out.println("// same device has more than on registration ID: update database");
                return false;
            }
            return true;
        } else {
            String error = result.getErrorCodeName();
            if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                System.out.println("// application has been removed from device - unregister database");    
            }
        }
        return false;
        
    }
    
    public void sendToBrunoDevice(List<Action> actions) {
        
        sender = new Sender(apiKey);
        // A mensagem é um conjunto de key-values
    Message message = new Message.Builder()
            .delayWhileIdle(true) // Wait for device to become active before sending.
            .addData("WIFI_STATE", "OFF")
            .addData("SILENT_MODE_STATE", "ON")
            //.addData("RINGER_VOLUME", "50")
          //  .addData("DISPLAY_BRIGHTNESS", "100")
          //  .addData("AUTO_ROTATION", "ON")
           // addData("AUTO_ROTATION", "ON")
            .build();
       // Message.Builder build = new Message.Builder();
//
       // build.delayWhileIdle(true);

        
        //for (Action a : actions) {
        //    build.addData(a.getFunctionality().getId().toString(), a.getAction());
       // }

        //Message message = build.build();


        try {
            // envia a mensagem, 5 retries
            result = sender.send(message, device, 5);

        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }

        if (result.getMessageId() != null) {
            String canonicalRegId = result.getCanonicalRegistrationId();
            if (canonicalRegId != null) {
                System.out.println("// same device has more than on registration ID: update database");
            }
        } else {
            String error = result.getErrorCodeName();
            if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                System.out.println("// application has been removed from device - unregister database");
            }
        }
    }
}
