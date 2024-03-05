package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patient_addresses")
public class PatientAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "Street Name Is Required!")
    @Size(min = 3, max = 150, message = "Street Name must be between 3 and 150 characters")
    private String street;

    @Column(nullable = false)
    @NotEmpty(message = "Please Enter City!")
    @Size(min = 3, max = 150, message = "City must be between 3 and 150 characters")
    private String city;

    @Column(nullable = false)
    @NotEmpty(message = "Please Enter State!")
    @Size(min = 2, max = 3, message = "State must be less than 3 characters")
    private String state;

    @Column(nullable = false)
    @NotEmpty(message = "Please Enter Zip Code!")
    @Size(min = 3, max = 15, message = "Zip Code must be between 3 and 15 characters")
    private String zipCode;

    @Column(nullable = false)
    @NotEmpty(message = "Please Enter Phone Number!")
    @Size(min = 3, max = 15, message = "Invalid Phone Number: Phone Number must be between 3 and 15 characters")
    private String phoneNumber;

    @Column(updatable = false)
    private Date createdAt;

    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public PatientAddress() {
    }

    // Constructors, getters, setters, etc.
    public PatientAddress(Patient patient, String street, String city, String state, String zipCode, String phoneNumber) {
        this.patient = patient;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}
