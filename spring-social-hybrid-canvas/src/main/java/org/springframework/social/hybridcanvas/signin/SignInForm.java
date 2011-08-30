package org.springframework.social.hybridcanvas.signin;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class SignInForm {
	@NotEmpty
	private String username;

	@Size(min = 6, message = "must be at least 6 characters")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
