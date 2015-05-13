package models;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import utils.HashUtil;

@Entity
@Table(name="users")
public class User extends Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	public Integer id;
	
	@Constraints.Required
	@Constraints.MaxLength(100)
	public String name;
    
    @Constraints.Required
    @Constraints.MaxLength(120)
    @Column(name="full_name", nullable=false)
    public String fullName;
    
    private String authToken;
    
    @Column(length = 256, unique = true, nullable = false)
    @Constraints.MaxLength(256)
    @Constraints.Required
    @Constraints.Email
    private String emailAddress;
    
    @Column(length = 64, nullable = false)
    private byte[] shaPassword;
    
    @Transient
    @Constraints.Required
    @Constraints.MinLength(6)
    @Constraints.MaxLength(256)
    @JsonIgnore
    private String password;
    
    @ManyToOne(optional=true)
	@JoinColumn(name="current_environment_id")
    public Environment currentEnvironment;
    
	@ManyToOne(optional=false)
	@JoinColumn(name="user_type_id")
	@Column(name="user_type_id")
	public UserType userType;
	
	@OneToMany
	public List<Device> devices;
	
	public String createToken() {
		authToken = UUID.randomUUID().toString();
		save();
		return authToken;
	}
	
	public void deleteAuthToken() {
		authToken = null;
		save();
	}

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		shaPassword = HashUtil.getSha512(password);
	}

	public Environment getCurrentEnvironment() {
		return currentEnvironment;
	}

	public void setCurrentEnvironment(Environment currentEnvironment) {
		this.currentEnvironment = currentEnvironment;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType type) {
		this.userType = type;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
	
	
}
