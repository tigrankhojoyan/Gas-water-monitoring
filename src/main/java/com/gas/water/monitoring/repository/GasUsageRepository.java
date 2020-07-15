package com.gas.water.monitoring.repository;

import com.gas.water.monitoring.model.GasUsage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GasUsageRepository extends JpaRepository<GasUsage, Long> {
    Optional<GasUsage> findFirstBySubscriber_ClientIdOrderBySubmittedTimeDesc(Long subscriberId);
    Page<GasUsage> findAllBySubscriber_ClientId(Long subscriberId, Pageable pageable);
    List<GasUsage> findAllBySubscriber_ClientId(Long subscriberId);
}
