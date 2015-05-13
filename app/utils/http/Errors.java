package utils.http;

import java.util.ArrayList;
import java.util.List;

public class Errors {
	public List<Message> errors = new ArrayList<Message>();

	public List<Message> getErrors() {
		return errors;
	}

	public void setErrors(List<Message> messages) {
		this.errors = messages;
	}
	
	public void addError(String message, int code) {
		errors.add(new Message(message, code));
	}
	
	
	public static Errors create(String message, int code) {
		Errors e = new Errors();
		e.addError(message, code);
		return e;
	}
	
	public static Errors create(Message...messages) {
		Errors e = new Errors();
		
		for (Message msg : messages) {
			e.addError(msg.message, msg.code);
		}
		
		return e;
	}
}