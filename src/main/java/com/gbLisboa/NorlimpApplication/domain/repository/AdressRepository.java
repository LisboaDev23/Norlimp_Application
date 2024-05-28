package com.gbLisboa.NorlimpApplication.domain.repository;

import com.gbLisboa.NorlimpApplication.domain.model.Adress;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdressRepository extends JpaRepository<Adress, Long> {
    List<Adress> findManyAdressByUser (User user);
}
