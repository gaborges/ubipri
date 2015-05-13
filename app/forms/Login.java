package forms;

import play.data.validation.Constraints;

public class Login {
	@Constraints.Required
	@Constraints.Email
	public String emailAddress;
	
	@Constraints.Required
	public String password;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}