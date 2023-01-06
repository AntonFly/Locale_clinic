package com.clinic.entities.keys;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockId implements Serializable {

    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "vendor_code", nullable = false)
    private int vendor;
}