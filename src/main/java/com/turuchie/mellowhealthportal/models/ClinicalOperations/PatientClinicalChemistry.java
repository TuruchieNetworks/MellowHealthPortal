package com.turuchie.mellowhealthportal.models.ClinicalOperations;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.turuchie.mellowhealthportal.models.PatientOperations.Patient;

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
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "patient_clinical_chemistry_records")
public class PatientClinicalChemistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "albumin", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Albumin Must Be Greater Than 0 grams per deciliter g/dL")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Alanine Transaminase Must Be LessThan or equal to 1000 (g/dL), Normal Range is between 3.5-5.5 grams per deciliter (g/dL)!")
    private BigDecimal albumin;

    @Column(name = "amylase", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Amylase Must Be Greater Than 0 IU/L")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Amylase Must Be Less Than or equal to 1000 IU/L, Normal Range is between 40-140 units per liter U/L")
    private BigDecimal amylase;

    @Column(name = "bicarbonate", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Bicarbonate Must Be Greater Than 0 mEq/L")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Bicarbonate Must Be LessThan or equal to 1000 mEq/L, Normal Range is between 22 to 29 mEq/L")
    private BigDecimal bicarbonate;

    @Column(name = "bun", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "BUN Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "BUN Must Be LessThan or equal to 1000 mg/dl, Normal Range is between 5 to 20  mg/dl")
    private BigDecimal bun;

    @Column(name = "fasting_glucose", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Fasting Glucose Levels Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Fasting Glucose Levels Must Be Less Than or equal to 1000 mg/L, Normal Range is between 70 to 100 mg/dL")
    private BigDecimal fastingGlucose;


    @Column(name = "alanine_transaminase", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Alanine Transaminase Must Be Greater Than 0 U/L, Normal Range is between 4-36  U/L ")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Alanine Transaminase Must Be LessThan or equal to 1000 international units er liter (IU/L)")
    private BigDecimal alanineTransaminase;

    @Column(name = "alkaline_phosphatase", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Alkaline Phosphatase Must Be Greater Than 0 U/L")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Alkaline Phosphatase Must Be LessThan or equal to 1000 U/L, Normal Range is between 44-147  U/L")
    private BigDecimal alkalinePhosphatase;

    @Column(name = "aspartate_transaminase", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Aspartate Transaminase Must Be Greater Than 0 U/L")
    @DecimalMax(value = "1000.0", inclusive = true, message = "Aspartate Transaminase Must Be Less Than or equal to 1000 U/L, Normal Range is between 8-33 U/L")
    private BigDecimal aspartateTransaminase;

    @Column(name = "gamma_glutamyl_transferase", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Gamma Glutamyl Transferase Levels Must Be Greater Than 0 IU/L")
    @DecimalMax(value = "100.0", inclusive = true, message = "Gamma Glutamyl Transferase Levels Must Be Less Than or equal to 100 IU/L, Normal Range is between 7 to 47 U/L in males and 5 to 25 U/L in females!")
    private BigDecimal gammaGlutamylTransferase;

    @Column(name = "calcium", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Calcium Levels Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Calcuim Levels Must Be Less Than or equal to 100 mg/dl, Normal Range is between 8.3 to 10.3 mg/dl")
    private BigDecimal calcium;

    @Column(name = "chloride", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Chloride Levels Must Be Greater Than 0 mEq/L")
    @DecimalMax(value = "100.0", inclusive = true, message = "Chloride Levels Must Be Less Than or equal to 100 mEq/L, Normal Range is between 96 to 106 miliequivalents per liter (mEq/L)")
    private BigDecimal chloride;

    @Column(name = "creatinine_kinase", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Creatine Kinase Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Creatine Kinase Must Be Less Than or equal to 100 mg/L, Normal Range is between 22 to 198 mg/dL")
    private BigDecimal creatinineKinase;

    @Column(name = "direct_bilirubin", precision = 38, scale = 2)
    @DecimalMax(value = "100.0", inclusive = true, message = "Direct Bilirubin Levels Must Be Less Than or equal to 1000 mg/L, Normal Range is between 0 to 0.4 mg/dL")
    private BigDecimal directBilirubin;
    @Column(name = "indirect_bilirubin", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Indirect Bilirubin Levels Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Indirect Bilirubin Levels Must Be Less Than or equal to 100 mEq/L, Normal Range is between 0.2 to 0.8 mg/dL")
    private BigDecimal indirectBilirubin;

    @Column(name = "lipase", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Lipase Levels Must Be Greater Than 0 U/L")
    @DecimalMax(value = "100.0", inclusive = true, message = "Lipase Levels Must Be Less Than or equal to 100 mEq/L, Normal Range is between 10 to 140 U/L")
    private BigDecimal lipase;

    @Column(name = "total_bilirubin", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Total Bilirubin Levels Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Total Bilirubin Levels Must Be Less Than or equal to 100 mEq/L, Normal Range is less than 1 miligram per deciliter mg/dL")
    private BigDecimal totalBilirubin;

    @Column(name = "total_protein", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Total Protein Levels Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Total Protein Levels Must Be Less Than or equal to 100 g/dL, Normal Range is between 6.0 - 8.3 g/dL")
    private BigDecimal totalProtein;

    @Column(name = "urea", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Urea Levels Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Urea Levels Must Be Less Than or equal to 100 mEq/L, Normal Range is between 26 to 43 mg/dL")
    private BigDecimal urea;

    @Column(name = "potassium", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Potassium Levels Must Be Greater Than 0 mEq/L")
    @DecimalMax(value = "100.0", inclusive = true, message = "Potassium Levels Must Be Less Than or equal to 100 mEq/L, Normal Range is between 3.5 - 5.2 mEq/L")
    private BigDecimal potassium;

    @Column(name = "serum_creatinine", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Serum Creatinine Levels Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Serum Creatinine Levels Must Be Less Than or equal to 100 mEq/L, Normal Range is between 0.7 - 1.3 mg/dL for men, and 0.6 - 1.1 mg/dL for women!")
    private BigDecimal serumCreatinine;

    @Column(name = "sodium", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Sodium Levels Must Be Greater Than 0 mEq/L")
    @DecimalMax(value = "100.0", inclusive = true, message = "Sodium Levels Must Be Less Than or equal to 100 mEq/L, Normal Range is between 135 to 145 mEq/L")
    private BigDecimal sodium;

    @Column(name = "uric_acid", precision = 38, scale = 2)
    @DecimalMin(value = "0.0", inclusive = false, message = "Uric Acid Levels Must Be Greater Than 0 mg/dl")
    @DecimalMax(value = "100.0", inclusive = true, message = "Uric Acid Levels Must Be Less Than or equal to 100 mEq/L, Normal Range is between 3.5 to 7.3 mg/dL")
    private BigDecimal uricAcid;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id")
    @NotNull(message = "Please Select Patient Case!")
    private PatientCase patientCase;

    public PatientClinicalChemistry() {
    }

    public Long getId() {
        return id;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAlanineTransaminase() {
		return alanineTransaminase;
	}

	public void setAlanineTransaminase(BigDecimal alanineTransaminase) {
		this.alanineTransaminase = alanineTransaminase;
	}

	public BigDecimal getAlbumin() {
		return albumin;
	}

	public void setAlbumin(BigDecimal albumin) {
		this.albumin = albumin;
	}

	public BigDecimal getAlkalinePhosphatase() {
		return alkalinePhosphatase;
	}

	public void setAlkalinePhosphatase(BigDecimal alkalinePhosphatase) {
		this.alkalinePhosphatase = alkalinePhosphatase;
	}

	public BigDecimal getAmylase() {
		return amylase;
	}

	public void setAmylase(BigDecimal amylase) {
		this.amylase = amylase;
	}

	public BigDecimal getAspartateTransaminase() {
		return aspartateTransaminase;
	}

	public void setAspartateTransaminase(BigDecimal aspartateTransaminase) {
		this.aspartateTransaminase = aspartateTransaminase;
	}

	public BigDecimal getBicarbonate() {
		return bicarbonate;
	}

	public void setBicarbonate(BigDecimal bicarbonate) {
		this.bicarbonate = bicarbonate;
	}

	public BigDecimal getBun() {
		return bun;
	}

	public void setBun(BigDecimal bun) {
		this.bun = bun;
	}

	public BigDecimal getCalcium() {
		return calcium;
	}

	public void setCalcium(BigDecimal calcium) {
		this.calcium = calcium;
	}

	public BigDecimal getChloride() {
		return chloride;
	}

	public void setChloride(BigDecimal chloride) {
		this.chloride = chloride;
	}

	public BigDecimal getCreatinineKinase() {
		return creatinineKinase;
	}

	public void setCreatinineKinase(BigDecimal creatinineKinase) {
		this.creatinineKinase = creatinineKinase;
	}

	public BigDecimal getDirectBilirubin() {
		return directBilirubin;
	}

	public void setDirectBilirubin(BigDecimal directBilirubin) {
		this.directBilirubin = directBilirubin;
	}

	public BigDecimal getFastingGlucose() {
		return fastingGlucose;
	}

	public void setFastingGlucose(BigDecimal fastingGlucose) {
		this.fastingGlucose = fastingGlucose;
	}

	public BigDecimal getGammaGlutamylTransferase() {
		return gammaGlutamylTransferase;
	}

	public void setGammaGlutamylTransferase(BigDecimal gammaGlutamylTransferase) {
		this.gammaGlutamylTransferase = gammaGlutamylTransferase;
	}

	public BigDecimal getIndirectBilirubin() {
		return indirectBilirubin;
	}

	public void setIndirectBilirubin(BigDecimal indirectBilirubin) {
		this.indirectBilirubin = indirectBilirubin;
	}

	public BigDecimal getLipase() {
		return lipase;
	}

	public void setLipase(BigDecimal lipase) {
		this.lipase = lipase;
	}

	public BigDecimal getPotassium() {
		return potassium;
	}

	public void setPotassium(BigDecimal potassium) {
		this.potassium = potassium;
	}

	public BigDecimal getSerumCreatinine() {
		return serumCreatinine;
	}

	public void setSerumCreatinine(BigDecimal serumCreatinine) {
		this.serumCreatinine = serumCreatinine;
	}

	public BigDecimal getSodium() {
		return sodium;
	}

	public void setSodium(BigDecimal sodium) {
		this.sodium = sodium;
	}

	public BigDecimal getTotalBilirubin() {
		return totalBilirubin;
	}

	public void setTotalBilirubin(BigDecimal totalBilirubin) {
		this.totalBilirubin = totalBilirubin;
	}

	public BigDecimal getTotalProtein() {
		return totalProtein;
	}

	public void setTotalProtein(BigDecimal totalProtein) {
		this.totalProtein = totalProtein;
	}

	public BigDecimal getUrea() {
		return urea;
	}

	public void setUrea(BigDecimal urea) {
		this.urea = urea;
	}

	public BigDecimal getUricAcid() {
		return uricAcid;
	}

	public void setUricAcid(BigDecimal uricAcid) {
		this.uricAcid = uricAcid;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
