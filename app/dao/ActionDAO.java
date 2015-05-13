package dao;

import java.util.List;

import com.avaje.ebean.Ebean;

import dao.ebean.EbeanBaseDAO;
import models.AccessLevel;
import models.Action;
import models.Environment;

public class ActionDAO extends EbeanBaseDAO<Action, Long> {

	public List<Action> getCustomActions(Environment environment) {
		List<Action> actions = Ebean.find(Action.class)
		     .fetch("accessLevel")
		     .fetch("functionality")
		     .fetch("environment")
		     .where().eq("environment", environment)
		     .findList();
		
		return actions;
	}
	
	public List<Action> getCustomActions(Environment environment, AccessLevel accessLevel) {
		List<Action> actions = Ebean.find(Action.class)
		     .fetch("accessLevel")
		     .fetch("functionality")
		     .fetch("environment")
		     .where()
		     	.eq("environment", environment)
		     	.eq("accessLevel", accessLevel)
		     .findList();
		
		return actions;
	}
}
