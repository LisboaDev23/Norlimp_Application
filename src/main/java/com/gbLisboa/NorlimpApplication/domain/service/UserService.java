package com.gbLisboa.NorlimpApplication.domain.service;

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
public class UserService {

    private UserRepository userRepository;
    private LoginRepository loginRepository;

    public User findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuário não encontrado no banco de dados."));
    }

    @Transactional
    public User saveUserWithoutLogin(User user){
        boolean cpfInUse = userRepository.findByCpf(user.getCpf())
                .isPresent();
        boolean emailInUse = userRepository.findByEmail(user.getEmail())
                .isPresent();
        if (cpfInUse || emailInUse){
            throw new UserException("Usuário com cpf ou email já cadastrado, tente novamente.");
        }
        return userRepository.save(user);
    }


}
