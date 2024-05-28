package com.gbLisboa.NorlimpApplication.domain.repository;

import com.gbLisboa.NorlimpApplication.domain.model.Service;
import com.gbLisboa.NorlimpApplication.domain.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Optional<Type> findByName(String nameType);
    Optional<Type> findByService (Service service);
}
