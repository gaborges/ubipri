package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="access_levels")
public class AccessLevel extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_levels_id_seq")
    public Integer id;
  
    @Constraints.Required
    public double impactFactor;
  
    @ManyToOne(optional=false)
    @JoinColumn(name="access_type_id")
    public AccessType accessType;
  
    @ManyToOne(optional=false)
  	@JoinColumn(name="environment_type_id")
    public EnvironmentType environmentType;
    
    @OneToMany
    public List<Action> actions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getImpactFactor() {
		return impactFactor;
	}

	public void setImpactFactor(double impactFactor) {
		this.impactFactor = impactFactor;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	public EnvironmentType getEnvironmentType() {
		return environmentType;
	}

	public void setEnvironmentType(EnvironmentType environmentType) {
		this.environmentType = environmentType;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

    
}