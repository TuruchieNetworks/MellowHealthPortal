package com.turuchie.mellowhealthportal.models.PatientOperations;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoginPatient {
	
	@NotEmpty(message="Email is required!")
	@Email(message="Please Enter A Valid email!")
	private String email;

	@NotEmpty(message="Please Confirm Password!")
	@Size(min=8, max=128, message="Password must be between 8 and 128 characters!")
	private String password;

	public LoginPatient() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
