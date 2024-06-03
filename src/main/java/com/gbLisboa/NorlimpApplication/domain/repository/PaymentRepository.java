package com.gbLisboa.NorlimpApplication.domain.repository;

import com.gbLisboa.NorlimpApplication.domain.model.Payment;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByValue(Double value);
}
