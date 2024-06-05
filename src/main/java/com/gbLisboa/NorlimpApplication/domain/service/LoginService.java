package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.exception.LoginException;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.LoginRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class LoginService {

    private LoginRepository loginRepository;
    private UserService userService;

    public Login findLogin(Long loginId){
        return loginRepository.findById(loginId)
                .orElseThrow(() -> new LoginException("Login com id fornecido não encontrado."));
    }
    @Transactional
    public Login saveLogin (Login login){
        boolean emailInUse = loginRepository.findByEmail(login.getEmail())
                .filter(l ->!l.equals(login))
                .isPresent();
        if (emailInUse ){
            throw new LoginException("Não foi possível cadastrar um novo login com esse email no banco de dados pois o mesmo já está cadastrado.");
        }
        User user = userService.findUser(login.getUser().getId());
        login.setUser(user);
        return loginRepository.save(login);
    }

    @Transactional
    public void deleteLoginById (Long loginId){
        boolean loginExist = loginRepository.existsById(loginId);
        if (!loginExist){
            throw new LoginException("Não existe login criado no banco de dados com o id fornecido, portanto não é possível excluí-lo");
        }
        loginRepository.deleteById(loginId);
    }

    public User findUserByLogin (Login login){
        boolean loginExist = loginRepository.existsById(login.getId());
        if (!loginExist){
            throw new LoginException("Login inválido, logo não foi possível encontrar o usuário através do login.");
        }
        return findLogin(login.getId()).getUser();
    }

}
