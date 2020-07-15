package com.gas.water.monitoring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "gas_usage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GasUsage {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long clientId;

    @Column(nullable = false)
    private BigDecimal usedGasContent;

    @Column(nullable = false)
    private LocalDateTime submittedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Subscriber subscriber;

    @Override
    public String toString() {
        return "GasUsage{" +
                "clientId=" + clientId +
                ", usedGasContent=" + usedGasContent +
                ", submittedTime=" + submittedTime +
                ", subscriber=" + subscriber.getClientId() +
                '}';
    }
}
