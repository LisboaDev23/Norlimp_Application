package com.gbLisboa.NorlimpApplication.domain.repository;

import com.gbLisboa.NorlimpApplication.domain.model.Adress;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    User findByCpf(String cpf);
    Optional<User> findByEmail(String email);
}
