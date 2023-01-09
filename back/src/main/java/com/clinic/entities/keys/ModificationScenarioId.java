package com.clinic.entities.keys;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ModificationScenarioId implements Serializable {

    @Column(name = "modification_id")
    private Long modificationId;

    @Column(name = "scenario_id")
    private Long scenarioId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ModificationScenarioId that = (ModificationScenarioId) o;
        return Objects.equals(modificationId, that.modificationId) &&
                Objects.equals(scenarioId, that.scenarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modificationId, scenarioId);
    }
}
