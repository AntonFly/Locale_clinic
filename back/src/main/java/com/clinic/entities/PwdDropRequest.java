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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PwdDropRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean dropped;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar request_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar drop_date;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
