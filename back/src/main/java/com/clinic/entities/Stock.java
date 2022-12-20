package com.clinic.entities;

import com.clinic.entities.keys.StockId;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

@Table(name = "stock")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @EmbeddedId
    private StockId stockId;

    private String name;

    private int amount;

    private String description;

    private int minAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastUpdateTime;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "last_update")
    private User user;
}
