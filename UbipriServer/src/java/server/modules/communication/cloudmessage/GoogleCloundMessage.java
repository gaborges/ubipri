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
public class GoogleCloundMessage {
    // é preciso criar um projeto no Google APIs e gerar uma key pra usar o Cloud Messaging. Essa é a key do projeto que criei

    private static String apiKey = "AIzaSyDAVL0xUjF4i0k0FhpzZA4owWsUdBNPySY";
    Sender sender;
    Result result = null;
    // Essa é a key do device. Ele é registrado no projeto que criei, recebe essa key e vai mandar pro servidor na hora de registrar, junto com usuário, senha, ID do dispositivo e o que mais precisar
    String device = "APA91bFk15uE15UdWu4-55pcL-GqQnZKlLgqwHzSx3jqhxsarueGTtcYGaoaFZQCJuospRsJ-1-OU-8dmgh8YHWIaW7LLVkQmDdWcpPEy4KaoxD88CcvZWXrluLaLvxp4Nh5Bk1rBcIBLG0t82KF_jg1u0FjXg6kvQ";

    public void sendToBrunoDevice(List<Action> actions) {
        
        sender = new Sender(apiKey);
        // A mensagem é um conjunto de key-values
//    Message message = new Message.Builder()
//            .delayWhileIdle(true) // Wait for device to become active before sending.
//            .addData("BLUETOOTH", "OFF")
//            .addData("MOBILE_DATA", "OFF")
//            .addData("RINGER_VOLUME", "50")
//            .addData("DISPLAY_BRIGHTNESS", "100")
//            .addData("AUTO_ROTATION", "ON")
//            .build();
        Message.Builder build = new Message.Builder();

        build.delayWhileIdle(true);

        for (Action a : actions) {
            build.addData(a.getFunctionality().getId().toString(), a.getAction());
        }

        Message message = build.build();


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
