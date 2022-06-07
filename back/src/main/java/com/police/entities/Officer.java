package com.police.entities;

import com.fasterxml.jackson.annotation.*;
import com.police.entities.enums.OfficerStatus;
import com.police.entities.enums.Rank;
import com.police.entities.enums.Shift;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Officer")
@Entity
public class Officer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jabber;

    private String mail;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    @Enumerated(EnumType.STRING)
    private OfficerStatus officerStatus;

    @JsonManagedReference(value = "officer_person")
    @OneToOne(optional = false)
    @JoinColumn(name = "person")
    private Person person;

    @JsonManagedReference(value = "officer_user")
    @OneToOne
    @JoinColumn(name = "usr")
    private User usr;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    private Long salary;

    private Long premium;

    @JsonIgnore
    @OneToMany(mappedBy = "dispatcher")
    private List<Call> createdCalls = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(
            mappedBy = "calledOfficers",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
            )
    private List<Call> participatedCalls = new ArrayList<>();

    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
    @ManyToOne
    @JoinColumn(name = "policeStation")
    private PoliceStation policeStation;

    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
    @OneToMany(mappedBy = "officer")
    private List<WorkExperience> workExperiences = new ArrayList<>();

    public Officer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJabber() {
        return jabber;
    }

    public void setJabber(String jabber) {
        this.jabber = jabber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public OfficerStatus getOfficerStatus() {
        return officerStatus;
    }

    public void setOfficerStatus(OfficerStatus officerStatus) {
        this.officerStatus = officerStatus;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public User getUsr() {
        return usr;
    }

    public void setUsr(User usr) {
        this.usr = usr;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getPremium() {
        return premium;
    }

    public void setPremium(Long premium) {
        this.premium = premium;
    }

    public List<Call> getCreatedCalls() {
        return createdCalls;
    }

    public void setCreatedCalls(List<Call> createdCalls) {
        this.createdCalls = createdCalls;
    }

    public List<Call> getParticipatedCalls() {
        return participatedCalls;
    }

    public void setParticipatedCalls(List<Call> participatedCalls) {
        this.participatedCalls = participatedCalls;
    }

    public PoliceStation getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(PoliceStation policeStation) {
        this.policeStation = policeStation;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }
}
