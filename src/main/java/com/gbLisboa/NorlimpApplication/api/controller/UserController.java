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
    private LoginRepository loginRepository;

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
    @PostMapping
    public User registerUser(@Valid @RequestBody User user){
        return userService.saveUserWithoutLogin(user);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserModel> deleteUser(@PathVariable Long userId){
        if (!userRepository.existsById(userId)){
            return  ResponseEntity.noContent().build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long userId,
                                                @Valid @RequestBody User user){
        if (!userRepository.existsById(userId)){
            return ResponseEntity.notFound().build();
        }
        user.setId(userId);
        userService.saveUserWithoutLogin(user);
        return userRepository.findById(user.getId())
                .map(u -> modelMapper.map(u, UserModel.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
