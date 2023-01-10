package com.clinic.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Table(name = "scenario")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "scenario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ModificationScenario> modificationScenarios;

    public void addModification(Modification mod) {
        ModificationScenario modificationScenario = new ModificationScenario(this, mod);
        modificationScenarios.add(modificationScenario);
        mod.getModificationScenarios().add(modificationScenario);
    }

    public void removeModification(Modification mod) {
        for (Iterator<ModificationScenario> iterator = modificationScenarios.iterator();
             iterator.hasNext(); ) {
            ModificationScenario modificationScenario = iterator.next();

            if (modificationScenario.getScenario().equals(this) &&
                    modificationScenario.getModification().equals(mod)) {
                iterator.remove();
                modificationScenario.getModification().getModificationScenarios().remove(modificationScenario);
                modificationScenario.setScenario(null);
                modificationScenario.setModification(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scenario tag = (Scenario) o;
        return Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
