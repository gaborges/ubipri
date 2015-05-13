package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="device_communications")
public class DeviceCommunication extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_communications_id_seq")
    public Long id;

    @Constraints.Required
    @Constraints.MaxLength(100)
    public String name;
  
    @Constraints.Required
    @Constraints.MaxLength(100)
    public String address;
    
    public String parameters;
  
    @Constraints.Required
    @Constraints.MaxLength(100)
    @Column(name="address_format")
    public String addressFormat;
  
    @Constraints.Required
    @Constraints.MaxLength(50)
    public String port;
  
    @Constraints.Required
    @Column(name="preferred_order")
    public Integer preferredOrder;
  
    @ManyToOne(optional=false)
    @JoinColumn(name="communication_type_id")
    public CommunicationType communicationType; 
  
    @ManyToOne(optional=true)
    @JoinColumn(name="device_id")
    public Device device;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressFormat() {
		return addressFormat;
	}

	public void setAddressFormat(String addressFormat) {
		this.addressFormat = addressFormat;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Integer getPreferredOrder() {
		return preferredOrder;
	}

	public void setPreferredOrder(Integer preferredOrder) {
		this.preferredOrder = preferredOrder;
	}

	public CommunicationType getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(CommunicationType communicationType) {
		this.communicationType = communicationType;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

    
}