package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="action_args")
public class ActionArg extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actions_args_id_seq")
    public Integer id;
  
    @Constraints.Required
    @Constraints.MaxLength(100)
    public String label;
  
    @Constraints.Required
    @Constraints.MaxLength(100)
    public String value;
  
    @ManyToOne(optional=false)
    @JoinColumn(name="action_id")
    public Action action;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

    
}