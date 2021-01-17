package ru.mail.dtraider.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.mail.dtraider.crud.model.AuthGroup;
import ru.mail.dtraider.crud.model.User;
import ru.mail.dtraider.crud.service.UserService;

import java.util.LinkedList;
import java.util.List;

@RestController
@PreAuthorize(value = "hasRole('ADMIN')")
@RequestMapping("/rest")
public class RestApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable("id") long id) {
        final User user = userService.readUser(id);
        System.out.println(user);
        return user;
    }

    @DeleteMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) {

        List<AuthGroup> authGroupListNewUser = new LinkedList<>();

        AuthGroup authGroupUser = new AuthGroup();
        authGroupUser.setName(user.getEmail());
        authGroupUser.setAuthGroup("USER");
        authGroupListNewUser.add(authGroupUser);

        if (user.getAuthGroupList().size() > 1) {
            AuthGroup authGroupAdmin = new AuthGroup();
            authGroupAdmin.setName(user.getEmail());
            authGroupAdmin.setAuthGroup("ADMIN");
            authGroupListNewUser.add(authGroupAdmin);
        }

        user.setAuthGroupList(authGroupListNewUser);

        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        userService.createUser(user);
        return ResponseEntity.ok().body(user);
    }


    @PutMapping("/user")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        userService.updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getAge(), user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()), user.getAuthGroupList());
        return ResponseEntity.ok().body(user);
    }
}
