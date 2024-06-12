package com.gbLisboa.NorlimpApplication.domain.repository;

import com.gbLisboa.NorlimpApplication.domain.model.Adress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Long> {

    public Optional<Adress> findByRoad (String road);

    public Optional<Adress> findByNeighborhood(String neighborhood);

    public Optional<Adress> findByCity (String city);

    public Optional<Adress> findByState (String state);
}
