package com.clinic.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;

@Table(name = "pwd_drop_requests")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PwdDropRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean dropped;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_date")
    private Calendar requestDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "drop_date")
    private Calendar dropDate;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
