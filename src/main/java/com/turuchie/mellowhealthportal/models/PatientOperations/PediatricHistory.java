package com.turuchie.mellowhealthportal.models.PatientOperations;

import java.time.LocalDate;

import com.turuchie.mellowhealthportal.models.ClinicalOperations.PatientCase;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pediatricHistories")
public class PediatricHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Pregnancy details
    @Column(name = "full_term_pregnancy")
    @NotNull(message = "Please select whether the pregnancy was full term")
    private Boolean fullTermPregnancy;

    @Column(name = "routine_checkups")
    @NotBlank(message = "Please describe routine checkups during pregnancy")
    private String routineCheckups;

    @Column(name = "complications")
    @NotBlank(message = "Please describe any complications during pregnancy, delivery, or after delivery")
    private String complications;

    @Column(name = "ultrasound_performed")
    @NotNull(message = "Please select whether an ultrasound was performed during pregnancy")
    private Boolean ultrasoundPerformed;

    @Column(name = "smoking_drinking_drugs")
    @NotBlank(message = "Please describe if the mother smoked, drank, or used drugs during pregnancy")
    private String smokingDrinkingDrugs;

    @Column(name = "delivery_type")
    @NotBlank(message = "Please describe whether it was a vaginal delivery or a C-section")
    private String deliveryType;

    @Column(name = "child_medical_problems")
    @NotBlank(message = "Please describe any medical problems the child had after birth")
    private String childMedicalProblems;

    // Growth and development
    @Column(name = "first_smile")
    @NotNull(message = "Please Enter When Your Baby Smiled For The First Time")
    private LocalDate firstSmile;

    @Column(name = "first_sit_up")
    @NotNull(message = "Please Enter When Your Baby Sat Up For The First Time")
    private LocalDate firstSitUp;

    @Column(name = "start_crawling")
    @NotNull(message = "Please Enter When You Noticed Your Baby Crawling For The First Time")
    private LocalDate startCrawling;

    @Column(name = "start_talking")
    @NotNull(message = "Please Enter When You Noticed Your Baby Talking For The First Time")
    private LocalDate startTalking;

    @Column(name = "start_walking")
    @NotNull(message = "Please Enter When You Noticed Your Baby Walking For The First Time")
    private LocalDate startWalking;

    @Column(name = "learn_dress_himself")
    @NotNull(message = "Please Enter When You Noticed Your Baby Dressing Up Without Help For The First Time")
    private LocalDate learnDressHimself;

    @Column(name = "start_short_sentences")
    @NotNull(message = "Please Enter When You Noticed Your Baby Speaking Short Sentences For The First Time")
    private LocalDate startShortSentences;

    // Feeding history
    @Column(name = "breastfeeding")
    @NotNull(message = "Please select whether the child was breast-fed")
    private Boolean breastfeeding;

    @Column(name = "start_solid_food")
    @NotNull(message = "Please Enter When You Start Feeding Your Baby Solid Food For The First Time")
    private LocalDate startSolidFood;

    @Column(name = "child_appetite")
    @NotNull(message = "Please Enter Child's Appetite Pattern")
    private String childAppetite;

    @Column(name = "child_allergies")
    @NotNull(message = "Please Enter Any Child Allergies")
    private String childAllergies;

    @Column(name = "formula_fortified_iron")
    @NotNull(message = "Please select whether the child's formula is fortified with iron")
    private Boolean formulaFortifiedIron;

    @Column(name = "pediatric_multivitamins")
    @NotNull(message = "Please select whether the child is given pediatric multivitamins")
    private Boolean pediatricMultivitamins;

    // Routine pediatric care
    @Column(name = "immunizations_up_to_date")
    @NotNull(message = "Please select whether the child's immunizations are up to date")
    private Boolean immunizationsUpToDate;

    @Column(name = "last_routine_checkup_date")
    private LocalDate lastRoutineCheckupDate;

    @Column(name = "serious_illnesses")
    @NotEmpty(message = "Please Enter Any Serious Illness Or Enter Null If Unavailable")
    private String seriousIllnesses;

    @Column(name = "medications")
    @NotNull(message = "Please Enter Current Medications")
    private String medications;

    @Column(name = "hospitalized")
    @NotNull(message = "Please select whether the child has ever been hospitalized")
    private Boolean hospitalized;

    @Column(updatable = false)
    private LocalDate createdAt;
    
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "patientCase_id", nullable = false)
    private PatientCase patientCase;

	public PediatricHistory() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getFullTermPregnancy() {
		return fullTermPregnancy;
	}

	public void setFullTermPregnancy(Boolean fullTermPregnancy) {
		this.fullTermPregnancy = fullTermPregnancy;
	}

	public String getRoutineCheckups() {
		return routineCheckups;
	}

	public void setRoutineCheckups(String routineCheckups) {
		this.routineCheckups = routineCheckups;
	}

	public String getComplications() {
		return complications;
	}

	public void setComplications(String complications) {
		this.complications = complications;
	}

	public Boolean getUltrasoundPerformed() {
		return ultrasoundPerformed;
	}

	public void setUltrasoundPerformed(Boolean ultrasoundPerformed) {
		this.ultrasoundPerformed = ultrasoundPerformed;
	}

	public String getSmokingDrinkingDrugs() {
		return smokingDrinkingDrugs;
	}

	public void setSmokingDrinkingDrugs(String smokingDrinkingDrugs) {
		this.smokingDrinkingDrugs = smokingDrinkingDrugs;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getChildMedicalProblems() {
		return childMedicalProblems;
	}

	public void setChildMedicalProblems(String childMedicalProblems) {
		this.childMedicalProblems = childMedicalProblems;
	}

	public LocalDate getFirstSmile() {
		return firstSmile;
	}

	public void setFirstSmile(LocalDate firstSmile) {
		this.firstSmile = firstSmile;
	}

	public LocalDate getFirstSitUp() {
		return firstSitUp;
	}

	public void setFirstSitUp(LocalDate firstSitUp) {
		this.firstSitUp = firstSitUp;
	}

	public LocalDate getStartCrawling() {
		return startCrawling;
	}

	public void setStartCrawling(LocalDate startCrawling) {
		this.startCrawling = startCrawling;
	}

	public LocalDate getStartTalking() {
		return startTalking;
	}

	public void setStartTalking(LocalDate startTalking) {
		this.startTalking = startTalking;
	}

	public LocalDate getStartWalking() {
		return startWalking;
	}

	public void setStartWalking(LocalDate startWalking) {
		this.startWalking = startWalking;
	}

	public LocalDate getLearnDressHimself() {
		return learnDressHimself;
	}

	public void setLearnDressHimself(LocalDate learnDressHimself) {
		this.learnDressHimself = learnDressHimself;
	}

	public LocalDate getStartShortSentences() {
		return startShortSentences;
	}

	public void setStartShortSentences(LocalDate startShortSentences) {
		this.startShortSentences = startShortSentences;
	}

	public Boolean getBreastfeeding() {
		return breastfeeding;
	}

	public void setBreastfeeding(Boolean breastfeeding) {
		this.breastfeeding = breastfeeding;
	}

	public LocalDate getStartSolidFood() {
		return startSolidFood;
	}

	public void setStartSolidFood(LocalDate startSolidFood) {
		this.startSolidFood = startSolidFood;
	}

	public String getChildAppetite() {
		return childAppetite;
	}

	public void setChildAppetite(String childAppetite) {
		this.childAppetite = childAppetite;
	}

	public String getChildAllergies() {
		return childAllergies;
	}

	public void setChildAllergies(String childAllergies) {
		this.childAllergies = childAllergies;
	}

	public Boolean getFormulaFortifiedIron() {
		return formulaFortifiedIron;
	}

	public void setFormulaFortifiedIron(Boolean formulaFortifiedIron) {
		this.formulaFortifiedIron = formulaFortifiedIron;
	}

	public Boolean getPediatricMultivitamins() {
		return pediatricMultivitamins;
	}

	public void setPediatricMultivitamins(Boolean pediatricMultivitamins) {
		this.pediatricMultivitamins = pediatricMultivitamins;
	}

	public Boolean getImmunizationsUpToDate() {
		return immunizationsUpToDate;
	}

	public void setImmunizationsUpToDate(Boolean immunizationsUpToDate) {
		this.immunizationsUpToDate = immunizationsUpToDate;
	}

	public LocalDate getLastRoutineCheckupDate() {
		return lastRoutineCheckupDate;
	}

	public void setLastRoutineCheckupDate(LocalDate lastRoutineCheckupDate) {
		this.lastRoutineCheckupDate = lastRoutineCheckupDate;
	}

	public String getSeriousIllnesses() {
		return seriousIllnesses;
	}

	public void setSeriousIllnesses(String seriousIllnesses) {
		this.seriousIllnesses = seriousIllnesses;
	}

	public String getMedications() {
		return medications;
	}

	public void setMedications(String medications) {
		this.medications = medications;
	}

	public Boolean getHospitalized() {
		return hospitalized;
	}

	public void setHospitalized(Boolean hospitalized) {
		this.hospitalized = hospitalized;
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
