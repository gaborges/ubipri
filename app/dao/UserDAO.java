package dao;

import utils.HashUtil;
import dao.ebean.EbeanBaseDAO;
import models.User;

public class UserDAO extends EbeanBaseDAO<User, Long> {
	
	public User findByName(String name) {
		return createQuery().where().eq("name", name).findUnique();
	}
	
	public User findByAuthToken(String authToken) {
		if (authToken == null) {
			return null;
		}
		
		try {
			return createQuery().where().eq("authToken", authToken).findUnique();
		} catch (Exception e) {
			return null;
		}
	}
	
	public User findByEmailAddressAndPassword(String emailAddress, String password) {
		return createQuery().where().eq("emailAddress", emailAddress.toLowerCase())
				.eq("shaPassword", HashUtil.getSha512(password)).findUnique();
	}
}
