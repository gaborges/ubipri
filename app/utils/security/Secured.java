package utils.security;

import java.util.ArrayList;
import java.util.List;

import controllers.SecurityController;
import dao.UserDAO;
import models.User;
import play.libs.Json;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import utils.http.Errors;

public class Secured extends Security.Authenticator {
	
	private UserDAO userDao;
	
	public Secured() {
		super();
		userDao = new UserDAO();
	}
	
	@Override
	public String getUsername(Context ctx) {
		User user = null;
		String[] authTokenHeaderValues = ctx.request().headers().get(SecurityController.AUTH_TOKEN_HEADER);
		
		if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) 
				&& (authTokenHeaderValues[0] != null)) {
			user = userDao.findByAuthToken(authTokenHeaderValues[0]);
			if (user != null) {
				ctx.args.put("user", user);
				return user.getEmailAddress();
			}
		}
		return null;
	}
	@Override
	public Result onUnauthorized(Context ctx) {
		Errors err = new Errors();
		err.addError("Unauthorized access", 1);
		
		return unauthorized(Json.toJson(err));
	}
}