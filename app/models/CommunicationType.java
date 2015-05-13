package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="communication_types")
public class CommunicationType extends Model {
	public static final int SOCKET = 1;
	public static final int WS_SOAP = 2;
	public static final int WS_REST = 3;
	public static final int GOOGLE_CM = 4;
	public static final int USB = 5;
	public static final int RS232 = 6;
	public static final int HTTP = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "communication_types_id_seq")
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