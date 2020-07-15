package com.gas.water.monitoring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "subscriber")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Subscriber {
    @Id
    @GeneratedValue
    private Long clientId;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<GasUsage> gasUsages;

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<WaterUsage> waterUsages;

    public Subscriber addGasUsages(GasUsage gasUsage) {
        if (gasUsages == null) {
            gasUsages = new ArrayList<>();
        }
        gasUsages.add(gasUsage);
        return this;
    }

    public Subscriber addWaterUsage(WaterUsage waterUsage) {
        if (waterUsages == null) {
            waterUsages = new ArrayList<>();
        }
        waterUsages.add(waterUsage);
        return this;
    }

}
