package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="device_types")
public class DeviceType extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_types_id_seq")
    public Integer id;
  
    @Constraints.Required
    @Constraints.MaxLength(100)
    public String name;

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

}