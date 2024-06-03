package com.gbLisboa.NorlimpApplication.domain.repository;

import com.gbLisboa.NorlimpApplication.domain.model.Service;
import com.gbLisboa.NorlimpApplication.domain.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByType(Type type);
    Optional<Service> findByNameService(String nameService);
}
