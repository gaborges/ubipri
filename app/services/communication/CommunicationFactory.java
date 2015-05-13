package services.communication;

import models.CommunicationType;

public class CommunicationFactory {

	public static AbstractCommunication getInstance(CommunicationType type) {
		switch(type.getId()) {
	        case 1:// SOCKET
	            break;
	        case 2:// WEB SERVICE SOAP
	            break;
	        case 3:// WEB SERVICE REST
	            break;
	        case 4:// GOOGLE CLOUD MESSAGE
	
	            break;
	        case 5:// USB
	            break;
	        case 6:// RS-232
	            break;
	        case 7:// HTTP
	            break;
	        default:
	            break;
		}
		
		return null;
	}
}
