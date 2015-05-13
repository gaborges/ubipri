package controllers;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.google.android.gcm.server.Sender;

import models.Action;
import models.Device;
import models.DeviceCommunication;
import models.Environment;
import models.User;
import models.UserType;
import dao.ActionDAO;
import dao.DeviceDAO;
import dao.EnvironmentDAO;
import dao.UserDAO;
import dao.UserTypeDAO;
import play.*;
import play.libs.Json;
import play.mvc.*;
import utils.http.BaseController;
import utils.http.Message;
import utils.security.Secured;
import views.html.*;

public class Application extends BaseController {

	//@Security.Authenticated(Secured.class)
    public static Result index() {
		
		return ok(new Message("ok", 10));

    	/*if (request().accepts("text/html")) {
    		return ok(views.html.index.render("teste"));
    	} else if (request().accepts("application/json")) {
    		return ok(Json.toJson("Your new application is ready."));
    	} else {
    	    return badRequest();
    	}*/
    	//return ok(index.render("Your new application is ready."));
    }

}
