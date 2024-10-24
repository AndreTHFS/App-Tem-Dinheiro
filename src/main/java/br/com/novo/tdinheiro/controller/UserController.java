package br.com.novo.tdinheiro.controller;

import br.com.novo.tdinheiro.dto.CreateUserDto;
import br.com.novo.tdinheiro.dto.LoginRequest;
import br.com.novo.tdinheiro.dto.LoginResponse;
import br.com.novo.tdinheiro.dto.UpdateUserDto;
import br.com.novo.tdinheiro.entity.User;
import br.com.novo.tdinheiro.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
            var userToken = userService.login(loginRequest);
            return ResponseEntity.ok(userToken);
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto userDto)  {
            var user=  userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);


    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findById(@PathVariable(value="userId") UUID userId){
        var user = userService.findById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll(){
        if (!userService.findAll().isEmpty()){
            return ResponseEntity.ok().body(userService.findAll());
        }else return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "userId") UUID userId, @RequestBody UpdateUserDto userDto){
          userService.updateUser(userId, userDto);
          return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deleteUserById(@PathVariable(value = "userId") String id){
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataViolation(DataIntegrityViolationException e){
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleAuthenticationException(RuntimeException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }
}
