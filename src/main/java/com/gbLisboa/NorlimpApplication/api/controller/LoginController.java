package com.gbLisboa.NorlimpApplication.api.controller;

import com.gbLisboa.NorlimpApplication.api.model.LoginModel;
import com.gbLisboa.NorlimpApplication.domain.exception.LoginException;
import com.gbLisboa.NorlimpApplication.domain.model.Login;
import com.gbLisboa.NorlimpApplication.domain.repository.LoginRepository;
import com.gbLisboa.NorlimpApplication.domain.service.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    private LoginRepository loginRepository;
    private LoginService loginService;

    @GetMapping("/list")
    public List<LoginModel> findAll (){
        return loginService.findAllLogins();
    }
    @GetMapping("/{loginId}")
    public ResponseEntity<LoginModel> find (@PathVariable Long loginId){
        if (!loginRepository.existsById(loginId)){
            return ResponseEntity.notFound().build();
        }
        LoginModel loginFound = loginService.findLogin(loginId);
        return ResponseEntity.ok(loginFound);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/create")
    public ResponseEntity<LoginModel> register (@PathVariable Long userId,@Valid @RequestBody LoginModel loginModel){
        LoginModel loginCreated = loginService.saveLogin(userId, loginModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginCreated);
    }
    @DeleteMapping("/delete/{loginId}")
    public ResponseEntity<Void> delete (@PathVariable Long loginId){
        if (!loginRepository.existsById(loginId)){
            return ResponseEntity.notFound().build();
        }
        loginService.deleteLogin(loginId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/update/{loginId}")
    public ResponseEntity<LoginModel> update (@PathVariable Long userId,@PathVariable Long loginId,
                                              @Valid @RequestBody LoginModel loginModel){
        if (!loginRepository.existsById(loginId)){
            return ResponseEntity.notFound().build();
        }
        loginRepository.findById(loginId)
                .orElseThrow(() -> new LoginException("Login não encontrado!"))
                .setId(loginId);
        LoginModel loginUpdate = loginService.saveLogin(userId, loginModel);
        return ResponseEntity.ok(loginUpdate);
    }

}
