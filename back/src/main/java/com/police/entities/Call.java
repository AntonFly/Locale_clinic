package com.police.entities;

import com.fasterxml.jackson.annotation.*;
import com.police.entities.enums.CallStatus;
import lombok.Data;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Call")
@Entity
public class Call {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private Timestamp time;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "dispatcher", nullable = false)
    private Officer dispatcher;

    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "callLocation", nullable = false)
    private Location callLocation;

    @Enumerated(EnumType.STRING)
    private CallStatus status;


    @JsonIgnore
    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
            )
    @JoinTable(name = "call_officer",
                joinColumns = @JoinColumn(name = "call_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "officer_id", referencedColumnName = "id")
    )
    private List<Officer> calledOfficers = new ArrayList<>();

    public Call() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Officer getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Officer dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Location getCallLocation() {
        return callLocation;
    }

    public void setCallLocation(Location callLocation) {
        this.callLocation = callLocation;
    }

    public CallStatus getStatus() {
        return status;
    }

    public void setStatus(CallStatus status) {
        this.status = status;
    }

    public List<Officer> getCalledOfficers() {
        return calledOfficers;
    }

    public void setCalledOfficers(List<Officer> calledOfficers) {
        this.calledOfficers = calledOfficers;
    }
}
