package com.gas.water.monitoring.repository;

import com.gas.water.monitoring.model.WaterType;
import com.gas.water.monitoring.model.WaterUsage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WaterUsageRepository extends JpaRepository<WaterUsage, Long> {
    Optional<WaterUsage> findTopByWaterTypeAndSubscriberClientIdOrderBySubmittedTimeDesc(WaterType waterType, Long subscriberId);
    Page<WaterUsage> findAllByWaterTypeAndSubscriberClientId(WaterType waterType, Long subscriberId, Pageable pageable);
    List<WaterUsage> findAllBySubscriber_ClientId(Long subscriberId);
    List<WaterUsage> findAllBySubscriber_ClientIdAndWaterType(Long subscriberId, WaterType waterType);
}
