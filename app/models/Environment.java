package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="environments")
public class Environment extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "environments_id_seq")
    public Integer id;
    
    @Constraints.Required
    @Constraints.MaxLength(100)
    public String name;
  
    @Constraints.Required
    public double latitude;
  
    @Constraints.Required
    public double longitude;
  
    public double altitude;
  
    @Column(name="operating_range", nullable=false)
    public double operatingRange;
  
    public int version;
  
    @ManyToOne(optional=false)
    @JoinColumn(name="localization_type_id")
    public LocalizationType localizationType;
  
    @ManyToOne(optional=false)
    @JoinColumn(name="environment_type_id")
    public EnvironmentType environmentType;
  
    @ManyToOne(optional=true)
    @JoinColumn(name="parent_environment_id")
    public Environment parent;
  
    @OneToMany
    public List<Action> customActions;
  
    @OneToMany
    public List<Point> points;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public double getOperatingRange() {
		return operatingRange;
	}

	public void setOperatingRange(double operatingRange) {
		this.operatingRange = operatingRange;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public LocalizationType getLocalizationType() {
		return localizationType;
	}

	public void setLocalizationType(LocalizationType localizationType) {
		this.localizationType = localizationType;
	}

	public EnvironmentType getEnvironmentType() {
		return environmentType;
	}

	public void setEnvironmentType(EnvironmentType environmentType) {
		this.environmentType = environmentType;
	}

	public Environment getParent() {
		return parent;
	}

	public void setParent(Environment parent) {
		this.parent = parent;
	}

	public List<Action> getCustomActions() {
		return customActions;
	}

	public void setCustomActions(List<Action> customActions) {
		this.customActions = customActions;
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

    
}