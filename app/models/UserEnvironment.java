package models;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="user_in_environment")
public class UserEnvironment extends Model {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_in_environment_id_seq")
	public Integer id;
  
	@EmbeddedId
	public UserEnvironmentPK pk;
  
	@ManyToOne(optional=false)
	@JoinColumn(name="environment_id")
	public Environment environment;
	  
	@ManyToOne(optional=false)
	@JoinColumn(name="user_profile_environment_id")
	public UserProfileEnvironment userProfile;
  
	@ManyToOne(optional=false)
	@JoinColumn(name="current_access_type_id")
	public AccessType currentAccessType;
  
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id")
	public User user;
  
	@Column(name = "impact_factor")
	public double impactFactor;
  
	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public UserEnvironmentPK getPk() {
		return pk;
	}




	public void setPk(UserEnvironmentPK pk) {
		this.pk = pk;
	}




	public Environment getEnvironment() {
		return environment;
	}




	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}




	public UserProfileEnvironment getUserProfile() {
		return userProfile;
	}




	public void setUserProfile(UserProfileEnvironment userProfile) {
		this.userProfile = userProfile;
	}




	public AccessType getCurrentAccessType() {
		return currentAccessType;
	}




	public void setCurrentAccessType(AccessType currentAccessType) {
		this.currentAccessType = currentAccessType;
	}




	public User getUser() {
		return user;
	}




	public void setUser(User user) {
		this.user = user;
	}




	public double getImpactFactor() {
		return impactFactor;
	}




	public void setImpactFactor(double impactFactor) {
		this.impactFactor = impactFactor;
	}




	@Embeddable
	public class UserEnvironmentPK implements Serializable {
		private static final long serialVersionUID = 1L;

		@Column(name = "user_id")
		public Long userId;
	    
		@Column(name = "environment_id")
		public Long environmentId;
	    
		@Override
		public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final UserEnvironmentPK other = (UserEnvironmentPK) obj;
	        if ((this.userId == null) ? (other.userId != null) : !this.userId.equals(other.userId)) {
	                return false;
	            }
	        if ((this.environmentId == null) ? (other.environmentId != null) : !this.environmentId.equals(other.environmentId)) {
	            return false;
	        }
	        return true;
		}

		@Override
		public int hashCode() {
	        int hash = 3;
	        hash = 89 * hash + (this.userId != null ? this.userId.hashCode() : 0);
	        hash = 89 * hash + (this.environmentId != null ? this.environmentId.hashCode() : 0);
	        return hash;
		}
	}

}