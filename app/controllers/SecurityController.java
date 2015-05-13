package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;

import dao.UserDAO;
import forms.Login;
import models.User;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.F;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.RequestBody;
import utils.http.BaseController;
import utils.http.Message;
import utils.security.Secured;
import static play.libs.Json.toJson;
import static play.mvc.Controller.request;
import static play.mvc.Controller.response;

public class SecurityController extends BaseController {
	public final static String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
	public static final String AUTH_TOKEN = "authToken";
	
	private static UserDAO userDao = new UserDAO();
	
	public static User getUser() {
		return (User)Http.Context.current().args.get("user");
	}

	// returns an authToken
	public static Result login() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		
		if (loginForm.hasErrors()) {
			return badRequest(loginForm);
		}
		
		Login login = loginForm.get();
		
		User user = userDao.findByEmailAddressAndPassword(login.getEmailAddress(),
				login.getPassword());
		
		if (user == null) {
			return unauthorized(message("Unauthorized access", 1));
		} else {
			String authToken = user.createToken();
			response().setCookie(AUTH_TOKEN, authToken);
			
			Map<String, String> data = new HashMap<String, String>();
			data.put(AUTH_TOKEN, authToken);

			return ok(data);
		}
	}
	
	@Security.Authenticated(Secured.class)
	public static Result logout() {
		response().discardCookie(AUTH_TOKEN);
		getUser().deleteAuthToken();
		return redirect("/");
	}

}