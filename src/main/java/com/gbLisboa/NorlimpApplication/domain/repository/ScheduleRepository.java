package com.gbLisboa.NorlimpApplication.domain.repository;

import com.gbLisboa.NorlimpApplication.domain.model.Payment;
import com.gbLisboa.NorlimpApplication.domain.model.Schedule;
import com.gbLisboa.NorlimpApplication.domain.model.Service;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByPayment(Payment payment);
    List<Schedule> findSchedulesByDates(List<Date> dates);
    List<Schedule> findSchedulesByUser(User user);
}
