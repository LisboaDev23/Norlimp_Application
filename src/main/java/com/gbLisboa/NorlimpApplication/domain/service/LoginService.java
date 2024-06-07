package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.exception.LoginException;
import com.gbLisboa.NorlimpApplication.domain.exception.UserException;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.LoginRepository;
import com.gbLisboa.NorlimpApplication.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class LoginService {

    private LoginRepository loginRepository;
    private UserService userService;
    private UserRepository userRepository;


    public Login findLogin(Long loginId){
        return loginRepository.findById(loginId)
                .orElseThrow(() -> new LoginException("Login com id fornecido não encontrado."));
    }
    @Transactional
    public Login saveLogin (Login login){
        boolean emailInUse = loginRepository.findByEmail(login.getEmail())
                .filter(l ->!l.equals(login))
                .isPresent();
        if (emailInUse){
            throw new LoginException("Não foi possível cadastrar um novo login com esse email no banco de dados pois o mesmo já está cadastrado.");
        }
        return loginRepository.save(login);
    }

    public User findUserByLogin (Login login){
        boolean loginExist = loginRepository.existsById(login.getId());
        if (!loginExist){
            throw new LoginException("Login inválido, logo não foi possível encontrar o usuário através do login.");
        }
        return findLogin(login.getId()).getUser();
    }

    public Login findLoginByUser (User user){
        boolean userExist = userRepository.existsById(user.getId());
        if (!userExist){
            throw new UserException("Usuário não encontrado no banco de dados!");
        }
        return userService.findUser(user.getId()).getLogin();
    }

}
