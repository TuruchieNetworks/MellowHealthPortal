package com.turuchie.mellowhealthportal.models.Physicians;

import java.util.Date;
import java.util.List;

import com.turuchie.mellowhealthportal.models.Administrations.PhysiciansPatient;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.CoagulationRecord;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.CurrentMedication;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.DoseRegimen;
import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.AbdominalAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.DiagnosticRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.FollowUpRecord;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.NauseaVomitAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PainAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PatientVisitEvaluation;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PediatricFeverAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PediatricGIAssessment;
import com.turuchie.mellowhealthportal.models.DiagnosticProcedures.PhysicalAssessment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "physicians")
public class Physician {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "First Name Is Required!")
    @Size(min = 3, max = 50, message = "First Name must be between 3 and 50 characters")
    private String firstName;

    @NotEmpty(message = "Last Name Is Required!")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String lastName;

    @NotEmpty(message = "Certification Status Is Required!")
    @Size(min = 3, max = 10, message = "Board Certification Status Is Required!")
    private String isBoardCertified;

    @NotEmpty(message = "Please Enter Certification Specialty!")
    @Size(min = 3, max = 150, message = "Certification Title must be between 3 and 150 characters")
    private String certificationSpecialty;

    @Size(min = 3, max = 150, message = "Please Enter Valid DEA License Number!")
    @NotEmpty(message = "DEA License Number Is Required!")
    private String deaLicenseNumber;

    @NotEmpty(message = "Email Is Required!")
    @Email(message = "Please enter a valid email!")
    private String email;

    @NotEmpty(message = "Password Is Required!")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    private String password;

    @Transient
    @NotEmpty(message = "Passwords Must Match!")
    @Size(min = 8, max = 128, message = "Confirm Password must be between 8 and 128 characters")
    private String confirm;

    @Column(updatable = false)
    private Date createdAt;

    private Date updatedAt;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PatientCase> patientCases;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PhysiciansPatient> physiciansPatients;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PhysiciansAddresses> physiciansAddresses;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PhysicianNetwork> InsuranceNetworks;
  
    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PhysicalAssessment> physicalAssessments;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<FollowUpRecord> followUpRecords;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PainAssessment> painAssessments;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<CoagulationRecord> coagulationRecords;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<DoseRegimen> doseRegimenRecords;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<DiagnosticRecord> diagnosticRecords;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<CurrentMedication> currentMedication;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<AbdominalAssessment> abdominalPainAssessments;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<NauseaVomitAssessment> nauseaVomitAssessments;

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PediatricFeverAssessment> pediatricFeverAssessments;     

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PediatricGIAssessment> pediatricGIAssessments;   

    @OneToMany(mappedBy = "physician", cascade = CascadeType.ALL)
    private List<PatientVisitEvaluation> patientVisitEvaluations;  

    public Physician() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIsBoardCertified() {
		return isBoardCertified;
	}

	public void setIsBoardCertified(String isBoardCertified) {
		this.isBoardCertified = isBoardCertified;
	}

	public String getCertificationSpecialty() {
        return certificationSpecialty;
    }

    public void setCertificationSpecialty(String certificationSpecialty) {
        this.certificationSpecialty = certificationSpecialty;
    }

    public String getDeaLicenseNumber() {
		return deaLicenseNumber;
	}

	public void setDeaLicenseNumber(String deaLicenseNumber) {
		this.deaLicenseNumber = deaLicenseNumber;
	}

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

	public String getConfirm() {
        return confirm;
    }

	public List<PatientCase> getPatientCases() {
		return patientCases;
	}

	public void setPatientCases(List<PatientCase> patientCases) {
		this.patientCases = patientCases;
	}

	public List<PhysiciansPatient> getPhysiciansPatients() {
		return physiciansPatients;
	}

	public void setPhysiciansPatients(List<PhysiciansPatient> physiciansPatients) {
		this.physiciansPatients = physiciansPatients;
	}

	public List<PhysiciansAddresses> getPhysiciansAddresses() {
		return physiciansAddresses;
	}

	public void setPhysiciansAddresses(List<PhysiciansAddresses> physiciansAddresses) {
		this.physiciansAddresses = physiciansAddresses;
	}

	public List<PhysicianNetwork> getInsuranceNetworks() {
		return InsuranceNetworks;
	}

	public void setInsuranceNetworks(List<PhysicianNetwork> insuranceNetworks) {
		InsuranceNetworks = insuranceNetworks;
	}

	public List<PhysicalAssessment> getPhysicalAssessments() {
		return physicalAssessments;
	}

	public void setPhysicalAssessments(List<PhysicalAssessment> physicalAssessments) {
		this.physicalAssessments = physicalAssessments;
	}

	public List<FollowUpRecord> getFollowUpRecords() {
		return followUpRecords;
	}

	public void setFollowUpRecords(List<FollowUpRecord> followUpRecords) {
		this.followUpRecords = followUpRecords;
	}

	public List<PainAssessment> getPainAssessments() {
		return painAssessments;
	}

	public void setPainAssessments(List<PainAssessment> painAssessments) {
		this.painAssessments = painAssessments;
	}

	public List<DoseRegimen> getDoseRegimenRecords() {
		return doseRegimenRecords;
	}

	public void setDoseRegimenRecords(List<DoseRegimen> doseRegimenRecords) {
		this.doseRegimenRecords = doseRegimenRecords;
	}

	public List<DiagnosticRecord> getDiagnosticRecords() {
		return diagnosticRecords;
	}

	public void setDiagnosticRecords(List<DiagnosticRecord> diagnosticRecords) {
		this.diagnosticRecords = diagnosticRecords;
	}

	public List<CurrentMedication> getCurrentMedication() {
		return currentMedication;
	}

	public void setCurrentMedication(List<CurrentMedication> currentMedication) {
		this.currentMedication = currentMedication;
	}

    public List<CoagulationRecord> getCoagulationRecords() {
		return coagulationRecords;
	}

	public void setCoagulationRecords(List<CoagulationRecord> coagulationRecords) {
		this.coagulationRecords = coagulationRecords;
	}

	public List<AbdominalAssessment> getAbdominalPainAssessments() {
		return abdominalPainAssessments;
	}

	public void setAbdominalPainAssessments(List<AbdominalAssessment> abdominalPainAssessments) {
		this.abdominalPainAssessments = abdominalPainAssessments;
	}

	public List<NauseaVomitAssessment> getNauseaVomitAssessments() {
		return nauseaVomitAssessments;
	}

	public void setNauseaVomitAssessments(List<NauseaVomitAssessment> nauseaVomitAssessments) {
		this.nauseaVomitAssessments = nauseaVomitAssessments;
	}

	public List<PediatricFeverAssessment> getPediatricFeverAssessments() {
		return pediatricFeverAssessments;
	}

	public void setPediatricFeverAssessments(List<PediatricFeverAssessment> pediatricFeverAssessments) {
		this.pediatricFeverAssessments = pediatricFeverAssessments;
	}

	public List<PediatricGIAssessment> getPediatricGIAssessments() {
		return pediatricGIAssessments;
	}

	public void setPediatricGIAssessments(List<PediatricGIAssessment> pediatricGIAssessments) {
		this.pediatricGIAssessments = pediatricGIAssessments;
	}

	public List<PatientVisitEvaluation> getPatientVisitEvaluations() {
		return patientVisitEvaluations;
	}

	public void setPatientVisitEvaluations(List<PatientVisitEvaluation> patientVisitEvaluations) {
		this.patientVisitEvaluations = patientVisitEvaluations;
	}

	public void setConfirm(String confirm) {
        this.confirm = confirm;
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

    @Transient
    private String formPatientCases;

    public String getFormPatientCases() {
        return formPatientCases;
    }

    public void setFormPatientCases(String formPatientCases) {
        this.formPatientCases = formPatientCases;
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
