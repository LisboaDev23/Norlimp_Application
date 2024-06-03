package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginRepository loginRepository;

    public Login saveLogin (Login login){
        if (!login.getUsername().isEmpty() && !login.getEmail().isEmpty()
        && !login.getPassword().isEmpty()){
            return loginRepository.save(login);
        } else {
            throw new RuntimeException("Dados inseridos incorretamente para realização do login!");
        }
    }

    public void deleteLogin (Login login){
        if (loginRepository.findById(login.getId()).isPresent()){
            loginRepository.delete(login);
        } else {
            throw new RuntimeException("Login não está cadastrado no banco de dados!");
        }
    }
}
