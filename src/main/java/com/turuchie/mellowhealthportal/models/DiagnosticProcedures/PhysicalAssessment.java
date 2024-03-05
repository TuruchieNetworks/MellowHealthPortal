package com.turuchie.mellowhealthportal.models.DiagnosticProcedures;

import java.time.LocalDate;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;
import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;
import com.turuchie.mellowhealthportal.models.Physicians.Physician;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "physical_assessments")
public class PhysicalAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please Enter Oral Assessment!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Enter Null If Unavailable!")
    private String oralCavityDescription;

    @NotBlank(message = "Please Enter Any General Appearance Assessment!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Enter Null If Unavailable!")
    private String generalAppearanceDescription;

    @NotBlank(message = "Please Describe Skin Condition!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Skin Conditions Must Be Between 1 and 255 Characters!")
    private String skinDescription;

    @NotBlank(message = "Please HEENT Oservations!")
    @Size(min = 3, max = 255, message = "Invalid Entry, HEENT Observations Must Be Between 1 and 255 Characters!")
    private String heentDescription;

    @NotBlank(message = "Please Enter Neck Assessment!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Neck Assessment Must Be Between 1 and 255 Characters!")
    private String neckDescription;

    @NotBlank(message = "Please Enter Lungs Assessment!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Lungs Assessment Must Be Between 1 and 255 Characters!")
    private String lungsDescription;

    @NotBlank(message = "Please Enter Heart Assessment!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Heart Assessment Must Be Between 1 and 255 Characters!")
    private String heartDescription;

    @NotBlank(message = "Please Enter Abdomen Assessment!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Abdomen Assessment Must Be Between 1 and 255 Characters!")
    private String abdomenDescription;

    @NotBlank(message = "Please Describe External Extremities!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Extremities Assessment Must Be Between 1 and 255 Characters!")
    private String extremitiesDescription;

    @NotBlank(message = "Please Enter Neurologic Assessment!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Neurologic Assessment Must Be Between 1 and 255 Characters!")
    private String neurologicDescription;

    @NotBlank(message = "Please Enter Lymph Nodes Assessment!")
    @Size(min = 3, max = 255, message = "Invalid Entry, Lymph Nodes Assessment Must Be Between 1 and 255 Characters!")
    private String lymphNodesDescription;

    @Column(updatable = false)
    private LocalDate createdAt;

    private LocalDate updatedAt;

    @OneToOne(mappedBy = "physicalAssessment")
    private PainAssessment painAssessment;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Please Select Case ID!")
    private PatientCase patientCase;

    @ManyToOne
    @JoinColumn(name = "physician_id")
    @NotNull(message = "Please Select Physician ID!")
    private Physician physician;

    public PhysicalAssessment() {
    }

    // Constructors, getters, setters...
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOralCavityDescription() {
		return oralCavityDescription;
	}

	public void setOralCavityDescription(String oralCavityDescription) {
		this.oralCavityDescription = oralCavityDescription;
	}

	public String getGeneralAppearanceDescription() {
		return generalAppearanceDescription;
	}

	public void setGeneralAppearanceDescription(String generalAppearanceDescription) {
		this.generalAppearanceDescription = generalAppearanceDescription;
	}

	public String getSkinDescription() {
		return skinDescription;
	}

	public void setSkinDescription(String skinDescription) {
		this.skinDescription = skinDescription;
	}

	public String getHeentDescription() {
		return heentDescription;
	}

	public void setHeentDescription(String heentDescription) {
		this.heentDescription = heentDescription;
	}

	public String getNeckDescription() {
		return neckDescription;
	}

	public void setNeckDescription(String neckDescription) {
		this.neckDescription = neckDescription;
	}

	public String getLungsDescription() {
		return lungsDescription;
	}

	public void setLungsDescription(String lungsDescription) {
		this.lungsDescription = lungsDescription;
	}

	public String getHeartDescription() {
		return heartDescription;
	}

	public void setHeartDescription(String heartDescription) {
		this.heartDescription = heartDescription;
	}

	public String getAbdomenDescription() {
		return abdomenDescription;
	}

	public void setAbdomenDescription(String abdomenDescription) {
		this.abdomenDescription = abdomenDescription;
	}

	public String getExtremitiesDescription() {
		return extremitiesDescription;
	}

	public void setExtremitiesDescription(String extremitiesDescription) {
		this.extremitiesDescription = extremitiesDescription;
	}

	public String getNeurologicDescription() {
		return neurologicDescription;
	}

	public void setNeurologicDescription(String neurologicDescription) {
		this.neurologicDescription = neurologicDescription;
	}

	public String getLymphNodesDescription() {
		return lymphNodesDescription;
	}

	public void setLymphNodesDescription(String lymphNodesDescription) {
		this.lymphNodesDescription = lymphNodesDescription;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public PatientCase getPatientCase() {
		return patientCase;
	}

	public void setPatientCase(PatientCase patientCase) {
		this.patientCase = patientCase;
	}

	public Physician getPhysician() {
		return physician;
	}

	public void setPhysician(Physician physician) {
		this.physician = physician;
	}

	public PainAssessment getPainAssessment() {
		return painAssessment;
	}

	public void setPainAssessment(PainAssessment painAssessment) {
		this.painAssessment = painAssessment;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }
}