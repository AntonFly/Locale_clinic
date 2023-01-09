package com.clinic.entities;

import com.clinic.entities.keys.ModificationScenarioId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "ModificationScenario")
@Table(name = "modification_scenario")
@AllArgsConstructor
@Getter
@Setter
public class ModificationScenario {

    @EmbeddedId
    @JsonIgnore
    private ModificationScenarioId id;

    @ManyToOne//(fetch = FetchType.LAZY)
    @MapsId("modificationId")
    @JsonManagedReference
    private Modification modification;

    @ManyToOne//(fetch = FetchType.LAZY)
    @MapsId("scenarioId")
    @JsonBackReference
    private Scenario scenario;

    @Column(name = "priority")
    private long priority;

    public ModificationScenario() { }

    public ModificationScenario(Scenario scenario, Modification modification) {
        this.scenario = scenario;
        this.modification = modification;
        this.id = new ModificationScenarioId(scenario.getId(), modification.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ModificationScenario that = (ModificationScenario) o;
        return Objects.equals(modification, that.modification) &&
                Objects.equals(scenario, that.scenario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modification, scenario);
    }
}