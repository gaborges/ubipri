package controllers;

import models.User;
import dao.UserDAO;
import play.mvc.*;
import play.*;
import utils.http.BaseController;
import utils.security.Secured;

public class UserDevice extends BaseController {
	
	private static UserDAO dao = new UserDAO();
	
	//@Security.Authenticated(Secured.class)
	//Authorization: only read what you own
    public static Result list(String name) {
    	User user = dao.findByName(name);
    	
    	if (user == null) {
    		return notFound();
    	}
    	
    	return ok(user.getDevices());
    }
}
