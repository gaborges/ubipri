package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="environment_points")
public class Point extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "environment_points_id_seq")
	public Integer id;

	@Constraints.Required
	public double latitude;
  
	@Constraints.Required
	public double longitude;
  
	public double altitude;
  
	public double order;
  
	@ManyToOne(optional=false)
	@JoinColumn(name="environment_id")
	public Environment environment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getAltitude() {
		return altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public double getOrder() {
		return order;
	}

	public void setOrder(double order) {
		this.order = order;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

  
	
}