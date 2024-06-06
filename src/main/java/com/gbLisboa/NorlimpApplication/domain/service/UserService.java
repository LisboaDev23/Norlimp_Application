package com.gbLisboa.NorlimpApplication.domain.service;

import com.gbLisboa.NorlimpApplication.domain.exception.UserException;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private LoginService loginService;

    public User findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuário não encontrado no banco de dados."));
    }

    @Transactional
    public User saveUserWithoutLogin(User user, Login login){
        boolean cpfInUse = userRepository.findByCpf(user.getCpf())
                .isPresent();
        boolean emailInUse = userRepository.findByEmail(user.getEmail())
                .isPresent();
        if (cpfInUse || emailInUse){
            throw new UserException("Usuário com cpf ou email já cadastrado, tente novamente.");
        }
        user.setLogin(login);
        login.setUser(user);
        loginService.saveLogin(login);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId){
        boolean userIsPresent = userRepository.existsById(userId);
        if (!userIsPresent){
            throw new UserException("Não foi possível excluir o cliente pois tal não se encontra no banco de dados.");
        }
        userRepository.deleteById(userId);
    }


}
