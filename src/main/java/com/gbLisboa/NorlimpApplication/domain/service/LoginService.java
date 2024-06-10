package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.api.model.LoginModel;
import com.gbLisboa.NorlimpApplication.domain.exception.LoginException;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.repository.LoginRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LoginService {

    private LoginRepository loginRepository;
    private ModelMapper modelMapper;

    public List<LoginModel> findAllLogins (){
        return loginRepository.findAll()
                .stream()
                .map(this::toLoginModel)
                .collect(Collectors.toList());
    }

    public LoginModel findLogin(Long loginId){
        return loginRepository.findById(loginId)
                .map(this::toLoginModel)
                .orElseThrow(() -> new LoginException("Login não encontrado"));
    }
    @Transactional
    public LoginModel saveLogin (Login login){
        boolean emailInUse = loginRepository.findByEmail(login.getEmail())
                .filter(l ->!l.equals(login))
                .isPresent();
        if (emailInUse){
            throw new LoginException("Não foi possível cadastrar um novo login com esse email no banco de dados pois o mesmo já está cadastrado.");
        }
        Login loginCreated = loginRepository.save(login);
        return modelMapper.map(loginCreated, LoginModel.class);
    }
    @Transactional
    public void deleteLogin (Long loginId){
        try{
            loginRepository.deleteById(loginId);
        } catch (RuntimeException e){
            if (!loginRepository.existsById(loginId)){
                throw new LoginException("Login não encontrado!");
            }
            throw new LoginException("Login não deletado!");
        }
    }
    @Transactional
    public LoginModel updateLogin(Long loginId,
                                 Login login){
        LoginModel loginModel = loginRepository.findById(loginId)
                .map(l -> modelMapper.map(l, LoginModel.class))
                .orElseThrow(() -> new LoginException("Login não encontrado!"));
        loginRepository.save(login);
        return loginModel;
    }
    private LoginModel toLoginModel(Login login){
        return modelMapper.map(login, LoginModel.class);
    }

}
