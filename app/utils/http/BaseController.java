package utils.http;

import com.fasterxml.jackson.databind.JsonNode;

import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;

public abstract class BaseController extends Controller {

	public static Status status(Object obj, int code) {
		if (request().accepts("application/json")) {
	        return status(code, toJson(obj));
	    } else {
	        return badRequest();
	    }
	}
	
	public static JsonNode toJson(Object obj) {
		if (obj instanceof Form) {
			return ((Form)obj).errorsAsJson();
		} else {
			return Json.toJson(obj);
		}
	}
	
	public static Message message(String message, int code) {
		return new Message(message, code);
	}
	
	public static Status ok(Object obj) {
		return status(obj, 200);
	}
	
	public static Status created(Object obj) {
		return status(obj, 201);
	}
	
	public static Status notModified(Object obj) {
		return status(obj, 304);
	}
	
	public static Status badRequest(Object obj) {
		return status(obj, 400);
	}
	
	public static Status unauthorized(Object obj) {
		return status(obj, 401);
	}
	
	public static Status forbidden(Object obj) {
		return status(obj, 403);
	}
	
	public static Status notFound(Object obj) {
		return status(obj, 404);
	}
	
	public static Status internalServerError(Object obj) {
		return status(obj, 500);
	}
}
