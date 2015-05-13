package dao;

import java.util.List;

import com.avaje.ebean.Ebean;

import dao.ebean.EbeanBaseDAO;
import models.Device;
import models.User;

public class DeviceDAO extends EbeanBaseDAO<Device, Integer> {

	public Device findByCode(String code) {
		return Ebean.find(Device.class).where().eq("code", code).findUnique();
	}
	
	public List<Device> findAllByUser(User user) {
		List<Device> devices = Ebean.find(Device.class)
			 .fetch("deviceType")
		     .where().eq("user", user)
		     .findList();
		
		return devices;
	}
	
	public boolean exists(String code) {
		int count = Ebean.find(getPersistentClass())
			.where().eq("code", code)
			.findRowCount();
		
		return (count != 0);
	}
}
