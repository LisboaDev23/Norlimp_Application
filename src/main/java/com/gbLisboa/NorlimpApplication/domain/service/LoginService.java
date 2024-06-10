package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.api.model.LoginModel;
import com.gbLisboa.NorlimpApplication.domain.exception.LoginException;
import com.gbLisboa.NorlimpApplication.domain.exception.UserException;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.LoginRepository;
import com.gbLisboa.NorlimpApplication.domain.repository.UserRepository;
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
    private UserRepository userRepository;
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
    public LoginModel saveLogin (Long userId,LoginModel loginModel){
        if (loginRepository.findByEmail(loginModel.getEmail()).isPresent()){
            throw new LoginException("Email já cadastrado, tente novamente!");
        } else if (loginRepository.findByUsername(loginModel.getUsername()).isPresent()) {
            throw new LoginException("Nome de usuário já cadastrado, tente novamente!");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuário não encontrado!"));
        Login login = new Login();
        login.setEmail(loginModel.getEmail());
        login.setUsername(loginModel.getUsername());
        login.setPassword(loginModel.getPassword());
        login.setUser(user);
        loginRepository.save(login);
        loginModel.setId(login.getId());
        loginModel.setUser(userId);
        return modelMapper.map(login, LoginModel.class);
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


    private LoginModel toLoginModel(Login login){
        return modelMapper.map(login, LoginModel.class);
    }

}
