package services.communication;

import java.io.IOException;
import java.util.Map;

import play.Logger;
import models.DeviceCommunication;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GoogleCloudMessageCommunication extends AbstractCommunication {
	private static final Logger.ALogger logger = Logger.of(GoogleCloudMessageCommunication.class);
	private static final int MAX_RETRIES = 5;
	
	private Sender sender;
	private String apiKey;
	
	private Sender getSender() {
		if (sender == null) {
			sender = new Sender(apiKey);
		}
		return sender;
	}
	
	public boolean send(DeviceCommunication comm, Map<String, String> data) {
		Message.Builder builder = new Message.Builder();
		
		for (Map.Entry<String, String> e : data.entrySet()) {
			builder.addData(e.getKey(), e.getValue());
		}
		
		builder.delayWhileIdle(true);
		
		Message message = builder.build();
		Result result;
		
		try {
            result = getSender().send(message, comm.getParameters(), MAX_RETRIES);
        } catch (IOException e) {
        	logger.error("Unable to send Google Cloud Message", e);
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
                logger.error("Application has been removed from device");
            }
            
            // implement other error messages
            // maybe set the error as a variable
        }
        return false;
	}
}
