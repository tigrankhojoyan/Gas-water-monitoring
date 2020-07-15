package com.gas.water.monitoring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "water_usage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WaterUsage {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private Long clientId;

    @Column(nullable = false)
    private BigDecimal waterAmount;

    private LocalDateTime submittedTime;

    @Column(nullable = false)
    private WaterType waterType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Subscriber subscriber;

    @Override
    public String toString() {
        return "WaterUsage{" +
                "clientId=" + clientId +
                ", waterAmount=" + waterAmount +
                ", submittedTime=" + submittedTime +
                ", waterType=" + waterType +
                ", subscriber=" + subscriber.getClientId() +
                '}';
    }
}
