package com.turuchie.mellowhealthportal.models.Physicians;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "physician_networks")
public class PhysicianNetwork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "physician_id", nullable = false)
    private Physician physician;

    @Column(nullable = false)
    private String groupName;

    @Column(nullable = false)
    private String agentName;

    @Column(nullable = false)
    private String coverageArea;

    // Constructors, getters, setters, etc.

    public PhysicianNetwork() {
    }

    public PhysicianNetwork(Physician physician, String groupName, String agentName, String coverageArea) {
        this.physician = physician;
        this.groupName = groupName;
        this.agentName = agentName;
        this.coverageArea = coverageArea;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getCoverageArea() {
        return coverageArea;
    }

    public void setCoverageArea(String coverageArea) {
        this.coverageArea = coverageArea;
    }
}
