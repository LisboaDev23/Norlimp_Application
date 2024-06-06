package com.gbLisboa.NorlimpApplication.api.controller;

import com.gbLisboa.NorlimpApplication.api.model.UserModel;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.model.User;
import com.gbLisboa.NorlimpApplication.domain.repository.LoginRepository;
import com.gbLisboa.NorlimpApplication.domain.repository.UserRepository;
import com.gbLisboa.NorlimpApplication.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private UserService userService;
    private ModelMapper modelMapper;

    @GetMapping
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> findUser(@PathVariable Long userId){
        return userRepository.findById(userId)
                .map(user -> modelMapper.map(user, UserModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{loginId}")
    public User registerUser(@Valid @RequestBody User user){
        Login login = user.getLogin();
        return userService.saveUserWithoutLogin(user,login);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserModel> deleteUser(@PathVariable Long userId){
        if (!userRepository.existsById(userId)){
            return  ResponseEntity.noContent().build();
        }
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long userId,
                                                @Valid @RequestBody User user){
        if (!userRepository.existsById(userId)){
            return ResponseEntity.notFound().build();
        }
        user.setId(userId);
        Login login = user.getLogin();
        userService.saveUserWithoutLogin(user,login);
        return userRepository.findById(user.getId())
                .map(u -> modelMapper.map(u, UserModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
