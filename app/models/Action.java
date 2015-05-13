package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="actions")
public class Action extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actions_id_seq")
    public Integer id;
  
    @ManyToOne(optional=false)
    @JoinColumn(name="access_level_id")
    public AccessLevel accessLevel;
  
    @ManyToOne(optional=false)
    @JoinColumn(name="functionality_id")
    public Functionality functionality;
  
    @ManyToOne(optional=true)
    @JoinColumn(name="custom_environment_id")
    public Environment environment;
  
    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(name="action_name")
    public String action;
  
    @Formats.DateTime(pattern="dd/MM/yyyy HH:mm:ss")
    @Column(name="start_date")
    public Date startDate;
  
    @Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="end_date")
    public Date endDate;
  
    @Constraints.Required
    @Column(name="start_daily_interval")
    public Integer startDailyInterval;
  
    @Constraints.Required
    @Column(name="interval_duration")
    public Integer durationInterval;
  
    @OneToMany
    public List<ActionArg> args;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(AccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Functionality getFunctionality() {
		return functionality;
	}

	public void setFunctionality(Functionality functionality) {
		this.functionality = functionality;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getStartDailyInterval() {
		return startDailyInterval;
	}

	public void setStartDailyInterval(Integer startDailyInterval) {
		this.startDailyInterval = startDailyInterval;
	}

	public Integer getDurationInterval() {
		return durationInterval;
	}

	public void setDurationInterval(Integer durationInterval) {
		this.durationInterval = durationInterval;
	}

	public List<ActionArg> getArgs() {
		return args;
	}

	public void setArgs(List<ActionArg> args) {
		this.args = args;
	}
  
    
}